import { Component, inject, OnInit, ViewChild } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';
import dayjs from 'dayjs'; // Import Dayjs
import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { IAutocarejob } from '../autocarejob.model';
import { IAutojobsinvoice } from 'app/entities/autojobsinvoice/autojobsinvoice.model';
import { ICustomervehicle } from 'app/entities/customervehicle/customervehicle.model';
import { CustomervehicleService } from 'app/entities/customervehicle/service/customervehicle.service';
import { CustomerService } from 'app/entities/customer/service/customer.service';
import { ICustomer } from 'app/entities/customer/customer.model';
import { AutocareappointmentService } from 'app/entities/autocareappointment/service/autocareappointment.service';
import { IAutocareappointment } from 'app/entities/autocareappointment/autocareappointment.model';
import { AutocarejobService } from '../service/autocarejob.service';

import { AutocarejobFormService, AutocarejobFormGroup } from '../update/autocarejob-form.service';
import { AutojobsinvoiceUpdateComponent } from 'app/entities/autojobsinvoice/update/autojobsinvoice-update.component';
import { AutojobsinvoiceService } from 'app/entities/autojobsinvoice/service/autojobsinvoice.service';
import { AutojobsinvoicelinesService } from 'app/entities/autojobsinvoicelines/service/autojobsinvoicelines.service';
import { AutojobsalesinvoiceservicechargelineComponent } from 'app/entities/autojobsalesinvoiceservicechargeline/list/autojobsalesinvoiceservicechargeline.component';
import { AutojobsalesinvoiceservicechargelineService } from 'app/entities/autojobsalesinvoiceservicechargeline/service/autojobsalesinvoiceservicechargeline.service';
import { AutojobsaleinvoicecommonservicechargeComponent } from 'app/entities/autojobsaleinvoicecommonservicecharge/list/autojobsaleinvoicecommonservicecharge.component';
import { AutojobsaleinvoicecommonservicechargeService } from 'app/entities/autojobsaleinvoicecommonservicecharge/service/autojobsaleinvoicecommonservicecharge.service';
import { ISalesinvoice } from 'app/entities/salesinvoice/salesinvoice.model';
import { SalesinvoiceService } from 'app/entities/salesinvoice/service/salesinvoice.service';
import { SalesInvoiceServiceChargeLineService } from 'app/entities/sales-invoice-service-charge-line/service/sales-invoice-service-charge-line.service';
import { SaleInvoiceCommonServiceChargeService } from 'app/entities/sale-invoice-common-service-charge/service/sale-invoice-common-service-charge.service';
import { SalesInvoiceLinesService } from 'app/entities/sales-invoice-lines/service/sales-invoice-lines.service';

