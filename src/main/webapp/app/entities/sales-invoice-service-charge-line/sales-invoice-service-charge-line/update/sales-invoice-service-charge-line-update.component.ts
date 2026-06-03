import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { forkJoin, Observable } from 'rxjs';
import { debounceTime, finalize } from 'rxjs/operators';
import { FormBuilder, FormGroup, FormArray } from '@angular/forms';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { IBillingserviceoption } from 'app/entities/billingserviceoption/billingserviceoption.model';
import { ISalesInvoiceServiceChargeLine, NewSalesInvoiceServiceChargeLine } from '../sales-invoice-service-charge-line.model';
import { SalesInvoiceServiceChargeLineService } from '../service/sales-invoice-service-charge-line.service';
import {
  SalesInvoiceServiceChargeLineFormGroup,
  SalesInvoiceServiceChargeLineFormService,
} from './sales-invoice-service-charge-line-form.service';

@Component({
  standalone: true,
  selector: 'jhi-sales-invoice-service-charge-line-update',
  templateUrl: './sales-invoice-service-charge-line-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class SalesInvoiceServiceChargeLineUpdateComponent implements OnInit {
  isSaving = false;
  salesInvoiceServiceChargeLine: ISalesInvoiceServiceChargeLine[] = [];
  filteredItems: IBillingserviceoption[][] = [];
  protected salesInvoiceServiceChargeLineService = inject(SalesInvoiceServiceChargeLineService);
  protected salesInvoiceServiceChargeLineFormService = inject(SalesInvoiceServiceChargeLineFormService);
  protected activatedRoute = inject(ActivatedRoute);
  protected fb = inject(FormBuilder);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: FormGroup = new FormGroup({
    serviceChargeLines: new FormArray([]),
  });

  get serviceChargeLinesArray(): FormArray {
    return this.editForm.get('serviceChargeLines') as FormArray;
  }
  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ salesInvoiceServiceChargeLines }) => {
      if (salesInvoiceServiceChargeLines && salesInvoiceServiceChargeLines.length > 0) {
        this.salesInvoiceServiceChargeLine = salesInvoiceServiceChargeLines;
        this.updateForm(salesInvoiceServiceChargeLines);
      } else {
        this.addServiceChargeLine();
      }
    });
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
        name: selectedItem.servicename,

        // Add any other fields you want to update with the selected item's details
      });
      console.log(salesInvoiceLineGroup.value);
    } else {
      console.log('Item not found for code:', selectedItemCode);
    }
  }
  save(inid: number): void {
    this.isSaving = true;

    const serviceChargeLines = this.serviceChargeLinesArray.value.map((line: any, index: number) => ({
      ...line,
      invoiceId: inid, // Assign invoice ID
      lineId: line.lineid ?? index + 1, // Ensure unique line ID
      optionId: index + 1,
    }));

    console.log('Modified sales invoice lines:', serviceChargeLines);

    const requests: Observable<HttpResponse<ISalesInvoiceServiceChargeLine>>[] = serviceChargeLines.map(
      (line: ISalesInvoiceServiceChargeLine | NewSalesInvoiceServiceChargeLine) => {
        console.log('Processing line:', line);
        console.log('Line ID:', line?.id); // Use optional chaining to avoid errors

        return line.id
          ? this.salesInvoiceServiceChargeLineService.update(line)
          : this.salesInvoiceServiceChargeLineService.create(line as NewSalesInvoiceServiceChargeLine);
      },
    );

    forkJoin(requests)
      .pipe(finalize(() => this.onSaveFinalize()))
      .subscribe({
        next: () => this.onSaveSuccess(),
        error: () => this.onSaveError(),
      });
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
    const newDummy = this.salesInvoiceServiceChargeLineFormService.createSalesInvoiceServiceChargeLineFormGroup();
    this.serviceChargeLinesArray.push(newDummy);
  }

  removeServiceChargeLine(index: number): void {
    this.serviceChargeLinesArray.removeAt(index);
  }

  protected updateForm(salesInvoiceServiceChargeLines: ISalesInvoiceServiceChargeLine[]): void {
    this.serviceChargeLinesArray.clear();
    salesInvoiceServiceChargeLines.forEach(line => {
      this.serviceChargeLinesArray.push(this.salesInvoiceServiceChargeLineFormService.createSalesInvoiceServiceChargeLineFormGroup(line));
    });
  }
}
