import { Component, EventEmitter, OnInit, Input, Output, inject, SimpleChanges } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { forkJoin, Observable, of } from 'rxjs';
import { debounceTime, finalize } from 'rxjs/operators';
import { FormBuilder, FormControl, FormGroup, FormArray } from '@angular/forms';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { IBillingserviceoption } from 'app/entities/billingserviceoption/billingserviceoption.model';
import { ISalesInvoiceServiceChargeLine, NewSalesInvoiceServiceChargeLine } from '../sales-invoice-service-charge-line.model';
import { SalesInvoiceServiceChargeLineService } from '../service/sales-invoice-service-charge-line.service';
import {
  SalesInvoiceServiceChargeLineFormGroup,
  SalesInvoiceServiceChargeLineFormService,
} from './sales-invoice-service-charge-line-form.service';
import { IVehicletype } from 'app/entities/vehicletype/vehicletype.model';
import { VehicletypeService } from 'app/entities/vehicletype/service/vehicletype.service';
import { DecimalInputDirective } from 'app/shared/decimal-input.directive';
import { AutojobsalesinvoiceservicechargelineService } from 'app/entities/autojobsalesinvoiceservicechargeline/service/autojobsalesinvoiceservicechargeline.service';
import { NewAutojobsalesinvoiceservicechargeline } from 'app/entities/autojobsalesinvoiceservicechargeline/autojobsalesinvoiceservicechargeline.model';

@Component({
  standalone: true,
  selector: 'jhi-sales-invoice-service-charge-line-update',
  templateUrl: './sales-invoice-service-charge-line-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule, DecimalInputDirective],
})
export class SalesInvoiceServiceChargeLineUpdateComponent implements OnInit {
  private static readonly DISCOUNT_ENTRY_PCT_MARKER = '[PCT]';
  private static readonly DISCOUNT_ENTRY_VAL_MARKER = '[VAL]';

  isSaving = false;
  showCodeField: boolean = true;
  salesInvoiceServiceChargeLine: ISalesInvoiceServiceChargeLine[] = [];
  filteredItems: IBillingserviceoption[][] = [];
  protected salesInvoiceServiceChargeLineService = inject(SalesInvoiceServiceChargeLineService);
  protected salesInvoiceServiceChargeLineFormService = inject(SalesInvoiceServiceChargeLineFormService);
  protected activatedRoute = inject(ActivatedRoute);
  protected fb = inject(FormBuilder);

  @Output() totalUpdated = new EventEmitter<number>(); // Emit total to parent
  @Output() totalDiscountUpdated = new EventEmitter<number>();
  protected vehicletypesService = inject(VehicletypeService);
  protected autojobsalesinvoiceservicechargelineService = inject(AutojobsalesinvoiceservicechargelineService);
  @Input() fetchedServices: any;
  @Input() allowManual: boolean = true;
  @Input() sourceInvoiceId: number | null = null;
  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: FormGroup = new FormGroup({
    serviceChargeLines: new FormArray([]),
  });

  typeid: number = 0;
  vehicletypes: IVehicletype[] = [];
  searchTerm: string = '';

  get flattenedBillingOptions(): any[] {
    return this.allBillingServiceOptions.reduce((acc, val) => acc.concat(val), []);
  }

  get filteredBillingOptions(): any[] {
    const flattened = this.flattenedBillingOptions;
    if (!this.searchTerm) {
      return flattened;
    }
    const lowerTerm = this.searchTerm.toLowerCase();
    return flattened.filter(option => option.servicename?.toLowerCase().includes(lowerTerm));
  }

