import dayjs from 'dayjs/esm';

export interface IReceiptpaymentsdetails {
  id: number;
  lineid?: number | null;
  paymentamount?: number | null;
  totalreceiptamount?: number | null;
  checkqueamount?: number | null;
  checkqueno?: string | null;
  checkquedate?: dayjs.Dayjs | null;
  checkqueexpiredate?: dayjs.Dayjs | null;
  bankname?: string | null;
  bankid?: number | null;
  bankbranchname?: string | null;
  bankbranchid?: number | null;
  creditcardno?: string | null;
  creditcardamount?: number | null;
  reference?: string | null;
  otherdetails?: string | null;
  lmu?: string | null;
  lmd?: dayjs.Dayjs | null;
  termid?: number | null;
  termname?: string | null;
  accountno?: string | null;
  accountnumber?: string | null;
  chequereturndate?: dayjs.Dayjs | null;
  isdeposit?: boolean | null;
  depositeddate?: dayjs.Dayjs | null;
  chequestatuschangeddate?: dayjs.Dayjs | null;
  returnchequesttledate?: dayjs.Dayjs | null;
  chequestatusid?: number | null;
  isPdCheque?: boolean | null;
  depositdate?: dayjs.Dayjs | null;
  accountid?: number | null;
  accountcode?: string | null;
  bankdepositbankname?: string | null;
  bankdepositbankid?: number | null;
  bankdepositbankbranchname?: string | null;
  bankdepositbankbranchid?: number | null;
  returnchequefine?: number | null;
  companybankid?: number | null;
  isbankreconciliation?: boolean | null;
}

export type NewReceiptpaymentsdetails = Omit<IReceiptpaymentsdetails, 'id'> & { id: null };
