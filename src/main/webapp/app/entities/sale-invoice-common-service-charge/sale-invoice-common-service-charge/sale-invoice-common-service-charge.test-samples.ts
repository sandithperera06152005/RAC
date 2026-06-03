import { ISaleInvoiceCommonServiceCharge, NewSaleInvoiceCommonServiceCharge } from './sale-invoice-common-service-charge.model';

export const sampleWithRequiredData: ISaleInvoiceCommonServiceCharge = {
  id: 30482,
};

export const sampleWithPartialData: ISaleInvoiceCommonServiceCharge = {
  id: 2203,
  mainId: 16975,
  description: 'milky',
};

export const sampleWithFullData: ISaleInvoiceCommonServiceCharge = {
  id: 7754,
  invoiceId: 22999,
  lineId: 27371,
  optionId: 21913,
  mainId: 26582,
  code: 'outside unfreeze poorly',
  name: 'till',
  description: 'meh upliftingly',
  value: 5685.56,
  discount: 13386.74,
  servicePrice: 20860.47,
};

export const sampleWithNewData: NewSaleInvoiceCommonServiceCharge = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
