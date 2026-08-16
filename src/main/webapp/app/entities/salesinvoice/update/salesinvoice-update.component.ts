import { Component, inject, OnInit, ViewChild, ChangeDetectorRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable, forkJoin } from 'rxjs';
import { finalize } from 'rxjs/operators';
import { debounceTime } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import dayjs from 'dayjs/esm';
import { AccountService } from 'app/core/auth/account.service';

import { ISalesInvoiceLines } from 'app/entities/sales-invoice-lines/sales-invoice-lines.model';
import { ISaleInvoiceCommonServiceCharge } from 'app/entities/sale-invoice-common-service-charge/sale-invoice-common-service-charge.model';
import { ISalesInvoiceServiceChargeLine } from 'app/entities/sales-invoice-service-charge-line/sales-invoice-service-charge-line/sales-invoice-service-charge-line.model';
import { IAutojobsaleinvoicecommonservicecharge } from 'app/entities/autojobsaleinvoicecommonservicecharge/autojobsaleinvoicecommonservicecharge.model';
import { ISalesinvoice } from '../salesinvoice.model';
import { SalesinvoiceService } from '../service/salesinvoice.service';
import { SalesinvoiceFormService, SalesinvoiceFormGroup } from './salesinvoice-form.service';
import { SalesInvoiceLinesUpdateComponent } from '../../sales-invoice-lines/update/sales-invoice-lines-update.component';
import { SaleInvoiceCommonServiceChargeUpdateComponent } from '../../sale-invoice-common-service-charge/update/sale-invoice-common-service-charge-update.component';
import { SalesInvoiceServiceChargeLineUpdateComponent } from '../../sales-invoice-service-charge-line/update/sales-invoice-service-charge-line-update.component';
import { VehicletypeService } from 'app/entities/vehicletype/service/vehicletype.service';
import { IVehicletype } from 'app/entities/vehicletype/vehicletype.model';
import { IInventory } from 'app/entities/inventory/inventory.model';
import { SalesInvoiceLinesService } from 'app/entities/sales-invoice-lines/service/sales-invoice-lines.service';
import { AutojobsinvoicelinesService } from 'app/entities/autojobsinvoicelines/service/autojobsinvoicelines.service';
import { AutojobsinvoiceService } from 'app/entities/autojobsinvoice/service/autojobsinvoice.service';
import { AutocareappointmentService } from 'app/entities/autocareappointment/service/autocareappointment.service';
import { IAutocareappointment } from 'app/entities/autocareappointment/autocareappointment.model';
import { IAutojobsinvoice } from 'app/entities/autojobsinvoice/autojobsinvoice.model';
import { NewAutojobsalesinvoiceservicechargeline } from 'app/entities/autojobsalesinvoiceservicechargeline/autojobsalesinvoiceservicechargeline.model';
import { ReceiptModalComponent } from 'app/entities/receipt-modal/receipt-modal.component';
import { AutocarejobService } from 'app/entities/autocarejob/service/autocarejob.service';
import { IAutocarejob } from 'app/entities/autocarejob/autocarejob.model';
import { DecimalInputDirective } from 'app/shared/decimal-input.directive';
import { CustomervehicleService } from 'app/entities/customervehicle/service/customervehicle.service';
import { ICustomervehicle } from 'app/entities/customervehicle/customervehicle.model';
import { CustomerService } from 'app/entities/customer/service/customer.service';
import { ICustomer } from 'app/entities/customer/customer.model';
import { AutojobsinvoicelinebatchesService } from 'app/entities/autojobsinvoicelinebatches/service/autojobsinvoicelinebatches.service';

declare const bootstrap: any;
@Component({
  standalone: true,
  selector: 'jhi-salesinvoice-update',
  templateUrl: './salesinvoice-update.component.html',
  imports: [
    SharedModule,
    FormsModule,
    ReactiveFormsModule,
    SalesInvoiceLinesUpdateComponent,
    SaleInvoiceCommonServiceChargeUpdateComponent,
    SalesInvoiceServiceChargeLineUpdateComponent,
    ReceiptModalComponent,
    DecimalInputDirective,
  ],
})
export class SalesinvoiceUpdateComponent implements OnInit {
  isSaving = false;
  selectedReceipt: String = 'hello world';
  salesinvoice: ISalesinvoice | null = null;
  showCodeField: boolean = false;
  @ViewChild(SalesInvoiceLinesUpdateComponent) salesInvoiceLinesUpdateComponent!: SalesInvoiceLinesUpdateComponent;

  @ViewChild(SalesInvoiceServiceChargeLineUpdateComponent)
  SalesInvoiceServiceChargeLinesUpdateComponent!: SalesInvoiceServiceChargeLineUpdateComponent;

  @ViewChild(SaleInvoiceCommonServiceChargeUpdateComponent)
  SaleInvoiceCommonServiceChargesUpdateComponent!: SaleInvoiceCommonServiceChargeUpdateComponent;
  protected salesInvoiceService = inject(SalesinvoiceService);
  autojobinvoice = inject(AutojobsinvoiceService);
  protected vehicletypesService = inject(VehicletypeService);
  protected salesinvoiceService = inject(SalesinvoiceService);
  protected salesinvoiceFormService = inject(SalesinvoiceFormService);
  protected activatedRoute = inject(ActivatedRoute);
  protected router = inject(Router);
  protected salesInvoiceLinesService = inject(SalesInvoiceLinesService);
  protected autocarejobService = inject(AutocarejobService);
  protected accountService = inject(AccountService);
  protected customervehicleService = inject(CustomervehicleService);
  protected customerService = inject(CustomerService);
  protected autocareappointmentService = inject(AutocareappointmentService);
  protected cdr = inject(ChangeDetectorRef);
  protected jobInvoiceLineBatches = inject(AutojobsinvoicelinebatchesService);

  filteredVehicles: any[] = [];
  filteredCustomers: ICustomer[] = [];
  showVehicleDropdown = false;

