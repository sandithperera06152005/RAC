import { Component, EventEmitter, Input, OnInit, Output, SimpleChanges, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable, forkJoin } from 'rxjs';
import { debounceTime, finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule, FormArray, FormBuilder, FormGroup, FormControl } from '@angular/forms';
import { ICommonserviceoption } from 'app/entities/commonserviceoption/commonserviceoption.model';
import {
  ISaleInvoiceCommonServiceChargeDummy,
  NewSaleInvoiceCommonServiceChargeDummy,
} from '../sale-invoice-common-service-charge-dummy.model';
import { SaleInvoiceCommonServiceChargeDummyService } from '../service/sale-invoice-common-service-charge-dummy.service';
import { SaleInvoiceCommonServiceChargeDummyFormService } from './sale-invoice-common-service-charge-dummy-form.service';

@Component({
  standalone: true,
  selector: 'jhi-sale-invoice-common-service-charge-dummy-update',
  templateUrl: './sale-invoice-common-service-charge-dummy-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class SaleInvoiceCommonServiceChargeDummyUpdateComponent implements OnInit {
  isSaving = false;
  @Input() readonly = false;
  saleInvoiceCommonServiceChargeDummies: ISaleInvoiceCommonServiceChargeDummy[] = [];
  filteredItems: ICommonserviceoption[][] = []; // Array of arrays to store filtered items for each row
  showCodeField: boolean = true;
  protected saleInvoiceCommonServiceChargeDummyService = inject(SaleInvoiceCommonServiceChargeDummyService);
  protected saleInvoiceCommonServiceChargeDummyFormService = inject(SaleInvoiceCommonServiceChargeDummyFormService);
  protected activatedRoute = inject(ActivatedRoute);
  commonServiceOptions: ICommonserviceoption[] = [];
  @Output() totalUpdated = new EventEmitter<number>();
  @Input() fetchedServicesCommon: any;
  editForm: FormGroup = new FormGroup({
    serviceChargeDummies: new FormArray([]),
  });
  protected fb = inject(FormBuilder);

  get serviceChargeDummiesArray(): FormArray {
    return this.editForm.get('serviceChargeDummies') as FormArray;
  }
  ngOnChanges(changes: SimpleChanges): void {
    if (changes['fetchedServicesCommon'] && this.fetchedServicesCommon) {
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
      id: [item.id],
      name: [item.itemname],
      value: [item.sellingprice],
      isCustomerService: [false],
      description: [item.itemname],
      code: [item.itemcode],
    });

    // Add the new form group to the form array
    this.serviceChargeDummiesArray.push(newItem);
    this.totalvalue(newItem);
  }
  totalvalue(formGroup: FormGroup): void {
    const value = formGroup.get('value');

    // Update the line total when the value changes
    value?.valueChanges.pipe(debounceTime(300)).subscribe(() => this.updateLineTotal());

    // Initialize the line total when the form is added
    this.updateLineTotal();
  }
  totalsum: number = 0; // Global variable to store total value
  updateLineTotal(): void {
    // Calculate the total by summing up all values in the serviceChargeLines array
    const total = this.serviceChargeDummiesArray.controls
      .map(control => control.get('value')?.value || 0)
      .reduce((acc, value) => acc + value, 0);

    // Emit the total to the parent component
    this.totalUpdated.emit(total);
    console.log('Updated Total cccccccccccccccccccccccc:', total); // Log the updated total
    this.totalsum = total;
  }
  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ saleInvoiceCommonServiceChargeDummies }) => {
      if (saleInvoiceCommonServiceChargeDummies && saleInvoiceCommonServiceChargeDummies.length > 0) {
        this.saleInvoiceCommonServiceChargeDummies = saleInvoiceCommonServiceChargeDummies;
        this.updateForm(saleInvoiceCommonServiceChargeDummies);
      }
    });
    this.fetchCommonServiceOptions();
  }
  searchTerm: string = '';

  get filteredCommonOptions(): ICommonserviceoption[] {
    return this.commonServiceOptions.filter(option => option.name?.toLowerCase().includes(this.searchTerm.toLowerCase()));
  }

  fetchCommonServiceOptions(): void {
    this.saleInvoiceCommonServiceChargeDummyService
      .getElementsByUserInputCode()
      .pipe(debounceTime(300))
      .subscribe({
        next: (response: HttpResponse<ICommonserviceoption[]>) => {
          this.commonServiceOptions = response.body || [];
          console.log('API response items:', this.commonServiceOptions);
        },
        error: err => console.error('Error fetching common service options:', err),
      });
  }

  toggleService(option: ICommonserviceoption): void {
    const index = this.serviceChargeDummiesArray.controls.findIndex(control => {
      const group = control as FormGroup;
      return group.get('id')?.value === option.id;
    });

    if (index === -1) {
      // Add to table
      const formGroup = new FormGroup({
        id: new FormControl(option.id),
        description: new FormControl(option.description),
        name: new FormControl(option.name),
        value: new FormControl(option.value),
        mainid: new FormControl(option.mainid),
        code: new FormControl(''),
      });
      this.serviceChargeDummiesArray.push(formGroup);
    } else {
      // Remove from table
      this.serviceChargeDummiesArray.removeAt(index);
    }
    this.updateLineTotal();
  }

  isSelected(optionId: number | undefined): boolean {
    return this.serviceChargeDummiesArray.controls.some(control => {
      const group = control as FormGroup;
      return group.get('id')?.value === optionId;
    });
  }

  calculateTotal(total: number): void {
    console.log('Total Value:', total);
    this.totalUpdated.emit(total);
  }

  onItemCodeSelect(event: Event, index: number): void {
    const inputElement = <HTMLInputElement>event.target;
    const selectedItemCode = inputElement.value;
    const selectedItem = this.filteredItems[index].find(item => item.name === selectedItemCode);

    if (selectedItem) {
      console.log('Selected item:', selectedItem);
      const salesInvoiceLineGroup = this.serviceChargeDummiesArray.at(index) as FormGroup;

      salesInvoiceLineGroup.patchValue({
        description: selectedItem.description,
        name: selectedItem.name,
        value: selectedItem.value,
        mainid: selectedItem.mainid,
      });
      console.log(salesInvoiceLineGroup.value);
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

    this.saleInvoiceCommonServiceChargeDummyService
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
        error: error => {
          console.error('Error fetching items:', error);
          this.filteredItems[index] = []; // Clear suggestions on error
        },
      });
  }

  previousState(): void {
    window.history.back();
  }

  save(inid: number): void {
    this.isSaving = true;

    const serviceChargeDummies = this.serviceChargeDummiesArray.value.map((line: any, index: number) => ({
      ...line,
      invoiceid: inid, // Assign invoice ID
      lineid: line.lineid ?? index + 1, // Ensure unique line ID
      optionid: index + 1, // Default option ID to 0
    }));

    console.log('Modified sales invoice lines:', serviceChargeDummies);

    // Log each dummy's id to check its value
    console.log('Service Charge Dummies before saving:', serviceChargeDummies);

    const requests: Observable<HttpResponse<ISaleInvoiceCommonServiceChargeDummy>>[] = serviceChargeDummies.map(
      (dummy: ISaleInvoiceCommonServiceChargeDummy | NewSaleInvoiceCommonServiceChargeDummy) => {
        console.log('Processing Dummy - ID:', dummy.id); // Log ID of each dummy

        return dummy.id
          ? this.saleInvoiceCommonServiceChargeDummyService.update(dummy)
          : this.saleInvoiceCommonServiceChargeDummyService.create({ ...dummy, id: null });
      },
    );

    forkJoin(requests)
      .pipe(finalize(() => this.onSaveFinalize()))
      .subscribe({
        next: () => this.onSaveSuccess(),
        error: () => this.onSaveError(),
      });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Handle API error
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(saleInvoiceCommonServiceChargeDummies: ISaleInvoiceCommonServiceChargeDummy[]): void {
    this.serviceChargeDummiesArray.clear();
    saleInvoiceCommonServiceChargeDummies.forEach(dummy => {
      this.serviceChargeDummiesArray.push(
        this.saleInvoiceCommonServiceChargeDummyFormService.createSaleInvoiceCommonServiceChargeDummyFormGroup(dummy),
      );
    });
  }

  // Inside SaleInvoiceCommonServiceChargeDummyUpdateComponent

  addServiceChargeDummy(): void {
    // Push a new form group into the serviceChargeDummies array
    const newDummy = this.saleInvoiceCommonServiceChargeDummyFormService.createSaleInvoiceCommonServiceChargeDummyFormGroup();
    this.serviceChargeDummiesArray.push(newDummy);
  }

  removeServiceChargeDummy(index: number): void {
    this.serviceChargeDummiesArray.removeAt(index);
  }
}
