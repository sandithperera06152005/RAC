import { ISalesInvoiceServiceChargeLine, NewSalesInvoiceServiceChargeLine } from './sales-invoice-service-charge-line.model';

export const sampleWithRequiredData: ISalesInvoiceServiceChargeLine = {
  id: 26685,
};

export const sampleWithPartialData: ISalesInvoiceServiceChargeLine = {
  id: 21970,
  invoiceId: 28350,
  serviceName: 'uselessly terribly',
  serviceDescription: 'grandiose',
  servicePrice: 27157.15,
};

export const sampleWithFullData: ISalesInvoiceServiceChargeLine = {
  id: 24384,
  invoiceId: 2422,
  lineId: 1884,
  optionId: 19446,
  serviceName: 'huddle',
  serviceDescription: 'reflate meh drat',
  value: 31507.19,
  isCustomerService: false,
  discount: 21300.89,
  servicePrice: 13211.7,
};

export const sampleWithNewData: NewSalesInvoiceServiceChargeLine = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
