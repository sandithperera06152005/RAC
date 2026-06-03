import { Component, inject, OnInit, ViewChild } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';
import dayjs from 'dayjs/esm';
import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { IAutocarejob } from '../autocarejob.model';
import { IAutojobsinvoice } from 'app/entities/autojobsinvoice/autojobsinvoice.model';
import { ICustomervehicle } from 'app/entities/customervehicle/customervehicle.model';
import { CustomervehicleService } from 'app/entities/customervehicle/service/customervehicle.service';
import { CustomerService } from 'app/entities/customer/service/customer.service';

import { AutocareappointmentService } from 'app/entities/autocareappointment/service/autocareappointment.service';
import { IAutocareappointment } from 'app/entities/autocareappointment/autocareappointment.model';
import { AutocarejobService } from '../service/autocarejob.service';
import { AutojobsinvoicelinebatchesService } from 'app/entities/autojobsinvoicelinebatches/service/autojobsinvoicelinebatches.service';
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
import { AlertService } from 'app/core/util/alert.service';
import { AlertMuteService } from 'app/core/util/alert-mute.service';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/auth/account.model';

@Component({
  standalone: true,
  selector: 'jhi-autocarejob-itemissue',
  templateUrl: './autocarejob-itemissue.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule, AutojobsinvoiceUpdateComponent],
})
export class AutocarejobitemissueComponent implements OnInit {
  isSaving = false;
  autocarejob: IAutocarejob | null = null;
  salesinvoice: ISalesinvoice | null = null;
  autocarejobsinvoices: IAutojobsinvoice[] = [];
  customervehicles: ICustomervehicle[] = [];
  customerDetails: any | null = null;
  autocareappointments: IAutocareappointment[] = [];
  jobinvoicelinebatches = inject(AutojobsinvoicelinebatchesService);
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
  protected accountService = inject(AccountService);
  account: any | null = null;
  currentUserId: number = 0;
  protected customervehicleService = inject(CustomervehicleService);
  protected customerService = inject(CustomerService);
  protected autocareappointmentService = inject(AutocareappointmentService);
  protected alertService = inject(AlertService);
  protected alertMuteService = inject(AlertMuteService);
  issuedItems: any[] = []; // Items that are issued
  allBatches: any[] = []; // Store all batches (issued and non-issued)
  availableItems: any[] = [];
  persistedIssuedKeys = new Set<string>();

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: AutocarejobFormGroup = this.autocarejobFormService.createAutocarejobFormGroup();

  ngOnInit(): void {
    this.accountService.identity().subscribe(account => {
      this.account = account;
      if (account) {
        // Try getting ID from account object directly if present
        if ((account as any).id) {
          this.currentUserId = (account as any).id;
        } else {
          // Fallback to localStorage as set by AccountService
          const storedUserId = localStorage.getItem('userId');
          if (storedUserId) {
            this.currentUserId = parseInt(storedUserId, 10);
          }
        }
        console.log('Current User ID for Item Issuance:', this.currentUserId);
      }
    });

    this.activatedRoute.data.subscribe(({ autocarejob }) => {
      this.autocarejob = autocarejob;

      if (autocarejob) {
        console.log(this.autocarejob?.customername);
        this.updateForm(autocarejob);
      }
      this.fetchhistory();
    });
  }

  previousState(): void {
    window.history.back();
  }

  autojobsInvoicesMap: {
    [key: number]: {
      invoice: IAutojobsinvoice;
      invoiceLines: any[];
    };
  } = {};

  get allInvoiceLines(): any[] {
    return Object.values(this.autojobsInvoicesMap)
      .flatMap(invoice => invoice.invoiceLines)
      .filter(line => !this.isLineIssued(line)); // Only lines not issued
  }

  get hasItemHistory(): boolean {
    return this.allInvoiceLines.length > 0 || this.issuedItems.length > 0;
  }

  fetchhistory(): void {
    this.autojobsinvoiceService.query({ 'jobid.equals': this.autocarejob?.id }).subscribe((res: HttpResponse<IAutojobsinvoice[]>) => {
      if (!res.body || res.body.length === 0) {
        return;
      }

      const invoiceRequests = res.body.map(invoice => {
        this.autojobsInvoicesMap[invoice.id!] = {
          invoice,
          invoiceLines: [],
        };

        return this.autojobsinvoicelinesService
          .queryByInvoiceId(invoice.id!)
          .toPromise()
          .then(linesRes => {
            this.autojobsInvoicesMap[invoice.id!].invoiceLines = linesRes?.body || [];
          });
      });

      // Wait until ALL invoice lines are loaded
      Promise.all(invoiceRequests).then(() => {
        this.syncIssuedStateFromBatches();
      });
    });
  }

  loadIssuedItems(): void {
    this.issuedItems = Object.values(this.autojobsInvoicesMap)
      .flatMap(inv => inv.invoiceLines)
      .filter(line => this.isLineIssued(line)); // Only issued items
  }

  syncIssuedStateFromBatches(): void {
    const invoiceLines = Object.values(this.autojobsInvoicesMap).flatMap(inv => inv.invoiceLines);
    const parentIds = invoiceLines.map(line => line.invocieid ?? line.id).filter((id): id is number => typeof id === 'number');

    if (parentIds.length === 0) {
      this.persistedIssuedKeys.clear();
      this.loadIssuedItems();
      return;
    }

    this.jobinvoicelinebatches.queryByParentLineIds(parentIds).subscribe({
      next: res => {
        this.allBatches = res.body ?? [];
        this.persistedIssuedKeys = new Set(this.allBatches.filter(batch => batch.issued).map(batch => this.buildLineKey(batch)));
        this.loadIssuedItems();
      },
      error: err => {
        console.error('Error loading issued item state:', err);
        this.loadIssuedItems();
      },
    });
  }