  filteredItems: IInventory[][] = [];
  ISalesInvoiceLines: ISalesInvoiceLines[] = [];
  ISalesInvoiceServiceChargeLine: ISalesInvoiceServiceChargeLine[] = [];
  NewAutojobsalesinvoiceservicechargeline: NewAutojobsalesinvoiceservicechargeline[] = [];
  ISaleInvoiceCommonServiceCharge: ISaleInvoiceCommonServiceCharge[] = [];
  // Initialize editForm with SalesinvoiceFormService
  editForm: SalesinvoiceFormGroup = this.salesinvoiceFormService.createSalesinvoiceFormGroup();
  discountOption: string = 'percentage'; // Default value
  discountValue: number = 0;
  itemDiscountOption: string = 'percentage';
  itemDiscountValue: number = 0;
  subTotal: number = 0;
  totalamount: number = 0;
  totalLineDiscount: number = 0; // Tracks sum of line discounts from billing lines
  totalServiceChargeDiscount: number = 0; // Tracks sum of discounts from service charge lines
  i: number = 0;
  customername: string = '';
  customeraddress: string = '';
  vehicleno: string = '';
  receiptdate: Date = new Date();
  term: string = '';
  date: Date = new Date();
  amount: number = 0;
  checkdate: Date = new Date();
  checkno: string = '';
  bank: string = '';
  totalamountinword: string = '';
  comments: string = '';
  customerid: number = 0;
  isactive: boolean = true;
  deposited: boolean = true;
  createdby: number = 0;
  accountId: number = 0;
  accountCode: string = '';

  newcode: string = '';
  sourceInvoiceId: number | null = null;
  private currentAutocareJobId: number | null = null;

  /** Returns current local time offset so it serializes as local time instead of UTC */
  private localNow(): dayjs.Dayjs {
    return dayjs().add(-new Date().getTimezoneOffset(), 'minute');
  }

  /** Offsets a parsed date so it serializes as local time instead of UTC */
  private localDate(val: any): dayjs.Dayjs {
    return val ? dayjs(val).add(-new Date().getTimezoneOffset(), 'minute') : this.localNow();
  }

  ngOnInit(): void {
    console.log('starttt');

    // this.servicelines(id);
    // this.servicecommonlines(id);
    // this.invoicelines(id);
    // Extract ID from query params in case it's not in route data
    this.activatedRoute.queryParams.subscribe(params => {
      const sourceInvoiceId = this.toValidId(params['id']);
      console.log('Query Params ID:', params['id']);

      if (sourceInvoiceId === null) {
        this.clearFetchedSourceData();
        this.sourceInvoiceId = null;
        return;
      }
      this.sourceInvoiceId = sourceInvoiceId;
      this.loadSalesInvoiceDummy(sourceInvoiceId);
    });

    this.loadVehicleTypes();
    this.fetchReceiptCode();

    this.editForm.get('isvatinvoice')?.valueChanges.subscribe(isVatInvoice => {
      if (isVatInvoice) {
        this.fetchVatReceiptCode();
      } else {
        this.fetchReceiptCode();
      }
    });

    // Subscribe to form control valueChanges
    this.editForm.get('valuediscount')?.valueChanges.subscribe(() => this.calculateDiscount());
    this.editForm.get('subtotal')?.valueChanges.subscribe(() => this.calculateDiscount());
  }

  vehicletypes: IVehicletype[] = [];
  loadVehicleTypes(): void {
    this.vehicletypesService.query({ size: 1000 }).subscribe((res: HttpResponse<IVehicletype[]>) => {
      this.vehicletypes = res.body || [];
      console.log('Loaded Vehicle Types:', this.vehicletypes); // Display the loaded vehicle types in the console
    });
  }

  fetchReceiptCode(): void {
    this.salesInvoiceService.fetchReceiptCode().subscribe(
      (response: HttpResponse<{ code: string }>) => {
        const newCode = response.body?.code;
        if (newCode) {
          console.log('Updated Sales Invoice Code:', newCode);
          this.applyInvoiceCode(newCode);
        }
      },
      error => {
        console.error('Error fetching invoice code:', error);
      },
    );
  }

  fetchVatReceiptCode(): void {
    this.salesInvoiceService.fetchNextVatCode().subscribe(
      (response: HttpResponse<{ code: string }>) => {
        const newCode = response.body?.code;
        if (newCode) {
          console.log('Updated VAT Sales Invoice Code:', newCode);
          this.applyInvoiceCode(newCode);
        }
      },
      error => {
        console.error('Error fetching VAT invoice code:', error);
      },
    );
  }

  private applyInvoiceCode(newCode: string): void {
    this.newcode = newCode;
    this.editForm.patchValue({ code: newCode });
  }
  fetchaccountid(name: string): void {
    this.salesInvoiceService.fetchReceiptAccountId(name).subscribe(
      (response: HttpResponse<any>) => {
        console.log('Full Response:', response);
        console.log('Status:', response.status);

        const accounts = response.body || [];

        if (accounts.length > 0) {
          // Assuming the first matched account holds the customer's balance
          const customerAccount = accounts[0];

          // Grab the balance (or default to 0 if null)
          const amountOwing = customerAccount.balance || 0;
          this.accountId = customerAccount.id || 0;
          this.accountCode = customerAccount.code || '';

          console.log(`Loaded Amount Owing for ${name} from Accounts:`, amountOwing);
          console.log(`Loaded Account ID: ${this.accountId}, Account Code: ${this.accountCode}`);

          // We no longer patch this to the form because we are taking it from AutoJobsInvoice
        }
      },
      error => {
        console.error('Error fetching account balance:', error);
      },
    );
  }

  /**
   * Fetches all active Sales Invoices for the given customer from dbo.SalesInvoice,
   * sums only the positive AmountOwing values, and displays the total in the Amount Owing field.
   */
  fetchCustomerAmountOwing(customerId: number): void {
    this.salesinvoiceService.query({ 'customerid.equals': customerId, 'isactive.equals': true, size: 1000 }).subscribe({
      next: (response: HttpResponse<ISalesinvoice[]>) => {
        const invoices = response.body || [];
        // Only sum invoices that have a positive outstanding balance (exclude fully paid ones)
        const totalOwing = invoices
          .filter(inv => (Number(inv.amountowing) || 0) > 0)
          .reduce((sum, inv) => sum + (Number(inv.amountowing) || 0), 0);

        // Round to 2 decimal places and treat near-zero floating point as 0
        const rounded = Math.round(totalOwing * 100) / 100;
        console.log(`Customer ${customerId} — active invoices: ${invoices.length}, total AmountOwing: ${rounded}`);
        this.editForm.patchValue({ amountowing: rounded });
        this.cdr.detectChanges();
      },
      error: err => {
        console.error('Error fetching customer AmountOwing from SalesInvoice:', err);
      },
    });
  }

