import dayjs from 'dayjs/esm';

export interface IAutojobsinvoicelinebatches {
  id: number;
  lineid?: number | null;
  batchlineid?: number | null;
  itemid?: number | null;
  code?: string | null;
  batchid?: number | null;
  batchcode?: string | null;
  txdate?: dayjs.Dayjs | null;
  manufacturedate?: dayjs.Dayjs | null;
  expireddate?: dayjs.Dayjs | null;
  qty?: number | null;
  cost?: number | null;
  price?: number | null;
  notes?: string | null;
  lmu?: number | null;
  lmd?: dayjs.Dayjs | null;
  nbt?: boolean | null;
  vat?: boolean | null;
  discount?: number | null;
  total?: number | null;
  issued?: boolean | null;
  issuedby?: number | null;
  issueddatetime?: dayjs.Dayjs | null;
  addedbyid?: number | null;
  canceloptid?: number | null;
  cancelopt?: string | null;
  cancelby?: number | null;
}

export type NewAutojobsinvoicelinebatches = Omit<IAutojobsinvoicelinebatches, 'id'> & { id: null };
