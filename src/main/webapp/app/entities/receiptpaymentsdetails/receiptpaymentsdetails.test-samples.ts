import dayjs from 'dayjs/esm';

import { IReceiptpaymentsdetails, NewReceiptpaymentsdetails } from './receiptpaymentsdetails.model';

export const sampleWithRequiredData: IReceiptpaymentsdetails = {
  id: 25149,
};

export const sampleWithPartialData: IReceiptpaymentsdetails = {
  id: 28296,
  lineid: 21009,
  paymentamount: 8836.63,
  totalreceiptamount: 31301.55,
  checkqueamount: 1682.19,
  checkqueno: 'quaintly',
  checkqueexpiredate: dayjs('2025-02-18T06:36'),
  bankname: 'mediocre oxidise and',
  bankbranchname: 'heartbeat because',
  bankbranchid: 17352,
  lmd: dayjs('2025-02-18T10:48'),
  termname: 'besides testify ick',
  accountno: 'minus bright wash',
  accountnumber: 'ouch icebreaker',
  depositeddate: dayjs('2025-02-18T00:56'),
  chequestatuschangeddate: dayjs('2025-02-17T17:20'),
  returnchequesttledate: dayjs('2025-02-18T11:46'),
  chequestatusid: 16195,
  depositdate: dayjs('2025-02-18T08:01'),
  accountid: 9777,
  accountcode: 'quickly but',
  bankdepositbankid: 17826,
  bankdepositbankbranchid: 12791,
  isbankreconciliation: false,
};

export const sampleWithFullData: IReceiptpaymentsdetails = {
  id: 24589,
  lineid: 17881,
  paymentamount: 24833.77,
  totalreceiptamount: 7206.02,
  checkqueamount: 11252.13,
  checkqueno: 'whether',
  checkquedate: dayjs('2025-02-18T06:23'),
  checkqueexpiredate: dayjs('2025-02-18T05:32'),
  bankname: 'concerning',
  bankid: 23670,
  bankbranchname: 'put monthly',
  bankbranchid: 22062,
  creditcardno: 'likewise',
  creditcardamount: 228.31,
  reference: 'after beret',
  otherdetails: 'unlawful happy-go-lucky',
  lmu: 'summarise',
  lmd: dayjs('2025-02-18T00:38'),
  termid: 12941,
  termname: 'meaningfully blah',
  accountno: 'store lighthearted',
  accountnumber: 'last yowza wherever',
  chequereturndate: dayjs('2025-02-17T23:20'),
  isdeposit: true,
  depositeddate: dayjs('2025-02-17T14:28'),
  chequestatuschangeddate: dayjs('2025-02-18T05:03'),
  returnchequesttledate: dayjs('2025-02-18T11:00'),
  chequestatusid: 28697,
  isPdCheque: false,
  depositdate: dayjs('2025-02-18T12:56'),
  accountid: 1444,
  accountcode: 'writ apt kissingly',
  bankdepositbankname: 'shore galoshes',
  bankdepositbankid: 20273,
  bankdepositbankbranchname: 'furthermore disappointment mathematics',
  bankdepositbankbranchid: 19702,
  returnchequefine: 32718.86,
  companybankid: 31538,
  isbankreconciliation: false,
};

export const sampleWithNewData: NewReceiptpaymentsdetails = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
