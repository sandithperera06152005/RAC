import { Injectable } from '@angular/core';
import { FormArray, FormControl, FormGroup, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ISalesInvoiceLines, NewSalesInvoiceLines } from '../sales-invoice-lines.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ISalesInvoiceLines for edit and NewSalesInvoiceLinesFormGroupInput for create.
 */
type SalesInvoiceLinesFormGroupInput = ISalesInvoiceLines | PartialWithRequiredKeyOf<NewSalesInvoiceLines>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ISalesInvoiceLines | NewSalesInvoiceLines> = Omit<T, 'lmd'> & {
  lmd?: string | null;
};

type SalesInvoiceLinesFormRawValue = FormValueOf<ISalesInvoiceLines>;

type NewSalesInvoiceLinesFormRawValue = FormValueOf<NewSalesInvoiceLines>;

type SalesInvoiceLinesFormDefaults = Pick<NewSalesInvoiceLines, 'id' | 'lmd' | 'nbt' | 'vat'>;

type SalesInvoiceLinesFormGroupContent = {
  id: FormControl<SalesInvoiceLinesFormRawValue['id'] | NewSalesInvoiceLines['id']>;
  invoiceid: FormControl<SalesInvoiceLinesFormRawValue['invoiceid']>;
  lineid: FormControl<SalesInvoiceLinesFormRawValue['lineid']>;
  itemid: FormControl<SalesInvoiceLinesFormRawValue['itemid']>;
  itemcode: FormControl<SalesInvoiceLinesFormRawValue['itemcode']>;
  itemname: FormControl<SalesInvoiceLinesFormRawValue['itemname']>;
  description: FormControl<SalesInvoiceLinesFormRawValue['description']>;
  unitofmeasurement: FormControl<SalesInvoiceLinesFormRawValue['unitofmeasurement']>;
  quantity: FormControl<SalesInvoiceLinesFormRawValue['quantity']>;
  itemcost: FormControl<SalesInvoiceLinesFormRawValue['itemcost']>;
  itemprice: FormControl<SalesInvoiceLinesFormRawValue['itemprice']>;
  discount: FormControl<SalesInvoiceLinesFormRawValue['discount']>;
  tax: FormControl<SalesInvoiceLinesFormRawValue['tax']>;
  sellingprice: FormControl<SalesInvoiceLinesFormRawValue['sellingprice']>;
  linetotal: FormControl<SalesInvoiceLinesFormRawValue['linetotal']>;
  lmu: FormControl<SalesInvoiceLinesFormRawValue['lmu']>;
  lmd: FormControl<SalesInvoiceLinesFormRawValue['lmd']>;
  nbt: FormControl<SalesInvoiceLinesFormRawValue['nbt']>;
  vat: FormControl<SalesInvoiceLinesFormRawValue['vat']>;
};

export type SalesInvoiceLinesFormGroup = FormGroup<SalesInvoiceLinesFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class SalesInvoiceLinesFormService {
  createSalesInvoiceLinesFormArray(salesInvoiceLinesArray: SalesInvoiceLinesFormGroupInput[] = [{ id: null }]): FormArray {
    const formArray = new FormArray(
      salesInvoiceLinesArray.map(salesInvoiceLines => this.createSalesInvoiceLinesFormGroup(salesInvoiceLines)),
    );
    return formArray;
  }

  createSalesInvoiceLinesFormGroup(salesInvoiceLines: SalesInvoiceLinesFormGroupInput = { id: null }): SalesInvoiceLinesFormGroup {
    const salesInvoiceLinesRawValue = this.convertSalesInvoiceLinesToSalesInvoiceLinesRawValue({
      ...this.getFormDefaults(),
      ...salesInvoiceLines,
    });
    return new FormGroup<SalesInvoiceLinesFormGroupContent>({
      id: new FormControl(
        { value: salesInvoiceLinesRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      invoiceid: new FormControl(salesInvoiceLinesRawValue.invoiceid),
      lineid: new FormControl(salesInvoiceLinesRawValue.lineid),
      itemid: new FormControl(salesInvoiceLinesRawValue.itemid),
      itemcode: new FormControl(salesInvoiceLinesRawValue.itemcode),
      itemname: new FormControl(salesInvoiceLinesRawValue.itemname),
      description: new FormControl(salesInvoiceLinesRawValue.description),
      unitofmeasurement: new FormControl(salesInvoiceLinesRawValue.unitofmeasurement),
      quantity: new FormControl(salesInvoiceLinesRawValue.quantity),
      itemcost: new FormControl(salesInvoiceLinesRawValue.itemcost),
      itemprice: new FormControl(salesInvoiceLinesRawValue.itemprice),
      discount: new FormControl(salesInvoiceLinesRawValue.discount),
      tax: new FormControl(salesInvoiceLinesRawValue.tax),
      sellingprice: new FormControl(salesInvoiceLinesRawValue.sellingprice),
      linetotal: new FormControl(salesInvoiceLinesRawValue.linetotal),
      lmu: new FormControl(salesInvoiceLinesRawValue.lmu),
      lmd: new FormControl(salesInvoiceLinesRawValue.lmd),
      nbt: new FormControl(salesInvoiceLinesRawValue.nbt),
      vat: new FormControl(salesInvoiceLinesRawValue.vat),
    });
  }

  getSalesInvoiceLines(form: FormArray): (ISalesInvoiceLines | NewSalesInvoiceLines)[] {
    return form.controls.map(control =>
      this.convertSalesInvoiceLinesRawValueToSalesInvoiceLines(
        control.getRawValue() as SalesInvoiceLinesFormRawValue | NewSalesInvoiceLinesFormRawValue,
      ),
    );
  }

  resetForm(form: FormArray, salesInvoiceLines: SalesInvoiceLinesFormGroupInput[]): void {
    const salesInvoiceLinesRawValue = salesInvoiceLines.map(salesInvoiceLines =>
      this.convertSalesInvoiceLinesToSalesInvoiceLinesRawValue({
        ...this.getFormDefaults(),
        ...salesInvoiceLines,
      }),
    );
    form.clear();
    salesInvoiceLinesRawValue.forEach(salesInvoiceLine => {
      form.push(this.createSalesInvoiceLinesFormGroup(this.convertSalesInvoiceLinesRawValueToSalesInvoiceLines(salesInvoiceLine)));
    });
  }

  private getFormDefaults(): SalesInvoiceLinesFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      lmd: currentTime,
      nbt: false,
      vat: false,
    };
  }

  private convertSalesInvoiceLinesRawValueToSalesInvoiceLines(
    rawSalesInvoiceLines: SalesInvoiceLinesFormRawValue | NewSalesInvoiceLinesFormRawValue,
  ): ISalesInvoiceLines | NewSalesInvoiceLines {
    return {
      ...rawSalesInvoiceLines,
      lmd: dayjs(rawSalesInvoiceLines.lmd, DATE_TIME_FORMAT),
    };
  }

  private convertSalesInvoiceLinesToSalesInvoiceLinesRawValue(
    salesInvoiceLines: ISalesInvoiceLines | (Partial<NewSalesInvoiceLines> & SalesInvoiceLinesFormDefaults),
  ): SalesInvoiceLinesFormRawValue | PartialWithRequiredKeyOf<NewSalesInvoiceLinesFormRawValue> {
    return {
      ...salesInvoiceLines,
      lmd: salesInvoiceLines.lmd ? salesInvoiceLines.lmd.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
