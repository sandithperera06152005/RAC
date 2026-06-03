import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IVoucherPaymentsDetails, NewVoucherPaymentsDetails } from '../voucher-payments-details.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IVoucherPaymentsDetails for edit and NewVoucherPaymentsDetailsFormGroupInput for create.
 */
type VoucherPaymentsDetailsFormGroupInput = IVoucherPaymentsDetails | PartialWithRequiredKeyOf<NewVoucherPaymentsDetails>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IVoucherPaymentsDetails | NewVoucherPaymentsDetails> = Omit<
  T,
  'checkqueDate' | 'checkqueExpireDate' | 'lmd' | 'depositedDate'
> & {
  checkqueDate?: string | null;
  checkqueExpireDate?: string | null;
  lmd?: string | null;
  depositedDate?: string | null;
};

type VoucherPaymentsDetailsFormRawValue = FormValueOf<IVoucherPaymentsDetails>;

type NewVoucherPaymentsDetailsFormRawValue = FormValueOf<NewVoucherPaymentsDetails>;

type VoucherPaymentsDetailsFormDefaults = Pick<
  NewVoucherPaymentsDetails,
  'id' | 'checkqueDate' | 'checkqueExpireDate' | 'lmd' | 'isDeposit' | 'depositedDate' | 'isBankReconciliation'
>;

type VoucherPaymentsDetailsFormGroupContent = {
  id: FormControl<VoucherPaymentsDetailsFormRawValue['id'] | NewVoucherPaymentsDetails['id']>;
  lineID: FormControl<VoucherPaymentsDetailsFormRawValue['lineID']>;
  paymentAmount: FormControl<VoucherPaymentsDetailsFormRawValue['paymentAmount']>;
  totalVoucherAmount: FormControl<VoucherPaymentsDetailsFormRawValue['totalVoucherAmount']>;
  checkqueAmount: FormControl<VoucherPaymentsDetailsFormRawValue['checkqueAmount']>;
  checkqueNo: FormControl<VoucherPaymentsDetailsFormRawValue['checkqueNo']>;
  checkqueDate: FormControl<VoucherPaymentsDetailsFormRawValue['checkqueDate']>;
  checkqueExpireDate: FormControl<VoucherPaymentsDetailsFormRawValue['checkqueExpireDate']>;
  bankName: FormControl<VoucherPaymentsDetailsFormRawValue['bankName']>;
  bankID: FormControl<VoucherPaymentsDetailsFormRawValue['bankID']>;
  creditCardNo: FormControl<VoucherPaymentsDetailsFormRawValue['creditCardNo']>;
  creditCardAmount: FormControl<VoucherPaymentsDetailsFormRawValue['creditCardAmount']>;
  reference: FormControl<VoucherPaymentsDetailsFormRawValue['reference']>;
  otherDetails: FormControl<VoucherPaymentsDetailsFormRawValue['otherDetails']>;
  lmu: FormControl<VoucherPaymentsDetailsFormRawValue['lmu']>;
  lmd: FormControl<VoucherPaymentsDetailsFormRawValue['lmd']>;
  termID: FormControl<VoucherPaymentsDetailsFormRawValue['termID']>;
  termName: FormControl<VoucherPaymentsDetailsFormRawValue['termName']>;
  accountNo: FormControl<VoucherPaymentsDetailsFormRawValue['accountNo']>;
  accountNumber: FormControl<VoucherPaymentsDetailsFormRawValue['accountNumber']>;
  accountId: FormControl<VoucherPaymentsDetailsFormRawValue['accountId']>;
  accountCode: FormControl<VoucherPaymentsDetailsFormRawValue['accountCode']>;
  chequeStatusId: FormControl<VoucherPaymentsDetailsFormRawValue['chequeStatusId']>;
  isDeposit: FormControl<VoucherPaymentsDetailsFormRawValue['isDeposit']>;
  depositedDate: FormControl<VoucherPaymentsDetailsFormRawValue['depositedDate']>;
  companyBankId: FormControl<VoucherPaymentsDetailsFormRawValue['companyBankId']>;
  isBankReconciliation: FormControl<VoucherPaymentsDetailsFormRawValue['isBankReconciliation']>;
};

