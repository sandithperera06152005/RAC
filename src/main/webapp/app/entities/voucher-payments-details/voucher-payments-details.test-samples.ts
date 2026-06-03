import dayjs from 'dayjs/esm';

import { IVoucherPaymentsDetails, NewVoucherPaymentsDetails } from './voucher-payments-details.model';

export const sampleWithRequiredData: IVoucherPaymentsDetails = {
  id: 22798,
};

export const sampleWithPartialData: IVoucherPaymentsDetails = {
  id: 19477,
  lineID: 16101,
  paymentAmount: 27693.88,
  checkqueNo: 'which through',
  checkqueDate: dayjs('2025-02-10T05:02'),
  bankName: 'old-fashioned because',
  creditCardNo: 'e-mail important ha',
  creditCardAmount: 10459.11,
  otherDetails: 'provided glaring anti',
  lmd: dayjs('2025-02-11T04:39'),
  termID: 3371,
  termName: 'whoa pharmacopoeia',
  accountId: 7209,
  accountCode: 'unethically encouragement',
  depositedDate: dayjs('2025-02-10T09:27'),
};

export const sampleWithFullData: IVoucherPaymentsDetails = {
  id: 297,
  lineID: 24457,
  paymentAmount: 1216.22,
  totalVoucherAmount: 11626.5,
  checkqueAmount: 11296.48,
  checkqueNo: 'free who vibration',
  checkqueDate: dayjs('2025-02-10T21:52'),
  checkqueExpireDate: dayjs('2025-02-10T05:47'),
  bankName: 'ew',
  bankID: 31537,
  creditCardNo: 'volleyball',
  creditCardAmount: 10226.75,
  reference: 'as after pause',
  otherDetails: 'exalt',
  lmu: 'adventurously distant',
  lmd: dayjs('2025-02-10T16:02'),
  termID: 29207,
  termName: 'awkwardly naturally',
  accountNo: 31037,
  accountNumber: 23969,
  accountId: 14469,
  accountCode: 'confound uneven airport',
  chequeStatusId: 25468,
  isDeposit: true,
  depositedDate: dayjs('2025-02-10T07:09'),
  companyBankId: 23844,
  isBankReconciliation: true,
};

export const sampleWithNewData: NewVoucherPaymentsDetails = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