  onDiscountValueChange(event: any): void {
    this.discountValue = event.target.value; // Update the discountValue with the input value
    console.log('Updated Discount Value:', this.discountValue); // Log the updated value to the console

    this.calculateDiscount(); // Log the updated value to the console
    // Call the function to calculate discount
  }
  buyquantity: number = 0; // Store the buy quantity value

  // Function to handle changes in the quantity field
  onBuyQtyChange(event: Event): void {
    const inputElement = event.target as HTMLInputElement;
    this.buyquantity = Number(inputElement.value);
    console.log('Buy Quantity:', this.buyquantity);
  }

  onsubtotalValueChange(event: any): void {
    this.subTotal = event.target.value; // Update the discountValue with the input value
    console.log('Updated Discount Value:', this.subTotal); // Log the updated value to the console
    // Call the function to calculate discount
  }

  calculateDiscount(): void {
    console.log('Form Values:', this.editForm.value); // Debug the entire form

    const subtotal = Number(this.editForm.get('subtotal')?.value) || 0;
    const valueDiscount = this.discountValue;

    console.log('Selected Discount Option:', this.discountOption); // Log the selected option
    console.log('Sub Total:', subtotal);
    console.log('Discount Value:', valueDiscount);

    // Invoice-level discount (from the Final Payments box only)
    let invoiceLevelDiscount = 0;

    if (this.discountOption === 'percentage') {
      invoiceLevelDiscount = (subtotal * Number(valueDiscount)) / 100;
    } else if (this.discountOption === 'value') {
      invoiceLevelDiscount = valueDiscount;
    }

    invoiceLevelDiscount = Math.min(invoiceLevelDiscount, subtotal);

    // netTotal only subtracts the invoice-level discount from subtotal.
    // Line discounts are already reflected in subtotal (lineTotal = price×qty - lineDiscount).
    const netTotal = Number((subtotal - invoiceLevelDiscount).toFixed(2));

    // totaldiscount saved to DB = invoice-level discount + sum of all line-level discounts (for reporting)
    const totalDiscount = Number((invoiceLevelDiscount + this.totalLineDiscount + this.totalServiceChargeDiscount).toFixed(2));

    console.log('Invoice-level Discount:', invoiceLevelDiscount);
    console.log('Total Line Discount:', this.totalLineDiscount);
    console.log('Total Service Charge Discount:', this.totalServiceChargeDiscount);
    console.log('Total Discount (for DB):', totalDiscount);
    console.log('Net Total:', netTotal);

    this.editForm.patchValue({
      totaldiscount: totalDiscount,
      nettotal: netTotal,
    });
    this.totalamount = netTotal;
  }

  onDiscountOptionChange(option: string): void {
    this.discountOption = option;
    console.log('Discount Option Changed:', this.discountOption);
    this.calculateDiscount(); // Recalculate discount based on the new option
  }

  total1: number = 0; // Total from first child
  total2: number = 0;
  total3: number = 0;
  subtotal: number = 0; // Store subtotal

  receiveTotal(total: number, source: string) {
    if (source === 'child1') {
      this.total1 = Number(total) || 0; // Update total from first child
    } else if (source === 'child2') {
      this.total2 = Number(total) || 0; // Update total from second child
    } else if (source === 'child3') {
      this.total3 = Number(total) || 0; // Update total from second child
    }

    this.subTotal = this.total1 + this.total2 + this.total3; // Combine the totals
    this.totalamount = this.subTotal;
    console.log('Total1:', this.total1, 'Total2:', this.total2, 'SubTotal:', this.subTotal);

    this.editForm.patchValue({
      subtotal: this.subTotal,
    });
    this.calculateDiscount();
  }

  receiveTotalLineDiscount(lineDiscount: number): void {
    this.totalLineDiscount = lineDiscount;
    console.log('Received Total Line Discount from child:', lineDiscount);
    this.calculateDiscount();
  }

  receiveTotalServiceChargeDiscount(serviceChargeDiscount: number): void {
    this.totalServiceChargeDiscount = serviceChargeDiscount;
    console.log('Received Total Service Charge Discount from child:', serviceChargeDiscount);
    this.calculateDiscount();
  }

  fetchedServicesCommon: {
    id?: number;
    sourceAutoJobInvoiceId?: number;
    sourceAutoJobLineNumber?: number;
    itemcode: string;
    itemname: string;
    sellingprice: number;
    optionId?: number;
    mainId?: number;
    code?: string;
    discount?: number;
    servicePrice?: number;
  }[] = [];

  private toValidId(value: unknown): number | null {
    const numericValue = Number(value);
    return Number.isInteger(numericValue) && numericValue > 0 ? numericValue : null;
  }

  private clearFetchedSourceData(): void {
    this.fetchedItems = [];
    this.fetchedServices = [];
    this.fetchedServicesCommon = [];
  }

  private normalizeKeyPart(value: unknown): string {
    return String(value ?? '')
      .trim()
      .toLowerCase();
  }

  private normalizeMoneyKeyPart(value: unknown): string {
    const numericValue = Number(value);
    return Number.isFinite(numericValue) ? numericValue.toFixed(2) : '0.00';
  }

  private uniqueByKey<T>(items: T[], keySelector: (item: T) => string): T[] {
    const seen = new Set<string>();
    return items.filter(item => {
      const key = keySelector(item);
      if (seen.has(key)) {
        return false;
      }
      seen.add(key);
      return true;
    });
  }

