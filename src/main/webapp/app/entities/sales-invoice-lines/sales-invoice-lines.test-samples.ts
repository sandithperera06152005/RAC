import dayjs from 'dayjs/esm';

import { ISalesInvoiceLines, NewSalesInvoiceLines } from './sales-invoice-lines.model';

export const sampleWithRequiredData: ISalesInvoiceLines = {
  id: 29932,
};

export const sampleWithPartialData: ISalesInvoiceLines = {
  id: 32386,
  invoiceid: 30794,
  lineid: 20278,
  itemcode: 'fatal',
  unitofmeasurement: 'upwardly potentially',
  quantity: 19749.5,
  itemprice: 14132.88,
  discount: 4418.57,
  tax: 15167.55,
  sellingprice: 5708.01,
  lmd: dayjs('2025-01-29T01:05'),
};

export const sampleWithFullData: ISalesInvoiceLines = {
  id: 26429,
  invoiceid: 17995,
  lineid: 19909,
  itemid: 11439,
  itemcode: 'icy simple too',
  itemname: 'footrest eek',
  description: 'jovially hence unfreeze',
  unitofmeasurement: 'tabulate earth given',
  quantity: 30804.39,
  itemcost: 14221.45,
  itemprice: 11536.22,
  discount: 28483.25,
  tax: 7041.35,
  sellingprice: 10091.32,
  linetotal: 5954.42,
  lmu: 12278,
  lmd: dayjs('2025-01-28T14:56'),
  nbt: false,
  vat: false,
};

export const sampleWithNewData: NewSalesInvoiceLines = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
