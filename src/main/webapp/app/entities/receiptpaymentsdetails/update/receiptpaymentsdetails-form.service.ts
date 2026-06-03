import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IReceiptpaymentsdetails, NewReceiptpaymentsdetails } from '../receiptpaymentsdetails.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IReceiptpaymentsdetails for edit and NewReceiptpaymentsdetailsFormGroupInput for create.
 */
type ReceiptpaymentsdetailsFormGroupInput = IReceiptpaymentsdetails | PartialWithRequiredKeyOf<NewReceiptpaymentsdetails>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IReceiptpaymentsdetails | NewReceiptpaymentsdetails> = Omit<
  T,
  | 'checkquedate'
  | 'checkqueexpiredate'
  | 'lmd'
  | 'chequereturndate'
  | 'depositeddate'
  | 'chequestatuschangeddate'
  | 'returnchequesttledate'
  | 'depositdate'
> & {
  checkquedate?: string | null;
  checkqueexpiredate?: string | null;
  lmd?: string | null;
  chequereturndate?: string | null;
  depositeddate?: string | null;
  chequestatuschangeddate?: string | null;
  returnchequesttledate?: string | null;
  depositdate?: string | null;
};

type ReceiptpaymentsdetailsFormRawValue = FormValueOf<IReceiptpaymentsdetails>;

type NewReceiptpaymentsdetailsFormRawValue = FormValueOf<NewReceiptpaymentsdetails>;

type ReceiptpaymentsdetailsFormDefaults = Pick<
  NewReceiptpaymentsdetails,
  | 'id'
  | 'checkquedate'
  | 'checkqueexpiredate'
  | 'lmd'
  | 'chequereturndate'
  | 'isdeposit'
  | 'depositeddate'
  | 'chequestatuschangeddate'
  | 'returnchequesttledate'
  | 'isPdCheque'
  | 'depositdate'
  | 'isbankreconciliation'
>;

type ReceiptpaymentsdetailsFormGroupContent = {
  id: FormControl<ReceiptpaymentsdetailsFormRawValue['id'] | NewReceiptpaymentsdetails['id']>;
  lineid: FormControl<ReceiptpaymentsdetailsFormRawValue['lineid']>;
  paymentamount: FormControl<ReceiptpaymentsdetailsFormRawValue['paymentamount']>;
  totalreceiptamount: FormControl<ReceiptpaymentsdetailsFormRawValue['totalreceiptamount']>;
  checkqueamount: FormControl<ReceiptpaymentsdetailsFormRawValue['checkqueamount']>;
  checkqueno: FormControl<ReceiptpaymentsdetailsFormRawValue['checkqueno']>;
  checkquedate: FormControl<ReceiptpaymentsdetailsFormRawValue['checkquedate']>;
  checkqueexpiredate: FormControl<ReceiptpaymentsdetailsFormRawValue['checkqueexpiredate']>;
  bankname: FormControl<ReceiptpaymentsdetailsFormRawValue['bankname']>;
  bankid: FormControl<ReceiptpaymentsdetailsFormRawValue['bankid']>;
  bankbranchname: FormControl<ReceiptpaymentsdetailsFormRawValue['bankbranchname']>;
  bankbranchid: FormControl<ReceiptpaymentsdetailsFormRawValue['bankbranchid']>;
  creditcardno: FormControl<ReceiptpaymentsdetailsFormRawValue['creditcardno']>;
  creditcardamount: FormControl<ReceiptpaymentsdetailsFormRawValue['creditcardamount']>;
  reference: FormControl<ReceiptpaymentsdetailsFormRawValue['reference']>;
  otherdetails: FormControl<ReceiptpaymentsdetailsFormRawValue['otherdetails']>;
  lmu: FormControl<ReceiptpaymentsdetailsFormRawValue['lmu']>;
  lmd: FormControl<ReceiptpaymentsdetailsFormRawValue['lmd']>;
  termid: FormControl<ReceiptpaymentsdetailsFormRawValue['termid']>;
  termname: FormControl<ReceiptpaymentsdetailsFormRawValue['termname']>;
  accountno: FormControl<ReceiptpaymentsdetailsFormRawValue['accountno']>;
  accountnumber: FormControl<ReceiptpaymentsdetailsFormRawValue['accountnumber']>;
  chequereturndate: FormControl<ReceiptpaymentsdetailsFormRawValue['chequereturndate']>;
  isdeposit: FormControl<ReceiptpaymentsdetailsFormRawValue['isdeposit']>;
  depositeddate: FormControl<ReceiptpaymentsdetailsFormRawValue['depositeddate']>;
  chequestatuschangeddate: FormControl<ReceiptpaymentsdetailsFormRawValue['chequestatuschangeddate']>;
  returnchequesttledate: FormControl<ReceiptpaymentsdetailsFormRawValue['returnchequesttledate']>;
  chequestatusid: FormControl<ReceiptpaymentsdetailsFormRawValue['chequestatusid']>;
  isPdCheque: FormControl<ReceiptpaymentsdetailsFormRawValue['isPdCheque']>;
  depositdate: FormControl<ReceiptpaymentsdetailsFormRawValue['depositdate']>;
  accountid: FormControl<ReceiptpaymentsdetailsFormRawValue['accountid']>;
  accountcode: FormControl<ReceiptpaymentsdetailsFormRawValue['accountcode']>;
  bankdepositbankname: FormControl<ReceiptpaymentsdetailsFormRawValue['bankdepositbankname']>;
  bankdepositbankid: FormControl<ReceiptpaymentsdetailsFormRawValue['bankdepositbankid']>;
  bankdepositbankbranchname: FormControl<ReceiptpaymentsdetailsFormRawValue['bankdepositbankbranchname']>;
  bankdepositbankbranchid: FormControl<ReceiptpaymentsdetailsFormRawValue['bankdepositbankbranchid']>;
  returnchequefine: FormControl<ReceiptpaymentsdetailsFormRawValue['returnchequefine']>;
  companybankid: FormControl<ReceiptpaymentsdetailsFormRawValue['companybankid']>;
  isbankreconciliation: FormControl<ReceiptpaymentsdetailsFormRawValue['isbankreconciliation']>;
};

