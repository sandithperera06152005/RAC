import {
  ISalesInvoiceServiceChargeLineDummy,
  NewSalesInvoiceServiceChargeLineDummy,
} from './sales-invoice-service-charge-line-dummy.model';

export const sampleWithRequiredData: ISalesInvoiceServiceChargeLineDummy = {
  id: 28423,
};

export const sampleWithPartialData: ISalesInvoiceServiceChargeLineDummy = {
  id: 19179,
  serviceName: 'frantically furthermore',
};

export const sampleWithFullData: ISalesInvoiceServiceChargeLineDummy = {
  id: 17233,
  invoiceId: 29429,
  lineId: 21012,
  optionId: 11625,
  serviceName: 'compassionate mortally',
  serviceDescription: 'juggernaut likely',
  value: 1909.62,
  isCustomerService: false,
};

export const sampleWithNewData: NewSalesInvoiceServiceChargeLineDummy = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
