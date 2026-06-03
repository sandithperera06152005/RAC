import dayjs from 'dayjs/esm';

import { IAutojobsinvoicelinebatches, NewAutojobsinvoicelinebatches } from './autojobsinvoicelinebatches.model';

export const sampleWithRequiredData: IAutojobsinvoicelinebatches = {
  id: 3927,
};

export const sampleWithPartialData: IAutojobsinvoicelinebatches = {
  id: 5238,
  lineid: 860,
  code: 'showy certification quinoa',
  batchid: 23864,
  txdate: dayjs('2025-02-27T05:59'),
  qty: 26601.11,
  lmu: 28302,
  nbt: true,
  vat: true,
  discount: 9180.86,
  issued: true,
  issueddatetime: dayjs('2025-02-27T08:30'),
  cancelby: 24048,
};

export const sampleWithFullData: IAutojobsinvoicelinebatches = {
  id: 19385,
  lineid: 28497,
  batchlineid: 894,
  itemid: 26932,
  code: 'overreact intend deduct',
  batchid: 2752,
  batchcode: 'elderly knotty',
  txdate: dayjs('2025-02-27T01:41'),
  manufacturedate: dayjs('2025-02-26T23:32'),
  expireddate: dayjs('2025-02-27T00:32'),
  qty: 21079.98,
  cost: 6650.61,
  price: 20034.4,
  notes: 'acceptable than',
  lmu: 21861,
  lmd: dayjs('2025-02-27T09:51'),
  nbt: false,
  vat: false,
  discount: 6865.45,
  total: 32501.2,
  issued: true,
  issuedby: 27043,
  issueddatetime: dayjs('2025-02-27T08:07'),
  addedbyid: 15113,
  canceloptid: 28367,
  cancelopt: 'heartfelt probable',
  cancelby: 13653,
};

export const sampleWithNewData: NewAutojobsinvoicelinebatches = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
