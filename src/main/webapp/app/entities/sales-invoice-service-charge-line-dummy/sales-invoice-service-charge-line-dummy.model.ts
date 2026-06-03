export interface ISalesInvoiceServiceChargeLineDummy {
  id: number;
  invoiceId?: number | null;
  lineId?: number | null;
  optionId?: number | null;
  serviceName?: string | null;
  serviceDescription?: string | null;
  value?: number | null;
  isCustomerService?: boolean | null;
}

export type NewSalesInvoiceServiceChargeLineDummy = Omit<ISalesInvoiceServiceChargeLineDummy, 'id'> & { id: null };