  private servicecommonlines(ids: number[]): void {
    if (!ids || ids.length === 0) return;
    forkJoin(ids.map(id => this.salesInvoiceService.fetchServiceCommon(id))).subscribe(
      responses => {
        this.fetchedServicesCommon = [];
        responses.forEach((res, index) => {
          const sourceAutoJobInvoiceId = ids[index];
          if (res.body && res.body.length > 0) {
            res.body.forEach((item: any) => {
              this.fetchedServicesCommon.push({
                id: item.id,
                sourceAutoJobInvoiceId,
                sourceAutoJobLineNumber: item.lineid,
                itemcode: item.code ?? '',
                itemname: item.name ?? '',
                sellingprice: item.value ?? 0,
                optionId: item.optionid,
                mainId: item.mainid,
                code: item.code,
                discount: item.discount,
                servicePrice: item.serviceprice,
              });
            });
          }
        });
        this.fetchedServicesCommon = this.uniqueByKey(this.fetchedServicesCommon, item =>
          [
            item.sourceAutoJobInvoiceId,
            item.optionId,
            item.mainId,
            this.normalizeKeyPart(item.itemcode || item.code),
            this.normalizeKeyPart(item.itemname),
            this.normalizeMoneyKeyPart(item.sellingprice),
          ].join('|'),
        );
        console.log('Fetched Itemssssscommon:', this.fetchedServicesCommon);
      },
      error => {
        console.error('Error fetching service common lines:', error);
      },
    );
  }

  fetchedServices: {
    id?: number;
    sourceAutoJobInvoiceId?: number;
    sourceAutoJobLineNumber?: number;
    itemname: string;
    sellingprice: number;
    optionId?: number;
    serviceDescription?: string;
    discount?: number;
    servicePrice?: number;
  }[] = [];

  private servicelines(ids: number[]): void {
    if (!ids || ids.length === 0) return;
    forkJoin(ids.map(id => this.salesInvoiceService.fetchService(id))).subscribe(
      responses => {
        this.fetchedServices = [];
        responses.forEach((res, index) => {
          const sourceAutoJobInvoiceId = ids[index];
          if (res.body && res.body.length > 0) {
            res.body.forEach((item: any) => {
              this.fetchedServices.push({
                id: item.id,
                sourceAutoJobInvoiceId,
                sourceAutoJobLineNumber: item.lineid,
                itemname: item.servicename ?? '',
                sellingprice: item.value ?? 0,
                optionId: item.optionid,
                serviceDescription: item.servicediscription,
                discount: item.discount,
                servicePrice: item.serviceprice,
              });
            });
          }
        });
        this.fetchedServices = this.uniqueByKey(this.fetchedServices, item =>
          [
            item.sourceAutoJobInvoiceId,
            item.optionId,
            this.normalizeKeyPart(item.itemname),
            this.normalizeKeyPart(item.serviceDescription),
            this.normalizeMoneyKeyPart(item.sellingprice),
          ].join('|'),
        );
        console.log('Fetched Itemssssssssssssssssss:', this.fetchedServices);
      },
      error => {
        console.error('Error fetching service lines:', error);
      },
    );
  }

  fetchedItems: {
    id?: number;
    itemid?: number;
    itemcode: string;
    itemname: string;
    unitofmeasurement?: string;
    quantity: number;
    sellingprice: number;
    itemcost?: number;
    discount?: number;
    description?: string;
    discountEntryType?: 'percentage' | 'value';
    discountPercentage?: number;
    lineid?: number;
  }[] = [];

  /** Autojob/advisor-care lines use Dis(%); saved sales lines may use [PCT]/[VAL] in description. */
  private resolveFetchedLineDiscountEntryType(
    description: string,
    discount: number,
    quantity: number,
    sellingprice: number,
  ): 'percentage' | 'value' | undefined {
    if (description.startsWith('[VAL]')) {
      return 'value';
    }
    if (description.startsWith('[PCT]')) {
      return 'percentage';
    }
    if (discount > 0 && quantity > 0 && sellingprice > 0) {
      return 'percentage';
    }
    return undefined;
  }

  private buildIssuedItemKey(item: any): string {
    return `${item.invocieid ?? item.id ?? ''}|${item.lineid ?? ''}|${item.itemid ?? ''}|${item.itemcode ?? item.code ?? ''}`;
  }

  private invoicelines(ids: number[]): void {
    if (!ids || ids.length === 0) return;
    forkJoin({
      lineResponses: forkJoin(ids.map(id => this.salesInvoiceService.fetchInvoiceLines(id))),
      batchResponse: this.jobInvoiceLineBatches.queryByParentLineIds(ids),
    }).subscribe({
      next: ({ lineResponses, batchResponse }) => {
        const issuedKeys = new Set(
          (batchResponse.body ?? []).filter(batch => batch.issued === true).map(batch => this.buildIssuedItemKey(batch)),
        );
        this.fetchedItems = [];
        lineResponses.forEach(res => {
          if (res.body && res.body.length > 0) {
            res.body
              .filter((item: any) => issuedKeys.has(this.buildIssuedItemKey(item)))
              .forEach((item: any) => {
                const quantity = Number(item.quantity ?? 0);
                const sellingprice = Number(item.sellingprice ?? 0);
                const discount = Number(item.discount ?? item.discountamount ?? item.discountAmount ?? item.totaldiscount ?? 0);
                const description = String(item.description ?? '');
                const discountEntryType = this.resolveFetchedLineDiscountEntryType(description, discount, quantity, sellingprice);
                const discountPercentage =
                  discountEntryType === 'percentage' && discount > 0 && sellingprice > 0 && quantity > 0
                    ? Number(((discount / quantity / sellingprice) * 100).toFixed(4))
                    : undefined;
                this.fetchedItems.push({
                  id: item.id,
                  itemid: item.itemid,
                  itemcode: item.itemcode ?? '',
                  itemname: item.itemname ?? '',
                  unitofmeasurement: item.unitofmeasurement ?? '',
                  quantity,
                  sellingprice,
                  itemcost: item.itemcost ?? item.lastcost ?? 0,
                  discount,
                  description: item.description,
                  discountEntryType,
                  discountPercentage,
                  lineid: item.lineid,
                });
              });
          }
        });
        this.fetchedItems = [...this.fetchedItems];
        this.enrichFetchedItemsFromSavedSalesLines();
        console.log('Fetched Items:', this.fetchedItems);
      },
      error: error => {
        console.error('Error fetching invoice lines:', error);
      },
    });
  }

