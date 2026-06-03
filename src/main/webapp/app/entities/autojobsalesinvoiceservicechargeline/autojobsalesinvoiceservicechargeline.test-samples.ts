import {
  IAutojobsalesinvoiceservicechargeline,
  NewAutojobsalesinvoiceservicechargeline,
} from './autojobsalesinvoiceservicechargeline.model';

export const sampleWithRequiredData: IAutojobsalesinvoiceservicechargeline = {
  id: 12797,
};

export const sampleWithPartialData: IAutojobsalesinvoiceservicechargeline = {
  id: 28733,
  invoiceid: 24757,
  lineid: 13482,
  servicediscription: 'defiantly',
  addedbyid: 25723,
  iscustomersrvice: true,
  serviceprice: 20048.32,
};

export const sampleWithFullData: IAutojobsalesinvoiceservicechargeline = {
  id: 15599,
  invoiceid: 5436,
  lineid: 15568,
  optionid: 24845,
  servicename: 'unto extroverted',
  servicediscription: 'scrap dial',
  value: 4687.11,
  addedbyid: 4846,
  iscustomersrvice: false,
  discount: 19655.26,
  serviceprice: 26941.35,
};

export const sampleWithNewData: NewAutojobsalesinvoiceservicechargeline = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
