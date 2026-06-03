import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IVoucher, NewVoucher } from '../voucher.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IVoucher for edit and NewVoucherFormGroupInput for create.
 */
type VoucherFormGroupInput = IVoucher | PartialWithRequiredKeyOf<NewVoucher>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IVoucher | NewVoucher> = Omit<T, 'voucherDate' | 'lmd' | 'date'> & {
  voucherDate?: string | null;
  lmd?: string | null;
  date?: string | null;
};

type VoucherFormRawValue = FormValueOf<IVoucher>;

type NewVoucherFormRawValue = FormValueOf<NewVoucher>;

type VoucherFormDefaults = Pick<NewVoucher, 'id' | 'voucherDate' | 'lmd' | 'date' | 'isActive'>;

type VoucherFormGroupContent = {
  id: FormControl<VoucherFormRawValue['id'] | NewVoucher['id']>;
  code: FormControl<VoucherFormRawValue['code']>;
  voucherDate: FormControl<VoucherFormRawValue['voucherDate']>;
  supplierName: FormControl<VoucherFormRawValue['supplierName']>;
  supplierAddress: FormControl<VoucherFormRawValue['supplierAddress']>;
  totalAmount: FormControl<VoucherFormRawValue['totalAmount']>;
  totalAmountInWord: FormControl<VoucherFormRawValue['totalAmountInWord']>;
  comments: FormControl<VoucherFormRawValue['comments']>;
  lmu: FormControl<VoucherFormRawValue['lmu']>;
  lmd: FormControl<VoucherFormRawValue['lmd']>;
  termId: FormControl<VoucherFormRawValue['termId']>;
  term: FormControl<VoucherFormRawValue['term']>;
  date: FormControl<VoucherFormRawValue['date']>;
  amountPaid: FormControl<VoucherFormRawValue['amountPaid']>;
  supplierID: FormControl<VoucherFormRawValue['supplierID']>;
  isActive: FormControl<VoucherFormRawValue['isActive']>;
  createdBy: FormControl<VoucherFormRawValue['createdBy']>;
};

export type VoucherFormGroup = FormGroup<VoucherFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class VoucherFormService {
  createVoucherFormGroup(voucher: VoucherFormGroupInput = { id: null }): VoucherFormGroup {
    const voucherRawValue = this.convertVoucherToVoucherRawValue({
      ...this.getFormDefaults(),
      ...voucher,
    });
    return new FormGroup<VoucherFormGroupContent>({
      id: new FormControl(
        { value: voucherRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      code: new FormControl(voucherRawValue.code),
      voucherDate: new FormControl(voucherRawValue.voucherDate),
      supplierName: new FormControl(voucherRawValue.supplierName),
      supplierAddress: new FormControl(voucherRawValue.supplierAddress),
      totalAmount: new FormControl(voucherRawValue.totalAmount),
      totalAmountInWord: new FormControl(voucherRawValue.totalAmountInWord),
      comments: new FormControl(voucherRawValue.comments),
      lmu: new FormControl(voucherRawValue.lmu),
      lmd: new FormControl(voucherRawValue.lmd),
      termId: new FormControl(voucherRawValue.termId),
      term: new FormControl(voucherRawValue.term),
      date: new FormControl(voucherRawValue.date),
      amountPaid: new FormControl(voucherRawValue.amountPaid),
      supplierID: new FormControl(voucherRawValue.supplierID),
      isActive: new FormControl(voucherRawValue.isActive),
      createdBy: new FormControl(voucherRawValue.createdBy),
    });
  }

  getVoucher(form: VoucherFormGroup): IVoucher | NewVoucher {
    return this.convertVoucherRawValueToVoucher(form.getRawValue() as VoucherFormRawValue | NewVoucherFormRawValue);
  }

  resetForm(form: VoucherFormGroup, voucher: VoucherFormGroupInput): void {
    const voucherRawValue = this.convertVoucherToVoucherRawValue({ ...this.getFormDefaults(), ...voucher });
    form.reset(
      {
        ...voucherRawValue,
        id: { value: voucherRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): VoucherFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      voucherDate: currentTime,
      lmd: currentTime,
      date: currentTime,
      isActive: false,
    };
  }

  private convertVoucherRawValueToVoucher(rawVoucher: VoucherFormRawValue | NewVoucherFormRawValue): IVoucher | NewVoucher {
    return {
      ...rawVoucher,
      voucherDate: dayjs(rawVoucher.voucherDate, DATE_TIME_FORMAT),
      lmd: dayjs(rawVoucher.lmd, DATE_TIME_FORMAT),
      date: dayjs(rawVoucher.date, DATE_TIME_FORMAT),
    };
  }

  private convertVoucherToVoucherRawValue(
    voucher: IVoucher | (Partial<NewVoucher> & VoucherFormDefaults),
  ): VoucherFormRawValue | PartialWithRequiredKeyOf<NewVoucherFormRawValue> {
    return {
      ...voucher,
      voucherDate: voucher.voucherDate ? voucher.voucherDate.format(DATE_TIME_FORMAT) : undefined,
      lmd: voucher.lmd ? voucher.lmd.format(DATE_TIME_FORMAT) : undefined,
      date: voucher.date ? voucher.date.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