  get serviceChargeLinesArray(): FormArray {
    return this.editForm.get('serviceChargeLines') as FormArray;
  }
  ngOnChanges(changes: SimpleChanges): void {
    if (changes['fetchedServices'] && this.fetchedServices) {
      this.serviceChargeLinesArray.clear();
      // Loop through the fetchedItems array and add each item to the form array
      this.fetchedServices.forEach((item: any) => {
        this.addItemToFormArray(item);
      });
      console.log('Fetched Items on Change:', this.fetchedServices); // Log fetched items
    }
  }
  addItemToFormArray(item: any): void {
    const formGroup = this.createServiceChargeLineFormGroup(item);
    this.serviceChargeLinesArray.push(formGroup);
    this.totalvalue(formGroup);
  }
  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ salesInvoiceServiceChargeLines }) => {
      if (salesInvoiceServiceChargeLines && salesInvoiceServiceChargeLines.length > 0) {
        this.salesInvoiceServiceChargeLine = salesInvoiceServiceChargeLines;
        this.updateForm(salesInvoiceServiceChargeLines);
      }
    });
    this.loadVehicleTypes();
  }
  totalvalue(formGroup: FormGroup): void {
    const discountControl = formGroup.get('discount');
    const valueControl = formGroup.get('value');
    discountControl?.valueChanges.pipe(debounceTime(300)).subscribe(() => this.updateLineTotal());
    valueControl?.valueChanges.pipe(debounceTime(300)).subscribe(() => this.updateLineTotal());

    this.updateLineTotal();
  }

  onDiscountPercentageChange(index: number): void {
    const formGroup = this.serviceChargeLinesArray.at(index) as FormGroup;
    const servicePrice = Number(formGroup.get('servicePrice')?.value || 0);
    const discountPercentage = Number(formGroup.get('discountpercentage')?.value || 0);
    const discount = Number(((servicePrice * discountPercentage) / 100).toFixed(2));

    formGroup.patchValue(
      {
        discountvalue: null,
        discount,
        discountEntryType: 'percentage',
      },
      { emitEvent: false },
    );
    this.applyNetValueFromDiscount(formGroup);
    this.updateLineTotal();
  }

  onDiscountValueChange(index: number): void {
    const formGroup = this.serviceChargeLinesArray.at(index) as FormGroup;
    const raw = formGroup.get('discountvalue')?.value;
    if (raw === null || raw === undefined || raw === '') {
      formGroup.patchValue(
        {
          discountpercentage: null,
          discountvalue: null,
          discount: 0,
          discountEntryType: 'value',
        },
        { emitEvent: false },
      );
      this.applyNetValueFromDiscount(formGroup);
      this.updateLineTotal();
      return;
    }

    const discountValue = this.parseDiscountInputValue(raw);
    if (discountValue === null) {
      return;
    }

    formGroup.patchValue(
      {
        discountpercentage: null,
        discount: discountValue,
        discountEntryType: 'value',
      },
      { emitEvent: false },
    );
    this.applyNetValueFromDiscount(formGroup);
    this.updateLineTotal();
  }

  onDiscountValueBlur(index: number, event: FocusEvent): void {
    const formGroup = this.serviceChargeLinesArray.at(index) as FormGroup;
    const raw = (event.target as HTMLInputElement).value;
    if (raw === null || raw === undefined || raw === '') {
      return;
    }

    const discountValue = this.parseDiscountInputValue(raw);
    if (discountValue === null) {
      formGroup.patchValue(
        {
          discountvalue: null,
          discount: 0,
        },
        { emitEvent: false },
      );
      this.applyNetValueFromDiscount(formGroup);
      this.updateLineTotal();
      return;
    }

    const formatted = discountValue.toFixed(2);
    const rounded = Number(formatted);
    formGroup.patchValue(
      {
        discountvalue: rounded > 0 ? formatted : null,
        discount: rounded > 0 ? rounded : 0,
        discountpercentage: null,
        discountEntryType: 'value',
      },
      { emitEvent: false },
    );
    this.applyNetValueFromDiscount(formGroup);
    this.updateLineTotal();
  }

  formatLineDiscountDisplay(index: number): string {
    const formGroup = this.serviceChargeLinesArray.at(index) as FormGroup;
    const discount = Number(formGroup?.get('discount')?.value || 0);
    return discount > 0 ? discount.toFixed(2) : '';
  }

  updateLineTotal(): void {
    const total = this.serviceChargeLinesArray.controls
      .map(control => Number(control.get('value')?.value || 0))
      .reduce((acc, value) => acc + value, 0);

    const totalLineDiscount = this.serviceChargeLinesArray.controls
      .map(control => Number(control.get('discount')?.value || 0))
      .reduce((acc, value) => acc + value, 0);

    this.totalUpdated.emit(total);
    this.totalDiscountUpdated.emit(totalLineDiscount);
  }

  private createServiceChargeLineFormGroup(item: any): FormGroup {
    const servicePrice = this.resolveServicePrice(item);
    const discount = Number(item.discount ?? 0);
    const { discountPercentage, discountValue } = this.resolveDiscountDisplayFields(item, discount, servicePrice);
    const netValue = Number((servicePrice - Math.min(Math.max(discount, 0), servicePrice)).toFixed(2));

    const formGroup = this.fb.group({
      id: [item.id ?? null],
      invoiceId: [item.invoiceId ?? null],
      lineId: [item.lineId ?? null],
      serviceName: [item.serviceName ?? item.itemname ?? ''],
      value: [item.value != null && item.value !== '' ? Number(item.value) : netValue],
      isCustomerService: [item.isCustomerService ?? false],
      optionId: [item.optionId ?? 0],
      serviceDescription: [item.serviceDescription ?? ''],
      discount: [discount],
      servicePrice: [servicePrice],
      discountpercentage: [discountPercentage],
      discountvalue: [discountValue],
      discountEntryType: [item.discountEntryType ?? null],
      sourceAutoJobLineId: [item.sourceAutoJobLineId ?? item.id ?? null],
      sourceAutoJobInvoiceId: [item.sourceAutoJobInvoiceId ?? null],
      sourceAutoJobLineNumber: [item.sourceAutoJobLineNumber ?? null],
    });

    this.ensureDiscountControls(formGroup);
    this.applyNetValueFromDiscount(formGroup);
    return formGroup;
  }

  /** Match pre-discount behaviour: autocare lines often have value in sellingprice while serviceprice is 0/null. */
  private resolveServicePrice(item: any): number {
    const explicitPrice = item.servicePrice ?? item.serviceprice;
    if (explicitPrice != null && Number(explicitPrice) > 0) {
      return Number(explicitPrice);
    }
    return Number(item.sellingprice ?? item.value ?? 0);
  }

  private ensureDiscountControls(formGroup: FormGroup): void {
    if (!formGroup.get('discountpercentage')) {
      formGroup.addControl('discountpercentage', new FormControl(null));
    }
    if (!formGroup.get('discountvalue')) {
      formGroup.addControl('discountvalue', new FormControl(null));
    }
    if (!formGroup.get('discountEntryType')) {
      formGroup.addControl('discountEntryType', new FormControl(null));
    }
  }

  private resolveDiscountDisplayFields(
    item: any,
    discount: number,
    servicePrice: number,
  ): { discountPercentage: number | null; discountValue: number | null } {
    const entryType = item.discountEntryType ?? this.parseDiscountEntryTypeFromDescription(item.serviceDescription);
    if (entryType === 'percentage') {
      const pct = Number(item.discountPercentage ?? item.discountpercentage ?? 0);
      const resolvedPct = pct > 0 ? pct : servicePrice > 0 && discount > 0 ? Number(((discount / servicePrice) * 100).toFixed(4)) : 0;
      return {
        discountPercentage: resolvedPct > 0 ? resolvedPct : null,
        discountValue: null,
      };
    }
    if (entryType === 'value') {
      return {
        discountPercentage: null,
        discountValue: discount > 0 ? Number(discount.toFixed(2)) : null,
      };
    }
    if (discount > 0 && servicePrice > 0) {
      return {
        discountPercentage: Number(((discount / servicePrice) * 100).toFixed(4)),
        discountValue: null,
      };
    }
    return { discountPercentage: null, discountValue: null };
  }

  private parseDiscountEntryTypeFromDescription(description: string | null | undefined): 'percentage' | 'value' | undefined {
    if (!description) {
      return undefined;
    }
    if (description.startsWith(SalesInvoiceServiceChargeLineUpdateComponent.DISCOUNT_ENTRY_PCT_MARKER)) {
      return 'percentage';
    }
    if (description.startsWith(SalesInvoiceServiceChargeLineUpdateComponent.DISCOUNT_ENTRY_VAL_MARKER)) {
      return 'value';
    }
    return undefined;
  }

  private buildDescriptionWithDiscountEntry(line: any, entryType: 'percentage' | 'value' | null): string | null {
    const base = String(line.serviceDescription ?? '')
      .replace(/^\[(?:PCT|VAL)\]/, '')
      .trim();
    if (entryType === 'percentage') {
      return `${SalesInvoiceServiceChargeLineUpdateComponent.DISCOUNT_ENTRY_PCT_MARKER}${base}`;
    }
    if (entryType === 'value') {
      return `${SalesInvoiceServiceChargeLineUpdateComponent.DISCOUNT_ENTRY_VAL_MARKER}${base}`;
    }
    return line.serviceDescription ?? null;
  }

  private inferDiscountEntryTypeFromForm(formGroup: FormGroup): 'percentage' | 'value' | null {
    const explicit = formGroup.get('discountEntryType')?.value;
    if (explicit === 'percentage' || explicit === 'value') {
      return explicit;
    }
    const pct = formGroup.get('discountpercentage')?.value;
    const hasPct = pct !== null && pct !== undefined && pct !== '';
    const val = formGroup.get('discountvalue')?.value;
    const hasVal = val !== null && val !== undefined && val !== '';
    if (hasPct && !hasVal) {
      return 'percentage';
    }
    if (hasVal && !hasPct) {
      return 'value';
    }
    return null;
  }

  private parseDiscountInputValue(raw: string | number | null | undefined): number | null {
    if (raw === null || raw === undefined || raw === '') {
      return null;
    }
    const parsed = Number(String(raw).replace(/,/g, ''));
    return Number.isFinite(parsed) ? parsed : null;
  }

  private applyNetValueFromDiscount(formGroup: FormGroup): void {
    const servicePrice = Number(formGroup.get('servicePrice')?.value || 0);
    const discount = Number(formGroup.get('discount')?.value || 0);
    const cappedDiscount = Math.min(Math.max(discount, 0), servicePrice);
    const netValue = Number((servicePrice - cappedDiscount).toFixed(2));

    formGroup.patchValue(
      {
        discount: cappedDiscount,
        value: netValue,
      },
      { emitEvent: false },
    );
  }

  loadVehicleTypes(): void {
    this.vehicletypesService.query({ size: 1000 }).subscribe((res: HttpResponse<IVehicletype[]>) => {
      this.vehicletypes = res.body || [];
      console.log('Loaded Vehicle Types:', this.vehicletypes); // Display the loaded vehicle types in the console
    });
  }
  allBillingServiceOptions: any[] = [];

  onDropdownChange(event: Event): void {
    // Get the selected dropdown value (the id of the selected vehicle type)
    const selectedValue = (event.target as HTMLSelectElement).value;
    const typeid = Number(selectedValue); // Parse the selected value to a number

    // Log the selected typeid to the console
    console.log('Selected Vehicle Type ID:', typeid);
    this.typeid = typeid;
    // Clear previous data before loading new data
    this.allBillingServiceOptions = [];

    // Make the API call to get the billingserviceoption ids
    this.salesInvoiceServiceChargeLineService.getElementsByID(typeid).subscribe({
      next: response => {
        // Log the full response to understand its structure
        console.log('API Response:', response);

        // Check if response.body is an array and contains the 'billingserviceoption' field
        if (response.body && Array.isArray(response.body)) {
          // Extract the 'billingserviceoption' ids from the response
          const billingserviceoptionIds = response.body.map((item: any) => item.billingserviceoptionid);

          // Log the billingserviceoption ids to the console
          console.log('Billing Service Option IDs:', billingserviceoptionIds);

          // Now, use the getbillingid function to fetch details for each billingserviceoptionid
          billingserviceoptionIds.forEach(id => {
            this.salesInvoiceServiceChargeLineService.getbillingid(id).subscribe({
              next: billingResponse => {
                // Log the detailed response for each billingserviceoption
                console.log('Billing Service Option Details for ID ' + id, billingResponse.body);

                // Add the billing service option details to the allBillingServiceOptions array
                if (billingResponse.body) {
                  this.allBillingServiceOptions.push(billingResponse.body);
                }
                console.log('All Billing Service Options:', this.allBillingServiceOptions);
              },

              error: err => {
                console.error('Error fetching details for Billing Service Option ID ' + id, err);
              },
            });
          });
        } else {
          console.error('Unexpected API response format:', response);
        }
      },
      error: err => {
        console.error('API Error:', err); // Log if there's an error
      },
    });
  }

  selectedServices: { id: number; servicename: string }[] = [];

  onCheckboxChange(event: any, servicename: string, id: number) {
    if (event.target.checked) {
      // Add logic
      console.log(`Fetching billing values for service ${id} and type ${this.typeid}`);
      this.salesInvoiceServiceChargeLineService.biliingvalues(id, this.typeid).subscribe({
        next: response => {
          const billingValues = response.body;
          const fetchedValue = billingValues && billingValues.length > 0 ? billingValues[0].value : 0;

          const formGroup = this.createServiceChargeLineFormGroup({
            serviceName: servicename,
            value: fetchedValue,
            isCustomerService: false,
            optionId: id,
            discount: 0,
            servicePrice: fetchedValue,
          });

          // Check if we can reuse an empty row
          const emptyRowIndex = this.serviceChargeLinesArray.controls.findIndex(control => {
            const group = control as FormGroup;
            return !group.get('id')?.value && !group.get('optionId')?.value && !group.get('serviceName')?.value;
          });

          if (emptyRowIndex !== -1) {
            (this.serviceChargeLinesArray.at(emptyRowIndex) as FormGroup).patchValue({
              serviceName: servicename,
              value: fetchedValue,
              optionId: id,
              servicePrice: fetchedValue,
            });
            this.totalvalue(this.serviceChargeLinesArray.at(emptyRowIndex) as FormGroup);
          } else {
            this.serviceChargeLinesArray.push(formGroup);
            this.totalvalue(formGroup);
          }

          if (!this.selectedServices.some(s => s.id === id)) {
            this.selectedServices.push({ id, servicename });
          }
        },
        error: err => console.error('Error fetching billing values', err),
      });
    } else {
      // Remove logic
      const index = this.serviceChargeLinesArray.controls.findIndex(control => {
        const group = control as FormGroup;
        return group.get('optionId')?.value === id;
      });

      if (index !== -1) {
        this.serviceChargeLinesArray.removeAt(index);
        this.updateLineTotal();
      }

      this.selectedServices = this.selectedServices.filter(service => service.id !== id);
    }
  }

  isSelected(id: number): boolean {
    return this.serviceChargeLinesArray.controls.some(control => {
      const group = control as FormGroup;
      return group.get('optionId')?.value === id;
    });
  }

  toggleService(option: any): void {
    const alreadySelected = this.isSelected(option.id);
    this.onCheckboxChange({ target: { checked: !alreadySelected } } as any, option.servicename, option.id);
  }

  onDropdownChan1ge(event: Event): number {
    return parseInt((event.target as HTMLSelectElement).value, 10);
  }

  previousState(): void {
    window.history.back();
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

    this.salesInvoiceServiceChargeLineService
      .getElementsByUserInputCode(value) // Call the service to fetch items
      .pipe(debounceTime(300)) // Debounce to avoid frequent calls
      .subscribe({
        next: (response: HttpResponse<IBillingserviceoption[]>) => {
          const items = response.body || [];

          // Log the response body items received
          console.log('API response items:', items);

          this.filteredItems[index] = items.filter(item => item && item.servicename); // Filter out invalid items
          console.log('Filtered items for index', index, ':', this.filteredItems[index]); // Log filtered items
        },
        error: error => {
          console.error('Error fetching items:', error);
          this.filteredItems[index] = []; // Clear suggestions on error
        },
      });
  }
  onItemCodeSelect(event: Event, index: number): void {
    // Get the selected value (the item code)
    const inputElement = <HTMLInputElement>event.target;
    const selectedItemCode = inputElement.value; // The item code entered by the user

    // Find the matching item from the filteredItems array
    const selectedItem = this.filteredItems[index].find(item => item.servicename === selectedItemCode);

    // If the item is found, update the form for this row with the item's details
    if (selectedItem) {
      console.log('Selected item:', selectedItem);

      // Update form controls for this row (e.g., item code, item name, etc.)
      const salesInvoiceLineGroup = this.serviceChargeLinesArray.at(index) as FormGroup;

      salesInvoiceLineGroup.patchValue({
        serviceDescription: selectedItem.servicediscription,
        serviceName: selectedItem.servicename,
        optionId: selectedItem.id,
      });
      console.log(salesInvoiceLineGroup.value);
      this.totalvalue(salesInvoiceLineGroup);
    } else {
      console.log('Item not found for code:', selectedItemCode);
    }
  }

  save(inid: number): Observable<any> {
    this.isSaving = true;

    const serviceChargeLines = this.serviceChargeLinesArray.controls.map((control, index) => {
      const line = (control as FormGroup).getRawValue();
      const entryType = this.inferDiscountEntryTypeFromForm(control as FormGroup);
      return {
        ...line,
        invoiceId: inid,
        lineId: index + 1,
        optionId: line.optionId,
        serviceDescription: this.buildDescriptionWithDiscountEntry(line, entryType),
      };
    });

    console.log('Modified sales invoice lines:', serviceChargeLines);

    const requests: Observable<any>[] = [];

    // Calculate next lineid for AutoJobs if we have a sourceInvoiceId
    let nextAutoLineId = 1;
    if (this.sourceInvoiceId) {
      const existingLineIds = serviceChargeLines
        .map((l: any) => Number(l.sourceAutoJobLineNumber))
        .filter((lineId: number) => Number.isFinite(lineId) && lineId > 0);
      if (existingLineIds.length > 0) {
        nextAutoLineId = Math.max(...existingLineIds) + 1;
      }
    }

    serviceChargeLines.forEach((line: any) => {
      console.log('Processing line:', line);
      // If the line doesn't have an ID, or if it belongs to a different invoice, create it as new
      if (!line.id || line.invoiceId !== inid) {
        const salesInvoiceLine = this.toSalesInvoiceServiceChargeLinePayload(line);
        requests.push(
          this.salesInvoiceServiceChargeLineService.create({
            ...salesInvoiceLine,
            id: null,
            invoiceId: inid,
          } as NewSalesInvoiceServiceChargeLine),
        );

        // ONLY save to AutoJobs if it's TRULY a new item added on this page
        if (this.sourceInvoiceId && !line.id && !this.isSourceAutoJobLine(line)) {
          const autoLine: NewAutojobsalesinvoiceservicechargeline = {
            id: null,
            invoiceid: this.sourceInvoiceId,
            lineid: nextAutoLineId++,
            optionid: line.optionId,
            servicename: line.serviceName,
            servicediscription: line.serviceDescription,
            value: line.value,
            addedbyid: line.addedById,
            iscustomersrvice: line.isCustomerService,
            discount: line.discount,
            serviceprice: line.servicePrice,
          };
          requests.push(this.autojobsalesinvoiceservicechargelineService.create(autoLine));
        }
      } else {
        // If it's an existing line for THIS invoice, update it
        requests.push(
          this.salesInvoiceServiceChargeLineService.update(
            this.toSalesInvoiceServiceChargeLinePayload(line) as ISalesInvoiceServiceChargeLine,
          ),
        );
      }
    });

    if (requests.length > 0) {
      return forkJoin(requests).pipe(finalize(() => this.onSaveFinalize()));
    } else {
      this.onSaveFinalize();
      return of(null);
    }
  }

  private isSourceAutoJobLine(line: any): boolean {
    return Number(line.sourceAutoJobLineId) > 0 || Number(line.sourceAutoJobInvoiceId) > 0 || Number(line.sourceAutoJobLineNumber) > 0;
  }

  private toSalesInvoiceServiceChargeLinePayload(line: any): ISalesInvoiceServiceChargeLine | NewSalesInvoiceServiceChargeLine {
    const {
      sourceAutoJobLineId,
      sourceAutoJobInvoiceId,
      sourceAutoJobLineNumber,
      discountpercentage,
      discountvalue,
      discountEntryType,
      ...payload
    } = line;
    return payload as ISalesInvoiceServiceChargeLine | NewSalesInvoiceServiceChargeLine;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISalesInvoiceServiceChargeLine>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }
  addServiceChargeLine(): void {
    const formGroup = this.createServiceChargeLineFormGroup({});
    this.serviceChargeLinesArray.push(formGroup);
    this.totalvalue(formGroup);
  }

  removeServiceChargeLine(index: number): void {
    this.serviceChargeLinesArray.removeAt(index);
    this.updateLineTotal();
  }

  protected updateForm(salesInvoiceServiceChargeLines: ISalesInvoiceServiceChargeLine[]): void {
    this.serviceChargeLinesArray.clear();
    salesInvoiceServiceChargeLines.forEach(line => {
      const formGroup = this.createServiceChargeLineFormGroup(line);
      this.serviceChargeLinesArray.push(formGroup);
      this.totalvalue(formGroup);
    });
  }
}
