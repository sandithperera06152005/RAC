import { Component, EventEmitter, OnInit, Output, Input, inject, SimpleChanges } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable, forkJoin } from 'rxjs';
import { debounceTime, finalize } from 'rxjs/operators';
import { FormBuilder, FormGroup, FormArray } from '@angular/forms';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { IVehicletype } from 'app/entities/vehicletype/vehicletype.model';
import { VehicletypeService } from 'app/entities/vehicletype/service/vehicletype.service';
import { IBillingserviceoption } from 'app/entities/billingserviceoption/billingserviceoption.model';
import {
  ISalesInvoiceServiceChargeLineDummy,
  NewSalesInvoiceServiceChargeLineDummy,
} from '../sales-invoice-service-charge-line-dummy.model';
import { SalesInvoiceServiceChargeLineDummyService } from '../service/sales-invoice-service-charge-line-dummy.service';
import { SalesInvoiceServiceChargeLineDummyFormService } from './sales-invoice-service-charge-line-dummy-form.service';

@Component({
  standalone: true,
  selector: 'jhi-sales-invoice-service-charge-line-dummy-update',
  templateUrl: './sales-invoice-service-charge-line-dummy-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class SalesInvoiceServiceChargeLineDummyUpdateComponent implements OnInit {
  isSaving = false;
  @Input() readonly = false;
  showCodeField: boolean = true;
  salesInvoiceServiceChargeLines: ISalesInvoiceServiceChargeLineDummy[] = [];
  filteredItems: IBillingserviceoption[][] = [];
  protected salesInvoiceServiceChargeLineDummyService = inject(SalesInvoiceServiceChargeLineDummyService);
  protected salesInvoiceServiceChargeLineDummyFormService = inject(SalesInvoiceServiceChargeLineDummyFormService);
  protected activatedRoute = inject(ActivatedRoute);
  protected fb = inject(FormBuilder);
  @Output() totalUpdated = new EventEmitter<number>(); // Emit total to parent
  @Input() fetchedServices: any;
  protected vehicletypesService = inject(VehicletypeService);
  editForm: FormGroup = new FormGroup({
    serviceChargeLines: new FormArray([]),
  });
  typeid: number = 0;
  totalfetch: number = 0;
  vehicletypes: IVehicletype[] = [];
  get serviceChargeLinesArray(): FormArray {
    return this.editForm.get('serviceChargeLines') as FormArray;
  }
  ngOnChanges(changes: SimpleChanges): void {
    if (changes['fetchedServices'] && this.fetchedServices) {
      // Loop through the fetchedItems array and add each item to the form array
      this.fetchedServices.forEach((item: any) => {
        this.addItemToFormArray(item);
      });
      console.log('Fetched Items on Change:', this.fetchedServices); // Log fetched items
    }
  }

  addItemToFormArray(item: any): void {
    const newItem = this.fb.group({
      serviceName: [item.itemname],
      value: [item.sellingprice],
      isCustomerService: [false],
      id: [item.id],
    });

    if (item.id) {
      this.selectedServices.push({ id: item.id, servicename: item.itemname });
    }

    this.serviceChargeLinesArray.push(newItem);
    this.totalvalue(newItem);
  }
  totalvalue(formGroup: FormGroup): void {
    const value = formGroup.get('value');

    // Update the line total when the value changes
    value?.valueChanges.pipe(debounceTime(300)).subscribe(() => this.updateLineTotal());

    // Initialize the line total when the form is added
    this.updateLineTotal();
  }

  updateLineTotal(): void {
    // Calculate the total by summing up all values in the serviceChargeLines array
    const total = this.serviceChargeLinesArray.controls
      .map(control => control.get('value')?.value || 0)
      .reduce((acc, value) => acc + value, 0);

    // Emit the total to the parent component
    this.totalUpdated.emit(total);
    console.log('Updated Total ssssssssssser:', total); // Log the updated total
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ salesInvoiceServiceChargeLines }) => {
      if (salesInvoiceServiceChargeLines && salesInvoiceServiceChargeLines.length > 0) {
        this.salesInvoiceServiceChargeLines = salesInvoiceServiceChargeLines;
        this.updateForm(salesInvoiceServiceChargeLines);
      }
    });
    this.loadVehicleTypes();
  }
  loadVehicleTypes(): void {
    this.vehicletypesService.query({ size: 1000 }).subscribe((res: HttpResponse<IVehicletype[]>) => {
      this.vehicletypes = res.body || [];
      console.log('Loaded Vehicle Types:', this.vehicletypes); // Display the loaded vehicle types in the console
    });
  }

  allBillingServiceOptions: IBillingserviceoption[][] = [];
  searchTerm: string = '';

  get flattenedBillingOptions(): IBillingserviceoption[] {
    return this.allBillingServiceOptions.reduce((acc: IBillingserviceoption[], val: IBillingserviceoption[]) => acc.concat(val), []);
  }

  get filteredBillingOptions(): IBillingserviceoption[] {
    return this.flattenedBillingOptions.filter(option => option.servicename?.toLowerCase().includes(this.searchTerm.toLowerCase()));
  }

  onDropdownChange(event: Event): void {
    const selectedValue = (event.target as HTMLSelectElement).value;
    const typeid = Number(selectedValue);
    console.log('Selected Vehicle Type ID:', typeid);
    this.typeid = typeid;
    this.allBillingServiceOptions = [];

    this.salesInvoiceServiceChargeLineDummyService.getElementsByID(typeid).subscribe({
      next: response => {
        if (response.body && Array.isArray(response.body)) {
          const billingserviceoptionIds = response.body.map((item: any) => item.billingserviceoptionid);
          const requests = billingserviceoptionIds.map(id => this.salesInvoiceServiceChargeLineDummyService.getbillingid(id));

          forkJoin(requests).subscribe({
            next: (results: HttpResponse<IBillingserviceoption[]>[]) => {
              this.allBillingServiceOptions = results.map(res => res.body).filter((body): body is IBillingserviceoption[] => body !== null);
              console.log('All Billing Service Options loaded:', this.allBillingServiceOptions);
            },
            error: err => console.error('Error fetching billing details:', err),
          });
        }
      },
      error: err => console.error('API Error:', err),
    });
  }

  toggleService(option: IBillingserviceoption): void {
    const index = this.selectedServices.findIndex(s => s.id === option.id);
    if (index === -1) {
      this.selectedServices.push({ id: option.id!, servicename: option.servicename! });
      // Real-time add to table
      this.salesInvoiceServiceChargeLineDummyService.biliingvalues(option.id!, this.typeid).subscribe(response => {
        const billingValues = response.body;
        const fetchedValue = billingValues && billingValues.length > 0 ? billingValues[0].value : 0;

        this.serviceChargeLinesArray.push(
          this.fb.group({
            serviceName: [option.servicename],
            value: [fetchedValue],
            isCustomerService: [false],
            id: [option.id],
          }),
        );
        this.updateLineTotal();
      });
    } else {
      this.selectedServices.splice(index, 1);
      // Remove from table
      const tableIndex = this.serviceChargeLinesArray.controls.findIndex(c => c.get('id')?.value === option.id);
      if (tableIndex !== -1) {
        this.serviceChargeLinesArray.removeAt(tableIndex);
        this.updateLineTotal();
      }
    }
  }

  isSelected(optionId: number | undefined): boolean {
    return this.selectedServices.some(s => s.id === optionId);
  }

  addToTable() {
    if (this.selectedServices.length === 0) {
      return; // No selected services, nothing to add
    }

    const existingRows = this.serviceChargeLinesArray.controls.length;
    console.log('typeid:', this.typeid);

    let completedRequests = 0; // Track completed API requests
    let totalFetchedValue = 0;
    this.selectedServices.forEach((service, index) => {
      console.log('Selected service ID:', service.id);
      console.log(`Sending API request to fetch billing values with params: service.id=${service.id}, typeid=${this.typeid}`);

      this.salesInvoiceServiceChargeLineDummyService.biliingvalues(service.id, this.typeid).subscribe(response => {
        console.log('API Response:', response);

        const billingValues = response.body;
        const fetchedValue = billingValues && billingValues.length > 0 ? billingValues[0].value : 0;
        totalFetchedValue += fetchedValue ?? 0;
        if (index === 0 && existingRows > 0) {
          // Update first row if it exists
          const firstRow = this.serviceChargeLinesArray.controls[0];
          firstRow.get('serviceName')?.setValue(service.servicename);
          firstRow.get('iqd')?.setValue(service.id);
          firstRow.get('value')?.setValue(fetchedValue);
        } else {
          // Add new row with fetched value
          this.serviceChargeLinesArray.push(
            this.fb.group({
              serviceName: [service.servicename],
              value: [fetchedValue], // Set fetched value
              isCustomerService: [false],
              id: [service.id],
            }),
          );
        }

        // Increment completed requests counter
        completedRequests++;
        this.totalfetch = totalFetchedValue;
        console.log('Current total fetched value:', this.totalfetch);

        this.calculateTotal(this.totalfetch);
        // When all requests are done, calculate total
      });
    });
    console.log('Final total before API completion (may be outdated):', this.totalfetch);

    // Reset selected services after processing
    this.selectedServices = [];
  }

  calculateTotal(total: number): void {
    console.log('Total Value:', total); // Log the correct total value
    this.totalUpdated.emit(total); // Emit total to parent
    // Small delay to allow UI updates
  }

  selectedServices: { id: number; servicename: string }[] = [];

  onCheckboxChange(event: any, servicename: string, id: number) {
    if (event.target.checked) {
      // Add the service with its id to the selected services list
      this.selectedServices.push({ id, servicename });
    } else {
      // Remove the service with its id from the selected services list
      this.selectedServices = this.selectedServices.filter(service => service.id !== id);
    }

    // Log selected services to the console
    console.log('Selected Services:', this.selectedServices);
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
    const dropdownElement = document.querySelector('.form-select') as HTMLSelectElement;
    if (!dropdownElement) {
      console.error('Dropdown not found');
      return;
    }

    // Get the selected dropdown value

    this.salesInvoiceServiceChargeLineDummyService
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

  save(inid: number): void {
    this.isSaving = true;

    const serviceChargeLines = this.serviceChargeLinesArray.value.map((line: any, index: number) => ({
      ...line,
      invoiceId: inid, // Assign invoice ID
      lineId: line.lineid ?? index + 1, // Ensure unique line ID
      optionId: index + 1,
    }));

    console.log('Modified sales invoice lines:', serviceChargeLines);

    const requests: Observable<HttpResponse<ISalesInvoiceServiceChargeLineDummy>>[] = serviceChargeLines.map(
      (line: ISalesInvoiceServiceChargeLineDummy | NewSalesInvoiceServiceChargeLineDummy) => {
        console.log('Processing line:', line);
        console.log('Line ID:', line?.id); // Use optional chaining to avoid errors

        return line.id
          ? this.salesInvoiceServiceChargeLineDummyService.update(line)
          : this.salesInvoiceServiceChargeLineDummyService.create(line as NewSalesInvoiceServiceChargeLineDummy);
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

  protected updateForm(salesInvoiceServiceChargeLines: ISalesInvoiceServiceChargeLineDummy[]): void {
    this.serviceChargeLinesArray.clear();
    salesInvoiceServiceChargeLines.forEach(line => {
      this.serviceChargeLinesArray.push(
        this.salesInvoiceServiceChargeLineDummyFormService.createSalesInvoiceServiceChargeLineDummyFormGroup(line),
      );
    });
  }

  addServiceChargeLine(): void {
    const newDummy = this.salesInvoiceServiceChargeLineDummyFormService.createSalesInvoiceServiceChargeLineDummyFormGroup();
    this.serviceChargeLinesArray.push(newDummy);
  }

  removeServiceChargeLine(index: number): void {
    this.serviceChargeLinesArray.removeAt(index);
  }
}
