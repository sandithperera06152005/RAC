import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { forkJoin, Observable } from 'rxjs';
import { debounceTime, finalize } from 'rxjs/operators';
import { FormsModule, ReactiveFormsModule, FormArray, FormGroup } from '@angular/forms';
import SharedModule from 'app/shared/shared.module';

import { ISaleInvoiceCommonServiceCharge, NewSaleInvoiceCommonServiceCharge } from '../sale-invoice-common-service-charge.model';
import { SaleInvoiceCommonServiceChargeService } from '../service/sale-invoice-common-service-charge.service';
import {
  SaleInvoiceCommonServiceChargeFormGroup,
  SaleInvoiceCommonServiceChargeFormService,
} from './sale-invoice-common-service-charge-form.service';
import { IServicesubcategory } from 'app/entities/servicesubcategory/servicesubcategory.model';

@Component({
  standalone: true,
  selector: 'jhi-sale-invoice-common-service-charge-update',
  templateUrl: './sale-invoice-common-service-charge-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class SaleInvoiceCommonServiceChargeUpdateComponent implements OnInit {
  isSaving = false;
  saleInvoiceCommonServiceCharge: ISaleInvoiceCommonServiceCharge[] = [];
  filteredItems: IServicesubcategory[][] = [];
  protected saleInvoiceCommonServiceChargeService = inject(SaleInvoiceCommonServiceChargeService);
  protected saleInvoiceCommonServiceChargeFormService = inject(SaleInvoiceCommonServiceChargeFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: FormGroup = new FormGroup({
    serviceCharges: new FormArray([]),
  });
  get serviceChargesArray(): FormArray {
    return this.editForm.get('serviceCharges') as FormArray;
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ saleInvoiceCommonServiceCharges }) => {
      if (saleInvoiceCommonServiceCharges && saleInvoiceCommonServiceCharges.length > 0) {
        this.saleInvoiceCommonServiceCharge = saleInvoiceCommonServiceCharges;
        this.updateForm(saleInvoiceCommonServiceCharges);
      } else {
        this.addServiceChargeDummy(); // Add default row when no data is available
      }
    });
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

        mainid: selectedItem.mainid,
        // Add any other fields you want to update with the selected item's details
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

    this.saleInvoiceCommonServiceChargeService
      .getElementsByUserInputCode(value) // Call the service to fetch items
      .pipe(debounceTime(300)) // Debounce to avoid frequent calls
      .subscribe({
        next: (response: HttpResponse<IServicesubcategory[]>) => {
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

  save(inid: number): void {
    this.isSaving = true;

    const serviceCharges = this.serviceChargesArray.value.map((line: any, index: number) => ({
      ...line,
      invoiceId: inid, // Assign invoice ID
      lineid: line.lineid ?? index + 1, // Ensure unique line ID
      optionid: index + 1, // Default option ID to 0
    }));

    console.log('Modified sales invoice lines:', serviceCharges);

    // Log each dummy's id to check its value
    console.log('Service Charge Dummies before saving:', serviceCharges);

    const requests: Observable<HttpResponse<ISaleInvoiceCommonServiceCharge>>[] = serviceCharges.map(
      (dummy: ISaleInvoiceCommonServiceCharge | NewSaleInvoiceCommonServiceCharge) => {
        console.log('Processing Dummy - ID:', dummy.id); // Log ID of each dummy

        return dummy.id
          ? this.saleInvoiceCommonServiceChargeService.update(dummy)
          : this.saleInvoiceCommonServiceChargeService.create({ ...dummy, id: null });
      },
    );

    forkJoin(requests)
      .pipe(finalize(() => this.onSaveFinalize()))
      .subscribe({
        next: () => this.onSaveSuccess(),
        error: () => this.onSaveError(),
      });
  }
  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISaleInvoiceCommonServiceCharge>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }
  addServiceChargeDummy(): void {
    // Push a new form group into the serviceChargeDummies array
    const newDummy = this.saleInvoiceCommonServiceChargeFormService.createSaleInvoiceCommonServiceChargeFormGroup();
    this.serviceChargesArray.push(newDummy);
  }
  removeServiceChargeDummy(index: number): void {
    this.serviceChargesArray.removeAt(index);
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
}
