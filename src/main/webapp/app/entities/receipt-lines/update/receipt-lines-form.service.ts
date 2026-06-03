import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IReceiptLines, NewReceiptLines } from '../receipt-lines.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IReceiptLines for edit and NewReceiptLinesFormGroupInput for create.
 */
type ReceiptLinesFormGroupInput = IReceiptLines | PartialWithRequiredKeyOf<NewReceiptLines>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IReceiptLines | NewReceiptLines> = Omit<T, 'lmd'> & {
  lmd?: string | null;
};

type ReceiptLinesFormRawValue = FormValueOf<IReceiptLines>;

type NewReceiptLinesFormRawValue = FormValueOf<NewReceiptLines>;

type ReceiptLinesFormDefaults = Pick<NewReceiptLines, 'id' | 'lmd'>;

type ReceiptLinesFormGroupContent = {
  id: FormControl<ReceiptLinesFormRawValue['id'] | NewReceiptLines['id']>;
  lineid: FormControl<ReceiptLinesFormRawValue['lineid']>;
  invoicecode: FormControl<ReceiptLinesFormRawValue['invoicecode']>;
  invoicetype: FormControl<ReceiptLinesFormRawValue['invoicetype']>;
  originalamount: FormControl<ReceiptLinesFormRawValue['originalamount']>;
  amountowing: FormControl<ReceiptLinesFormRawValue['amountowing']>;
  discountavailable: FormControl<ReceiptLinesFormRawValue['discountavailable']>;
  discounttaken: FormControl<ReceiptLinesFormRawValue['discounttaken']>;
  amountreceived: FormControl<ReceiptLinesFormRawValue['amountreceived']>;
  lmu: FormControl<ReceiptLinesFormRawValue['lmu']>;
  lmd: FormControl<ReceiptLinesFormRawValue['lmd']>;
  accountid: FormControl<ReceiptLinesFormRawValue['accountid']>;
};

export type ReceiptLinesFormGroup = FormGroup<ReceiptLinesFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ReceiptLinesFormService {
  createReceiptLinesFormGroup(receiptLines: ReceiptLinesFormGroupInput = { id: null }): ReceiptLinesFormGroup {
    const receiptLinesRawValue = this.convertReceiptLinesToReceiptLinesRawValue({
      ...this.getFormDefaults(),
      ...receiptLines,
    });
    return new FormGroup<ReceiptLinesFormGroupContent>({
      id: new FormControl(
        { value: receiptLinesRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      lineid: new FormControl(receiptLinesRawValue.lineid),
      invoicecode: new FormControl(receiptLinesRawValue.invoicecode),
      invoicetype: new FormControl(receiptLinesRawValue.invoicetype),
      originalamount: new FormControl(receiptLinesRawValue.originalamount),
      amountowing: new FormControl(receiptLinesRawValue.amountowing),
      discountavailable: new FormControl(receiptLinesRawValue.discountavailable),
      discounttaken: new FormControl(receiptLinesRawValue.discounttaken),
      amountreceived: new FormControl(receiptLinesRawValue.amountreceived),
      lmu: new FormControl(receiptLinesRawValue.lmu),
      lmd: new FormControl(receiptLinesRawValue.lmd),
      accountid: new FormControl(receiptLinesRawValue.accountid),
    });
  }

  getReceiptLines(form: ReceiptLinesFormGroup): IReceiptLines | NewReceiptLines {
    return this.convertReceiptLinesRawValueToReceiptLines(form.getRawValue() as ReceiptLinesFormRawValue | NewReceiptLinesFormRawValue);
  }

  resetForm(form: ReceiptLinesFormGroup, receiptLines: ReceiptLinesFormGroupInput): void {
    const receiptLinesRawValue = this.convertReceiptLinesToReceiptLinesRawValue({ ...this.getFormDefaults(), ...receiptLines });
    form.reset(
      {
        ...receiptLinesRawValue,
        id: { value: receiptLinesRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ReceiptLinesFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      lmd: currentTime,
    };
  }

  private convertReceiptLinesRawValueToReceiptLines(
    rawReceiptLines: ReceiptLinesFormRawValue | NewReceiptLinesFormRawValue,
  ): IReceiptLines | NewReceiptLines {
    return {
      ...rawReceiptLines,
      lmd: dayjs(rawReceiptLines.lmd, DATE_TIME_FORMAT),
    };
  }

  private convertReceiptLinesToReceiptLinesRawValue(
    receiptLines: IReceiptLines | (Partial<NewReceiptLines> & ReceiptLinesFormDefaults),
  ): ReceiptLinesFormRawValue | PartialWithRequiredKeyOf<NewReceiptLinesFormRawValue> {
    return {
      ...receiptLines,
      lmd: receiptLines.lmd ? receiptLines.lmd.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
