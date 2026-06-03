import { IInvoiceServiceCharge, NewInvoiceServiceCharge } from './invoice-service-charge.model';

export const sampleWithRequiredData: IInvoiceServiceCharge = {
  id: 19066,
};

export const sampleWithPartialData: IInvoiceServiceCharge = {
  id: 32059,
  invoiceId: 14807,
  lineId: 12792,
  optionId: 7893,
  serviceDiscription: 'nor below woefully',
  value: 18854.09,
  discount: 27178.88,
  servicePrice: 23963.6,
};

export const sampleWithFullData: IInvoiceServiceCharge = {
  id: 22974,
  invoiceId: 1854,
  lineId: 9996,
  optionId: 25981,
  serviceName: 'even aw pulverize',
  serviceDiscription: 'shady for',
  value: 20824.95,
  addedById: 1319,
  isCustomerSrvice: false,
  discount: 9848.38,
  servicePrice: 4606.85,
};

export const sampleWithNewData: NewInvoiceServiceCharge = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
