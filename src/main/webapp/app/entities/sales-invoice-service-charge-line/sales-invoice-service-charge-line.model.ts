export interface ISalesInvoiceServiceChargeLine {
  id: number;
  invoiceId?: number | null;
  lineId?: number | null;
  optionId?: number | null;
  serviceName?: string | null;
  serviceDescription?: string | null;
  value?: number | null;
  isCustomerService?: boolean | null;
  discount?: number | null;
  servicePrice?: number | null;
}

export type NewSalesInvoiceServiceChargeLine = Omit<ISalesInvoiceServiceChargeLine, 'id'> & { id: null };
