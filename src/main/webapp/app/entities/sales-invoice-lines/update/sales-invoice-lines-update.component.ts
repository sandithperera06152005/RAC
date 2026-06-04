import { Component, EventEmitter, Input, OnInit, Output, SimpleChanges, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable, forkJoin, of } from 'rxjs';
import { catchError, debounceTime, finalize, tap } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { IInventory } from 'app/entities/inventory/inventory.model';
import { ISalesInvoiceLines } from '../sales-invoice-lines.model';
import { SalesInvoiceLinesService } from '../service/sales-invoice-lines.service';
import { SalesInvoiceLinesFormGroup, SalesInvoiceLinesFormService } from './sales-invoice-lines-form.service';
import { FormBuilder, FormArray, FormControl, FormGroup, Validators } from '@angular/forms';
import dayjs from 'dayjs/esm';
import CommonModule from 'app/shared/shared.module';
import { DecimalInputDirective } from 'app/shared/decimal-input.directive';
import { AutojobsinvoicelinesService } from 'app/entities/autojobsinvoicelines/service/autojobsinvoicelines.service';
import { NewAutojobsinvoicelines } from 'app/entities/autojobsinvoicelines/autojobsinvoicelines.model';
import { TransactionsService } from 'app/entities/transactions/service/transactions.service';
import { AccountsService } from 'app/entities/accounts/service/accounts.service';
import { CustomerService } from 'app/entities/customer/service/customer.service';
import { SalesinvoiceService } from 'app/entities/salesinvoice/service/salesinvoice.service';
import { NewTransactions } from 'app/entities/transactions/transactions.model';
import { BinCardService } from 'app/entities/bin-card/service/bin-card.service';
import { IBinCard, NewBinCard } from 'app/entities/bin-card/bin-card.model';
import { InventoryService } from 'app/entities/inventory/service/inventory.service';
import { InventorybatchesService } from 'app/entities/inventorybatches/service/inventorybatches.service';

