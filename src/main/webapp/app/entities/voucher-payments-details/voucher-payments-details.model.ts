import dayjs from 'dayjs/esm';

export interface IVoucherPaymentsDetails {
  id: number;
  lineID?: number | null;
  paymentAmount?: number | null;
  totalVoucherAmount?: number | null;
  checkqueAmount?: number | null;
  checkqueNo?: string | null;
  checkqueDate?: dayjs.Dayjs | null;
  checkqueExpireDate?: dayjs.Dayjs | null;
  bankName?: string | null;
  bankID?: number | null;
  creditCardNo?: string | null;
  creditCardAmount?: number | null;
  reference?: string | null;
  otherDetails?: string | null;
  lmu?: string | null;
  lmd?: dayjs.Dayjs | null;
  termID?: number | null;
  termName?: string | null;
  accountNo?: number | null;
  accountNumber?: number | null;
  accountId?: number | null;
  accountCode?: string | null;
  chequeStatusId?: number | null;
  isDeposit?: boolean | null;
  depositedDate?: dayjs.Dayjs | null;
  companyBankId?: number | null;
  isBankReconciliation?: boolean | null;
}

export type NewVoucherPaymentsDetails = Omit<IVoucherPaymentsDetails, 'id'> & { id: null };
