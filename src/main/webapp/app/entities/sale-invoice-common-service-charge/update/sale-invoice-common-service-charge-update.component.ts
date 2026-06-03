import { Component, EventEmitter, OnInit, Output, Input, inject, SimpleChanges } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { forkJoin, Observable, of } from 'rxjs';
import { debounceTime, finalize } from 'rxjs/operators';
import { FormsModule, ReactiveFormsModule, FormArray, FormGroup, FormControl, FormBuilder } from '@angular/forms';
import SharedModule from 'app/shared/shared.module';

import { ISaleInvoiceCommonServiceCharge, NewSaleInvoiceCommonServiceCharge } from '../sale-invoice-common-service-charge.model';
import { SaleInvoiceCommonServiceChargeService } from '../service/sale-invoice-common-service-charge.service';
import {
  SaleInvoiceCommonServiceChargeFormGroup,
  SaleInvoiceCommonServiceChargeFormService,
} from './sale-invoice-common-service-charge-form.service';
import { ICommonserviceoption } from 'app/entities/commonserviceoption/commonserviceoption.model';
import { DecimalInputDirective } from 'app/shared/decimal-input.directive';
import { AutojobsaleinvoicecommonservicechargeService } from 'app/entities/autojobsaleinvoicecommonservicecharge/service/autojobsaleinvoicecommonservicecharge.service';
import { NewAutojobsaleinvoicecommonservicecharge } from 'app/entities/autojobsaleinvoicecommonservicecharge/autojobsaleinvoicecommonservicecharge.model';

@Component({
  standalone: true,
  selector: 'jhi-sale-invoice-common-service-charge-update',
  templateUrl: './sale-invoice-common-service-charge-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule, DecimalInputDirective],
})
export class SaleInvoiceCommonServiceChargeUpdateComponent implements OnInit {
  isSaving = false;
  saleInvoiceCommonServiceCharge: ISaleInvoiceCommonServiceCharge[] = [];
  filteredItems: ICommonserviceoption[][] = [];
  showCodeField: boolean = true;
  protected saleInvoiceCommonServiceChargeService = inject(SaleInvoiceCommonServiceChargeService);
  protected saleInvoiceCommonServiceChargeFormService = inject(SaleInvoiceCommonServiceChargeFormService);
  protected activatedRoute = inject(ActivatedRoute);
  protected autojobsaleinvoicecommonservicechargeService = inject(AutojobsaleinvoicecommonservicechargeService);
  @Input() fetchedServicesCommon: any;
  @Input() allowManual: boolean = true;
  @Input() sourceInvoiceId: number | null = null;
  commonServiceOptions: ICommonserviceoption[] = [];
  @Output() totalUpdated = new EventEmitter<number>();
  searchTerm: string = '';

