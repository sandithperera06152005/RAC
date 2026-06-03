import dayjs from 'dayjs/esm';

import { IAutojobsinvoice, NewAutojobsinvoice } from './autojobsinvoice.model';

export const sampleWithRequiredData: IAutojobsinvoice = {
  id: 25759,
};

export const sampleWithPartialData: IAutojobsinvoice = {
  id: 1236,
  code: 'custody sin absent',
  invoicedate: dayjs('2024-10-03T10:18'),
  delieverydate: dayjs('2024-10-03T14:55'),
  autojobsrepid: 2529,
  autojobsrepname: 'hmph',
  delieverfrom: 'question ouch embed',
  customername: 'oh',
  customeraddress: 'often horst or',
  totaltax: 14044.62,
  totaldiscount: 258.32,
  nettotal: 10884.94,
  message: 'place consequently',
  lmd: dayjs('2024-10-03T21:22'),
  referencecode: 'hmph woot',
  autocarecompanyid: 5176,
};

export const sampleWithFullData: IAutojobsinvoice = {
  id: 29842,
  code: 'profuse',
  invoicedate: dayjs('2024-10-04T06:15'),
  createddate: dayjs('2024-10-03T14:30'),
  jobid: 15896,
  quoteid: 16379,
  orderid: 19878,
  delieverydate: dayjs('2024-10-03T11:54'),
  autojobsrepid: 26024,
  autojobsrepname: 'onto lumbering partial',
  delieverfrom: 'overvalue when',
  customerid: 19809,
  customername: 'release developmental shadowbox',
  customeraddress: 'hence',
  deliveryaddress: 'patiently',
  subtotal: 4432.66,
  totaltax: 22111.57,
  totaldiscount: 22287.99,
  nettotal: 10684.08,
  message: 'than silent',
  lmu: 3492,
  lmd: dayjs('2024-10-04T00:10'),
  paidamount: 2555.9,
  amountowing: 32665.66,
  isactive: false,
  locationid: 27706,
  locationcode: 'potable quizzically',
  referencecode: 'knottily suddenly brochure',
  createdbyid: 17100,
  createdbyname: 'woot wearily conceal',
  autocarecompanyid: 1056,
};

export const sampleWithNewData: NewAutojobsinvoice = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
