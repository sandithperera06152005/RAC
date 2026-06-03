import dayjs from 'dayjs/esm';

export interface IVoucher {
  id: number;
  code?: string | null;
  voucherDate?: dayjs.Dayjs | null;
  supplierName?: string | null;
  supplierAddress?: string | null;
  totalAmount?: number | null;
  totalAmountInWord?: string | null;
  comments?: string | null;
  lmu?: number | null;
  lmd?: dayjs.Dayjs | null;
  termId?: number | null;
  term?: string | null;
  date?: dayjs.Dayjs | null;
  amountPaid?: number | null;
  supplierID?: number | null;
  isActive?: boolean | null;
  createdBy?: number | null;
}

export type NewVoucher = Omit<IVoucher, 'id'> & { id: null };