@Component({
  standalone: true,
  selector: 'jhi-autocarejob-history',
  templateUrl: './autocarejob-history.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule, AutojobsinvoiceUpdateComponent],
})
export class AutocarejobhistoryComponent implements OnInit {
  isSaving = false;
  autocarejob: IAutocarejob | null = null;
  salesinvoice: ISalesinvoice | null = null;
  autocarejobsinvoices: IAutojobsinvoice[] = [];
  customervehicles: ICustomervehicle[] = [];
  customerDetails: any | null = null;
  autocareappointments: IAutocareappointment[] = [];
  protected autocarejobService = inject(AutocarejobService);
  protected autocarejobFormService = inject(AutocarejobFormService);
  autojobsinvoiceService = inject(AutojobsinvoiceService);
  autojobsinvoicelinesService = inject(AutojobsinvoicelinesService);
  autojobsservicelinesService = inject(AutojobsalesinvoiceservicechargelineService);
  autojobsalescommonService = inject(AutojobsaleinvoicecommonservicechargeService);
  salesinvoiceService = inject(SalesinvoiceService);
  salesinvoiceservicechargelineService = inject(SalesInvoiceServiceChargeLineService);
  salesinvoicecommonservicechargeService = inject(SaleInvoiceCommonServiceChargeService);
  salesinvoicelineService = inject(SalesInvoiceLinesService);
  protected activatedRoute = inject(ActivatedRoute);
  protected customervehicleService = inject(CustomervehicleService);
  protected customerService = inject(CustomerService);
  protected autocareappointmentService = inject(AutocareappointmentService);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: AutocarejobFormGroup = this.autocarejobFormService.createAutocarejobFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ autocarejob }) => {
      this.autocarejob = autocarejob;

      if (autocarejob) {
        console.log(this.autocarejob?.customername);
        this.updateForm(autocarejob);
      }
      this.fetchhistory();
      this.fetchinvoicehistory();
    });
  }

  previousState(): void {
    window.history.back();
  }

  filteredVehicles: IAutocareappointment[] = [];

  onVehicleSearch(event: Event): void {
    const input = event.target as HTMLInputElement;
    const searchTerm = input.value;

    if (searchTerm.length > 2) {
      // Use the new service method to fetch matching results
      this.autocareappointmentService.findByVehicleNumber(searchTerm).subscribe(response => {
        this.filteredVehicles = response.body || [];
      });
    } else {
      // Clear the suggestions if input is too short
      this.filteredVehicles = [];
    }
  }

  get invoiceIds(): number[] {
    return Object.keys(this.autojobsInvoicesMap).map(id => Number(id));
  }

  autojobsInvoicesMap: {
    [key: number]: {
      invoice: IAutojobsinvoice;
      invoiceLines: any[];
      serviceLines: any[];
      commonServiceCharges: any[];
    };
  } = {};

  fetchhistory(): void {
    this.autojobsinvoiceService
      .query({ 'customername.contains': this.autocarejob?.customername })
      .subscribe((res: HttpResponse<IAutojobsinvoice[]>) => {
        if (res.body && res.body.length > 0) {
          res.body.forEach(invoice => {
            const invoiceId = invoice.id!;
            this.autojobsInvoicesMap[invoiceId] = {
              invoice,
              invoiceLines: [],
              serviceLines: [],
              commonServiceCharges: [],
            };

            // Fetch Invoice Lines
            this.autojobsinvoicelinesService.queryByInvoiceId(invoiceId).subscribe((linesRes: HttpResponse<any[]>) => {
              this.autojobsInvoicesMap[invoiceId].invoiceLines = linesRes.body || [];
            });

            // Fetch Service Charges
            this.autojobsservicelinesService.queryByInvoiceId(invoiceId).subscribe((servicesRes: HttpResponse<any[]>) => {
              this.autojobsInvoicesMap[invoiceId].serviceLines = servicesRes.body || [];
            });

            // Fetch Common Service Charges
            this.autojobsalescommonService.queryByInvoiceId(invoiceId).subscribe((chargesRes: HttpResponse<any[]>) => {
              this.autojobsInvoicesMap[invoiceId].commonServiceCharges = chargesRes.body || [];
            });
          });
        }
      });
  }

  get invoiceId(): number[] {
    return Object.keys(this.salesInvoicesMap).map(id => Number(id));
  }

  salesInvoicesMap: {
    [key: number]: {
      invoice: ISalesinvoice;
      invoiceLines: any[];
      serviceLines: any[];
      commonServiceCharges: any[];
    };
  } = {};

  fetchinvoicehistory(): void {
    this.salesinvoiceService
      .query({ 'customername.contains': this.autocarejob?.customername })
      .subscribe((res: HttpResponse<ISalesinvoice[]>) => {
        if (res.body && res.body.length > 0) {
          res.body.forEach(invoice => {
            const invoiceId = invoice.id!;
            this.salesInvoicesMap[invoiceId] = {
              invoice,
              invoiceLines: [],
              serviceLines: [],
              commonServiceCharges: [],
            };

            // Fetch Invoice Lines
            this.salesinvoicelineService.queryByInvoiceId(invoiceId).subscribe((linesRes: HttpResponse<any[]>) => {
              this.salesInvoicesMap[invoiceId].invoiceLines = linesRes.body || [];
              console.log(`Invoice Lines for ID ${invoiceId}:`, linesRes.body);
            });

            // Fetch Service Charges
            this.salesinvoiceservicechargelineService.queryByInvoiceId(invoiceId).subscribe((servicesRes: HttpResponse<any[]>) => {
              this.salesInvoicesMap[invoiceId].serviceLines = servicesRes.body || [];
              console.log(`Service Lines for ID ${invoiceId}:`, servicesRes.body);
            });

            // Fetch Common Service Charges
            this.salesinvoicecommonservicechargeService.queryByInvoiceId(invoiceId).subscribe((chargesRes: HttpResponse<any[]>) => {
              this.salesInvoicesMap[invoiceId].commonServiceCharges = chargesRes.body || [];
            });
          });
        }
      });
  }

  searchedCustomer: ICustomer | null = null;

  save(): void {
    this.isSaving = true;
    const autocarejob = this.autocarejobFormService.getAutocarejob(this.editForm);
    autocarejob.jobtypeid = this.editForm.get('jobtypeid')?.value;
    autocarejob.vehicleid = this.editForm.get('vehicleid')?.value;
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }
  printSection(sectionId: string): void {
    const printContents = document.getElementById(sectionId)?.innerHTML;
    if (!printContents) {
      return;
    }

    const originalContents = document.body.innerHTML;

    document.body.innerHTML = `
    <html>
      <head>
        <title>Print</title>
        <style>
          body {
            font-family: Arial, sans-serif;
            padding: 20px;
          }
          table {
            width: 100%;
            border-collapse: collapse;
          }
          table, th, td {
            border: 1px solid #000;
          }
          th, td {
            padding: 6px;
            font-size: 12px;
          }
          h6, h5 {
            margin-top: 15px;
          }
        </style>
      </head>
      <body>
        ${printContents}
      </body>
    </html>
  `;

    window.print();
    document.body.innerHTML = originalContents;
    window.location.reload(); // restore Angular state
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(autocarejob: IAutocarejob): void {
    this.autocarejob = autocarejob;
    this.autocarejobFormService.resetForm(this.editForm, autocarejob);
  }
}
