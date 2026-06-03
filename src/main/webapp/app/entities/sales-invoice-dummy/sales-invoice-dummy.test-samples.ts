import dayjs from 'dayjs/esm';

import { ISalesInvoiceDummy, NewSalesInvoiceDummy } from './sales-invoice-dummy.model';

export const sampleWithRequiredData: ISalesInvoiceDummy = {
  id: 17582,
};

export const sampleWithPartialData: ISalesInvoiceDummy = {
  id: 2855,
  invoiceDate: dayjs('2025-01-29T13:14'),
  createdDate: dayjs('2025-01-29T07:18'),
  orderID: 31013,
  deliveryDate: dayjs('2025-01-30T05:06'),
  salesRepID: 12811,
  deliverFrom: 'merry striking',
  customerName: 'mooch next next',
  customerAddress: 'representation tight bah',
  totalTax: 1484.45,
  netTotal: 10117.96,
  message: 'numb camouflage while',
  lmu: 93,
  lmd: dayjs('2025-01-29T09:49'),
  paidAmount: 1233.38,
  amountOwing: 28762.72,
  isActive: false,
  locationCode: 'where powerfully uncork',
  referenceCode: 'plus',
  createdByName: 'pro',
  autoCareJobId: 11695,
  nbtAmount: 13486.61,
  dummyCommission: 29960.84,
  paidCommission: 12979.53,
  paidBy: 24661,
  originalInvoiceCode: 'penalise',
};

export const sampleWithFullData: ISalesInvoiceDummy = {
  id: 4644,
  originalInvoiceId: 16885,
  code: 'croon fortunately whereas',
  invoiceDate: dayjs('2025-01-30T00:43'),
  createdDate: dayjs('2025-01-29T15:18'),
  quoteID: 28808,
  orderID: 184,
  deliveryDate: dayjs('2025-01-29T10:37'),
  salesRepID: 8332,
  salesRepName: 'if',
  deliverFrom: 'whose elver now',
  customerID: 22803,
  customerName: 'vanadyl quietly',
  customerAddress: 'bah if charlatan',
  deliveryAddress: 'season direct pfft',
  subTotal: 20166.34,
  totalTax: 26606.73,
  totalDiscount: 8973.71,
  netTotal: 24965.63,
  message: 'however',
  lmu: 182,
  lmd: dayjs('2025-01-29T10:31'),
  paidAmount: 28874.81,
  amountOwing: 12961.36,
  isActive: true,
  locationID: 25867,
  locationCode: 'lazy which',
  referenceCode: 'golden wrongly clinking',
  createdById: 5162,
  createdByName: 'pepper flat bony',
  autoCareCharges: 3675.35,
  autoCareJobId: 15338,
  vehicleNo: 'phooey consequently',
  nbtAmount: 19786.83,
  vatAmount: 23492.53,
  dummyCommission: 24301.08,
  commissionPaidDate: dayjs('2025-01-29T11:58'),
  paidCommission: 3042.64,
  paidBy: 13925,
  originalInvoiceCode: 'psst bootleg ew',
};

export const sampleWithNewData: NewSalesInvoiceDummy = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
