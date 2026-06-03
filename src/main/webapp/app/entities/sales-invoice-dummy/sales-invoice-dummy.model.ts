import dayjs from 'dayjs/esm';

export interface ISalesInvoiceDummy {
  id: number;
  originalInvoiceId?: number | null;
  code?: string | null;
  invoiceDate?: dayjs.Dayjs | null;
  createdDate?: dayjs.Dayjs | null;
  quoteID?: number | null;
  orderID?: number | null;
  deliveryDate?: dayjs.Dayjs | null;
  salesRepID?: number | null;
  salesRepName?: string | null;
  deliverFrom?: string | null;
  customerID?: number | null;
  customerName?: string | null;
  customerAddress?: string | null;
  deliveryAddress?: string | null;
  subTotal?: number | null;
  totalTax?: number | null;
  totalDiscount?: number | null;
  netTotal?: number | null;
  message?: string | null;
  lmu?: number | null;
  lmd?: dayjs.Dayjs | null;
  paidAmount?: number | null;
  amountOwing?: number | null;
  isActive?: boolean | null;
  locationID?: number | null;
  locationCode?: string | null;
  referenceCode?: string | null;
  createdById?: number | null;
  createdByName?: string | null;
  autoCareCharges?: number | null;
  autoCareJobId?: number | null;
  vehicleNo?: string | null;
  nbtAmount?: number | null;
  vatAmount?: number | null;
  dummyCommission?: number | null;
  commissionPaidDate?: dayjs.Dayjs | null;
  paidCommission?: number | null;
  paidBy?: number | null;
  originalInvoiceCode?: string | null;
}

export type NewSalesInvoiceDummy = Omit<ISalesInvoiceDummy, 'id'> & { id: null };
