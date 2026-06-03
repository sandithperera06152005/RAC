import dayjs from 'dayjs/esm';

import { IVoucherLines, NewVoucherLines } from './voucher-lines.model';

export const sampleWithRequiredData: IVoucherLines = {
  id: 27863,
};

export const sampleWithPartialData: IVoucherLines = {
  id: 435,
  discountAvailable: 13917.71,
  accountId: 30334,
};

export const sampleWithFullData: IVoucherLines = {
  id: 20867,
  lineID: 16342,
  grnCode: 'why gripping',
  grnType: 'stabilise pfft',
  originalAmount: 3912.54,
  amountOwing: 20270.53,
  discountAvailable: 21931.41,
  discountTaken: 21029.76,
  amountReceived: 15682.79,
  lmu: 25788,
  lmd: dayjs('2025-02-11T01:51'),
  accountId: 19389,
};

export const sampleWithNewData: NewVoucherLines = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
