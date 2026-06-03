import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IAutojobsinvoicelinebatches, NewAutojobsinvoicelinebatches } from '../autojobsinvoicelinebatches.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAutojobsinvoicelinebatches for edit and NewAutojobsinvoicelinebatchesFormGroupInput for create.
 */
type AutojobsinvoicelinebatchesFormGroupInput = IAutojobsinvoicelinebatches | PartialWithRequiredKeyOf<NewAutojobsinvoicelinebatches>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IAutojobsinvoicelinebatches | NewAutojobsinvoicelinebatches> = Omit<
  T,
  'txdate' | 'manufacturedate' | 'expireddate' | 'lmd' | 'issueddatetime'
> & {
  txdate?: string | null;
  manufacturedate?: string | null;
  expireddate?: string | null;
  lmd?: string | null;
  issueddatetime?: string | null;
};

type AutojobsinvoicelinebatchesFormRawValue = FormValueOf<IAutojobsinvoicelinebatches>;

type NewAutojobsinvoicelinebatchesFormRawValue = FormValueOf<NewAutojobsinvoicelinebatches>;

type AutojobsinvoicelinebatchesFormDefaults = Pick<
  NewAutojobsinvoicelinebatches,
  'id' | 'txdate' | 'manufacturedate' | 'expireddate' | 'lmd' | 'nbt' | 'vat' | 'issued' | 'issueddatetime'
>;

type AutojobsinvoicelinebatchesFormGroupContent = {
  id: FormControl<AutojobsinvoicelinebatchesFormRawValue['id'] | NewAutojobsinvoicelinebatches['id']>;
  lineid: FormControl<AutojobsinvoicelinebatchesFormRawValue['lineid']>;
  batchlineid: FormControl<AutojobsinvoicelinebatchesFormRawValue['batchlineid']>;
  itemid: FormControl<AutojobsinvoicelinebatchesFormRawValue['itemid']>;
  code: FormControl<AutojobsinvoicelinebatchesFormRawValue['code']>;
  batchid: FormControl<AutojobsinvoicelinebatchesFormRawValue['batchid']>;
  batchcode: FormControl<AutojobsinvoicelinebatchesFormRawValue['batchcode']>;
  txdate: FormControl<AutojobsinvoicelinebatchesFormRawValue['txdate']>;
  manufacturedate: FormControl<AutojobsinvoicelinebatchesFormRawValue['manufacturedate']>;
  expireddate: FormControl<AutojobsinvoicelinebatchesFormRawValue['expireddate']>;
  qty: FormControl<AutojobsinvoicelinebatchesFormRawValue['qty']>;
  cost: FormControl<AutojobsinvoicelinebatchesFormRawValue['cost']>;
  price: FormControl<AutojobsinvoicelinebatchesFormRawValue['price']>;
  notes: FormControl<AutojobsinvoicelinebatchesFormRawValue['notes']>;
  lmu: FormControl<AutojobsinvoicelinebatchesFormRawValue['lmu']>;
  lmd: FormControl<AutojobsinvoicelinebatchesFormRawValue['lmd']>;
  nbt: FormControl<AutojobsinvoicelinebatchesFormRawValue['nbt']>;
  vat: FormControl<AutojobsinvoicelinebatchesFormRawValue['vat']>;
  discount: FormControl<AutojobsinvoicelinebatchesFormRawValue['discount']>;
  total: FormControl<AutojobsinvoicelinebatchesFormRawValue['total']>;
  issued: FormControl<AutojobsinvoicelinebatchesFormRawValue['issued']>;
  issuedby: FormControl<AutojobsinvoicelinebatchesFormRawValue['issuedby']>;
  issueddatetime: FormControl<AutojobsinvoicelinebatchesFormRawValue['issueddatetime']>;
  addedbyid: FormControl<AutojobsinvoicelinebatchesFormRawValue['addedbyid']>;
  canceloptid: FormControl<AutojobsinvoicelinebatchesFormRawValue['canceloptid']>;
  cancelopt: FormControl<AutojobsinvoicelinebatchesFormRawValue['cancelopt']>;
  cancelby: FormControl<AutojobsinvoicelinebatchesFormRawValue['cancelby']>;
};