  /** Merge discount-entry markers from previously saved SalesInvoiceLines (description [PCT]/[VAL] prefixes). */
  private enrichFetchedItemsFromSavedSalesLines(): void {
    const jobId = this.currentAutocareJobId ?? this.editForm.get('autocarejobid')?.value;
    if (!jobId) {
      return;
    }
    this.salesinvoiceService.query({ 'autocarejobid.equals': Number(jobId), size: 50 }).subscribe({
      next: (invoiceRes: HttpResponse<ISalesinvoice[]>) => {
        const invoiceIds = (invoiceRes.body ?? []).map(inv => inv.id).filter((id): id is number => id != null);
        if (invoiceIds.length === 0) {
          return;
        }
        forkJoin(invoiceIds.map(invoiceId => this.salesInvoiceLinesService.queryByInvoiceId(invoiceId))).subscribe({
          next: lineResponses => {
            const savedLines = lineResponses.flatMap(res => res.body ?? []);
            this.fetchedItems = this.fetchedItems.map(item => {
              const saved = savedLines.find(
                line =>
                  line.itemcode === item.itemcode &&
                  line.itemid === item.itemid &&
                  Math.abs(Number(line.discount ?? 0) - Number(item.discount ?? 0)) < 0.01,
              );
              if (!saved?.description) {
                return item;
              }
              return {
                ...item,
                description: saved.description,
                discount: saved.discount ?? item.discount,
              };
            });
            this.fetchedItems = [...this.fetchedItems];
            this.cdr.detectChanges();
          },
        });
      },
    });
  }

  private loadSalesInvoiceDummy(id: number): void {
    console.log('iddddd', id);
    this.salesInvoiceService.fetchJobInvoice(id).subscribe(response => {
      const salesInvoiceDummy = response.body[0];
      if (!salesInvoiceDummy) {
        this.clearFetchedSourceData();
        return;
      }
      console.log('Retrieved dataaaaaaaaaaaaa:', response);
      console.log('Retrieved dataaaaaaaaaaaaa:', salesInvoiceDummy);

      this.fetchaccountid(salesInvoiceDummy.customername);
      this.customername = salesInvoiceDummy.customername;
      this.customeraddress = salesInvoiceDummy.customeraddress;
      this.receiptdate = salesInvoiceDummy.receiptdate;
      this.term = salesInvoiceDummy.term;
      this.date = salesInvoiceDummy.date;
      this.amount = salesInvoiceDummy.amount;
      this.checkdate = salesInvoiceDummy.checkdate;
      this.checkno = salesInvoiceDummy.checkno;
      this.bank = salesInvoiceDummy.bank;
      this.totalamountinword = salesInvoiceDummy.totalamountinword;
      this.comments = salesInvoiceDummy.comments;
      this.customerid = salesInvoiceDummy.customerid;
      this.isactive = salesInvoiceDummy.isactive;
      this.deposited = salesInvoiceDummy.deposited;
      this.createdby = salesInvoiceDummy.createdby;
      this.totalamount = salesInvoiceDummy.totalamount;

      const transformedData: any = {
        id: null as unknown as number,
        code: (salesInvoiceDummy as any).code || undefined,
        orderid: 0,
        customerid:
          (salesInvoiceDummy as any).customerid ?? (salesInvoiceDummy as any).customerID ?? (salesInvoiceDummy as any).customerId ?? null,
        customername: (salesInvoiceDummy as any).customername,
        vehicleno: '',
        customeraddress: (salesInvoiceDummy as any).customeraddress,
        amountowing: Number((salesInvoiceDummy as any).amountowing) || 0,
        subtotal: Number((salesInvoiceDummy as any).subtotal) || 0,
        nettotal: Number((salesInvoiceDummy as any).nettotal) || 0,
        totaltax: Number((salesInvoiceDummy as any).totaltax) || 0,
        totaldiscount: Number((salesInvoiceDummy as any).totaldiscount) || 0,
        autocarejobid: (salesInvoiceDummy as any).jobid || null,
      };

      // Fetch vehicle number from the linked autocarejob via jobid
      const jobId = (salesInvoiceDummy as any).jobid;
      this.currentAutocareJobId = jobId != null && Number(jobId) > 0 ? Number(jobId) : null;
      if (jobId != null && Number(jobId) > 0) {
        this.autocarejobService.find(Number(jobId)).subscribe({
          next: (jobRes: HttpResponse<IAutocarejob>) => {
            const job = jobRes.body;
            const vehicleNumber = job?.vehiclenumber ?? '';
            transformedData.vehicleno = vehicleNumber;
            this.vehicleno = vehicleNumber;
            this.updateForm(transformedData);
            console.log('Vehicle number fetched from autocarejob:', vehicleNumber);
          },
          error: () => {
            this.updateForm(transformedData);
          },
        });

        // Fetch all invoice IDs for this job to aggregate lines
        this.autojobinvoice.query({ 'jobid.equals': Number(jobId), page: 0, size: 1000 }).subscribe({
          next: (invoiceResponse: HttpResponse<IAutojobsinvoice[]>) => {
            const invoices = invoiceResponse.body || [];
            const invoiceIds = invoices.map(inv => inv.id).filter(invId => invId != null) as number[];
            if (invoiceIds.length > 0) {
              this.invoicelines(invoiceIds);
              this.servicelines(invoiceIds);
              this.servicecommonlines(invoiceIds);
            } else {
              this.invoicelines([id]);
              this.servicelines([id]);
              this.servicecommonlines([id]);
            }
          },
          error: () => {
            this.invoicelines([id]);
            this.servicelines([id]);
            this.servicecommonlines([id]);
          },
        });
      } else {
        this.updateForm(transformedData);
        this.invoicelines([id]);
        this.servicelines([id]);
        this.servicecommonlines([id]);
      }

      console.log('Transformed Data:', transformedData);
    });
  }

  selectedItem: {
    id?: number | null;
    code: string;
    name: string;
    description?: string | null;
    unitofmeasurement?: string | null;
    availablequantity: number;
    lastcost?: number | null;
    lastsellingprice: number;
    discount?: number;
    discountOption?: string;
    discountEntryType?: 'percentage' | 'value';
    itemDiscountValue?: number;
    isNew?: boolean;
  } | null = null;
  private selectedInventoryItem: IInventory | null = null;

