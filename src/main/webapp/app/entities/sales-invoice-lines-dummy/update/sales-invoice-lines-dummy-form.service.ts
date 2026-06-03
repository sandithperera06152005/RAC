import { Injectable } from '@angular/core';
import { FormArray, FormControl, FormGroup, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ISalesInvoiceLinesDummy, NewSalesInvoiceLinesDummy } from '../sales-invoice-lines-dummy.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ISalesInvoiceLinesDummy for edit and NewSalesInvoiceLinesDummyFormGroupInput for create.
 */
type SalesInvoiceLinesDummyFormGroupInput = ISalesInvoiceLinesDummy | PartialWithRequiredKeyOf<NewSalesInvoiceLinesDummy>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ISalesInvoiceLinesDummy | NewSalesInvoiceLinesDummy> = Omit<T, 'lmd'> & {
  lmd?: string | null;
};

type SalesInvoiceLinesDummyFormRawValue = FormValueOf<ISalesInvoiceLinesDummy>;

type NewSalesInvoiceLinesDummyFormRawValue = FormValueOf<NewSalesInvoiceLinesDummy>;

type SalesInvoiceLinesDummyFormDefaults = Pick<NewSalesInvoiceLinesDummy, 'id' | 'lmd' | 'nbt' | 'vat'>;

type SalesInvoiceLinesDummyFormGroupContent = {
  id: FormControl<SalesInvoiceLinesDummyFormRawValue['id'] | NewSalesInvoiceLinesDummy['id']>;
  invoiceId: FormControl<SalesInvoiceLinesDummyFormRawValue['invoiceId']>;
  lineId: FormControl<SalesInvoiceLinesDummyFormRawValue['lineId']>;
  itemId: FormControl<SalesInvoiceLinesDummyFormRawValue['itemId']>;
  itemCode: FormControl<SalesInvoiceLinesDummyFormRawValue['itemCode']>;
  itemName: FormControl<SalesInvoiceLinesDummyFormRawValue['itemName']>;
  description: FormControl<SalesInvoiceLinesDummyFormRawValue['description']>;
  unitOfMeasurement: FormControl<SalesInvoiceLinesDummyFormRawValue['unitOfMeasurement']>;
  quantity: FormControl<SalesInvoiceLinesDummyFormRawValue['quantity']>;
  itemCost: FormControl<SalesInvoiceLinesDummyFormRawValue['itemCost']>;
  itemPrice: FormControl<SalesInvoiceLinesDummyFormRawValue['itemPrice']>;
  discount: FormControl<SalesInvoiceLinesDummyFormRawValue['discount']>;
  tax: FormControl<SalesInvoiceLinesDummyFormRawValue['tax']>;
  sellingPrice: FormControl<SalesInvoiceLinesDummyFormRawValue['sellingPrice']>;
  lineTotal: FormControl<SalesInvoiceLinesDummyFormRawValue['lineTotal']>;
  lmu: FormControl<SalesInvoiceLinesDummyFormRawValue['lmu']>;
  lmd: FormControl<SalesInvoiceLinesDummyFormRawValue['lmd']>;
  nbt: FormControl<SalesInvoiceLinesDummyFormRawValue['nbt']>;
  vat: FormControl<SalesInvoiceLinesDummyFormRawValue['vat']>;
};

