import dayjs from 'dayjs/esm';

import { IVoucher, NewVoucher } from './voucher.model';

export const sampleWithRequiredData: IVoucher = {
  id: 27798,
};

export const sampleWithPartialData: IVoucher = {
  id: 7033,
  supplierName: 'lovely',
  supplierAddress: 'off hmph',
  totalAmountInWord: 'zen',
  comments: 'openly machine',
  lmu: 4927,
  lmd: dayjs('2025-02-10T17:21'),
  termId: 366,
  supplierID: 20682,
  createdBy: 14111,
};

export const sampleWithFullData: IVoucher = {
  id: 17100,
  code: 'airbrush between',
  voucherDate: dayjs('2025-02-10T20:43'),
  supplierName: 'times',
  supplierAddress: 'instead colossal',
  totalAmount: 15294.76,
  totalAmountInWord: 'expense toward',
  comments: 'rebound impolite',
  lmu: 11474,
  lmd: dayjs('2025-02-10T09:32'),
  termId: 7339,
  term: 'pfft disposer',
  date: dayjs('2025-02-10T15:49'),
  amountPaid: 15420.62,
  supplierID: 11075,
  isActive: true,
  createdBy: 10471,
};

export const sampleWithNewData: NewVoucher = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
