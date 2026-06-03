import dayjs from 'dayjs/esm';

import { IReceiptLines, NewReceiptLines } from './receipt-lines.model';

export const sampleWithRequiredData: IReceiptLines = {
  id: 22814,
};

export const sampleWithPartialData: IReceiptLines = {
  id: 4655,
  amountreceived: 13056.07,
  lmd: dayjs('2025-02-26T00:49'),
  accountid: 32478,
};

export const sampleWithFullData: IReceiptLines = {
  id: 19580,
  lineid: 18439,
  invoicecode: 'yum duh',
  invoicetype: 'ouch specific elementary',
  originalamount: 10073.39,
  amountowing: 11427.38,
  discountavailable: 21015.85,
  discounttaken: 10111.67,
  amountreceived: 24461.24,
  lmu: 24158,
  lmd: dayjs('2025-02-25T10:53'),
  accountid: 12606,
};

export const sampleWithNewData: NewReceiptLines = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
