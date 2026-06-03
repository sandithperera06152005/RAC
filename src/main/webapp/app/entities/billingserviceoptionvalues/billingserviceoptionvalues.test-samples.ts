import dayjs from 'dayjs/esm';

import { IBillingserviceoptionvalues, NewBillingserviceoptionvalues } from './billingserviceoptionvalues.model';

export const sampleWithRequiredData: IBillingserviceoptionvalues = {
  id: 11988,
};

export const sampleWithPartialData: IBillingserviceoptionvalues = {
  id: 4667,
  vehicletypeid: 30690,
  value: 15629.44,
  lmd: dayjs('2024-10-03T15:39'),
};

export const sampleWithFullData: IBillingserviceoptionvalues = {
  id: 7964,
  vehicletypeid: 4748,
  billingserviceoptionid: 16367,
  value: 30140.89,
  lmd: dayjs('2024-10-04T00:40'),
  lmu: 3676,
};

export const sampleWithNewData: NewBillingserviceoptionvalues = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
