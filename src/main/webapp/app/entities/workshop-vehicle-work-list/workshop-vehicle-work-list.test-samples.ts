import dayjs from 'dayjs/esm';

import { IWorkshopVehicleWorkList, NewWorkshopVehicleWorkList } from './workshop-vehicle-work-list.model';

export const sampleWithRequiredData: IWorkshopVehicleWorkList = {
  id: 20067,
};

export const sampleWithPartialData: IWorkshopVehicleWorkList = {
  id: 31533,
  vehicleworkid: 26633,
  lineid: 31920,
  workid: 21771,
};

export const sampleWithFullData: IWorkshopVehicleWorkList = {
  id: 23602,
  vehicleworkid: 18589,
  lineid: 16220,
  workid: 28373,
  workshopwork: 'dream which supportive',
  isjobdone: false,
  jobdonedate: dayjs('2025-02-17T06:12'),
  jobnumber: 'unkempt cheerfully',
  jobvalue: 28333.64,
  estimatevalue: 21557.01,
};

export const sampleWithNewData: NewWorkshopVehicleWorkList = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
