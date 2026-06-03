import dayjs from 'dayjs/esm';

import { ISalesinvoice, NewSalesinvoice } from './salesinvoice.model';

export const sampleWithRequiredData: ISalesinvoice = {
  id: 23700,
};

export const sampleWithPartialData: ISalesinvoice = {
  id: 4747,
  createddate: dayjs('2024-10-03T03:24'),
  delieverydate: dayjs('2024-10-03T06:49'),
  customerid: 5334,
  customername: 'hydrolyse',
  customeraddress: 'kiddingly better heavenly',
  deliveryaddress: 'phew deck or',
  subtotal: 22166.53,
  totaltax: 29889.25,
  nettotal: 13555.19,
  amountowing: 21826.83,
  isactive: false,
  locationid: 5958,
  locationcode: 'twin',
  referencecode: 'service',
  createdbyid: 1502,
  createdbyname: 'scamper approximate circular',
  autocarejobid: 31566,
  currentmeter: 'anti',
  dummybillamount: 21784.6,
  nbtamount: 712.7,
  vatamount: 6988.86,
  invcanceldate: dayjs('2024-10-02T09:05'),
  invcancelby: 30188,
  discountcode: 'aha deduct coin',
};

export const sampleWithFullData: ISalesinvoice = {
  id: 6651,
  code: 'against bah excitedly',
  invoicedate: dayjs('2024-10-02T17:31'),
  createddate: dayjs('2024-10-02T16:15'),
  quoteid: 20931,
  orderid: 21007,
  delieverydate: dayjs('2024-10-03T01:46'),
  salesrepid: 31935,
  salesrepname: 'transplant fathom asset',
  delieverfrom: 'until',
  customerid: 4004,
  customername: 'validity',
  customeraddress: 'why',
  deliveryaddress: 'carouse',
  subtotal: 10932.75,
  totaltax: 31889.75,
  totaldiscount: 5908.99,
  nettotal: 26844.99,
  message: 'questioningly hm',
  lmu: 22192,
  lmd: dayjs('2024-10-03T00:34'),
  paidamount: 9914.45,
  amountowing: 16637.61,
  isactive: false,
  locationid: 13332,
  locationcode: 'indeed thrifty epic',
  referencecode: 'petal lest',
  createdbyid: 14025,
  createdbyname: 'passionate buttery',
  autocarecharges: 23568.33,
  autocarejobid: 12523,
  vehicleno: 'keenly',
  nextmeter: 'stitcher vibrant delirious',
  currentmeter: 'times fort nautical',
  remarks: 'scrape',
  hasdummybill: false,
  dummybillid: 25376,
  dummybillamount: 17780.1,
  dummycommision: 27040.18,
  isserviceinvoice: false,
  nbtamount: 31865.6,
  vatamount: 4603.06,
  autocarecompanyid: 1709,
  iscompanyinvoice: false,
  invcanceldate: dayjs('2024-10-02T18:36'),
  invcancelby: 4329,
  isvatinvoice: true,
  paymenttype: 'league that',
  pendingamount: 10594.48,
  advancepayment: 29397.43,
  discountcode: 'however fax even',
};

export const sampleWithNewData: NewSalesinvoice = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
