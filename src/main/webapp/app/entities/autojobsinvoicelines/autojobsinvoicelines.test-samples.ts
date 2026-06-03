import dayjs from 'dayjs/esm';

import { IAutojobsinvoicelines, NewAutojobsinvoicelines } from './autojobsinvoicelines.model';

export const sampleWithRequiredData: IAutojobsinvoicelines = {
  id: 25829,
};

export const sampleWithPartialData: IAutojobsinvoicelines = {
  id: 5775,
  invocieid: 1874,
  lineid: 8074,
  itemid: 16837,
  itemname: 'whose sans',
  unitofmeasurement: 'gee boohoo',
  quantity: 26980.87,
  nbt: false,
};

export const sampleWithFullData: IAutojobsinvoicelines = {
  id: 11674,
  invocieid: 14623,
  lineid: 13862,
  itemid: 6973,
  itemcode: 'wilted uneven how',
  itemname: 'or out',
  description: 'hm zowie',
  unitofmeasurement: 'which',
  quantity: 15532.07,
  itemcost: 8165.92,
  itemprice: 20600.66,
  discount: 26596.38,
  tax: 3178.35,
  sellingprice: 4012.4,
  linetotal: 30940.9,
  lmu: 4054,
  lmd: dayjs('2025-02-11T16:46'),
  nbt: true,
  vat: false,
};

export const sampleWithNewData: NewAutojobsinvoicelines = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
