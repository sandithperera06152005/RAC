import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { debounceTime, finalize } from 'rxjs/operators';
import { SaleInvoiceCommonServiceChargeDummyUpdateComponent } from '../../sale-invoice-common-service-charge-dummy/update/sale-invoice-common-service-charge-dummy-update.component';
import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule, FormGroup, FormControl } from '@angular/forms';
import { Component, inject, OnInit, viewChild, ViewChild } from '@angular/core';
import { ISalesInvoiceDummy } from '../sales-invoice-dummy.model';
import { SalesInvoiceDummyService } from '../service/sales-invoice-dummy.service';
import { SalesInvoiceDummyFormGroup, SalesInvoiceDummyFormService } from './sales-invoice-dummy-form.service';
import { SalesInvoiceLinesDummyUpdateComponent } from '../../sales-invoice-lines-dummy/update/sales-invoice-lines-dummy-update.component';
import { SalesInvoiceServiceChargeLineDummyUpdateComponent } from '../../sales-invoice-service-charge-line-dummy/update/sales-invoice-service-charge-line-dummy-update.component';
import { VehicletypeService } from 'app/entities/vehicletype/service/vehicletype.service';
import { IInventory } from 'app/entities/inventory/inventory.model';
import { ISalesInvoiceLines } from 'app/entities/sales-invoice-lines/sales-invoice-lines.model';
import { ISaleInvoiceCommonServiceCharge } from 'app/entities/sale-invoice-common-service-charge/sale-invoice-common-service-charge.model';
import { ISalesInvoiceServiceChargeLine } from 'app/entities/sales-invoice-service-charge-line/sales-invoice-service-charge-line/sales-invoice-service-charge-line.model';
import { SalesInvoiceLinesDummyService } from '../../sales-invoice-lines-dummy/service/sales-invoice-lines-dummy.service';
import { IVehicletype } from 'app/entities/vehicletype/vehicletype.model';
@Component({
  standalone: true,
  selector: 'jhi-sales-invoice-dummy-update',
  templateUrl: './sales-invoice-dummy-update.component.html',
  imports: [
    SharedModule,
    FormsModule,
    ReactiveFormsModule,
    SaleInvoiceCommonServiceChargeDummyUpdateComponent,
    SalesInvoiceLinesDummyUpdateComponent,
    SalesInvoiceServiceChargeLineDummyUpdateComponent,
  ],
})
export class SalesInvoiceDummyUpdateComponent implements OnInit {
  isSaving = false;
  @ViewChild(SalesInvoiceServiceChargeLineDummyUpdateComponent)
  SalesInvoiceServiceChargeLineDummyUpdateComponent!: SalesInvoiceServiceChargeLineDummyUpdateComponent;
  @ViewChild(SalesInvoiceLinesDummyUpdateComponent) SalesInvoiceLinesDummyUpdateComponent!: SalesInvoiceLinesDummyUpdateComponent;
  @ViewChild(SaleInvoiceCommonServiceChargeDummyUpdateComponent)
  SaleInvoiceCommonServiceChargeDummyUpdateComponent!: SaleInvoiceCommonServiceChargeDummyUpdateComponent;
  salesInvoiceDummy: ISalesInvoiceDummy | null = null;
  showCodeField: boolean = false;
  protected salesInvoiceLinesDummyService = inject(SalesInvoiceLinesDummyService);
  protected salesInvoiceDummyService = inject(SalesInvoiceDummyService);
  protected salesInvoiceDummyFormService = inject(SalesInvoiceDummyFormService);
  protected activatedRoute = inject(ActivatedRoute);
  protected vehicletypesService = inject(VehicletypeService);
  filteredItems: IInventory[][] = [];
  ISalesInvoiceLines: ISalesInvoiceLines[] = [];
  ISalesInvoiceServiceChargeLine: ISalesInvoiceServiceChargeLine[] = [];
  ISaleInvoiceCommonServiceCharge: ISaleInvoiceCommonServiceCharge[] = [];
  discountOption: string = 'percentage'; // Default value
  discountValue: number = 0;
  editForm: SalesInvoiceDummyFormGroup = this.salesInvoiceDummyFormService.createSalesInvoiceDummyFormGroup();
  i: number = 0;
  readonly = false;
  ngOnInit(): void {
    this.activatedRoute.queryParams.subscribe(params => {
      this.readonly = params['view'] === 'true';
      const id = params['id'];
      if (id) {
        this.loadSalesInvoiceDummy(id);
      }
      this.servicelines(id);
      this.servicecommonlines(id);
      console.log('Query idddddddd:', id);
      this.invoicelines(id);
    });
    this.loadVehicleTypes();
    // Subscribe to form control valueChanges
    this.editForm.get('valueDiscount')?.valueChanges.subscribe(() => this.calculateDiscount());
    this.editForm.get('subTotal')?.valueChanges.subscribe(() => this.calculateDiscount());
  }
  onDiscountValueChange(event: any): void {
    this.discountValue = event.target.value; // Update the discountValue with the input value
    console.log('Updated Discount Value:', this.discountValue);

    this.calculateDiscount(); // Log the updated value to the console
    // Call the function to calculate discount
  }
  buyQuantity: number = 0; // Store the buy quantity value

