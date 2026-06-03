import {
  ISaleInvoiceCommonServiceChargeDummy,
  NewSaleInvoiceCommonServiceChargeDummy,
} from './sale-invoice-common-service-charge-dummy.model';

export const sampleWithRequiredData: ISaleInvoiceCommonServiceChargeDummy = {
  id: 4012,
};

export const sampleWithPartialData: ISaleInvoiceCommonServiceChargeDummy = {
  id: 1707,
  lineid: 21659,
  optionid: 22301,
  mainid: 15359,
  description: 'hm but',
  value: 8495.47,
};

export const sampleWithFullData: ISaleInvoiceCommonServiceChargeDummy = {
  id: 11781,
  invoiceid: 3894,
  lineid: 25913,
  optionid: 28263,
  mainid: 22511,
  code: 'sadly earth',
  name: 'geez hm whenever',
  description: 'yippee yowza',
  value: 11140.98,
};

export const sampleWithNewData: NewSaleInvoiceCommonServiceChargeDummy = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
