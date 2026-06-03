import { IInvoiceServiceCommon, NewInvoiceServiceCommon } from './invoice-service-common.model';

export const sampleWithRequiredData: IInvoiceServiceCommon = {
  id: 22626,
};

export const sampleWithPartialData: IInvoiceServiceCommon = {
  id: 10024,
  optionId: 13331,
  description: 'mid tabletop incomparable',
  value: 24573.36,
};

export const sampleWithFullData: IInvoiceServiceCommon = {
  id: 21305,
  invoiceId: 12136,
  lineId: 11590,
  optionId: 17933,
  mainId: 22979,
  code: 6752.04,
  name: 'which',
  description: 'ordinary',
  value: 8899.56,
  addedById: 29835,
  discount: 6006.77,
  servicePrice: 14600.83,
};

export const sampleWithNewData: NewInvoiceServiceCommon = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
