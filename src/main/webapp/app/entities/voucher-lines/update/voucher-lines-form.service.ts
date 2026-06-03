import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators, FormArray } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IVoucherLines, NewVoucherLines } from '../voucher-lines.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IVoucherLines for edit and NewVoucherLinesFormGroupInput for create.
 */
type VoucherLinesFormGroupInput = IVoucherLines | PartialWithRequiredKeyOf<NewVoucherLines>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IVoucherLines | NewVoucherLines> = Omit<T, 'lmd'> & {
  lmd?: string | null;
};

type VoucherLinesFormRawValue = FormValueOf<IVoucherLines>;

type NewVoucherLinesFormRawValue = FormValueOf<NewVoucherLines>;

type VoucherLinesFormDefaults = Pick<NewVoucherLines, 'id' | 'lmd'>;

type VoucherLinesFormGroupContent = {
  id: FormControl<VoucherLinesFormRawValue['id'] | NewVoucherLines['id']>;
  lineID: FormControl<VoucherLinesFormRawValue['lineID']>;
  grnCode: FormControl<VoucherLinesFormRawValue['grnCode']>;
  grnType: FormControl<VoucherLinesFormRawValue['grnType']>;
  originalAmount: FormControl<VoucherLinesFormRawValue['originalAmount']>;
  amountOwing: FormControl<VoucherLinesFormRawValue['amountOwing']>;
  discountAvailable: FormControl<VoucherLinesFormRawValue['discountAvailable']>;
  discountTaken: FormControl<VoucherLinesFormRawValue['discountTaken']>;
  amountReceived: FormControl<VoucherLinesFormRawValue['amountReceived']>;
  lmu: FormControl<VoucherLinesFormRawValue['lmu']>;
  lmd: FormControl<VoucherLinesFormRawValue['lmd']>;
  accountId: FormControl<VoucherLinesFormRawValue['accountId']>;
};

export type VoucherLinesFormGroup = FormGroup<VoucherLinesFormGroupContent>;
@Injectable({ providedIn: 'root' })
export class VoucherLinesFormService {
  createVoucherLinesFormArray(voucherLines: (IVoucherLines | Partial<NewVoucherLines>)[] = []): FormArray {
    const formArray = new FormArray(voucherLines.map(voucherLine => this.createVoucherLinesFormGroup({ id: null, ...voucherLine })));
    return formArray;
  }

  createVoucherLinesFormGroup(voucherLine: VoucherLinesFormGroupInput = { id: null }): VoucherLinesFormGroup {
    const voucherLineRawValue = this.convertVoucherLinesToVoucherLinesRawValue({
      ...this.getFormDefaults(),
      ...voucherLine,
    });

    return new FormGroup<VoucherLinesFormGroupContent>({
      id: new FormControl(
        { value: voucherLineRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      lineID: new FormControl(voucherLineRawValue.lineID),
      grnCode: new FormControl(voucherLineRawValue.grnCode),
      grnType: new FormControl(voucherLineRawValue.grnType),
      originalAmount: new FormControl(voucherLineRawValue.originalAmount),
      amountOwing: new FormControl(voucherLineRawValue.amountOwing),
      discountAvailable: new FormControl(voucherLineRawValue.discountAvailable),
      discountTaken: new FormControl(voucherLineRawValue.discountTaken),
      amountReceived: new FormControl(voucherLineRawValue.amountReceived),
      lmu: new FormControl(voucherLineRawValue.lmu),
      lmd: new FormControl(voucherLineRawValue.lmd),
      accountId: new FormControl(voucherLineRawValue.accountId),
    });
  }

  getVoucherLines(form: FormArray): (IVoucherLines | NewVoucherLines)[] {
    return form.controls.map(control =>
      this.convertVoucherLinesRawValueToVoucherLines(control.getRawValue() as VoucherLinesFormRawValue | NewVoucherLinesFormRawValue),
    );
  }

  resetForm(form: FormArray, voucherLinesArray: VoucherLinesFormGroupInput[]): void {
    form.clear();
    voucherLinesArray.forEach(voucherLine => {
      form.push(this.createVoucherLinesFormGroup(voucherLine));
    });
  }

  private getFormDefaults(): VoucherLinesFormDefaults {
    return {
      id: null,
      lmd: dayjs(),
    };
  }

  private convertVoucherLinesRawValueToVoucherLines(
    rawVoucherLines: VoucherLinesFormRawValue | NewVoucherLinesFormRawValue,
  ): IVoucherLines | NewVoucherLines {
    return {
      ...rawVoucherLines,
      lmd: rawVoucherLines.lmd ? dayjs(rawVoucherLines.lmd, DATE_TIME_FORMAT) : undefined,
    };
  }

  private convertVoucherLinesToVoucherLinesRawValue(
    voucherLine: IVoucherLines | (Partial<NewVoucherLines> & VoucherLinesFormDefaults),
  ): VoucherLinesFormRawValue | PartialWithRequiredKeyOf<NewVoucherLinesFormRawValue> {
    return {
      ...voucherLine,
      lmd: voucherLine.lmd ? voucherLine.lmd.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
