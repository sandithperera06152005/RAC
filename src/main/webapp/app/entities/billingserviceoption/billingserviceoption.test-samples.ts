import dayjs from 'dayjs/esm';

import { IBillingserviceoption, NewBillingserviceoption } from './billingserviceoption.model';

export const sampleWithRequiredData: IBillingserviceoption = {
  id: 13457,
};

export const sampleWithPartialData: IBillingserviceoption = {
  id: 5073,
  servicediscription: 'vaguely',
  isactive: false,
  billtocustomer: false,
};

export const sampleWithFullData: IBillingserviceoption = {
  id: 27605,
  servicename: 'warmly',
  servicediscription: 'separately',
  isactive: false,
  lmd: dayjs('2024-10-04T03:56'),
  lmu: 30941,
  orderby: 31054,
  billtocustomer: false,
};

export const sampleWithNewData: NewBillingserviceoption = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
