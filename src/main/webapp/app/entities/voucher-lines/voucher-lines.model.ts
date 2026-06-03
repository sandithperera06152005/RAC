import dayjs from 'dayjs/esm';

export interface IVoucherLines {
  id: number;
  lineID?: number | null;
  grnCode?: string | null;
  grnType?: string | null;
  originalAmount?: number | null;
  amountOwing?: number | null;
  discountAvailable?: number | null;
  discountTaken?: number | null;
  amountReceived?: number | null;
  lmu?: number | null;
  lmd?: dayjs.Dayjs | null;
  accountId?: number | null;
}

export type NewVoucherLines = Omit<IVoucherLines, 'id'> & { id: null };
