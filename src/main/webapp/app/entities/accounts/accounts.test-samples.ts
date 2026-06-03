import dayjs from 'dayjs/esm';

import { IAccounts, NewAccounts } from './accounts.model';

export const sampleWithRequiredData: IAccounts = {
  id: 15875,
};

export const sampleWithPartialData: IAccounts = {
  id: 26878,
  type: 23817,
  balance: 27356.63,
  hasbatches: true,
  accountlevel: 5643,
  accountsnumberingsystem: 6806,
  subparentid: 3260,
  canedit: false,
  amount: 20678.44,
  creditamount: 24641.08,
  debitamount: 31645.02,
};

export const sampleWithFullData: IAccounts = {
  id: 3737,
  code: 'among',
  date: dayjs('2024-10-02T13:40'),
  name: 'unexpectedly machine',
  description: 'permafrost boo murky',
  type: 19896,
  parent: 499,
  balance: 26254.66,
  lmu: 30774,
  lmd: dayjs('2024-10-02T23:38'),
  hasbatches: false,
  accountvalue: 22465.61,
  accountlevel: 30605,
  accountsnumberingsystem: 7023,
  subparentid: 27413,
  canedit: true,
  amount: 6942.38,
  creditamount: 28862.86,
  debitamount: 23352.81,
  debitorcredit: 'wherever patiently louse',
  reporttype: 22101,
};

export const sampleWithNewData: NewAccounts = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
