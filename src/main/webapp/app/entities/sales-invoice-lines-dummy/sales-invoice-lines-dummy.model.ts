import dayjs from 'dayjs/esm';

export interface ISalesInvoiceLinesDummy {
  id: number;
  invoiceId?: number | null;
  lineId?: number | null;
  itemId?: number | null;
  itemCode?: string | null;
  itemName?: string | null;
  description?: string | null;
  unitOfMeasurement?: string | null;
  quantity?: number | null;
  itemCost?: number | null;
  itemPrice?: number | null;
  discount?: number | null;
  tax?: number | null;
  sellingPrice?: number | null;
  lineTotal?: number | null;
  lmu?: number | null;
  lmd?: dayjs.Dayjs | null;
  nbt?: boolean | null;
  vat?: boolean | null;
}

export type NewSalesInvoiceLinesDummy = Omit<ISalesInvoiceLinesDummy, 'id'> & { id: null };