  // Function to handle changes in the quantity field
  onBuyQtyChange(event: Event): void {
    const inputElement = event.target as HTMLInputElement;
    this.buyQuantity = Number(inputElement.value);
    console.log('Buy Quantity:', this.buyQuantity);
  }

  calculateDiscount(): void {
    console.log('Form Values:', this.editForm.value); // Debug the entire form

    const subTotal = Number(this.editForm.get('subTotal')?.value) || 0;
    const valueDiscount = this.discountValue;

    console.log('Selected Discount Option:', this.discountOption); // Log the selected option
    console.log('Sub Total:', subTotal);
    console.log('Discount Value:', valueDiscount);

    let totalDiscount = 0;

    if (this.discountOption === 'percentage') {
      totalDiscount = (subTotal * Number(valueDiscount)) / 100;
    } else if (this.discountOption === 'value') {
      totalDiscount = valueDiscount;
    }

    totalDiscount = Math.min(totalDiscount, subTotal);
    const netTotal = subTotal - totalDiscount;

    console.log('Total Discount:', totalDiscount);
    console.log('Net Total:', netTotal);

    this.editForm.patchValue({
      totalDiscount: Number(totalDiscount.toFixed(2)),
      netTotal: Number(netTotal.toFixed(2)),
    });
  }

  onDiscountOptionChange(option: string): void {
    this.discountOption = option;
    console.log('Discount Option Changed:', this.discountOption);
    this.calculateDiscount(); // Recalculate discount based on the new option
  }

  total1: number = 0; // Total from first child
  total2: number = 0;
  total3: number = 0;
  subTotal: number = 0; // Store subtotal
  // Store subtotal

  receiveTotal(total: number, source: string) {
    if (source === 'child1') {
      this.total1 = total; // Update total from first child
    } else if (source === 'child2') {
      this.total2 = total; // Update total from second child
    } else if (source === 'child3') {
      this.total3 = total; // Update total from second child
    }

    this.subTotal = this.total1 + this.total2 + this.total3; // Combine the totals
    console.log('Total1:', this.total1, 'Total2:', this.total2, 'SubTotal:', this.subTotal);

    this.editForm.patchValue({
      subTotal: this.subTotal,
    });
  }
  vehicletypes: IVehicletype[] = [];
  loadVehicleTypes(): void {
    this.vehicletypesService.query({ size: 1000 }).subscribe((res: HttpResponse<IVehicletype[]>) => {
      this.vehicletypes = res.body || [];
      console.log('Loaded Vehicle Types:', this.vehicletypes); // Display the loaded vehicle types in the console
    });
  }

  fetchedServicesCommon: { itemname: string; sellingprice: number; id: number }[] = [];

  private servicecommonlines(id: number): void {
    this.salesInvoiceDummyService.fetchServiceCommon(id).subscribe(
      (res: HttpResponse<ISaleInvoiceCommonServiceCharge[]>) => {
        if (res.body && res.body.length > 0) {
          // Clear previous fetched items before adding new ones
          this.fetchedServicesCommon = [];

          res.body.forEach(item => {
            this.fetchedServicesCommon.push({
              itemname: item.name ?? '',
              sellingprice: item.value ?? 0,
              id: item.optionId ?? 0,
            });
          });

          // Log the complete array of fetched items
          console.log('Fetched Itemssssscommon:', res.body);
        } else {
          console.log('No invoice lines found.');
        }
      },
      error => {
        console.error('Error fetching invoice lines:', error);
      },
    );
  }

  fetchedServices: { itemname: string; sellingprice: number; id: number }[] = [];

  private servicelines(id: number): void {
    this.salesInvoiceDummyService.fetchService(id).subscribe(
      (res: HttpResponse<ISalesInvoiceServiceChargeLine[]>) => {
        if (res.body && res.body.length > 0) {
          // Clear previous fetched items before adding new ones
          this.fetchedServices = [];

          res.body.forEach(item => {
            this.fetchedServices.push({
              itemname: item.serviceName ?? '',
              sellingprice: item.value ?? 0,
              id: item.optionId ?? 0,
            });
          });

          // Log the complete array of fetched items
          console.log('Fetched Itemssssssssssssssssss:', res.body);
        } else {
          console.log('No invoice lines found.');
        }
      },
      error => {
        console.error('Error fetching invoice lines:', error);
      },
    );
  }

  fetchedItems: { code: string; itemname: string; quantity: number; sellingprice: number }[] = [];

  private invoicelines(id: number): void {
    this.salesInvoiceDummyService.fetchInvoiceLines(id).subscribe(
      (res: HttpResponse<ISalesInvoiceLines[]>) => {
        if (res.body && res.body.length > 0) {
          // Clear previous fetched items before adding new ones
          this.fetchedItems = [];

          res.body.forEach(item => {
            this.fetchedItems.push({
              code: item.itemcode ?? '',
              itemname: item.itemname ?? '',
              quantity: item.quantity ?? 0,
              sellingprice: item.sellingprice ?? 0,
            });
          });

          // Log the complete array of fetched items
          console.log('Fetched Items:', this.fetchedItems);
        } else {
          console.log('No invoice lines found.');
        }
      },
      error => {
        console.error('Error fetching invoice lines:', error);
      },
    );
  }