  itemname: string = ''; // Variable to hold the selected item's name
  availablequantity: number = 0;
  lastsellingprice: number = 0;
  code: string = '';
  onItemCodeSelect(event: Event, index: number): void {
    const inputElement = event.target as HTMLInputElement;
    const selectedCode = inputElement.value;

    // Find the selected item based on the code
    const selectedItem = this.filteredItems[index]?.find(item => item.code === selectedCode);

    if (selectedItem) {
      console.log('Selected Item:', selectedItem);
      this.selectedInventoryItem = selectedItem;
      this.itemname = selectedItem.name ?? ''; // Update itemName with the selected item's name or an empty string if undefined
      this.availablequantity = selectedItem.availablequantity ?? 0;
      this.lastsellingprice = selectedItem.lastsellingprice ?? 0;
      this.code = selectedItem.code ?? '';
      this.buyquantity = 1;
    } else {
      console.warn('No matching item found for:', selectedCode);
      this.selectedInventoryItem = null;
      this.itemname = ''; // Clear itemName if no match is found
      this.buyquantity = 0;
    }
  }
  onAddItem(): void {
    if (this.availablequantity <= 0) {
      alert('Available Quantity for this item is 0. Cannot add to list.');
      return;
    }

    if (this.buyquantity <= 0) {
      alert('Please enter a valid Buy Quantity.');
      return;
    }

    if (this.buyquantity > this.availablequantity) {
      alert(`Buy Quantity (${this.buyquantity}) cannot be larger than Available Quantity (${this.availablequantity}).`);
      return;
    }

    const existingLines = this.salesInvoiceLinesUpdateComponent?.salesInvoiceLinesArray?.controls ?? [];
    const selectedItemId = this.selectedInventoryItem?.id ?? null;
    const selectedItemCode = (this.code ?? '').trim().toLowerCase();
    const alreadyAdded = existingLines.some(control => {
      const lineItemId = control.get('itemid')?.value;
      const lineItemCode = (control.get('itemcode')?.value ?? '').toString().trim().toLowerCase();
      return (selectedItemId != null && lineItemId === selectedItemId) || (!!selectedItemCode && lineItemCode === selectedItemCode);
    });
    if (alreadyAdded) {
      alert(`"${this.itemname || this.code}" is already added to the list.`);
      return;
    }

    let itemDiscount = 0;
    if (this.itemDiscountOption === 'percentage') {
      itemDiscount = (this.lastsellingprice * this.itemDiscountValue) / 100;
    } else {
      itemDiscount = this.itemDiscountValue;
    }

    // Store the selected item as an object
    this.selectedItem = {
      id: this.selectedInventoryItem?.id ?? null,
      code: this.code,
      name: this.itemname,
      description: this.selectedInventoryItem?.description ?? this.itemname,
      unitofmeasurement: this.selectedInventoryItem?.unitofmeasurement ?? null,
      availablequantity: this.buyquantity,
      lastcost: this.selectedInventoryItem?.lastcost ?? 0,
      lastsellingprice: this.lastsellingprice,
      discount: itemDiscount * this.buyquantity, // Total line discount saved to SalesInvoiceLines.discount
      discountOption: this.itemDiscountOption,
      itemDiscountValue: this.itemDiscountValue,
      discountEntryType: this.itemDiscountOption === 'percentage' ? 'percentage' : 'value',
      isNew: true,
    };

    // Log the selected item to the console
    console.log('Selected Item:', this.selectedItem);
    console.log('Returned Buy Quantity:', this.buyquantity);
    // Call the function to get the buy quantity

    // Optionally reset the inputs after adding
    this.itemname = '';
    this.availablequantity = 0;
    this.lastsellingprice = 0;
    this.code = '';
    this.buyquantity = 0;
    this.itemDiscountValue = 0;
    this.selectedInventoryItem = null;
  }

  onItemCodeInput(event: Event, index: number): void {
    // Type assertion: Treat event target as HTMLInputElement
    const inputElement = <HTMLInputElement>event.target;
    const value = inputElement.value; // Get the value typed by the user

    // Log the input value to the console when a user types
    console.log('User typed:', value);

    if (!value) {
      this.filteredItems[index] = []; // Clear suggestions if input is empty
      return;
    }

    console.log('Fetching items for value:', value);

    this.salesInvoiceLinesService
      .getElementsByUserInputCode(value) // Call the service to fetch items
      .pipe(debounceTime(300)) // Debounce to avoid frequent calls
      .subscribe({
        next: (response: HttpResponse<IInventory[]>) => {
          const items = response.body || [];

          // Log the response body items received
          console.log('API response items:', items);

          this.filteredItems[index] = items.filter(item => item && item.code && item.name); // Filter out invalid items
          console.log('Filtered items for index', index, ':', this.filteredItems[index]); // Log filtered items
        },
        error: error => {
          console.error('Error fetching items:', error);
          this.filteredItems[index] = []; // Clear suggestions on error
        },
      });
  }

  onVehicleSearch(event: Event): void {
    const input = event.target as HTMLInputElement;
    const uppercasedValue = input.value.toUpperCase();
    if (input.value !== uppercasedValue) {
      input.value = uppercasedValue;
      this.editForm.get('vehicleno')?.setValue(uppercasedValue, { emitEvent: false });
    }
    const searchTerm = uppercasedValue;

    if (searchTerm.length > 2) {
      this.autocareappointmentService.findByVehicleNumber(searchTerm).subscribe(response => {
        this.filteredVehicles = this.getFirstUniqueAppointments(response.body || []);
        this.showVehicleDropdown = false; // Keep as input while searching
      });
    } else {
      this.filteredVehicles = [];
    }
  }

