export interface ISaleInvoiceCommonServiceChargeDummy {
  id: number;
  invoiceid?: number | null;
  lineid?: number | null;
  optionid?: number | null;
  mainid?: number | null;
  code?: string | null;
  name?: string | null;
  description?: string | null;
  value?: number | null;
}

export type NewSaleInvoiceCommonServiceChargeDummy = Omit<ISaleInvoiceCommonServiceChargeDummy, 'id'> & { id: null };
