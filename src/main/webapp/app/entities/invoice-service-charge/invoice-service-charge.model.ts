export interface IInvoiceServiceCharge {
  id: number;
  invoiceId?: number | null;
  lineId?: number | null;
  optionId?: number | null;
  serviceName?: string | null;
  serviceDiscription?: string | null;
  value?: number | null;
  addedById?: number | null;
  isCustomerSrvice?: boolean | null;
  discount?: number | null;
  servicePrice?: number | null;
}

export type NewInvoiceServiceCharge = Omit<IInvoiceServiceCharge, 'id'> & { id: null };
