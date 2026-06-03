export interface IInvoiceServiceCommon {
  id: number;
  invoiceId?: number | null;
  lineId?: number | null;
  optionId?: number | null;
  mainId?: number | null;
  code?: number | null;
  name?: string | null;
  description?: string | null;
  value?: number | null;
  addedById?: number | null;
  discount?: number | null;
  servicePrice?: number | null;
}

export type NewInvoiceServiceCommon = Omit<IInvoiceServiceCommon, 'id'> & { id: null };