export type SalesInvoiceLinesDummyFormGroup = FormGroup<SalesInvoiceLinesDummyFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class SalesInvoiceLinesDummyFormService {
  createSalesInvoiceLinesDummyFormArray(
    salesInvoiceLinesDummies: (ISalesInvoiceLinesDummy | Partial<NewSalesInvoiceLinesDummy>)[] = [],
  ): FormArray {
    const formArray = new FormArray(
      salesInvoiceLinesDummies.map(salesInvoiceLinesDummy => this.createSalesInvoiceLinesDummyFormGroup(salesInvoiceLinesDummy)),
    );
    return formArray;
  }

  createSalesInvoiceLinesDummyFormGroup(
    salesInvoiceLinesDummy: ISalesInvoiceLinesDummy | Partial<NewSalesInvoiceLinesDummy> = { id: null },
  ): FormGroup {
    const salesInvoiceLinesDummyRawValue = this.convertSalesInvoiceLinesDummyToRawValue({
      ...this.getFormDefaults(),
      ...salesInvoiceLinesDummy,
    });

    return new FormGroup({
      id: new FormControl(
        { value: salesInvoiceLinesDummyRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      invoiceId: new FormControl(salesInvoiceLinesDummyRawValue.invoiceId),
      lineId: new FormControl(salesInvoiceLinesDummyRawValue.lineId),
      itemId: new FormControl(salesInvoiceLinesDummyRawValue.itemId),
      itemCode: new FormControl(salesInvoiceLinesDummyRawValue.itemCode),
      itemName: new FormControl(salesInvoiceLinesDummyRawValue.itemName),
      description: new FormControl(salesInvoiceLinesDummyRawValue.description),
      unitOfMeasurement: new FormControl(salesInvoiceLinesDummyRawValue.unitOfMeasurement),
      quantity: new FormControl(salesInvoiceLinesDummyRawValue.quantity),
      itemCost: new FormControl(salesInvoiceLinesDummyRawValue.itemCost),
      itemPrice: new FormControl(salesInvoiceLinesDummyRawValue.itemPrice),
      discount: new FormControl(salesInvoiceLinesDummyRawValue.discount),
      tax: new FormControl(salesInvoiceLinesDummyRawValue.tax),
      sellingPrice: new FormControl(salesInvoiceLinesDummyRawValue.sellingPrice),
      lineTotal: new FormControl(salesInvoiceLinesDummyRawValue.lineTotal),
      lmu: new FormControl(salesInvoiceLinesDummyRawValue.lmu),
      lmd: new FormControl(salesInvoiceLinesDummyRawValue.lmd),
      nbt: new FormControl(salesInvoiceLinesDummyRawValue.nbt),
      vat: new FormControl(salesInvoiceLinesDummyRawValue.vat),
    });
  }

  getSalesInvoiceLinesDummy(formArray: FormArray): (ISalesInvoiceLinesDummy | NewSalesInvoiceLinesDummy)[] {
    return formArray.controls.map(form => this.convertRawValueToSalesInvoiceLinesDummy(form.getRawValue()));
  }

  resetForm(formArray: FormArray, salesInvoiceLinesDummies: (ISalesInvoiceLinesDummy | Partial<NewSalesInvoiceLinesDummy>)[]): void {
    formArray.clear();
    salesInvoiceLinesDummies.forEach(salesInvoiceLinesDummy => {
      formArray.push(this.createSalesInvoiceLinesDummyFormGroup(salesInvoiceLinesDummy));
    });
  }

  private getFormDefaults(): SalesInvoiceLinesDummyFormDefaults {
    return {
      id: null,
      lmd: dayjs(),
      nbt: false,
      vat: false,
    };
  }

  private convertRawValueToSalesInvoiceLinesDummy(
    rawSalesInvoiceLinesDummy: SalesInvoiceLinesDummyFormRawValue | NewSalesInvoiceLinesDummyFormRawValue,
  ): ISalesInvoiceLinesDummy | NewSalesInvoiceLinesDummy {
    return {
      ...rawSalesInvoiceLinesDummy,
      lmd: dayjs(rawSalesInvoiceLinesDummy.lmd, DATE_TIME_FORMAT),
    };
  }

  private convertSalesInvoiceLinesDummyToRawValue(
    salesInvoiceLinesDummy: ISalesInvoiceLinesDummy | Partial<NewSalesInvoiceLinesDummy>,
  ): SalesInvoiceLinesDummyFormRawValue {
    return {
      ...salesInvoiceLinesDummy,
      id: salesInvoiceLinesDummy.id ?? 0,
      lmd: salesInvoiceLinesDummy.lmd ? salesInvoiceLinesDummy.lmd.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