export type VoucherPaymentsDetailsFormGroup = FormGroup<VoucherPaymentsDetailsFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class VoucherPaymentsDetailsFormService {
  createVoucherPaymentsDetailsFormGroup(
    voucherPaymentsDetails: VoucherPaymentsDetailsFormGroupInput = { id: null },
  ): VoucherPaymentsDetailsFormGroup {
    const voucherPaymentsDetailsRawValue = this.convertVoucherPaymentsDetailsToVoucherPaymentsDetailsRawValue({
      ...this.getFormDefaults(),
      ...voucherPaymentsDetails,
    });
    return new FormGroup<VoucherPaymentsDetailsFormGroupContent>({
      id: new FormControl(
        { value: voucherPaymentsDetailsRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      lineID: new FormControl(voucherPaymentsDetailsRawValue.lineID),
      paymentAmount: new FormControl(voucherPaymentsDetailsRawValue.paymentAmount),
      totalVoucherAmount: new FormControl(voucherPaymentsDetailsRawValue.totalVoucherAmount),
      checkqueAmount: new FormControl(voucherPaymentsDetailsRawValue.checkqueAmount),
      checkqueNo: new FormControl(voucherPaymentsDetailsRawValue.checkqueNo),
      checkqueDate: new FormControl(voucherPaymentsDetailsRawValue.checkqueDate),
      checkqueExpireDate: new FormControl(voucherPaymentsDetailsRawValue.checkqueExpireDate),
      bankName: new FormControl(voucherPaymentsDetailsRawValue.bankName),
      bankID: new FormControl(voucherPaymentsDetailsRawValue.bankID),
      creditCardNo: new FormControl(voucherPaymentsDetailsRawValue.creditCardNo),
      creditCardAmount: new FormControl(voucherPaymentsDetailsRawValue.creditCardAmount),
      reference: new FormControl(voucherPaymentsDetailsRawValue.reference),
      otherDetails: new FormControl(voucherPaymentsDetailsRawValue.otherDetails),
      lmu: new FormControl(voucherPaymentsDetailsRawValue.lmu),
      lmd: new FormControl(voucherPaymentsDetailsRawValue.lmd),
      termID: new FormControl(voucherPaymentsDetailsRawValue.termID),
      termName: new FormControl(voucherPaymentsDetailsRawValue.termName),
      accountNo: new FormControl(voucherPaymentsDetailsRawValue.accountNo),
      accountNumber: new FormControl(voucherPaymentsDetailsRawValue.accountNumber),
      accountId: new FormControl(voucherPaymentsDetailsRawValue.accountId),
      accountCode: new FormControl(voucherPaymentsDetailsRawValue.accountCode),
      chequeStatusId: new FormControl(voucherPaymentsDetailsRawValue.chequeStatusId),
      isDeposit: new FormControl(voucherPaymentsDetailsRawValue.isDeposit),
      depositedDate: new FormControl(voucherPaymentsDetailsRawValue.depositedDate),
      companyBankId: new FormControl(voucherPaymentsDetailsRawValue.companyBankId),
      isBankReconciliation: new FormControl(voucherPaymentsDetailsRawValue.isBankReconciliation),
    });
  }

  getVoucherPaymentsDetails(form: VoucherPaymentsDetailsFormGroup): IVoucherPaymentsDetails | NewVoucherPaymentsDetails {
    return this.convertVoucherPaymentsDetailsRawValueToVoucherPaymentsDetails(
      form.getRawValue() as VoucherPaymentsDetailsFormRawValue | NewVoucherPaymentsDetailsFormRawValue,
    );
  }

  resetForm(form: VoucherPaymentsDetailsFormGroup, voucherPaymentsDetails: VoucherPaymentsDetailsFormGroupInput): void {
    const voucherPaymentsDetailsRawValue = this.convertVoucherPaymentsDetailsToVoucherPaymentsDetailsRawValue({
      ...this.getFormDefaults(),
      ...voucherPaymentsDetails,
    });
    form.reset(
      {
        ...voucherPaymentsDetailsRawValue,
        id: { value: voucherPaymentsDetailsRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): VoucherPaymentsDetailsFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      checkqueDate: currentTime,
      checkqueExpireDate: currentTime,
      lmd: currentTime,
      isDeposit: false,
      depositedDate: currentTime,
      isBankReconciliation: false,
    };
  }

  private convertVoucherPaymentsDetailsRawValueToVoucherPaymentsDetails(
    rawVoucherPaymentsDetails: VoucherPaymentsDetailsFormRawValue | NewVoucherPaymentsDetailsFormRawValue,
  ): IVoucherPaymentsDetails | NewVoucherPaymentsDetails {
    return {
      ...rawVoucherPaymentsDetails,
      checkqueDate: dayjs(rawVoucherPaymentsDetails.checkqueDate, DATE_TIME_FORMAT),
      checkqueExpireDate: dayjs(rawVoucherPaymentsDetails.checkqueExpireDate, DATE_TIME_FORMAT),
      lmd: dayjs(rawVoucherPaymentsDetails.lmd, DATE_TIME_FORMAT),
      depositedDate: dayjs(rawVoucherPaymentsDetails.depositedDate, DATE_TIME_FORMAT),
    };
  }

  private convertVoucherPaymentsDetailsToVoucherPaymentsDetailsRawValue(
    voucherPaymentsDetails: IVoucherPaymentsDetails | (Partial<NewVoucherPaymentsDetails> & VoucherPaymentsDetailsFormDefaults),
  ): VoucherPaymentsDetailsFormRawValue | PartialWithRequiredKeyOf<NewVoucherPaymentsDetailsFormRawValue> {
    return {
      ...voucherPaymentsDetails,
      checkqueDate: voucherPaymentsDetails.checkqueDate ? voucherPaymentsDetails.checkqueDate.format(DATE_TIME_FORMAT) : undefined,
      checkqueExpireDate: voucherPaymentsDetails.checkqueExpireDate
        ? voucherPaymentsDetails.checkqueExpireDate.format(DATE_TIME_FORMAT)
        : undefined,
      lmd: voucherPaymentsDetails.lmd ? voucherPaymentsDetails.lmd.format(DATE_TIME_FORMAT) : undefined,
      depositedDate: voucherPaymentsDetails.depositedDate ? voucherPaymentsDetails.depositedDate.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