export type AutojobsinvoicelinebatchesFormGroup = FormGroup<AutojobsinvoicelinebatchesFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AutojobsinvoicelinebatchesFormService {
  createAutojobsinvoicelinebatchesFormGroup(
    autojobsinvoicelinebatches: AutojobsinvoicelinebatchesFormGroupInput = { id: null },
  ): AutojobsinvoicelinebatchesFormGroup {
    const autojobsinvoicelinebatchesRawValue = this.convertAutojobsinvoicelinebatchesToAutojobsinvoicelinebatchesRawValue({
      ...this.getFormDefaults(),
      ...autojobsinvoicelinebatches,
    });
    return new FormGroup<AutojobsinvoicelinebatchesFormGroupContent>({
      id: new FormControl(
        { value: autojobsinvoicelinebatchesRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      lineid: new FormControl(autojobsinvoicelinebatchesRawValue.lineid),
      batchlineid: new FormControl(autojobsinvoicelinebatchesRawValue.batchlineid),
      itemid: new FormControl(autojobsinvoicelinebatchesRawValue.itemid),
      code: new FormControl(autojobsinvoicelinebatchesRawValue.code),
      batchid: new FormControl(autojobsinvoicelinebatchesRawValue.batchid),
      batchcode: new FormControl(autojobsinvoicelinebatchesRawValue.batchcode),
      txdate: new FormControl(autojobsinvoicelinebatchesRawValue.txdate),
      manufacturedate: new FormControl(autojobsinvoicelinebatchesRawValue.manufacturedate),
      expireddate: new FormControl(autojobsinvoicelinebatchesRawValue.expireddate),
      qty: new FormControl(autojobsinvoicelinebatchesRawValue.qty),
      cost: new FormControl(autojobsinvoicelinebatchesRawValue.cost),
      price: new FormControl(autojobsinvoicelinebatchesRawValue.price),
      notes: new FormControl(autojobsinvoicelinebatchesRawValue.notes),
      lmu: new FormControl(autojobsinvoicelinebatchesRawValue.lmu),
      lmd: new FormControl(autojobsinvoicelinebatchesRawValue.lmd),
      nbt: new FormControl(autojobsinvoicelinebatchesRawValue.nbt),
      vat: new FormControl(autojobsinvoicelinebatchesRawValue.vat),
      discount: new FormControl(autojobsinvoicelinebatchesRawValue.discount),
      total: new FormControl(autojobsinvoicelinebatchesRawValue.total),
      issued: new FormControl(autojobsinvoicelinebatchesRawValue.issued),
      issuedby: new FormControl(autojobsinvoicelinebatchesRawValue.issuedby),
      issueddatetime: new FormControl(autojobsinvoicelinebatchesRawValue.issueddatetime),
      addedbyid: new FormControl(autojobsinvoicelinebatchesRawValue.addedbyid),
      canceloptid: new FormControl(autojobsinvoicelinebatchesRawValue.canceloptid),
      cancelopt: new FormControl(autojobsinvoicelinebatchesRawValue.cancelopt),
      cancelby: new FormControl(autojobsinvoicelinebatchesRawValue.cancelby),
    });
  }

  getAutojobsinvoicelinebatches(form: AutojobsinvoicelinebatchesFormGroup): IAutojobsinvoicelinebatches | NewAutojobsinvoicelinebatches {
    return this.convertAutojobsinvoicelinebatchesRawValueToAutojobsinvoicelinebatches(
      form.getRawValue() as AutojobsinvoicelinebatchesFormRawValue | NewAutojobsinvoicelinebatchesFormRawValue,
    );
  }

  resetForm(form: AutojobsinvoicelinebatchesFormGroup, autojobsinvoicelinebatches: AutojobsinvoicelinebatchesFormGroupInput): void {
    const autojobsinvoicelinebatchesRawValue = this.convertAutojobsinvoicelinebatchesToAutojobsinvoicelinebatchesRawValue({
      ...this.getFormDefaults(),
      ...autojobsinvoicelinebatches,
    });
    form.reset(
      {
        ...autojobsinvoicelinebatchesRawValue,
        id: { value: autojobsinvoicelinebatchesRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): AutojobsinvoicelinebatchesFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      txdate: currentTime,
      manufacturedate: currentTime,
      expireddate: currentTime,
      lmd: currentTime,
      nbt: false,
      vat: false,
      issued: false,
      issueddatetime: currentTime,
    };
  }

  private convertAutojobsinvoicelinebatchesRawValueToAutojobsinvoicelinebatches(
    rawAutojobsinvoicelinebatches: AutojobsinvoicelinebatchesFormRawValue | NewAutojobsinvoicelinebatchesFormRawValue,
  ): IAutojobsinvoicelinebatches | NewAutojobsinvoicelinebatches {
    return {
      ...rawAutojobsinvoicelinebatches,
      txdate: dayjs(rawAutojobsinvoicelinebatches.txdate, DATE_TIME_FORMAT),
      manufacturedate: dayjs(rawAutojobsinvoicelinebatches.manufacturedate, DATE_TIME_FORMAT),
      expireddate: dayjs(rawAutojobsinvoicelinebatches.expireddate, DATE_TIME_FORMAT),
      lmd: dayjs(rawAutojobsinvoicelinebatches.lmd, DATE_TIME_FORMAT),
      issueddatetime: dayjs(rawAutojobsinvoicelinebatches.issueddatetime, DATE_TIME_FORMAT),
    };
  }

  private convertAutojobsinvoicelinebatchesToAutojobsinvoicelinebatchesRawValue(
    autojobsinvoicelinebatches:
      | IAutojobsinvoicelinebatches
      | (Partial<NewAutojobsinvoicelinebatches> & AutojobsinvoicelinebatchesFormDefaults),
  ): AutojobsinvoicelinebatchesFormRawValue | PartialWithRequiredKeyOf<NewAutojobsinvoicelinebatchesFormRawValue> {
    return {
      ...autojobsinvoicelinebatches,
      txdate: autojobsinvoicelinebatches.txdate ? autojobsinvoicelinebatches.txdate.format(DATE_TIME_FORMAT) : undefined,
      manufacturedate: autojobsinvoicelinebatches.manufacturedate
        ? autojobsinvoicelinebatches.manufacturedate.format(DATE_TIME_FORMAT)
        : undefined,
      expireddate: autojobsinvoicelinebatches.expireddate ? autojobsinvoicelinebatches.expireddate.format(DATE_TIME_FORMAT) : undefined,
      lmd: autojobsinvoicelinebatches.lmd ? autojobsinvoicelinebatches.lmd.format(DATE_TIME_FORMAT) : undefined,
      issueddatetime: autojobsinvoicelinebatches.issueddatetime
        ? autojobsinvoicelinebatches.issueddatetime.format(DATE_TIME_FORMAT)
        : undefined,
    };
  }
}
