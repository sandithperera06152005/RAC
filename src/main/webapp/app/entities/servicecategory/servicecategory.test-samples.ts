import dayjs from 'dayjs/esm';

import { IServicecategory, NewServicecategory } from './servicecategory.model';

export const sampleWithRequiredData: IServicecategory = {
  id: 22212,
};

export const sampleWithPartialData: IServicecategory = {
  id: 27407,
  name: 'oof politely jealously',
  description: 'fooey',
  lmu: 24202,
  lmd: dayjs('2024-08-19T17:45'),
  showsecurity: false,
};

export const sampleWithFullData: IServicecategory = {
  id: 2515,
  name: 'amidst',
  description: 'writhing',
  lmu: 26865,
  lmd: dayjs('2024-08-19T23:21'),
  showsecurity: true,
  sortorder: 24672,
  isactive: false,
};

export const sampleWithNewData: NewServicecategory = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
