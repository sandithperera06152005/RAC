export interface ISaleInvoiceCommonServiceCharge {
  id: number;
  invoiceId?: number | null;
  lineId?: number | null;
  optionId?: number | null;
  mainId?: number | null;
  code?: string | null;
  name?: string | null;
  description?: string | null;
  value?: number | null;
  discount?: number | null;
  servicePrice?: number | null;
}

export type NewSaleInvoiceCommonServiceCharge = Omit<ISaleInvoiceCommonServiceCharge, 'id'> & { id: null };
