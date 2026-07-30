import { CommonModule } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AutocarejobService } from '../autocarejob/service/autocarejob.service';
import { SalesInvoiceDummyService } from '../sales-invoice-dummy/service/sales-invoice-dummy.service';
import { TaxesService } from '../taxes/service/taxes.service';

const VAT_TAX_ID = 1002;

@Component({
  selector: 'jhi-printinvoice',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './printinvoice.component.html',
})
export class PrintinvoiceComponent implements OnInit {
  salesInvoice: any = null;
  invoiceLines: any[] = [];
  serviceLines: any[] = [];
  commonServiceLines: any[] = [];
  vatPercentage: number | null = null;

  protected salesInvoiceDummyService = inject(SalesInvoiceDummyService);
  protected taxesService = inject(TaxesService);
  protected autocarejobService = inject(AutocarejobService);

  private expectedRequests = 4;
  private completedRequests = 0;

  constructor(private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      const id = params['id'];

      if (id) {
        const numericId = Number(id);
        this.getSalesInvoice(numericId);
        this.getSalesInvoicelines(numericId);
        this.getSalesServicelines(numericId);
        this.getSalesSercolines(numericId);
      }
    });
  }

  get isVatInvoice(): boolean {
    return !!this.salesInvoice?.isvatinvoice;
  }

  getServicePrice(line: any): number {
    return Number(line?.servicePrice ?? line?.serviceprice ?? 0);
  }

  getInventoryLineTotalBeforeDiscount(line: any): number {
    return Number((Number(line?.quantity ?? 0) * Number(line?.sellingprice ?? 0)).toFixed(2));
  }

  get lineItemsSubtotal(): number {
    const inventoryTotal = this.invoiceLines.reduce((sum, line) => sum + this.getInventoryLineTotalBeforeDiscount(line), 0);
    const serviceTotal = this.serviceLines.reduce((sum, line) => sum + this.getServicePrice(line), 0);
    const commonTotal = this.commonServiceLines.reduce((sum, line) => sum + this.getServicePrice(line), 0);
    return inventoryTotal + serviceTotal + commonTotal;
  }

  get displaySubtotal(): number {
    const subtotal = this.lineItemsSubtotal;
    if (!this.isVatInvoice || this.vatPercentage == null) {
      return subtotal;
    }
    return (subtotal * 100) / (100 + this.vatPercentage);
  }

  get displayVatAmount(): number {
    if (!this.isVatInvoice || this.vatPercentage == null) {
      return this.salesInvoice?.vatamount ?? 0;
    }
    return this.displaySubtotal * (this.vatPercentage / 100);
  }

  get vatLabel(): string {
    if (!this.isVatInvoice || this.vatPercentage == null) {
      return '';
    }
    return `(${this.vatPercentage}%)`;
  }

  getSalesInvoice(id: number): void {
    this.salesInvoiceDummyService.find(id).subscribe({
      next: response => {
        this.salesInvoice = response.body;
        if (this.salesInvoice?.autocarejobid && (!this.salesInvoice.currentmeter || !this.salesInvoice.nextmeter)) {
          this.expectedRequests++;
          this.loadAutocareJobMeters(Number(this.salesInvoice.autocarejobid));
        }
        if (this.salesInvoice?.isvatinvoice) {
          this.expectedRequests++;
          this.loadVatTaxPercentage();
        }
        this.checkAndPrint();
      },
      error: err => {
        console.error('Error fetching Sales Invoice:', err);
        this.checkAndPrint();
      },
    });
  }

  private loadAutocareJobMeters(jobId: number): void {
    this.autocarejobService.find(jobId).subscribe({
      next: response => {
        const job = response.body;
        this.salesInvoice = {
          ...this.salesInvoice,
          currentmeter: this.salesInvoice?.currentmeter || job?.millage,
          nextmeter: this.salesInvoice?.nextmeter || job?.nextmillage,
          vehicleno: this.salesInvoice?.vehicleno || job?.vehiclenumber,
        };
        this.checkAndPrint();
      },
      error: err => {
        console.error('Error fetching Autocare Job meters:', err);
        this.checkAndPrint();
      },
    });
  }

  private loadVatTaxPercentage(): void {
    this.taxesService.find(VAT_TAX_ID).subscribe({
      next: response => {
        const tax = response.body;
        if (tax?.isactive && tax.percentage != null) {
          this.vatPercentage = tax.percentage;
        }
        this.checkAndPrint();
      },
      error: err => {
        console.error('Error fetching VAT tax rate:', err);
        this.checkAndPrint();
      },
    });
  }

  getSalesInvoicelines(id: number): void {
    this.salesInvoiceDummyService.fetchInvoiceLines(id).subscribe({
      next: response => {
        this.invoiceLines = response.body || [];
        this.checkAndPrint();
      },
      error: err => {
        console.error('Error fetching Sales Invoice lines:', err);
        this.checkAndPrint();
      },
    });
  }

  getSalesServicelines(id: number): void {
    this.salesInvoiceDummyService.fetchService(id).subscribe({
      next: response => {
        this.serviceLines = response.body || [];
        this.checkAndPrint();
      },
      error: err => {
        console.error('Error fetching Sales Service lines:', err);
        this.checkAndPrint();
      },
    });
  }

  getSalesSercolines(id: number): void {
    this.salesInvoiceDummyService.fetchServiceCommon(id).subscribe({
      next: response => {
        this.commonServiceLines = response.body || [];
        this.checkAndPrint();
      },
      error: err => {
        console.error('Error fetching Sales Service common lines:', err);
        this.checkAndPrint();
      },
    });
  }

  private checkAndPrint(): void {
    this.completedRequests++;
    if (this.completedRequests === this.expectedRequests) {
      setTimeout(() => {
        window.print();
      }, 1000);
    }
  }
}