  get filteredCommonOptions(): ICommonserviceoption[] {
    if (!this.searchTerm) {
      return this.commonServiceOptions;
    }
    const lowerTerm = this.searchTerm.toLowerCase();
    return this.commonServiceOptions.filter(option => option.name?.toLowerCase().includes(lowerTerm));
  }
  protected fb = inject(FormBuilder);
  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: FormGroup = new FormGroup({
    serviceCharges: new FormArray([]),
  });
  get serviceChargesArray(): FormArray {
    return this.editForm.get('serviceCharges') as FormArray;
  }
  updateLineTotal(): void {
    // Calculate the total by summing up all values in the serviceChargeLines array
    const total = this.serviceChargesArray.controls
      .map(control => Number(control.get('value')?.value || 0))
      .reduce((acc, value) => acc + value, 0);

    // Emit the total to the parent component
    this.totalUpdated.emit(total);
    console.log('Updated Total cccccccccccccccccccccccc:', total); // Log the updated total
  }
  ngOnChanges(changes: SimpleChanges): void {
    if (changes['fetchedServicesCommon'] && this.fetchedServicesCommon) {
      this.serviceChargesArray.clear();
      // Loop through the fetchedItems array and add each item to the form array
      this.fetchedServicesCommon.forEach((item: any) => {
        this.addItemToFormArray(item);
      });
      console.log('Fetched Items on Change:', this.fetchedServicesCommon); // Log fetched items
    }
  }
  addItemToFormArray(item: any): void {
    // Create a new form group for the item
    const newItem = this.fb.group({
      id: [null],
      name: [item.itemname],
      value: [item.sellingprice],
      isCustomerService: [false],
      description: [item.itemname],
      code: [item.itemcode || item.code],
      optionId: [item.optionId ?? null],
      mainId: [item.mainId ?? null],
      servicePrice: [item.servicePrice || item.sellingprice || 0],
      discount: [item.discount || 0],
      sourceAutoJobLineId: [item.id ?? null],
      sourceAutoJobInvoiceId: [item.sourceAutoJobInvoiceId ?? null],
      sourceAutoJobLineNumber: [item.sourceAutoJobLineNumber ?? null],
    });

    // Add the new form group to the form array
    this.serviceChargesArray.push(newItem);
    this.totalvalue(newItem);
  }
  totalvalue(formGroup: FormGroup): void {
    const value = formGroup.get('value');

    // Update the line total when the value changes
    value?.valueChanges.pipe(debounceTime(300)).subscribe(() => this.updateLineTotal());

    // Initialize the line total when the form is added
    this.updateLineTotal();
  }
  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ saleInvoiceCommonServiceCharges }) => {
      if (saleInvoiceCommonServiceCharges && saleInvoiceCommonServiceCharges.length > 0) {
        this.saleInvoiceCommonServiceCharge = saleInvoiceCommonServiceCharges;
        this.updateForm(saleInvoiceCommonServiceCharges);
      }
    });
    this.fetchCommonServiceOptions();
  }
  fetchCommonServiceOptions(): void {
    this.saleInvoiceCommonServiceChargeService
      .getElementsByUserInputCode()
      .pipe(debounceTime(300)) // Avoid frequent API calls
      .subscribe({
        next: (response: HttpResponse<ICommonserviceoption[]>) => {
          this.commonServiceOptions = response.body || [];
          console.log('API response items:', this.commonServiceOptions);
        },
        error: err => {
          console.error('Error fetching common service options:', err);
        },
      });
  }
  onCheckboxChange(event: Event, option: ICommonserviceoption): void {
    const checkbox = event.target as HTMLInputElement;

    if (checkbox.checked) {
      // Create the form group for the selected option
      const formGroup = this.fb.group({
        id: [null],
        optionId: [option.id],
        description: [option.description],
        name: [option.name],
        value: [option.value],
        mainId: [option.mainid],
        code: [option.code],
        servicePrice: [option.value],
        discount: [0],
      });

      // Check if we can reuse an empty row
      const emptyRowIndex = this.serviceChargesArray.controls.findIndex(control => {
        const group = control as FormGroup;
        return !group.get('id')?.value && !group.get('optionId')?.value && !group.get('name')?.value;
      });

      if (emptyRowIndex !== -1) {
        (this.serviceChargesArray.at(emptyRowIndex) as FormGroup).patchValue({
          optionId: option.id,
          description: option.description,
          name: option.name,
          value: option.value,
          mainId: option.mainid,
          code: option.code,
          servicePrice: option.value,
          discount: 0,
        });
        this.totalvalue(this.serviceChargesArray.at(emptyRowIndex) as FormGroup);
      } else {
        this.serviceChargesArray.push(formGroup);
        this.totalvalue(formGroup);
      }
    } else {
      // Remove the option if unchecked
      const index = this.serviceChargesArray.controls.findIndex(control => {
        const group = control as FormGroup;
        return group.get('optionId')?.value === option.id;
      });

      if (index !== -1) {
        this.serviceChargesArray.removeAt(index);
        this.updateLineTotal();
      }
    }
  }

  isSelected(id: number): boolean {
    return this.serviceChargesArray.controls.some(control => {
      const group = control as FormGroup;
      return group.get('optionId')?.value === id;
    });
  }

  toggleService(option: ICommonserviceoption): void {
    const alreadySelected = this.isSelected(option.id);
    if (alreadySelected) {
      // Uncheck logic
      this.onCheckboxChange({ target: { checked: false } } as any, option);
    } else {
      // Check logic
      this.onCheckboxChange({ target: { checked: true } } as any, option);
    }
  }
  onItemCodeSelect(event: Event, index: number): void {
    // Get the selected value (the item code)
    const inputElement = <HTMLInputElement>event.target;
    const selectedItemCode = inputElement.value; // The item code entered by the user

    // Find the matching item from the filteredItems array
    const selectedItem = this.filteredItems[index].find(item => item.name === selectedItemCode);

    // If the item is found, update the form for this row with the item's details
    if (selectedItem) {
      console.log('Selected item:', selectedItem);

      // Update form controls for this row (e.g., item code, item name, etc.)
      const salesInvoiceLineGroup = this.serviceChargesArray.at(index) as FormGroup;

      salesInvoiceLineGroup.patchValue({
        description: selectedItem.description,
        name: selectedItem.name,
        optionId: selectedItem.id,
        mainId: selectedItem.mainid,
        code: selectedItem.code,
        value: selectedItem.value,
        servicePrice: selectedItem.value,
      });
      console.log(salesInvoiceLineGroup.value);
      this.totalvalue(salesInvoiceLineGroup);
    } else {
      console.log('Item not found for code:', selectedItemCode);
    }
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

    this.saleInvoiceCommonServiceChargeService
      .getElementsByUserInputCode() // Call the service to fetch items
      .pipe(debounceTime(300)) // Debounce to avoid frequent calls
      .subscribe({
        next: (response: HttpResponse<ICommonserviceoption[]>) => {
          const items = response.body || [];

          // Log the response body items received
          console.log('API response items:', items);

          this.filteredItems[index] = items.filter(item => item && item.name); // Filter out invalid items
          console.log('Filtered items for index', index, ':', this.filteredItems[index]); // Log filtered items
        },
        error: (error: any) => {
          console.error('Error fetching items:', error);
          this.filteredItems[index] = []; // Clear suggestions on error
        },
      });
  }
  previousState(): void {
    window.history.back();
  }

  save(inid: number): Observable<any> {
    this.isSaving = true;

    const serviceCharges = this.serviceChargesArray.controls.map((control, index) => {
      const line = (control as FormGroup).getRawValue();
      const resolvedOption = this.resolveCommonServiceOption(line);

      return {
        ...line,
        ...resolvedOption,
        invoiceId: inid, // Assign invoice ID
        lineId: index + 1, // Ensure unique line ID for this invoice
      };
    });

    console.log('Modified sales invoice lines:', serviceCharges);

    // Log each dummy's id to check its value
    console.log('Service Charge Dummies before saving:', serviceCharges);

    const requests: Observable<any>[] = [];

    // Calculate next lineid for AutoJobs if we have a sourceInvoiceId
    let nextAutoLineId = 1;
    if (this.sourceInvoiceId) {
      const existingLineIds = serviceCharges
        .map((l: any) => Number(l.sourceAutoJobLineNumber))
        .filter((lineId: number) => Number.isFinite(lineId) && lineId > 0);
      if (existingLineIds.length > 0) {
        nextAutoLineId = Math.max(...existingLineIds) + 1;
      }
    }

    serviceCharges.forEach((dummy: any) => {
      console.log('Processing Dummy - ID:', dummy.id);
      // If the charge doesn't have an ID, or if it belongs to a different invoice, create it as new
      if (!dummy.id || dummy.invoiceId !== inid) {
        const saleInvoiceCommonServiceCharge = this.toSaleInvoiceCommonServiceChargePayload(dummy);
        requests.push(this.saleInvoiceCommonServiceChargeService.create({ ...saleInvoiceCommonServiceCharge, id: null, invoiceId: inid }));

        // ONLY save to AutoJobs if it's TRULY a new item added on this page
        if (this.sourceInvoiceId && !dummy.id && !this.isSourceAutoJobLine(dummy)) {
          const autoLine: NewAutojobsaleinvoicecommonservicecharge = {
            id: null,
            invoiceid: this.sourceInvoiceId,
            lineid: nextAutoLineId++,
            optionid: dummy.optionId,
            mainid: dummy.mainId,
            code: dummy.code,
            name: dummy.name,
            description: dummy.description,
            value: dummy.value,
            discount: dummy.discount,
            serviceprice: dummy.servicePrice,
          };
          requests.push(this.autojobsaleinvoicecommonservicechargeService.create(autoLine));
        }
      } else {
        // If it's an existing charge for THIS invoice, update it
        requests.push(
          this.saleInvoiceCommonServiceChargeService.update(
            this.toSaleInvoiceCommonServiceChargePayload(dummy) as ISaleInvoiceCommonServiceCharge,
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

  private toSaleInvoiceCommonServiceChargePayload(line: any): ISaleInvoiceCommonServiceCharge | NewSaleInvoiceCommonServiceCharge {
    const { sourceAutoJobLineId, sourceAutoJobInvoiceId, sourceAutoJobLineNumber, isCustomerService, ...payload } = line;
    return payload as ISaleInvoiceCommonServiceCharge | NewSaleInvoiceCommonServiceCharge;
  }
  addServiceChargeDummy(): void {
    // Push a new form group into the serviceChargeDummies array
    const newDummy = this.saleInvoiceCommonServiceChargeFormService.createSaleInvoiceCommonServiceChargeFormGroup();
    this.serviceChargesArray.push(newDummy);
  }
  removeServiceChargeDummy(index: number): void {
    this.serviceChargesArray.removeAt(index);
    this.updateLineTotal();
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

  protected updateForm(saleInvoiceCommonServiceCharges: ISaleInvoiceCommonServiceCharge[]): void {
    this.serviceChargesArray.clear();
    saleInvoiceCommonServiceCharges.forEach(dummy => {
      this.serviceChargesArray.push(this.saleInvoiceCommonServiceChargeFormService.createSaleInvoiceCommonServiceChargeFormGroup(dummy));
    });
  }

  private resolveCommonServiceOption(line: any): Partial<ISaleInvoiceCommonServiceCharge> {
    const normalizedOptionId = Number(line.optionId) > 0 ? Number(line.optionId) : null;
    const normalizedMainId = Number(line.mainId) > 0 ? Number(line.mainId) : null;
    const normalizedCode = typeof line.code === 'string' && line.code.trim() !== '' ? line.code : null;
    const selectedOption = this.commonServiceOptions.find(
      option =>
        option.id === normalizedOptionId ||
        (typeof line.name === 'string' &&
          typeof option.name === 'string' &&
          option.name.trim().toLowerCase() === line.name.trim().toLowerCase()),
    );

    return {
      optionId: normalizedOptionId ?? selectedOption?.id ?? 0,
      mainId: normalizedMainId ?? selectedOption?.mainid ?? null,
      code: normalizedCode ?? selectedOption?.code ?? null,
      servicePrice: line.servicePrice ?? selectedOption?.value ?? line.value ?? 0,
    };
  }
}
