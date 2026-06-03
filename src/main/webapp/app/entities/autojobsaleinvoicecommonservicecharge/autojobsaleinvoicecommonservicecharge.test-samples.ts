import {
  IAutojobsaleinvoicecommonservicecharge,
  NewAutojobsaleinvoicecommonservicecharge,
} from './autojobsaleinvoicecommonservicecharge.model';

export const sampleWithRequiredData: IAutojobsaleinvoicecommonservicecharge = {
  id: 23371,
};

export const sampleWithPartialData: IAutojobsaleinvoicecommonservicecharge = {
  id: 31979,
  lineid: 8789,
  optionid: 11274,
  mainid: 10695,
  name: 'modulo pack',
  description: 'unaware',
  value: 8733.6,
  discount: 9339.85,
  serviceprice: 3469.15,
};

export const sampleWithFullData: IAutojobsaleinvoicecommonservicecharge = {
  id: 26598,
  invoiceid: 2995,
  lineid: 6789,
  optionid: 17430,
  mainid: 7507,
  code: 'ferociously',
  name: 'enthusiastically unless',
  description: 'towards',
  value: 13276.41,
  addedbyid: 20064,
  discount: 26848.93,
  serviceprice: 32233.35,
};

export const sampleWithNewData: NewAutojobsaleinvoicecommonservicecharge = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
