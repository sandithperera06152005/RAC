import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { forkJoin, of } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';
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
import { AutojobsinvoiceService } from 'app/entities/autojobsinvoice/service/autojobsinvoice.service';
import { AutojobsinvoicelinesService } from 'app/entities/autojobsinvoicelines/service/autojobsinvoicelines.service';
import { AutojobsalesinvoiceservicechargelineService } from 'app/entities/autojobsalesinvoiceservicechargeline/service/autojobsalesinvoiceservicechargeline.service';
import { AutojobsaleinvoicecommonservicechargeService } from 'app/entities/autojobsaleinvoicecommonservicecharge/service/autojobsaleinvoicecommonservicecharge.service';
import { ISalesinvoice } from 'app/entities/salesinvoice/salesinvoice.model';
import { SalesinvoiceService } from 'app/entities/salesinvoice/service/salesinvoice.service';
import { SalesInvoiceServiceChargeLineService } from 'app/entities/sales-invoice-service-charge-line/service/sales-invoice-service-charge-line.service';
import { SaleInvoiceCommonServiceChargeService } from 'app/entities/sale-invoice-common-service-charge/service/sale-invoice-common-service-charge.service';
import { SalesInvoiceLinesService } from 'app/entities/sales-invoice-lines/service/sales-invoice-lines.service';

export interface JobHistoryDetail {
  job: IAutocarejob;
  invoice: IAutojobsinvoice | null;
  invoiceLines: any[];
  serviceLines: any[];
  commonServiceCharges: any[];
}

@Component({
  standalone: true,
  selector: 'jhi-autocarejob-history',
  templateUrl: './autocarejob-history.component.html',
  styleUrl: './autocarejob-history.component.scss',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class AutocarejobhistoryComponent implements OnInit {
  isSaving = false;
  isLoadingJobDetail = false;
  showJobDetailModal = false;
  autocarejob: IAutocarejob | null = null;
  salesinvoice: ISalesinvoice | null = null;
  autocarejobsinvoices: IAutojobsinvoice[] = [];
  customervehicles: ICustomervehicle[] = [];
  customerDetails: any | null = null;
  autocareappointments: IAutocareappointment[] = [];
  jobHistoryList: IAutocarejob[] = [];
  jobHistoryPageSize = 10;
  selectedJobDetail: JobHistoryDetail | null = null;

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
        this.updateForm(autocarejob);
      }
      this.fetchJobWiseHistory();
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
      this.autocareappointmentService.findByVehicleNumber(searchTerm).subscribe(response => {
        this.filteredVehicles = response.body || [];
      });
    } else {
      this.filteredVehicles = [];
    }
  }

  get invoiceIds(): number[] {
    return Object.keys(this.autojobsInvoicesMap).map(id => Number(id));
  }

  get pagedJobHistory(): IAutocarejob[] {
    return this.jobHistoryList.slice(0, this.jobHistoryPageSize);
  }

  autojobsInvoicesMap: {
    [key: number]: {
      invoice: IAutojobsinvoice;
      invoiceLines: any[];
      serviceLines: any[];
      commonServiceCharges: any[];
    };
  } = {};

  fetchJobWiseHistory(): void {
    const vehicleNumber = this.autocarejob?.vehiclenumber;
    if (!vehicleNumber) {
      this.jobHistoryList = [];
      return;
    }

    this.autocarejobService.findByVehicleNumber(vehicleNumber).subscribe((res: HttpResponse<IAutocarejob[]>) => {
      const jobs = res.body || [];
      this.jobHistoryList = jobs.sort((a, b) => {
        const dateA = a.jobdate ? dayjs(a.jobdate).valueOf() : 0;
        const dateB = b.jobdate ? dayjs(b.jobdate).valueOf() : 0;
        return dateB - dateA;
      });
    });
  }

  /** Kept for any remaining references to invoice-map based job data. */
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

            this.autojobsinvoicelinesService.queryByInvoiceId(invoiceId).subscribe((linesRes: HttpResponse<any[]>) => {
              this.autojobsInvoicesMap[invoiceId].invoiceLines = linesRes.body || [];
            });

            this.autojobsservicelinesService.queryByInvoiceId(invoiceId).subscribe((servicesRes: HttpResponse<any[]>) => {
              this.autojobsInvoicesMap[invoiceId].serviceLines = servicesRes.body || [];
            });

            this.autojobsalescommonService.queryByInvoiceId(invoiceId).subscribe((chargesRes: HttpResponse<any[]>) => {
              this.autojobsInvoicesMap[invoiceId].commonServiceCharges = chargesRes.body || [];
            });
          });
        }
      });
  }

  openJobDetail(job: IAutocarejob): void {
    this.isLoadingJobDetail = true;
    this.showJobDetailModal = true;
    this.selectedJobDetail = {
      job,
      invoice: null,
      invoiceLines: [],
      serviceLines: [],
      commonServiceCharges: [],
    };

    this.autojobsinvoiceService.query({ 'jobid.equals': job.id, page: 0, size: 100 }).subscribe({
      next: (invoiceRes: HttpResponse<IAutojobsinvoice[]>) => {
        const invoices = (invoiceRes.body || []).filter(inv => inv.id != null);
        if (invoices.length === 0) {
          this.isLoadingJobDetail = false;
          return;
        }

        const invoice = [...invoices].sort((a, b) => {
          const dateA = a.invoicedate ? dayjs(a.invoicedate).valueOf() : 0;
          const dateB = b.invoicedate ? dayjs(b.invoicedate).valueOf() : 0;
          return dateB - dateA;
        })[0];

        const invoiceId = invoice.id!;
        forkJoin({
          lines: this.autojobsinvoicelinesService.queryByInvoiceId(invoiceId).pipe(
            map(r => r.body || []),
            catchError(() => of([])),
          ),
          services: this.autojobsservicelinesService.queryByInvoiceId(invoiceId).pipe(
            map(r => r.body || []),
            catchError(() => of([])),
          ),
          common: this.autojobsalescommonService.queryByInvoiceId(invoiceId).pipe(
            map(r => r.body || []),
            catchError(() => of([])),
          ),
        }).subscribe({
          next: ({ lines, services, common }) => {
            this.selectedJobDetail = {
              job,
              invoice,
              invoiceLines: lines,
              serviceLines: services,
              commonServiceCharges: common,
            };
            this.isLoadingJobDetail = false;
          },
          error: () => {
            this.selectedJobDetail = { job, invoice, invoiceLines: [], serviceLines: [], commonServiceCharges: [] };
            this.isLoadingJobDetail = false;
          },
        });
      },
      error: () => {
        this.isLoadingJobDetail = false;
      },
    });
  }

  closeJobDetail(): void {
    this.showJobDetailModal = false;
    this.selectedJobDetail = null;
    this.isLoadingJobDetail = false;
  }

  formatJobDate(value: dayjs.Dayjs | string | null | undefined): string {
    if (!value) {
      return '-';
    }
    return dayjs(value).format('DD/MM/YYYY');
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

            this.salesinvoicelineService.queryByInvoiceId(invoiceId).subscribe((linesRes: HttpResponse<any[]>) => {
              this.salesInvoicesMap[invoiceId].invoiceLines = linesRes.body || [];
            });

            this.salesinvoiceservicechargelineService.queryByInvoiceId(invoiceId).subscribe((servicesRes: HttpResponse<any[]>) => {
              this.salesInvoicesMap[invoiceId].serviceLines = servicesRes.body || [];
            });

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
          .btn, .job-history-toolbar, .job-history-actions {
            display: none !important;
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
    window.location.reload();
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
