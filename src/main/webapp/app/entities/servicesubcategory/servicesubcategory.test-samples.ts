import dayjs from 'dayjs/esm';

import { IServicesubcategory, NewServicesubcategory } from './servicesubcategory.model';

export const sampleWithRequiredData: IServicesubcategory = {
  id: 29667,
};

export const sampleWithPartialData: IServicesubcategory = {
  id: 15926,
  name: 'vice opposite deeply',
  description: 'ew',
  mainid: 4183,
  mainname: 'admired brightly garage',
  isactive: true,
};

export const sampleWithFullData: IServicesubcategory = {
  id: 6851,
  name: 'for hospitable fishery',
  description: 'ditch',
  mainid: 9927,
  mainname: 'ack good cultivated',
  lmu: 13781,
  lmd: dayjs('2024-08-21T04:02'),
  isactive: false,
};

export const sampleWithNewData: NewServicesubcategory = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
