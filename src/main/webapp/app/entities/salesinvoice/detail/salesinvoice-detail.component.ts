import { Component, input, inject, OnInit, signal } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { ISalesinvoice } from '../salesinvoice.model';
import { SalesInvoiceLinesService } from 'app/entities/sales-invoice-lines/service/sales-invoice-lines.service';
import { SalesInvoiceServiceChargeLineService } from 'app/entities/sales-invoice-service-charge-line/service/sales-invoice-service-charge-line.service';
import { SaleInvoiceCommonServiceChargeService } from 'app/entities/sale-invoice-common-service-charge/service/sale-invoice-common-service-charge.service';
import { SalesinvoiceService } from '../service/salesinvoice.service';

@Component({
  standalone: true,
  selector: 'jhi-salesinvoice-detail',
  templateUrl: './salesinvoice-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class SalesinvoiceDetailComponent implements OnInit {
  salesinvoice = input<ISalesinvoice | null>(null);

  invoiceLines = signal<any[]>([]);
  serviceCharges = signal<any[]>([]);
  commonCharges = signal<any>([]);

  protected salesInvoiceLinesService = inject(SalesInvoiceLinesService);
  protected serviceChargeService = inject(SalesInvoiceServiceChargeLineService);
  protected commonChargeService = inject(SaleInvoiceCommonServiceChargeService);
  protected salesinvoiceService = inject(SalesinvoiceService);

  ngOnInit(): void {
    const invoice = this.salesinvoice();
    if (invoice?.id) {
      this.loadDetails(invoice.id);
    }
  }

  loadDetails(id: number): void {
    const invoice = this.salesinvoice();
    this.salesInvoiceLinesService.queryByInvoiceId(id).subscribe(res => {
      this.invoiceLines.set(res.body || []);
    });

    this.serviceChargeService.queryByInvoiceId(id).subscribe(res => {
      this.serviceCharges.set(res.body || []);
    });

    this.commonChargeService.queryByInvoiceId(id).subscribe(res => {
      const savedCharges = res.body || [];
      if (savedCharges.length === 0 && invoice?.orderid) {
        // Fallback to fetching from the original job invoice if no specific common charges were saved for this sales invoice
        this.salesinvoiceService.fetchServiceCommon(invoice.orderid).subscribe(originalRes => {
          this.commonCharges.set(originalRes.body || []);
        });
      } else {
        this.commonCharges.set(savedCharges);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }
  printInvoice(): void {
    const id = this.salesinvoice()?.id;
    if (id) {
      window.open('/printinvoice?id=' + id, '_blank');
    }
  }

  getServiceChargeDiscountAmount(service: any): number {
    return Number(service.discount ?? 0);
  }

  getServiceChargeBasePrice(service: any): number {
    const servicePrice = Number(service.servicePrice ?? service.serviceprice ?? 0);
    if (servicePrice > 0) {
      return servicePrice;
    }
    const discount = this.getServiceChargeDiscountAmount(service);
    const value = Number(service.value ?? 0);
    return Number((value + discount).toFixed(2));
  }

  getServiceChargeDiscountPercentage(service: any): string {
    const discount = this.getServiceChargeDiscountAmount(service);
    const basePrice = this.getServiceChargeBasePrice(service);
    const entryType = this.parseServiceChargeDiscountEntryType(service);
    if (discount <= 0) {
      return '-';
    }
    if (entryType === 'value') {
      return '-';
    }
    if (basePrice <= 0) {
      return '-';
    }
    return `${((discount / basePrice) * 100).toFixed(2)}%`;
  }

  getServiceChargeDiscountValue(service: any): string {
    const discount = this.getServiceChargeDiscountAmount(service);
    const entryType = this.parseServiceChargeDiscountEntryType(service);
    if (discount <= 0) {
      return '-';
    }
    if (entryType === 'percentage') {
      return '-';
    }
    return discount.toFixed(2);
  }

  private parseServiceChargeDiscountEntryType(service: any): 'percentage' | 'value' | undefined {
    const description = service.serviceDescription ?? service.servicediscription;
    if (!description) {
      return undefined;
    }
    if (description.startsWith('[PCT]')) {
      return 'percentage';
    }
    if (description.startsWith('[VAL]')) {
      return 'value';
    }
    return undefined;
  }
}
