import dayjs from 'dayjs/esm';

export interface IAutojobsinvoicelines {
  id: number;
  invocieid?: number | null;
  lineid?: number | null;
  itemid?: number | null;
  itemcode?: string | null;
  itemname?: string | null;
  description?: string | null;
  unitofmeasurement?: string | null;
  quantity?: number | null;
  itemcost?: number | null;
  itemprice?: number | null;
  discount?: number | null;
  tax?: number | null;
  sellingprice?: number | null;
  linetotal?: number | null;
  lmu?: number | null;
  lmd?: dayjs.Dayjs | null;
  nbt?: boolean | null;
  vat?: boolean | null;
}

export type NewAutojobsinvoicelines = Omit<IAutojobsinvoicelines, 'id'> & { id: null };