  private getFirstUniqueAppointments(appointments: IAutocareappointment[]): IAutocareappointment[] {
    const uniqueAppointments = new Map<string, IAutocareappointment>();
    [...appointments]
      .sort((left, right) => (left.id ?? Number.MAX_SAFE_INTEGER) - (right.id ?? Number.MAX_SAFE_INTEGER))
      .forEach(appointment => {
        const vehicleNumber = appointment.vehiclenumber?.trim();
        if (vehicleNumber && !uniqueAppointments.has(vehicleNumber)) {
          uniqueAppointments.set(vehicleNumber, appointment);
        }
      });
    return [...uniqueAppointments.values()];
  }

  onVehicleSelect(event: Event): void {
    const input = event.target as HTMLInputElement;
    const selectedVehicleNumber = input.value;

    if (!selectedVehicleNumber || selectedVehicleNumber === 'null') {
      this.editForm.patchValue({
        autocarejobid: null,
      });
      return;
    }

    const selectedVehicle = this.filteredVehicles.find(vehicle => vehicle.vehiclenumber === selectedVehicleNumber);

    if (selectedVehicle) {
      // If it's an appointment (has customername property), use its data
      if ('customername' in selectedVehicle) {
        this.editForm.patchValue({
          vehicleno: selectedVehicle.vehiclenumber || '',
          customerid: selectedVehicle.customerid ?? null,
          customername: selectedVehicle.customername || '',
        });
      } else {
        this.editForm.patchValue({
          vehicleno: selectedVehicle.vehiclenumber || '',
          customerid: selectedVehicle.customerid ?? null,
        });
      }

      // Try to find the latest autocarejob for this vehicle
      this.autocarejobService.query({ 'vehiclenumber.equals': selectedVehicleNumber, sort: ['id,desc'], size: 1 }).subscribe(jobRes => {
        const latestJob = jobRes.body?.[0];
        if (latestJob) {
          this.editForm.patchValue({
            autocarejobid: latestJob.id,
          });
          console.log('Linked to latest Autocare job:', latestJob.id);
          this.cdr.detectChanges();
        }
      });

      if (selectedVehicle.customerid) {
        this.customerService.find(selectedVehicle.customerid).subscribe(customerRes => {
          const customer = customerRes.body;
          if (customer) {
            const custName = customer.fullname || customer.businessname || '';
            this.customername = custName;
            this.editForm.patchValue({
              customername: custName,
              customeraddress: customer.residenceaddress || customer.businessaddress || '',
            });
            this.fetchaccountid(custName);
            // Fetch and display the customer's total outstanding Amount Owing
            this.fetchCustomerAmountOwing(selectedVehicle.customerid!);
            this.cdr.detectChanges();
          }
        });
      }
    }
  }

  onCustomerSearch(event: Event): void {
    const input = event.target as HTMLInputElement;
    const searchTerm = input.value;

    if (searchTerm.length > 2) {
      this.customerService.query({ 'fullname.contains': searchTerm }).subscribe(response => {
        this.filteredCustomers = response.body || [];
      });
    } else {
      this.filteredCustomers = [];
    }
  }

  onCustomerSelect(event: Event): void {
    const input = event.target as HTMLInputElement;
    const selectedCustomerName = input.value;

    const selectedCustomer = this.filteredCustomers.find(customer => (customer.fullname || customer.businessname) === selectedCustomerName);

    if (selectedCustomer) {
      const custName = selectedCustomer.fullname || selectedCustomer.businessname || '';
      this.customername = custName;
      this.editForm.patchValue({
        customerid: selectedCustomer.id,
        customername: custName,
        customeraddress: selectedCustomer.residenceaddress || selectedCustomer.businessaddress || '',
      });
      this.fetchaccountid(custName);

      // Fetch and display the customer's total outstanding Amount Owing
      if (selectedCustomer.id) {
        this.fetchCustomerAmountOwing(selectedCustomer.id);
      }

      // Load vehicles associated with this customer
      if (selectedCustomer.id) {
        this.customervehicleService.query({ 'customerid.equals': selectedCustomer.id, size: 100 }).subscribe(vehicleRes => {
          this.filteredVehicles = vehicleRes.body || [];
          if (this.filteredVehicles.length === 1) {
            this.showVehicleDropdown = false;
            const vehicle = this.filteredVehicles[0];
            this.editForm.patchValue({
              vehicleno: vehicle.vehiclenumber || '',
            });

            // Also try to link the latest job for this vehicle
            if (vehicle.vehiclenumber) {
              this.autocarejobService
                .query({ 'vehiclenumber.equals': vehicle.vehiclenumber, sort: ['id,desc'], size: 1 })
                .subscribe(jobRes => {
                  const latestJob = jobRes.body?.[0];
                  if (latestJob) {
                    this.editForm.patchValue({
                      autocarejobid: latestJob.id,
                    });
                  }
                  this.cdr.detectChanges();
                });
            }
          } else if (this.filteredVehicles.length > 1) {
            this.showVehicleDropdown = true;
            // Clear vehicle number to force selection from dropdown
            this.editForm.patchValue({
              vehicleno: '',
              autocarejobid: null,
            });
          } else {
            this.showVehicleDropdown = false;
          }
          this.cdr.detectChanges();
        });
      }
      this.cdr.detectChanges();
    }
  }

  previousState(): void {
    window.history.back();
  }

  checkDuplicateActiveInvoice(): Observable<HttpResponse<{ exists: boolean }>> {
    this.calculateDiscount();
    const salesinvoice = this.salesinvoiceFormService.getSalesinvoice(this.editForm);
    return this.salesinvoiceService.checkDuplicate(salesinvoice);
  }

