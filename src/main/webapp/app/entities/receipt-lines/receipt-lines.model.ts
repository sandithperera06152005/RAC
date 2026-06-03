import dayjs from 'dayjs/esm';

export interface IReceiptLines {
  id: number;
  lineid?: number | null;
  invoicecode?: string | null;
  invoicetype?: string | null;
  originalamount?: number | null;
  amountowing?: number | null;
  discountavailable?: number | null;
  discounttaken?: number | null;
  amountreceived?: number | null;
  lmu?: number | null;
  lmd?: dayjs.Dayjs | null;
  accountid?: number | null;
}

export type NewReceiptLines = Omit<IReceiptLines, 'id'> & { id: null };