  private loadSalesInvoiceDummy(id: number): void {
    this.salesInvoiceDummyService.find(id).subscribe(response => {
      const salesInvoiceDummy = response.body;
      console.log('Retrieved data:', salesInvoiceDummy);
      if (salesInvoiceDummy) {
        // Create a new object and assign customername to customerName
        const transformedData = {
          ...salesInvoiceDummy,
          id: null as unknown as number,
          customerName: (salesInvoiceDummy as any).customername,
          vehicleNo: (salesInvoiceDummy as any).vehicleno,
          customerAddress: (salesInvoiceDummy as any).customeraddress,
          // Assigning API response field to the correct model field
          subTotal: Number((salesInvoiceDummy as any).subtotal) || 0, // Ensure it's a number
          netTotal: Number((salesInvoiceDummy as any).nettotal) || 0, // Replace "8888" with a dynamic value
          totalTax: Number((salesInvoiceDummy as any).totaltax) || 0,
          totalDiscount: Number((salesInvoiceDummy as any).totaldiscount) || 0,
          originalInvoiceId: (salesInvoiceDummy as any).id ? Number((salesInvoiceDummy as any).id) : null,
          originalInvoiceCode: (salesInvoiceDummy as any).code, // Convert ID to number safely
          amountOwing: Number((salesInvoiceDummy as any).amountowing) || 0,
        };

        this.updateForm(transformedData);
        console.log('transformmmmmmmmmm', transformedData);
      }
    });
  }

  selectedItem: { name: string; availablequantity: number; lastsellingprice: number } | null = null;

  itemName: string = ''; // Variable to hold the selected item's name
  availablequantity: number = 0;
  lastsellingprice: number = 0;

  onItemCodeSelect(event: Event, index: number): void {
    const inputElement = event.target as HTMLInputElement;
    const selectedCode = inputElement.value;

    // Find the selected item based on the code
    const selectedItem = this.filteredItems[index]?.find(item => item.code === selectedCode);

    if (selectedItem) {
      console.log('Selected Item:', selectedItem);
      this.itemName = selectedItem.name ?? ''; // Update itemName with the selected item's name or an empty string if undefined
      this.availablequantity = selectedItem.availablequantity ?? 0;
      this.lastsellingprice = selectedItem.lastsellingprice ?? 0;
    } else {
      console.warn('No matching item found for:', selectedCode);
      this.itemName = ''; // Clear itemName if no match is found
    }
  }
  onAddItem(): void {
    // Store the selected item as an object
    this.selectedItem = {
      name: this.itemName,
      availablequantity: this.buyQuantity,
      lastsellingprice: this.lastsellingprice,
    };

    // Log the selected item to the console
    console.log('Selected Item:', this.selectedItem);
    console.log('Returned Buy Quantity:', this.buyQuantity);
    // Call the function to get the buy quantity

    // Optionally reset the inputs after adding
    this.itemName = '';
    this.availablequantity = 0;
    this.lastsellingprice = 0;
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

    this.salesInvoiceLinesDummyService
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
  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const salesInvoiceDummy = this.salesInvoiceDummyFormService.getSalesInvoiceDummy(this.editForm);
    if (salesInvoiceDummy.id !== null) {
      this.subscribeToSaveResponse(this.salesInvoiceDummyService.update(salesInvoiceDummy));
    } else {
      this.subscribeToSaveResponse(this.salesInvoiceDummyService.create(salesInvoiceDummy));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISalesInvoiceDummy>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: res => {
        console.log('Success Responseeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee:', res.body); // Log the success response

        if (res.status === 201) {
          if (res.body) {
            console.log('Sales invoice created:', res.body.id);
            if (this.SalesInvoiceServiceChargeLineDummyUpdateComponent) {
              this.SalesInvoiceServiceChargeLineDummyUpdateComponent.save(res.body.id); // Call save from the child component
            }
            if (this.SalesInvoiceLinesDummyUpdateComponent) {
              this.SalesInvoiceLinesDummyUpdateComponent.save(res.body.id); // Call save from the child component
            }
            if (this.SaleInvoiceCommonServiceChargeDummyUpdateComponent) {
              this.SaleInvoiceCommonServiceChargeDummyUpdateComponent.save(res.body.id); // Call save from the child component
            }
          }
        } else if (res.status === 200) {
          console.log('Sales invoice updated:', res.body);
        }

        this.onSaveSuccess();
      },
      error: err => {
        console.error('Error Response:', err); // Log the error response
        this.onSaveError();
      },
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

  protected updateForm(salesInvoiceDummy: ISalesInvoiceDummy): void {
    this.salesInvoiceDummy = salesInvoiceDummy;
    this.salesInvoiceDummyFormService.resetForm(this.editForm, salesInvoiceDummy);
  }
}