  save(): void {
    // Guard: customer must be selected before saving
    const customerId = this.editForm.get('customerid')?.value;
    const customerName = this.editForm.get('customername')?.value;
    if (!customerId && !customerName) {
      alert('Please select a customer before saving the Sales Invoice.');
      return;
    }

    this.calculateDiscount();
    this.isSaving = true;

    this.accountService.identity().subscribe(account => {
      const now = this.localNow();
      const salesinvoice = this.salesinvoiceFormService.getSalesinvoice(this.editForm);

      // Apply shifted current time to all requested date fields
      salesinvoice.invoicedate = now;
      salesinvoice.delieverydate = now;
      salesinvoice.lmd = now;
      if (account) {
        salesinvoice.lmu = account.id;
      }

      // Force orderid to be 0 as requested
      salesinvoice.orderid = 0;

      // Ensure totaltax defaults to 0 if not set
      salesinvoice.totaltax = salesinvoice.totaltax ?? 0;

      // Redundant mappings to cover potential backend naming inconsistencies
      (salesinvoice as any).orderID = salesinvoice.orderid;
      (salesinvoice as any).orderId = salesinvoice.orderid;
      (salesinvoice as any).TotalTax = salesinvoice.totaltax;
      (salesinvoice as any).totalTax = salesinvoice.totaltax;

      if (salesinvoice.paymenttype?.toLowerCase() === 'cash') {
        const nettotal = salesinvoice.nettotal ?? 0;
        const paidamount = salesinvoice.paidamount ?? 0;
        if (paidamount > nettotal) {
          salesinvoice.paidamount = nettotal;
        }
      }

      if (salesinvoice.id !== null) {
        // Update
        this.subscribeToSaveResponse(this.salesinvoiceService.update(salesinvoice));
      } else {
        // Create
        salesinvoice.createddate = now;
        salesinvoice.locationid = 0;
        salesinvoice.isactive = true;
        salesinvoice.nbtamount = 0;
        salesinvoice.vatamount = 0;
        salesinvoice.invcanceldate = null;
        salesinvoice.advancepayment = 0;

        const nettotal = salesinvoice.nettotal ?? 0;
        const paidamount = salesinvoice.paidamount ?? 0;
        const pendingamount = nettotal - paidamount;

        if (salesinvoice.paymenttype?.toLowerCase() === 'cash') {
          salesinvoice.pendingamount = 0;
          salesinvoice.amountowing = 0;
        } else {
          salesinvoice.pendingamount = pendingamount;
          salesinvoice.amountowing = pendingamount;
        }

        if (account) {
          salesinvoice.createdbyid = account.id;
          salesinvoice.createdbyname = account.firstName;
        }

        this.subscribeToSaveResponse(this.salesinvoiceService.create(salesinvoice));
      }
    });
  }

  incrementId(id: string): string {
    const match = id.match(/^([A-Za-z]+)(\d+)$/);
    if (!match) return id;
    const prefix = match[1];
    const number = parseInt(match[2], 10) + 1;
    return `${prefix}${number}`;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISalesinvoice>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: response => {
        if (response.status === 201 || response.status === 200) {
          const invoiceId = response.body?.id;
          if (invoiceId) {
            console.log(`Sales invoice ${response.status === 201 ? 'created' : 'updated'}:`, invoiceId);

            const childSaveObservables: Observable<any>[] = [];

            if (response.status === 201) {
              const sharedSubId = window.crypto.randomUUID
                ? window.crypto.randomUUID()
                : 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, c => {
                    const r = (Math.random() * 16) | 0,
                      v = c == 'x' ? r : (r & 0x3) | 0x8;
                    return v.toString(16);
                  });
              this.salesInvoiceLinesService.setSubId(sharedSubId);
              this.salesInvoiceLinesUpdateComponent.transactionmodule(invoiceId);
            }

            // Collect save observables from child components
            if (this.salesInvoiceLinesUpdateComponent) {
              childSaveObservables.push(this.salesInvoiceLinesUpdateComponent.save(invoiceId));
            }
            if (this.SalesInvoiceServiceChargeLinesUpdateComponent) {
              childSaveObservables.push(this.SalesInvoiceServiceChargeLinesUpdateComponent.save(invoiceId));
            }
            if (this.SaleInvoiceCommonServiceChargesUpdateComponent) {
              childSaveObservables.push(this.SaleInvoiceCommonServiceChargesUpdateComponent.save(invoiceId));
            }
            if (response.status === 201 && response.body?.autocarejobid) {
              childSaveObservables.push(
                this.autocarejobService.partialUpdate({ id: response.body.autocarejobid, isjobclose: true, isjobinvoiced: true }),
              );
            }

            if (childSaveObservables.length > 0) {
              forkJoin(childSaveObservables).subscribe({
                next: () => {
                  console.log('All child components saved successfully.');
                  this.generateMobileAppPdfAndFinish(invoiceId);
                },
                error: err => {
                  console.error('Error saving child components:', err);
                },
              });
            } else {
              this.generateMobileAppPdfAndFinish(invoiceId);
            }
          }
        }
      },
      error: err => {
        console.error('Error Response:', err);
        this.onSaveError(err);
      },
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(error?: any): void {
    if (error?.status === 400 && error?.error?.message === 'error.salesinvoiceexists') {
      alert('This sales invoice already exists.');
    }
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  private generateMobileAppPdfAndFinish(invoiceId: number): void {
    this.salesinvoiceService.generateMobileAppPdf(invoiceId).subscribe({
      next: response => {
        console.log('Mobile app invoice PDF saved:', response.body?.filePath);
        this.cleanupReceiptModalBackdrop();
        window.open('/printinvoice?id=' + invoiceId, '_blank');
        this.previousState();
      },
      error: err => {
        console.error('Failed to save mobile app invoice PDF:', err);
        this.cleanupReceiptModalBackdrop();
        window.open('/printinvoice?id=' + invoiceId, '_blank');
        this.previousState();
      },
    });
  }

  private cleanupReceiptModalBackdrop(): void {
    const modalElement = document.getElementById('exampleModal');
    const modalInstance =
      modalElement && typeof bootstrap !== 'undefined' && bootstrap.Modal ? bootstrap.Modal.getInstance(modalElement) : null;

    if (modalInstance) {
      modalInstance.hide();
    }

    if (modalElement) {
      modalElement.classList.remove('show');
      modalElement.setAttribute('aria-hidden', 'true');
      modalElement.removeAttribute('aria-modal');
      modalElement.removeAttribute('role');
      modalElement.style.display = 'none';
    }

    document.querySelectorAll('.modal-backdrop').forEach(backdrop => backdrop.remove());
    document.body.classList.remove('modal-open');
    document.body.style.removeProperty('overflow');
    document.body.style.removeProperty('padding-right');
  }

  protected updateForm(salesinvoice: ISalesinvoice): void {
    this.salesinvoice = salesinvoice;
    this.salesinvoiceFormService.resetForm(this.editForm, salesinvoice);
  }
}
