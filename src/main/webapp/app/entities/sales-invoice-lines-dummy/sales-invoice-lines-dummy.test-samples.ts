import dayjs from 'dayjs/esm';

import { ISalesInvoiceLinesDummy, NewSalesInvoiceLinesDummy } from './sales-invoice-lines-dummy.model';

export const sampleWithRequiredData: ISalesInvoiceLinesDummy = {
  id: 26378,
};

export const sampleWithPartialData: ISalesInvoiceLinesDummy = {
  id: 29853,
  itemCode: 'aside dislocate',
  itemName: 'rekindle voluntarily',
  unitOfMeasurement: 'gadzooks',
  quantity: 19146.37,
  itemCost: 32004.87,
  lmu: 9764,
  lmd: dayjs('2025-01-30T02:04'),
  nbt: false,
};

export const sampleWithFullData: ISalesInvoiceLinesDummy = {
  id: 11769,
  invoiceId: 11828,
  lineId: 1401,
  itemId: 12207,
  itemCode: 'indeed',
  itemName: 'delirious flash gosh',
  description: 'inexperienced ugh revoke',
  unitOfMeasurement: 'access regarding corrupt',
  quantity: 21595.27,
  itemCost: 2703.8,
  itemPrice: 32525.87,
  discount: 13283.36,
  tax: 30936.03,
  sellingPrice: 5037.92,
  lineTotal: 5373.31,
  lmu: 5383,
  lmd: dayjs('2025-01-29T15:35'),
  nbt: false,
  vat: false,
};

export const sampleWithNewData: NewSalesInvoiceLinesDummy = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