export type ReceiptpaymentsdetailsFormGroup = FormGroup<ReceiptpaymentsdetailsFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ReceiptpaymentsdetailsFormService {
  createReceiptpaymentsdetailsFormGroup(
    receiptpaymentsdetails: ReceiptpaymentsdetailsFormGroupInput = { id: null },
  ): ReceiptpaymentsdetailsFormGroup {
    const receiptpaymentsdetailsRawValue = this.convertReceiptpaymentsdetailsToReceiptpaymentsdetailsRawValue({
      ...this.getFormDefaults(),
      ...receiptpaymentsdetails,
    });
    return new FormGroup<ReceiptpaymentsdetailsFormGroupContent>({
      id: new FormControl(
        { value: receiptpaymentsdetailsRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      lineid: new FormControl(receiptpaymentsdetailsRawValue.lineid),
      paymentamount: new FormControl(receiptpaymentsdetailsRawValue.paymentamount),
      totalreceiptamount: new FormControl(receiptpaymentsdetailsRawValue.totalreceiptamount),
      checkqueamount: new FormControl(receiptpaymentsdetailsRawValue.checkqueamount),
      checkqueno: new FormControl(receiptpaymentsdetailsRawValue.checkqueno),
      checkquedate: new FormControl(receiptpaymentsdetailsRawValue.checkquedate),
      checkqueexpiredate: new FormControl(receiptpaymentsdetailsRawValue.checkqueexpiredate),
      bankname: new FormControl(receiptpaymentsdetailsRawValue.bankname),
      bankid: new FormControl(receiptpaymentsdetailsRawValue.bankid),
      bankbranchname: new FormControl(receiptpaymentsdetailsRawValue.bankbranchname),
      bankbranchid: new FormControl(receiptpaymentsdetailsRawValue.bankbranchid),
      creditcardno: new FormControl(receiptpaymentsdetailsRawValue.creditcardno),
      creditcardamount: new FormControl(receiptpaymentsdetailsRawValue.creditcardamount),
      reference: new FormControl(receiptpaymentsdetailsRawValue.reference),
      otherdetails: new FormControl(receiptpaymentsdetailsRawValue.otherdetails),
      lmu: new FormControl(receiptpaymentsdetailsRawValue.lmu),
      lmd: new FormControl(receiptpaymentsdetailsRawValue.lmd),
      termid: new FormControl(receiptpaymentsdetailsRawValue.termid),
      termname: new FormControl(receiptpaymentsdetailsRawValue.termname),
      accountno: new FormControl(receiptpaymentsdetailsRawValue.accountno),
      accountnumber: new FormControl(receiptpaymentsdetailsRawValue.accountnumber),
      chequereturndate: new FormControl(receiptpaymentsdetailsRawValue.chequereturndate),
      isdeposit: new FormControl(receiptpaymentsdetailsRawValue.isdeposit),
      depositeddate: new FormControl(receiptpaymentsdetailsRawValue.depositeddate),
      chequestatuschangeddate: new FormControl(receiptpaymentsdetailsRawValue.chequestatuschangeddate),
      returnchequesttledate: new FormControl(receiptpaymentsdetailsRawValue.returnchequesttledate),
      chequestatusid: new FormControl(receiptpaymentsdetailsRawValue.chequestatusid),
      isPdCheque: new FormControl(receiptpaymentsdetailsRawValue.isPdCheque),
      depositdate: new FormControl(receiptpaymentsdetailsRawValue.depositdate),
      accountid: new FormControl(receiptpaymentsdetailsRawValue.accountid),
      accountcode: new FormControl(receiptpaymentsdetailsRawValue.accountcode),
      bankdepositbankname: new FormControl(receiptpaymentsdetailsRawValue.bankdepositbankname),
      bankdepositbankid: new FormControl(receiptpaymentsdetailsRawValue.bankdepositbankid),
      bankdepositbankbranchname: new FormControl(receiptpaymentsdetailsRawValue.bankdepositbankbranchname),
      bankdepositbankbranchid: new FormControl(receiptpaymentsdetailsRawValue.bankdepositbankbranchid),
      returnchequefine: new FormControl(receiptpaymentsdetailsRawValue.returnchequefine),
      companybankid: new FormControl(receiptpaymentsdetailsRawValue.companybankid),
      isbankreconciliation: new FormControl(receiptpaymentsdetailsRawValue.isbankreconciliation),
    });
  }

  getReceiptpaymentsdetails(form: ReceiptpaymentsdetailsFormGroup): IReceiptpaymentsdetails | NewReceiptpaymentsdetails {
    return this.convertReceiptpaymentsdetailsRawValueToReceiptpaymentsdetails(
      form.getRawValue() as ReceiptpaymentsdetailsFormRawValue | NewReceiptpaymentsdetailsFormRawValue,
    );
  }

  resetForm(form: ReceiptpaymentsdetailsFormGroup, receiptpaymentsdetails: ReceiptpaymentsdetailsFormGroupInput): void {
    const receiptpaymentsdetailsRawValue = this.convertReceiptpaymentsdetailsToReceiptpaymentsdetailsRawValue({
      ...this.getFormDefaults(),
      ...receiptpaymentsdetails,
    });
    form.reset(
      {
        ...receiptpaymentsdetailsRawValue,
        id: { value: receiptpaymentsdetailsRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ReceiptpaymentsdetailsFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      checkquedate: currentTime,
      checkqueexpiredate: currentTime,
      lmd: currentTime,
      chequereturndate: currentTime,
      isdeposit: false,
      depositeddate: currentTime,
      chequestatuschangeddate: currentTime,
      returnchequesttledate: currentTime,
      isPdCheque: false,
      depositdate: currentTime,
      isbankreconciliation: false,
    };
  }

  private convertReceiptpaymentsdetailsRawValueToReceiptpaymentsdetails(
    rawReceiptpaymentsdetails: ReceiptpaymentsdetailsFormRawValue | NewReceiptpaymentsdetailsFormRawValue,
  ): IReceiptpaymentsdetails | NewReceiptpaymentsdetails {
    return {
      ...rawReceiptpaymentsdetails,
      checkquedate: dayjs(rawReceiptpaymentsdetails.checkquedate, DATE_TIME_FORMAT),
      checkqueexpiredate: dayjs(rawReceiptpaymentsdetails.checkqueexpiredate, DATE_TIME_FORMAT),
      lmd: dayjs(rawReceiptpaymentsdetails.lmd, DATE_TIME_FORMAT),
      chequereturndate: dayjs(rawReceiptpaymentsdetails.chequereturndate, DATE_TIME_FORMAT),
      depositeddate: dayjs(rawReceiptpaymentsdetails.depositeddate, DATE_TIME_FORMAT),
      chequestatuschangeddate: dayjs(rawReceiptpaymentsdetails.chequestatuschangeddate, DATE_TIME_FORMAT),
      returnchequesttledate: dayjs(rawReceiptpaymentsdetails.returnchequesttledate, DATE_TIME_FORMAT),
      depositdate: dayjs(rawReceiptpaymentsdetails.depositdate, DATE_TIME_FORMAT),
    };
  }

  private convertReceiptpaymentsdetailsToReceiptpaymentsdetailsRawValue(
    receiptpaymentsdetails: IReceiptpaymentsdetails | (Partial<NewReceiptpaymentsdetails> & ReceiptpaymentsdetailsFormDefaults),
  ): ReceiptpaymentsdetailsFormRawValue | PartialWithRequiredKeyOf<NewReceiptpaymentsdetailsFormRawValue> {
    return {
      ...receiptpaymentsdetails,
      checkquedate: receiptpaymentsdetails.checkquedate ? receiptpaymentsdetails.checkquedate.format(DATE_TIME_FORMAT) : undefined,
      checkqueexpiredate: receiptpaymentsdetails.checkqueexpiredate
        ? receiptpaymentsdetails.checkqueexpiredate.format(DATE_TIME_FORMAT)
        : undefined,
      lmd: receiptpaymentsdetails.lmd ? receiptpaymentsdetails.lmd.format(DATE_TIME_FORMAT) : undefined,
      chequereturndate: receiptpaymentsdetails.chequereturndate
        ? receiptpaymentsdetails.chequereturndate.format(DATE_TIME_FORMAT)
        : undefined,
      depositeddate: receiptpaymentsdetails.depositeddate ? receiptpaymentsdetails.depositeddate.format(DATE_TIME_FORMAT) : undefined,
      chequestatuschangeddate: receiptpaymentsdetails.chequestatuschangeddate
        ? receiptpaymentsdetails.chequestatuschangeddate.format(DATE_TIME_FORMAT)
        : undefined,
      returnchequesttledate: receiptpaymentsdetails.returnchequesttledate
        ? receiptpaymentsdetails.returnchequesttledate.format(DATE_TIME_FORMAT)
        : undefined,
      depositdate: receiptpaymentsdetails.depositdate ? receiptpaymentsdetails.depositdate.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
