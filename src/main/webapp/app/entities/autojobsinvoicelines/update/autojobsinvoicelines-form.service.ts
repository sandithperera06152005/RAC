import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IAutojobsinvoicelines, NewAutojobsinvoicelines } from '../autojobsinvoicelines.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAutojobsinvoicelines for edit and NewAutojobsinvoicelinesFormGroupInput for create.
 */
type AutojobsinvoicelinesFormGroupInput = IAutojobsinvoicelines | PartialWithRequiredKeyOf<NewAutojobsinvoicelines>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IAutojobsinvoicelines | NewAutojobsinvoicelines> = Omit<T, 'lmd'> & {
  lmd?: string | null;
};

type AutojobsinvoicelinesFormRawValue = FormValueOf<IAutojobsinvoicelines>;

type NewAutojobsinvoicelinesFormRawValue = FormValueOf<NewAutojobsinvoicelines>;

type AutojobsinvoicelinesFormDefaults = Pick<NewAutojobsinvoicelines, 'id' | 'lmd' | 'nbt' | 'vat'>;

type AutojobsinvoicelinesFormGroupContent = {
  id: FormControl<AutojobsinvoicelinesFormRawValue['id'] | NewAutojobsinvoicelines['id']>;
  invocieid: FormControl<AutojobsinvoicelinesFormRawValue['invocieid']>;
  lineid: FormControl<AutojobsinvoicelinesFormRawValue['lineid']>;
  itemid: FormControl<AutojobsinvoicelinesFormRawValue['itemid']>;
  itemcode: FormControl<AutojobsinvoicelinesFormRawValue['itemcode']>;
  itemname: FormControl<AutojobsinvoicelinesFormRawValue['itemname']>;
  description: FormControl<AutojobsinvoicelinesFormRawValue['description']>;
  unitofmeasurement: FormControl<AutojobsinvoicelinesFormRawValue['unitofmeasurement']>;
  quantity: FormControl<AutojobsinvoicelinesFormRawValue['quantity']>;
  itemcost: FormControl<AutojobsinvoicelinesFormRawValue['itemcost']>;
  itemprice: FormControl<AutojobsinvoicelinesFormRawValue['itemprice']>;
  discount: FormControl<AutojobsinvoicelinesFormRawValue['discount']>;
  tax: FormControl<AutojobsinvoicelinesFormRawValue['tax']>;
  sellingprice: FormControl<AutojobsinvoicelinesFormRawValue['sellingprice']>;
  linetotal: FormControl<AutojobsinvoicelinesFormRawValue['linetotal']>;
  lmu: FormControl<AutojobsinvoicelinesFormRawValue['lmu']>;
  lmd: FormControl<AutojobsinvoicelinesFormRawValue['lmd']>;
  nbt: FormControl<AutojobsinvoicelinesFormRawValue['nbt']>;
  vat: FormControl<AutojobsinvoicelinesFormRawValue['vat']>;
};

export type AutojobsinvoicelinesFormGroup = FormGroup<AutojobsinvoicelinesFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AutojobsinvoicelinesFormService {
  createAutojobsinvoicelinesFormGroup(
    autojobsinvoicelines: AutojobsinvoicelinesFormGroupInput = { id: null },
  ): AutojobsinvoicelinesFormGroup {
    const autojobsinvoicelinesRawValue = this.convertAutojobsinvoicelinesToAutojobsinvoicelinesRawValue({
      ...this.getFormDefaults(),
      ...autojobsinvoicelines,
    });
    return new FormGroup<AutojobsinvoicelinesFormGroupContent>({
      id: new FormControl(
        { value: autojobsinvoicelinesRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      invocieid: new FormControl(autojobsinvoicelinesRawValue.invocieid),
      lineid: new FormControl(autojobsinvoicelinesRawValue.lineid),
      itemid: new FormControl(autojobsinvoicelinesRawValue.itemid),
      itemcode: new FormControl(autojobsinvoicelinesRawValue.itemcode),
      itemname: new FormControl(autojobsinvoicelinesRawValue.itemname),
      description: new FormControl(autojobsinvoicelinesRawValue.description),
      unitofmeasurement: new FormControl(autojobsinvoicelinesRawValue.unitofmeasurement),
      quantity: new FormControl(autojobsinvoicelinesRawValue.quantity),
      itemcost: new FormControl(autojobsinvoicelinesRawValue.itemcost),
      itemprice: new FormControl(autojobsinvoicelinesRawValue.itemprice),
      discount: new FormControl(autojobsinvoicelinesRawValue.discount),
      tax: new FormControl(autojobsinvoicelinesRawValue.tax),
      sellingprice: new FormControl(autojobsinvoicelinesRawValue.sellingprice),
      linetotal: new FormControl(autojobsinvoicelinesRawValue.linetotal),
      lmu: new FormControl(autojobsinvoicelinesRawValue.lmu),
      lmd: new FormControl(autojobsinvoicelinesRawValue.lmd),
      nbt: new FormControl(autojobsinvoicelinesRawValue.nbt),
      vat: new FormControl(autojobsinvoicelinesRawValue.vat),
    });
  }

  getAutojobsinvoicelines(form: AutojobsinvoicelinesFormGroup): IAutojobsinvoicelines | NewAutojobsinvoicelines {
    return this.convertAutojobsinvoicelinesRawValueToAutojobsinvoicelines(
      form.getRawValue() as AutojobsinvoicelinesFormRawValue | NewAutojobsinvoicelinesFormRawValue,
    );
  }

  resetForm(form: AutojobsinvoicelinesFormGroup, autojobsinvoicelines: AutojobsinvoicelinesFormGroupInput): void {
    const autojobsinvoicelinesRawValue = this.convertAutojobsinvoicelinesToAutojobsinvoicelinesRawValue({
      ...this.getFormDefaults(),
      ...autojobsinvoicelines,
    });
    form.reset(
      {
        ...autojobsinvoicelinesRawValue,
        id: { value: autojobsinvoicelinesRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): AutojobsinvoicelinesFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      lmd: currentTime,
      nbt: false,
      vat: false,
    };
  }

  private convertAutojobsinvoicelinesRawValueToAutojobsinvoicelines(
    rawAutojobsinvoicelines: AutojobsinvoicelinesFormRawValue | NewAutojobsinvoicelinesFormRawValue,
  ): IAutojobsinvoicelines | NewAutojobsinvoicelines {
    return {
      ...rawAutojobsinvoicelines,
      lmd: dayjs(rawAutojobsinvoicelines.lmd, DATE_TIME_FORMAT),
    };
  }

  private convertAutojobsinvoicelinesToAutojobsinvoicelinesRawValue(
    autojobsinvoicelines: IAutojobsinvoicelines | (Partial<NewAutojobsinvoicelines> & AutojobsinvoicelinesFormDefaults),
  ): AutojobsinvoicelinesFormRawValue | PartialWithRequiredKeyOf<NewAutojobsinvoicelinesFormRawValue> {
    return {
      ...autojobsinvoicelines,
      lmd: autojobsinvoicelines.lmd ? autojobsinvoicelines.lmd.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
