import dayjs from 'dayjs/esm';

export interface ITransactions {
  id: number;
  accountId?: number | null;
  accountCode?: string | null;
  debit?: number | null;
  credit?: number | null;
  date?: dayjs.Dayjs | null;
  refDoc?: string | null;
  refId?: number | null;
  subId?: string | null;
  source?: string | null;
  paymentTermId?: number | null;
  paymentTermName?: string | null;
  lmu?: number | null;
  lmd?: dayjs.Dayjs | null;
}

export type NewTransactions = Omit<ITransactions, 'id'> & { id: null };