@Component({
  standalone: true,
  selector: 'jhi-sales-invoice-lines-update',
  templateUrl: './sales-invoice-lines-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule, DecimalInputDirective],
})
export class SalesInvoiceLinesUpdateComponent implements OnInit {
  isSaving = false;
  @Output() totalUpdated = new EventEmitter<number>();
  @Output() totalDiscountUpdated = new EventEmitter<number>();
  salesInvoiceLines: ISalesInvoiceLines[] = []; // Now an array of sales invoice lines
  filteredItems: IInventory[][] = []; // Array of arrays to store filtered items for each row
  showCodeField: boolean = false;
  protected salesInvoiceLinesService = inject(SalesInvoiceLinesService);
  protected salesInvoiceLinesFormService = inject(SalesInvoiceLinesFormService);
  protected activatedRoute = inject(ActivatedRoute);
  protected fb = inject(FormBuilder);
  @Input() selectedItem: any;
  @Input() fetchedItems: any;
  @Input() sourceInvoiceId: number | null = null;
  private static readonly DISCOUNT_ENTRY_PCT_MARKER = '[PCT]';
  private static readonly DISCOUNT_ENTRY_VAL_MARKER = '[VAL]';
  @Input() nextvalue: any;
  @Input() serviceChargeTotal: number = 0;
  @Input() commonServiceChargeTotal: number = 0;
  protected autojobsinvoicelinesService = inject(AutojobsinvoicelinesService);
  protected transactionsService = inject(TransactionsService);
  protected accountsService = inject(AccountsService);
  protected customerService = inject(CustomerService);
  protected salesinvoiceService = inject(SalesinvoiceService);
  protected binCardService = inject(BinCardService);
  protected inventoryService = inject(InventoryService);
  protected inventorybatchesService = inject(InventorybatchesService);
  bincard: IBinCard[] = [];
  private isBinCreated = false;
  // Use FormArray to handle multiple lines
  editForm: FormGroup = this.fb.group({
    salesInvoiceLines: this.fb.array([]), // Define a FormArray
  });
  toggleShowCodeField(): void {
    this.showCodeField = !this.showCodeField;
  }
  get salesInvoiceLinesDummyArray(): FormArray {
    return this.editForm.get('salesInvoiceLines') as FormArray;
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['selectedItem'] && this.selectedItem) {
      console.log('Before Adding Item:', this.salesInvoiceLinesArray.controls);
      this.addItemToFormArray(this.selectedItem);
      console.log('After Adding Item:', this.salesInvoiceLinesArray.controls);
    }
    if (changes['fetchedItems'] && this.fetchedItems) {
      this.salesInvoiceLinesDummyArray.clear();
      // Loop through the fetchedItems array and add each item to the form array
      this.fetchedItems.forEach((item: any) => {
        this.addItemToFormArray(item);
      });
      console.log('Fetched Items on Change:', this.fetchedItems); // Log fetched items
    }
    this.setvaluesbincar();
  }

  addItemToFormArray(item: any): void {
    const resolvedSellingPrice = Number(item.lastsellingprice ?? item.sellingprice ?? item.itemprice ?? 0);
    const quantity = Number(item.availablequantity ?? item.quantity ?? 0);
    const totalLineDiscount = Number(item.discount ?? 0);
    const discountEntryType = this.getDiscountEntryTypeForItem(item);
    const { discountPercentage, discountValue } = this.resolveDiscountDisplayFields(item, discountEntryType, totalLineDiscount);
    const discountValueIsPerUnit =
      discountEntryType === 'value' &&
      item.itemDiscountValue != null &&
      item.itemDiscountValue !== '' &&
      totalLineDiscount > 0 &&
      quantity > 0 &&
      Math.abs(totalLineDiscount - Number(item.itemDiscountValue) * quantity) < 0.01;
    const newItem = this.fb.group({
      itemid: [item.itemid ?? item.id ?? null],
      itemcode: [item.code || item.itemcode || ''], // Match template
      itemname: [item.name || item.itemname], // Match template
      description: [item.description ?? null],
      unitofmeasurement: [item.unitofmeasurement ?? null],
      quantity: [quantity],
      itemcost: [Number(item.lastcost ?? item.itemcost ?? 0)],
      itemprice: [resolvedSellingPrice],
      tax: [Number(item.tax ?? 0)],
      sellingprice: [resolvedSellingPrice], // Match template
      linetotal: [{ value: 0, disabled: true }], // Match template
      discount: [totalLineDiscount], // discount is now always the total discount for the line
      discountpercentage: [discountPercentage],
      discountvalue: [discountValue],
      discountEntryType: [discountEntryType ?? null],
      discountValueIsPerUnit: [discountValueIsPerUnit],
      isNew: [item.isNew ?? false],
      sourceLineId: [item.lineid ?? null],
    });
    console.log('New Item Addedaazzz:', newItem.value);
    console.log(this.selectedItem);
    this.salesInvoiceLinesDummyArray.push(newItem);
    // Calculate lineTotal dynamically when quantity or sellingPrice changes
    this.listenToQuantityAndPriceChanges(newItem);
  }
  listenToQuantityAndPriceChanges(formGroup: FormGroup): void {
    const quantityControl = formGroup.get('quantity');
    const sellingPriceControl = formGroup.get('sellingprice');
    const discountControl = formGroup.get('discount');

    // Use debounceTime to avoid too frequent updates (e.g., wait 300ms after the user stops typing)
    quantityControl?.valueChanges.pipe(debounceTime(300)).subscribe(() => this.updateLineTotal(formGroup));
    sellingPriceControl?.valueChanges.pipe(debounceTime(300)).subscribe(() => this.updateLineTotal(formGroup));
    discountControl?.valueChanges.pipe(debounceTime(300)).subscribe(() => this.updateLineTotal(formGroup));

    // Also update lineTotal when the form is initialized
    this.updateLineTotal(formGroup);
  }

  onDiscountPercentageChange(index: number): void {
    const salesInvoiceLineGroup = this.salesInvoiceLinesArray.at(index) as FormGroup;
    const quantity = Number(salesInvoiceLineGroup.get('quantity')?.value || 0);
    const sellingPrice = Number(salesInvoiceLineGroup.get('sellingprice')?.value || 0);
    const discountPercentage = Number(salesInvoiceLineGroup.get('discountpercentage')?.value || 0);
    const lineBaseAmount = quantity * sellingPrice;
    const totalDiscount = Number(((lineBaseAmount * discountPercentage) / 100).toFixed(2));

    salesInvoiceLineGroup.patchValue(
      {
        discountvalue: null,
        discount: totalDiscount,
        discountEntryType: 'percentage',
      },
      { emitEvent: false },
    );
    this.updateLineTotal(salesInvoiceLineGroup);
  }

  onDiscountValueChange(index: number): void {
    const salesInvoiceLineGroup = this.salesInvoiceLinesArray.at(index) as FormGroup;
    const raw = salesInvoiceLineGroup.get('discountvalue')?.value;
    if (raw === null || raw === undefined || raw === '') {
      salesInvoiceLineGroup.patchValue(
        {
          discountpercentage: null,
          discountvalue: null,
          discount: 0,
          discountEntryType: 'value',
          discountValueIsPerUnit: false,
        },
        { emitEvent: false },
      );
      this.updateLineTotal(salesInvoiceLineGroup);
      return;
    }
    const discountValue = Number(Number(raw).toFixed(2));

    salesInvoiceLineGroup.patchValue(
      {
        discountpercentage: null,
        discountvalue: discountValue > 0 ? discountValue : null,
        discount: discountValue,
        discountEntryType: 'value',
        discountValueIsPerUnit: false,
      },
      { emitEvent: false },
    );
    this.updateLineTotal(salesInvoiceLineGroup);
  }

  private resolveDiscountDisplayFields(
    item: any,
    discountEntryType: 'percentage' | 'value' | undefined,
    totalLineDiscount: number,
  ): { discountPercentage: number | null; discountValue: number | null } {
    if (discountEntryType === 'percentage') {
      let pct = Number(item.itemDiscountValue ?? item.discountpercentage ?? item.discountPercentage ?? 0);
      if (pct <= 0) {
        pct = this.percentageFromStoredLineDiscount(item, totalLineDiscount) ?? 0;
      }
      return {
        discountPercentage: pct > 0 ? pct : null,
        discountValue: null,
      };
    }
    if (discountEntryType === 'value') {
      if (item.itemDiscountValue != null && item.itemDiscountValue !== '') {
        const perUnit = Number(item.itemDiscountValue);
        return {
          discountPercentage: null,
          discountValue: perUnit > 0 ? Number(perUnit.toFixed(2)) : null,
        };
      }
      const quantity = Number(item.quantity ?? item.availablequantity ?? 0);
      if (totalLineDiscount > 0 && quantity > 0) {
        return {
          discountPercentage: null,
          discountValue: Number((totalLineDiscount / quantity).toFixed(2)),
        };
      }
      return {
        discountPercentage: null,
        discountValue: totalLineDiscount > 0 ? Number(totalLineDiscount.toFixed(2)) : null,
      };
    }
    const explicitPercentage = item.discountpercentage ?? item.discountPercentage;
    if (explicitPercentage != null && explicitPercentage !== '') {
      const pct = Number(explicitPercentage);
      return { discountPercentage: pct > 0 ? pct : null, discountValue: null };
    }
    const explicitValue = item.discountvalue ?? item.discountValue;
    if (explicitValue != null && explicitValue !== '') {
      const val = Number(Number(explicitValue).toFixed(2));
      return { discountPercentage: null, discountValue: val > 0 ? val : null };
    }
    const derivedPct = this.percentageFromStoredLineDiscount(item, totalLineDiscount);
    if (derivedPct != null && derivedPct > 0) {
      return {
        discountPercentage: Number(derivedPct.toFixed(2)),
        discountValue: null,
      };
    }
    return {
      discountPercentage: null,
      discountValue: totalLineDiscount > 0 ? Number(totalLineDiscount.toFixed(2)) : null,
    };
  }

  /** Reverse-calculates per-unit discount % from the total line discount stored in the DB. */
  private percentageFromStoredLineDiscount(item: any, totalLineDiscount: number): number | null {
    const quantity = Number(item.quantity ?? item.availablequantity ?? 0);
    const sellingPrice = Number(item.sellingprice ?? item.lastsellingprice ?? item.itemprice ?? 0);
    const lineBaseAmount = quantity * sellingPrice;
    if (lineBaseAmount <= 0 || totalLineDiscount <= 0) {
      return null;
    }
    const pct = (totalLineDiscount / lineBaseAmount) * 100;
    return pct > 0 ? Number(pct.toFixed(4)) : null;
  }

  private hasDiscountPercentage(formGroup: FormGroup): boolean {
    const pct = formGroup.get('discountpercentage')?.value;
    return pct !== null && pct !== undefined && pct !== '';
  }

  /** Line total discount for the Discount column (matches [SalesInvoiceLines].discount). */
  getLineDiscountDisplay(index: number): number | null {
    const formGroup = this.salesInvoiceLinesArray.at(index) as FormGroup;
    if (!formGroup) {
      return null;
    }

    const totalLineDiscount = Number(formGroup.get('discount')?.value || 0);
    if (totalLineDiscount > 0) {
      return Number(totalLineDiscount.toFixed(2));
    }

    const sellingPrice = Number(formGroup.get('sellingprice')?.value || 0);
    const quantity = Number(formGroup.get('quantity')?.value || 0);
    const pctRaw = formGroup.get('discountpercentage')?.value;
    const hasPct = pctRaw !== null && pctRaw !== undefined && pctRaw !== '';

    if (hasPct && sellingPrice > 0 && quantity > 0) {
      return Number(((quantity * sellingPrice * Number(pctRaw)) / 100).toFixed(2));
    }

    const valRaw = formGroup.get('discountvalue')?.value;
    const hasVal = valRaw !== null && valRaw !== undefined && valRaw !== '';
    if (hasVal && formGroup.get('discountValueIsPerUnit')?.value && quantity > 0) {
      return Number((Number(valRaw) * quantity).toFixed(2));
    }

    return null;
  }

  formatLineDiscountDisplay(index: number): string {
    const value = this.getLineDiscountDisplay(index);
    return value == null ? '' : value.toFixed(2);
  }

  private ensureDiscountControls(formGroup: FormGroup): void {
    if (!formGroup.get('discount')) {
      formGroup.addControl('discount', new FormControl(0));
    }
    if (!formGroup.get('discountpercentage')) {
      formGroup.addControl('discountpercentage', new FormControl(null));
    }
    if (!formGroup.get('discountvalue')) {
      formGroup.addControl('discountvalue', new FormControl(null));
    }
    if (!formGroup.get('discountEntryType')) {
      formGroup.addControl('discountEntryType', new FormControl(null));
    }
    if (!formGroup.get('discountValueIsPerUnit')) {
      formGroup.addControl('discountValueIsPerUnit', new FormControl(false));
    }
  }

  private getDiscountEntryTypeForItem(item: any): 'percentage' | 'value' | undefined {
    if (item.discountEntryType === 'percentage' || item.discountEntryType === 'value') {
      return item.discountEntryType;
    }
    const fromDescription = this.parseDiscountEntryTypeFromDescription(item.description);
    if (fromDescription) {
      return fromDescription;
    }
    const fromStorage = this.lookupDiscountEntryTypeFromStorage(item);
    if (fromStorage) {
      return fromStorage;
    }
    if (item.discountOption === 'percentage' || item.itemDiscountOption === 'percentage') {
      return 'percentage';
    }
    if (item.discountOption === 'value' || item.itemDiscountOption === 'value') {
      return 'value';
    }
    const totalDiscount = Number(item.discount ?? 0);
    if (totalDiscount > 0 && fromDescription !== 'value') {
      const quantity = Number(item.quantity ?? item.availablequantity ?? 0);
      const sellingPrice = Number(item.sellingprice ?? item.lastsellingprice ?? item.itemprice ?? 0);
      if (quantity > 0 && sellingPrice > 0) {
        return 'percentage';
      }
    }
    return undefined;
  }

  private parseDiscountEntryTypeFromDescription(description: string | null | undefined): 'percentage' | 'value' | undefined {
    if (!description) {
      return undefined;
    }
    if (description.startsWith(SalesInvoiceLinesUpdateComponent.DISCOUNT_ENTRY_PCT_MARKER)) {
      return 'percentage';
    }
    if (description.startsWith(SalesInvoiceLinesUpdateComponent.DISCOUNT_ENTRY_VAL_MARKER)) {
      return 'value';
    }
    return undefined;
  }

  private lookupDiscountEntryTypeFromStorage(item: any): 'percentage' | 'value' | undefined {
    if (this.sourceInvoiceId == null) {
      return undefined;
    }
    const map = this.readDiscountEntryStorageMap();
    const key = this.discountEntryStorageKey(item);
    const entryType = map[key];
    return entryType === 'percentage' || entryType === 'value' ? entryType : undefined;
  }

  private readDiscountEntryStorageMap(): Record<string, 'percentage' | 'value'> {
    if (this.sourceInvoiceId == null) {
      return {};
    }
    try {
      const raw = localStorage.getItem(`salesInvoiceLineDiscountEntry-${this.sourceInvoiceId}`);
      return raw ? (JSON.parse(raw) as Record<string, 'percentage' | 'value'>) : {};
    } catch {
      return {};
    }
  }

  private persistDiscountEntryTypesToStorage(): void {
    if (this.sourceInvoiceId == null) {
      return;
    }
    const map = this.readDiscountEntryStorageMap();
    this.salesInvoiceLinesArray.controls.forEach((control, index) => {
      const formGroup = control as FormGroup;
      const entryType = this.inferDiscountEntryTypeFromForm(formGroup);
      if (!entryType) {
        return;
      }
      map[this.discountEntryStorageKeyFromForm(formGroup, index)] = entryType;
    });
    localStorage.setItem(`salesInvoiceLineDiscountEntry-${this.sourceInvoiceId}`, JSON.stringify(map));
  }

  private discountEntryStorageKey(item: any): string {
    const lineid = item.lineid ?? item.sourceLineId ?? '';
    const itemid = item.itemid ?? '';
    const itemcode = item.itemcode ?? item.code ?? '';
    return `${lineid}|${itemid}|${itemcode}`;
  }

  private discountEntryStorageKeyFromForm(formGroup: FormGroup, index: number): string {
    const lineid = formGroup.get('sourceLineId')?.value ?? formGroup.get('lineid')?.value ?? index;
    const itemid = formGroup.get('itemid')?.value ?? '';
    const itemcode = formGroup.get('itemcode')?.value ?? '';
    return `${lineid}|${itemid}|${itemcode}`;
  }

  private inferDiscountEntryTypeFromForm(formGroup: FormGroup): 'percentage' | 'value' | null {
    const explicit = formGroup.get('discountEntryType')?.value;
    if (explicit === 'percentage' || explicit === 'value') {
      return explicit;
    }
    const hasPct = this.hasDiscountPercentage(formGroup);
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

  private buildDescriptionWithDiscountEntry(line: any, entryType: 'percentage' | 'value' | null): string | null {
    const base = String(line.description ?? line.itemname ?? '')
      .replace(/^\[(?:PCT|VAL)\]/, '')
      .trim();
    if (entryType === 'percentage') {
      return `${SalesInvoiceLinesUpdateComponent.DISCOUNT_ENTRY_PCT_MARKER}${base || line.itemname || ''}`;
    }
    if (entryType === 'value') {
      return `${SalesInvoiceLinesUpdateComponent.DISCOUNT_ENTRY_VAL_MARKER}${base || line.itemname || ''}`;
    }
    return line.description ?? line.itemname ?? null;
  }
  updateLineTotal(formGroup: FormGroup): void {
    const quantity = Number(formGroup.get('quantity')?.value || 0);
    const sellingPrice = Number(formGroup.get('sellingprice')?.value || 0);
    // discount is stored as the TOTAL line discount (per-unit × qty)
    const totalLineItemDiscount = Number(formGroup.get('discount')?.value || 0);
    const lineTotalControl = formGroup.get('linetotal');

    // Calculate line total: (sellingPrice * quantity) - totalDiscount
    // This is mathematically identical to (sellingPrice - discountPerUnit) * quantity
    const lineTotal = sellingPrice * quantity - totalLineItemDiscount;
    lineTotalControl?.setValue(lineTotal, { emitEvent: false }); // Set the value without emitting the event to avoid infinite loop

    // Calculate the total of all lineTotals in the form array
    const total = this.salesInvoiceLinesDummyArray.controls
      .map(control => Number(control.get('linetotal')?.value || 0))
      .reduce((acc, value) => acc + value, 0);

    // Sum all line-level discounts (each discount field already holds discount × qty)
    const totalLineDiscount = this.salesInvoiceLinesDummyArray.controls
      .map(control => Number(control.get('discount')?.value || 0))
      .reduce((acc, value) => acc + value, 0);

    console.log('Totallll:', total);
    console.log('Total Line Discount:', totalLineDiscount);
    // Emit the updated total of all lineTotals
    this.totalUpdated.emit(total);
    // Emit the total line-level discount so the parent can include it in TotalDiscount
    this.totalDiscountUpdated.emit(totalLineDiscount);
  }
  ngOnInit(): void {
    console.log('Selected Item on Initttt:', this.selectedItem); // Log selected item

    this.activatedRoute.data.subscribe(({ salesInvoiceLines }) => {
      if (salesInvoiceLines) {
        if (Array.isArray(salesInvoiceLines)) {
          if (salesInvoiceLines.length > 0) {
            this.salesInvoiceLines = salesInvoiceLines;
            this.updateForm(this.salesInvoiceLines);
          }
        } else if (salesInvoiceLines.id !== null && salesInvoiceLines.id !== undefined) {
          // It's a single object with a valid ID
          this.salesInvoiceLines = [salesInvoiceLines];
          this.updateForm(this.salesInvoiceLines);
        }
      }
      console.log('Sales Invoice Lines:', this.salesInvoiceLines);
    });
  }

  onItemCodeSelect(event: Event, index: number): void {
    // Get the selected value (the item code)
    const inputElement = <HTMLInputElement>event.target;
    const selectedItemCode = inputElement.value; // The item code entered by the user

    // Find the matching item from the filteredItems array
    const selectedItem = this.filteredItems[index].find(item => item.code === selectedItemCode);

    // If the item is found, update the form for this row with the item's details
    if (selectedItem) {
      console.log('Selected itemssss:', selectedItem);

      // Update form controls for this row (e.g., item code, item name, etc.)
      const salesInvoiceLineGroup = this.salesInvoiceLinesArray.at(index) as FormGroup;

      salesInvoiceLineGroup.patchValue({
        itemcode: selectedItem.code,
        itemname: selectedItem.name,
        itemid: selectedItem.id, // Update other fields as necessary
        description: selectedItem.description ?? null,
        unitofmeasurement: selectedItem.unitofmeasurement ?? null,
        itemcost: Number(selectedItem.lastcost ?? 0),
        itemprice: Number(selectedItem.lastsellingprice ?? 0),
        sellingprice: Number(selectedItem.lastsellingprice ?? 0),
        tax: Number((selectedItem as any).tax ?? 0),
        // Add any other fields you want to update with the selected item's details
      });
    } else {
      console.log('Item not found for code:', selectedItemCode);
    }
  }

  onItemNameSelect(event: Event, index: number): void {
    // Get the selected value (the item code)
    const inputElement = <HTMLInputElement>event.target;
    const selectedItemCode = inputElement.value; // The item code entered by the user

    // Find the matching item from the filteredItems array
    const selectedItem = this.filteredItems[index].find(item => item.name === selectedItemCode);

    // If the item is found, update the form for this row with the item's details
    if (selectedItem) {
      console.log('Selected item:', selectedItem);

      // Update form controls for this row (e.g., item code, item name, etc.)
      const salesInvoiceLineGroup = this.salesInvoiceLinesArray.at(index) as FormGroup;

      salesInvoiceLineGroup.patchValue({
        itemcode: selectedItem.code,
        itemname: selectedItem.name,
        itemid: selectedItem.id, // Update other fields as necessary
        description: selectedItem.description ?? null,
        unitofmeasurement: selectedItem.unitofmeasurement ?? null,
        itemcost: Number(selectedItem.lastcost ?? 0),
        itemprice: Number(selectedItem.lastsellingprice ?? 0),
        sellingprice: Number(selectedItem.lastsellingprice ?? 0),
        tax: Number((selectedItem as any).tax ?? 0),
        // Add any other fields you want to update with the selected item's details
      });
    } else {
      console.log('Item not found for code:', selectedItemCode);
    }
  }
  // Get FormArray instance
  get salesInvoiceLinesArray(): FormArray {
    return this.editForm.get('salesInvoiceLines') as FormArray;
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

  onQuantityChange(index: number): void {
    const salesInvoiceLineGroup = this.salesInvoiceLinesDummyArray.at(index) as FormGroup;
    if (this.hasDiscountPercentage(salesInvoiceLineGroup)) {
      this.onDiscountPercentageChange(index);
    } else if (salesInvoiceLineGroup.get('discountValueIsPerUnit')?.value) {
      const perUnit = Number(salesInvoiceLineGroup.get('discountvalue')?.value || 0);
      const quantity = Number(salesInvoiceLineGroup.get('quantity')?.value || 0);
      salesInvoiceLineGroup.patchValue(
        {
          discount: Number((perUnit * quantity).toFixed(2)),
        },
        { emitEvent: false },
      );
      this.updateLineTotal(salesInvoiceLineGroup);
    } else {
      this.updateLineTotal(salesInvoiceLineGroup);
    }
  }
  onItemNameInput(event: Event, index: number): void {
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
      .getElementsByUserInputName(value) // Call the service to fetch items
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
  calculateTotal(): void {
    const total = this.salesInvoiceLinesDummyArray.controls
      .map(control => control.get('linetotal')?.value || 0)
      .reduce((acc, value) => acc + value, 0);

    console.log('Total Selling Price:', total);
    this.totalUpdated.emit(total);
  }

  // Add a new line to the form
  addSalesInvoiceLine(): void {
    const newRow = this.salesInvoiceLinesFormService.createSalesInvoiceLinesFormGroup();
    console.log('Adding row:', newRow.value);
    this.salesInvoiceLinesArray.push(this.salesInvoiceLinesFormService.createSalesInvoiceLinesFormGroup());
  }

  // Remove a line from the form
  removeSalesInvoiceLine(index: number): void {
    this.salesInvoiceLinesArray.removeAt(index);
    this.calculateTotal();
  }

  previousState(): void {
    window.history.back();
  }

  save(inid: number): Observable<any> {
    this.isSaving = true;

    // Ensure the form is initialized properly
    if (!this.editForm) {
      console.error('Form is not initialized');
      return of(null); // Return empty observable if the form is not initialized
    }

    // Ensure the form is a FormGroup and check if 'salesInvoiceLines' is a FormArray
    if (!(this.editForm.get('salesInvoiceLines') instanceof FormArray)) {
      console.error('Form is not an instance of FormArray');
      return of(null); // Return empty observable if salesInvoiceLines is not a FormArray
    }

    // Check if the form is valid
    if (!this.editForm.valid) {
      console.error('Form is invalid', this.editForm.errors);
      return of(null); // Return empty observable if the form is not valid
    }

    // Get the invoice lines from the form (now it's a FormArray)
    let salesInvoiceLines = this.salesInvoiceLinesFormService.getSalesInvoiceLines(this.salesInvoiceLinesArray);

    this.persistDiscountEntryTypesToStorage();

    // Assign invoiceid to all rows and ensure unique lineid across all aggregated lines
    // discount is already stored as (per-unit × qty) in the form field, so pass through directly
    salesInvoiceLines = salesInvoiceLines.map((line, index) => {
      const formGroup = this.salesInvoiceLinesArray.at(index) as FormGroup;
      const entryType = formGroup ? this.inferDiscountEntryTypeFromForm(formGroup) : null;
      const {
        discountpercentage: _dp,
        discountvalue: _dv,
        discountEntryType: _de,
        discountValueIsPerUnit: _dvpu,
        ...lineForApi
      } = line as any;
      return {
        ...lineForApi,
        invoiceid: inid,
        lineid: index + 1,
        description: this.buildDescriptionWithDiscountEntry(line, entryType),
      };
    });

    console.log('Modified sales invoice lines:', salesInvoiceLines);

    const saveObservables: Observable<HttpResponse<ISalesInvoiceLines>>[] = [];

    // Calculate next lineid for AutoJobsInvoiceLines if we have a sourceInvoiceId
    let nextAutoLineId = 1;
    if (this.sourceInvoiceId) {
      const existingLineIds = salesInvoiceLines.filter(l => (l as any).sourceLineId !== null).map(l => Number((l as any).sourceLineId));
      if (existingLineIds.length > 0) {
        nextAutoLineId = Math.max(...existingLineIds) + 1;
      }
    }

    salesInvoiceLines.forEach(line => {
      // If the line doesn't have an ID, or if it belongs to a different invoice (aggregated data), create it as new in SalesInvoiceLines
      if (line.id === null || line.id === undefined || line.invoiceid !== inid) {
        saveObservables.push(this.salesInvoiceLinesService.create({ ...line, id: null, invoiceid: inid }));

        // ONLY save to AutoJobsInvoiceLines if it's TRULY a new item added on this page
        if (this.sourceInvoiceId && (line as any).isNew) {
          const autoLine: NewAutojobsinvoicelines = {
            id: null,
            invocieid: this.sourceInvoiceId, // Note the typo in model: 'invocieid'
            lineid: nextAutoLineId++,
            itemid: line.itemid,
            itemcode: line.itemcode,
            itemname: line.itemname,
            description: line.description,
            unitofmeasurement: line.unitofmeasurement,
            quantity: line.quantity,
            itemcost: line.itemcost,
            itemprice: line.itemprice,
            discount: line.discount,
            tax: line.tax,
            sellingprice: line.sellingprice,
            linetotal: line.linetotal,
          };
          saveObservables.push(this.autojobsinvoicelinesService.create(autoLine) as any);
        }
      } else {
        // If it's an existing line for THIS invoice, update it
        saveObservables.push(this.salesInvoiceLinesService.update(line));
      }
    });

    // If there are any observables, subscribe to them
    if (saveObservables.length > 0) {
      return forkJoin(saveObservables).pipe(finalize(() => this.onSaveFinalize()));
    } else {
      this.onSaveFinalize();
      return of(null);
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISalesInvoiceLines>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // API for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }
  protected updateForm(salesInvoiceLines: ISalesInvoiceLines[]): void {
    console.log('Received Sales Invoice Lines:', salesInvoiceLines);

    // Check the type of salesInvoiceLines
    console.log('Type of salesInvoiceLines:', Array.isArray(salesInvoiceLines) ? 'Array' : 'Not an Array');

    // Check if salesInvoiceLines is valid (non-empty array)
    if (!Array.isArray(salesInvoiceLines) || salesInvoiceLines.length === 0) {
      console.log('No sales invoice lines provided!');
      return; // Don't proceed if the data is invalid or empty
    }

    // If the data is valid, continue processing
    this.salesInvoiceLines = salesInvoiceLines;

    // Reset form and add each line to the form array
    this.salesInvoiceLinesArray.clear(); // Clear the existing form array before adding new lines

    // Add each line of salesInvoiceLines to the form array
    salesInvoiceLines.forEach(line => {
      const formGroup = this.salesInvoiceLinesFormService.createSalesInvoiceLinesFormGroup(line);
      this.ensureDiscountControls(formGroup);

      const totalLineDiscount = Number(formGroup.get('discount')?.value || 0);
      const quantity = Number(formGroup.get('quantity')?.value || 0);
      const discountEntryType = this.getDiscountEntryTypeForItem(line);
      const { discountPercentage, discountValue } = this.resolveDiscountDisplayFields(line, discountEntryType, totalLineDiscount);
      const discountValueIsPerUnit =
        discountEntryType === 'value' &&
        discountValue != null &&
        totalLineDiscount > 0 &&
        quantity > 0 &&
        Math.abs(totalLineDiscount - Number(discountValue) * quantity) < 0.01;
      (formGroup as FormGroup).patchValue(
        {
          discountpercentage: discountPercentage,
          discountvalue: discountValue,
          discountEntryType: discountEntryType ?? null,
          discountValueIsPerUnit,
        },
        { emitEvent: false },
      );
      console.log('Created form group:', formGroup.value); // Log the form group values
      this.salesInvoiceLinesArray.push(formGroup);
    });
  }

  removeInvoiceLine(index: number): void {
    this.salesInvoiceLinesDummyArray.removeAt(index);
    this.calculateTotal();
  }

  /** Returns current local time as a Dayjs that serializes to local time (not UTC) */
  private localNow(): dayjs.Dayjs {
    return dayjs().add(-new Date().getTimezoneOffset(), 'minute');
  }

  transactionmodule(inid: number): void {
    this.salesinvoiceService.find(inid).subscribe(res => {
      const invoice = res.body;
      if (invoice) {
        this.inventorytransac(invoice);
        this.inventoryLineCreditTransactions(invoice);
        this.serviceChargeDebitTransaction(invoice);
        this.createbin(invoice);
        this.updatesalesincome(invoice);
        this.addtrasction(invoice);
      }
    });
  }

  private inventorytransac(invoice: any): void {
    // Logic moved to closingstocktransaction and salestransaction as per supervisor's instruction
    this.closestockupdate(invoice);
  }

  updatesalesincome(invoice: any): void {
    this.salesincometransactions(invoice);
    this.salestransaction(invoice);
  }

  salesincometransactions(invoice: any): void {
    let totalItemCostSales = 0;
    let totalItemPriceSales = 0;

    this.salesInvoiceLinesDummyArray.controls.forEach(control => {
      const line = (control as FormGroup).getRawValue();
      const cost = (Number(line.itemcost) || 0) * (Number(line.quantity) || 0);
      const price = Number(line.linetotal) || 0;

      if ((Number(line.itemcost) || 0) > 0) {
        totalItemCostSales += cost;
        totalItemPriceSales += price;
      }
    });

    const profit = totalItemPriceSales - totalItemCostSales;

    // 1612047 – Item Sales Profit account DEBIT
    const transaction1: NewTransactions = {
      id: null,
      accountId: 33,
      accountCode: '42',
      debit: profit,
      credit: 0,
      date: this.localNow(),
      refDoc: invoice.code,
      refId: invoice.id,
      subId: this.salesInvoiceLinesService.getSubId(),
      source: 'Invoice-Item Profit',
      lmu: invoice.lmu,
      lmd: this.localNow(),
    };
    this.transactionsService.create(transaction1).subscribe();

    this.accountsService.find(33).subscribe(res => {
      const account = res.body;
      if (account) {
        this.accountsService.updateBalance(account.id, (account.balance ?? 0) + profit).subscribe();
      }
    });
  }

  addtrasction(invoice: any): void {
    // 1612042 – Customer AR account DEBIT → use invoice.nettotal (includes items + all service charges)
    const netTotal = Number(invoice.nettotal) || Number(invoice.subtotal) || 0;

    if (invoice.customerid) {
      this.customerService.find(invoice.customerid).subscribe(res => {
        const customer = res.body;
        if (customer) {
          this.salesinvoiceService.fetchReceiptAccountId(customer.fullname || '').subscribe(resAcc => {
            const accounts = resAcc.body;
            if (accounts && accounts.length > 0) {
              const account = accounts[0];
              const transaction3: NewTransactions = {
                id: null,
                accountId: account.id,
                accountCode: account.code,
                debit: netTotal,
                credit: 0,
                date: this.localNow(),
                refDoc: invoice.code,
                refId: invoice.id,
                subId: this.salesInvoiceLinesService.getSubId(),
                source: 'Invoice',
                lmu: invoice.lmu,
                lmd: this.localNow(),
              };
              this.transactionsService.create(transaction3).subscribe();
              this.accountsService.updateBalance(account.id, (account.balance ?? 0) + netTotal).subscribe();
            }
          });
        }
      });
    }
  }

  /** 1612043–N: One Credit per line item — credits the item's own inventory account */
  private inventoryLineCreditTransactions(invoice: any): void {
    this.salesInvoiceLinesDummyArray.controls.forEach(control => {
      const line = (control as FormGroup).getRawValue();
      const lineTotal = Number(line.linetotal) || 0;
      const itemId = Number(line.itemid) || 0;

      if (itemId <= 0 || lineTotal <= 0) return;

      // Fetch the inventory record to get its accountId
      this.inventoryService.query({ 'id.equals': itemId }).subscribe({
        next: invRes => {
          const inventory = invRes.body?.[0];
          if (!inventory) return;

          const invAccountId = (inventory as any).accountId ?? (inventory as any).accountid ?? null;
          if (!invAccountId) {
            console.warn(`No accountId on inventory record for itemId ${itemId}`);
            return;
          }

          const invCredit: NewTransactions = {
            id: null,
            accountId: invAccountId,
            accountCode: (inventory as any).accountCode ?? (inventory as any).accountcode ?? '',
            debit: 0,
            credit: lineTotal,
            date: this.localNow(),
            refDoc: invoice.code,
            refId: invoice.id,
            subId: this.salesInvoiceLinesService.getSubId(),
            // source: `Invoice-Inv-${line.itemcode ?? itemId}`,
            source: 'Inventory',
            lmu: invoice.lmu,
            lmd: this.localNow(),
          };
          this.transactionsService.create(invCredit).subscribe();

          // Adjust inventory account balance
          this.accountsService.find(invAccountId).subscribe(accRes => {
            const acc = accRes.body;
            if (acc) {
              this.accountsService.updateBalance(acc.id, (acc.balance ?? 0) - lineTotal).subscribe();
            }
          });
        },
        error: err => console.error(`Error fetching inventory for itemId ${itemId}:`, err),
      });
    });
  }

  /** 1612045: Service Charge account DEBIT — total of all service charges on this invoice */
  private serviceChargeDebitTransaction(invoice: any): void {
    const svcTotal = (this.serviceChargeTotal || 0) + (this.commonServiceChargeTotal || 0);
    if (svcTotal <= 0) return;

    const svcDebit: NewTransactions = {
      id: null,
      accountId: 32,
      accountCode: '41',
      debit: svcTotal,
      credit: 0,
      date: this.localNow(),
      refDoc: invoice.code,
      refId: invoice.id,
      subId: this.salesInvoiceLinesService.getSubId(),
      source: 'Invoice-Service Charge',
      lmu: invoice.lmu,
      lmd: this.localNow(),
    };
    this.transactionsService.create(svcDebit).subscribe();

    this.accountsService.find(32).subscribe(res => {
      const account = res.body;
      if (account) {
        this.accountsService.updateBalance(account.id, (account.balance ?? 0) + svcTotal).subscribe();
      }
    });
  }

  closestockupdate(invoice: any): void {
    const totalAmount = this.salesInvoiceLinesDummyArray.controls
      .map(control => control.get('linetotal')?.value || 0)
      .reduce((acc, val) => acc + val, 0);

    if (totalAmount > 0) {
      this.closingstocktransaction(invoice, totalAmount);
    }
  }

  closingstocktransaction(invoice: any, amount: number): void {
    // 1612046 – Closing Stock (CLSSTK) account DEBIT
    const transaction4: NewTransactions = {
      id: null,
      accountId: 125,
      accountCode: 'CLSSTK',
      debit: amount,
      credit: 0,
      date: this.localNow(),
      refDoc: invoice.code,
      refId: invoice.id,
      subId: this.salesInvoiceLinesService.getSubId(),
      source: 'Finish Goods Transfer',
      lmu: invoice.lmu,
      lmd: this.localNow(),
    };
    this.transactionsService.create(transaction4).subscribe();

    this.accountsService.find(125).subscribe(res => {
      const account = res.body;
      if (account) {
        this.accountsService.updateBalance(account.id, (account.balance ?? 0) - amount).subscribe();
      }
    });
  }

  private salestransaction(invoice: any): void {
    const totalCost = this.salesInvoiceLinesDummyArray.controls
      .map(control => (control.get('itemcost')?.value || 0) * (control.get('quantity')?.value || 0))
      .reduce((acc, val) => acc + val, 0);

    if (totalCost > 0) {
      const transaction5: NewTransactions = {
        id: null,
        accountId: 41,
        accountCode: '513',
        debit: totalCost,
        credit: 0,
        date: this.localNow(),
        refDoc: invoice.code,
        refId: invoice.id,
        subId: this.salesInvoiceLinesService.getSubId(),
        source: 'Invoice',
        lmu: invoice.lmu,
        lmd: this.localNow(),
      };
      this.transactionsService.create(transaction5).subscribe();

      this.accountsService.find(41).subscribe(res => {
        const account = res.body;
        if (account) {
          this.accountsService.updateBalance(account.id, (account.balance ?? 0) + totalCost).subscribe();
        }
      });
    }
  }

  setvaluesbincar(): void {
    this.bincard = this.salesInvoiceLinesDummyArray.value.map((item: any) => ({
      itemID: item.itemid,
      itemCode: item.itemcode,
      qtyIn: 0,
      qtyOut: item.quantity,
      reference: 'SalesInvoice',
      price: item.sellingprice,
      locationID: 1,
      lMD: dayjs().add(-new Date().getTimezoneOffset(), 'minute'),
      recordDate: dayjs().add(-new Date().getTimezoneOffset(), 'minute'),
      batchId: item.itemid,
      referenceCode: this.nextvalue,
    }));

    console.log('Updated bincard:', this.bincard);
  }

  private createbin(invoice: any): void {
    if (this.isBinCreated) {
      console.warn('Bin records already created for this session.');
      return;
    }
    if (!this.bincard || this.bincard.length === 0) {
      console.warn('No bin records to create.');
      return;
    }
    this.isBinCreated = true;

    this.bincard.forEach(bin => {
      // 1. Fetch real Inventory ID and opening balance by itemCode to ensure correct mapping
      this.inventoryService.query({ 'code.equals': bin.itemCode }).subscribe({
        next: invRes => {
          const inventory = invRes.body?.[0];
          if (inventory) {
            const realItemID = inventory.id;
            const openingBalance = inventory.availablequantity ?? 0;
            const newInventoryQty = openingBalance - (bin.qtyOut ?? 0);

            // Update main inventory table
            this.inventoryService.partialUpdate({ id: realItemID, availablequantity: newInventoryQty }).subscribe();

            // 2. Fetch the first active Batch ID for this item (FIFO)
            this.inventorybatchesService.query({ 'itemid.equals': realItemID, 'quantity.greaterThan': 0, sort: ['id,asc'] }).subscribe({
              next: batchRes => {
                const batch = batchRes.body?.[0];
                const realBatchId = batch ? batch.id : realItemID; // Fallback to itemID if no batch found

                // Update batch quantity if a batch was found
                if (batch) {
                  const newBatchQty = (batch.quantity ?? 0) - (bin.qtyOut ?? 0);
                  this.inventorybatchesService.partialUpdate({ id: batch.id, quantity: newBatchQty }).subscribe();
                }

                // 3. Construct and create the BinCard record
                const finalBin: NewBinCard = {
                  ...bin,
                  id: null,
                  itemID: realItemID,
                  batchId: realBatchId,
                  opening: openingBalance,
                  referenceCode: invoice.code,
                  recordDate: dayjs().add(-new Date().getTimezoneOffset(), 'minute'),
                  txDate: dayjs().add(-new Date().getTimezoneOffset(), 'minute'),
                  lMD: dayjs().add(-new Date().getTimezoneOffset(), 'minute'),
                  referenceDoc: 'Sales Invoice',
                  lMU: invoice.lmu,
                } as NewBinCard;

                this.binCardService.create(finalBin).subscribe({
                  next: () =>
                    console.log(`BinCard successfully created for ${bin.itemCode} (Item ID: ${realItemID}, Batch ID: ${realBatchId})`),
                  error: err => console.error(`Failed to create BinCard for ${bin.itemCode}:`, err),
                });
              },
              error: err => console.error(`Error fetching batches for item ${bin.itemCode}:`, err),
            });
          } else {
            console.warn(`No inventory record found for item code: ${bin.itemCode}`);
          }
        },
        error: err => console.error(`Error fetching inventory for item code ${bin.itemCode}:`, err),
      });
    });
  }
}