  buildLineKey(line: any): string {
    return `${line.invocieid ?? line.id ?? ''}|${line.lineid ?? ''}|${line.itemid ?? ''}|${line.itemcode ?? line.code ?? ''}`;
  }

  isLineIssued(line: any): boolean {
    return line?.issued === true || this.persistedIssuedKeys.has(this.buildLineKey(line));
  }

  //issue an item
  issueItem(issuedItem: any): void {
    if (!issuedItem) {
      console.warn('Invalid item selection.');
      return;
    }

    const nextbatchlineid = this.itemsArray.length > 0 ? Math.max(...this.itemsArray.map(item => item.batchlineid), 0) + 1 : 1;
    const parentInvoiceId = issuedItem.invocieid ?? null;
    const parentLineId = issuedItem.lineid ?? null;

    if (parentInvoiceId == null || parentLineId == null) {
      console.error('Missing parent invoice line key for issued item:', issuedItem);
      return;
    }

    const newItem: any = {
      id: parentInvoiceId,
      lineid: parentLineId,
      batchlineid: nextbatchlineid,
      itemid: issuedItem.itemid,
      code: issuedItem.itemcode ?? '',
      batchid: 0,
      batchcode: '',
      txdate: dayjs().add(-new Date().getTimezoneOffset(), 'minute'),
      manufacturedate: dayjs().add(-new Date().getTimezoneOffset(), 'minute'),
      expireddate: dayjs().add(-new Date().getTimezoneOffset(), 'minute'),
      qty: 1,
      cost: 0,
      price: 0,
      notes: issuedItem.description ?? '',
      lmu: 0,
      lmd: dayjs().add(-new Date().getTimezoneOffset(), 'minute'),
      nbt: false,
      vat: false,
      discount: 0,
      total: issuedItem.linetotal ?? 0,
      issued: true,
      issuedby: this.currentUserId,
      issueddatetime: dayjs().add(-new Date().getTimezoneOffset(), 'minute'),
      addedbyid: 0,
      canceloptid: 0,
      cancelopt: '',
      cancelby: 0,
    };

    this.itemsArray.push(newItem);

    this.alertMuteService.mute();
    // Save the issued item to the backend (now performs upsert in the service)
    this.jobinvoicelinebatches.create(newItem).subscribe({
      next: (response: any) => {
        console.log('Item marked as issued:', response);
        issuedItem.issued = true;
        this.persistedIssuedKeys.add(this.buildLineKey(issuedItem));
        this.loadIssuedItems(); // re-sync UI after update
        this.alertMuteService.unmute();
        this.alertService.addAlert({ type: 'success', message: 'Item Issued Successfully', timeout: 3000 });
      },
      error: (err: any) => {
        console.error('Error issuing item:', err);
        this.alertMuteService.unmute();
      },
    });

    console.log('Issued Items:', this.itemsArray);
  }

  //Cancel issued item
  cancelIssuedItem(item: any): void {
    this.issuedItems = this.issuedItems.filter(i => i.id !== item.id);
  }

  removeAvailableItem(line: any) {
    const index = this.allInvoiceLines.indexOf(line);
    if (index !== -1) {
      this.allInvoiceLines.splice(index, 1);
    }
  }

  itemsArray: Array<{
    id: number | null;
    lineid: number;
    batchlineid: number;
    itemid: number;
    code: string;
    batchid: number;
    batchcode: string;
    txdate: dayjs.Dayjs;
    manufacturedate: dayjs.Dayjs;
    expireddate: dayjs.Dayjs;
    qty: number;
    cost: number;
    price: number;
    notes: string;
    lmu: number;
    lmd: dayjs.Dayjs;
    nbt: boolean;
    vat: boolean;
    discount: number;
    total: number;
    issued: boolean;
    issuedby: number;
    issueddatetime: dayjs.Dayjs;
    addedbyid: number;
    canceloptid: number;
    cancelopt: string;
    cancelby: number;
  }> = [];

  saveAll(): void {
    if (this.itemsArray.length === 0) {
      console.warn('No items to save.');
      return;
    }

    this.isSaving = true;

    this.itemsArray.forEach(item => {
      const itemWithDayjsDates = {
        ...item,
        txdate: dayjs(item.txdate).toISOString(),
        manufacturedate: dayjs(item.manufacturedate).toISOString(),
        expireddate: dayjs(item.expireddate).toISOString(),
        lmd: dayjs(item.lmd).add(-new Date().getTimezoneOffset(), 'minute').toISOString(),
        issueddatetime: dayjs(item.issueddatetime).toISOString(),
      };

      // this.jobinvoicelinebatches.create(itemWithDayjsDates).subscribe({
      //   next: createResponse => {
      //     console.log('item created successfully:', createResponse);
      //   },
      //   error: createError => {
      //     console.error('Error creating service:', createError.body);
      //   }
      // });
    });
  }

  save(): void {
    this.isSaving = true;
    const autocarejob = this.autocarejobFormService.getAutocarejob(this.editForm);
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
