import dayjs from 'dayjs/esm';

import { ICommonserviceoption, NewCommonserviceoption } from './commonserviceoption.model';

export const sampleWithRequiredData: ICommonserviceoption = {
  id: 26450,
};

export const sampleWithPartialData: ICommonserviceoption = {
  id: 358,
  code: 'psst doodle wash',
  isactive: false,
};

export const sampleWithFullData: ICommonserviceoption = {
  id: 28896,
  mainid: 4817,
  code: 'acidic',
  name: 'taxicab ew',
  description: 'bandwidth',
  value: 28375.55,
  isactive: false,
  lmd: dayjs('2024-10-03T10:37'),
  lmu: 16410,
};

export const sampleWithNewData: NewCommonserviceoption = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
