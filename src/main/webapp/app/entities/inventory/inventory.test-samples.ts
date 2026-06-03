import dayjs from 'dayjs/esm';

import { IInventory, NewInventory } from './inventory.model';

export const sampleWithRequiredData: IInventory = {
  id: 19845,
};

export const sampleWithPartialData: IInventory = {
  id: 24370,
  code: 'accidentally promise exacerbate',
  description: 'crushing',
  type: 14860,
  classification1: 'scotch',
  classification3: 'whoever gratefully oddly',
  classification4: 'gah supposing',
  classification5: 'while considering spanish',
  isassemblyunit: true,
  reorderlevel: 23408.37,
  lastcost: 13241.9,
  availablequantity: 18434.72,
  itemspecfilepath: 'illusion while acknowledge',
  activeitem: true,
  minstock: 5370.83,
  maxstock: 25254.25,
  dailyaverage: 10854.22,
  buffertime: 21484.87,
  accountid: 9881,
  commissionempid: 12878,
  checktype: 'lest',
  reorderqty: 29302.07,
  notininvoice: true,
};

export const sampleWithFullData: IInventory = {
  id: 10161,
  code: 'homely consequently understanding',
  partnumber: 'traditionalism over',
  name: 'ski',
  description: 'uh-huh responsible improbable',
  type: 1182,
  classification1: 'instead ascertain narrowcast',
  classification2: 'upon earth',
  classification3: 'excluding ha lest',
  classification4: 'unwelcome',
  classification5: 'fondly incidentally',
  unitofmeasurement: 'dismal bob transplant',
  decimalplaces: 21386,
  isassemblyunit: true,
  assemblyunitof: 6783,
  reorderlevel: 21839.91,
  lastcost: 15561.22,
  lastsellingprice: 21288.07,
  lmu: 31292,
  lmd: dayjs('2024-08-19T15:40'),
  availablequantity: 23801.17,
  hasbatches: true,
  itemspecfilepath: 'badly continually',
  itemimagepath: 'sport',
  returnprice: 27890.73,
  activeitem: true,
  minstock: 15095.5,
  maxstock: 13618.45,
  dailyaverage: 12602.7,
  bufferlevel: 13029.8,
  leadtime: 22379.17,
  buffertime: 3940.2,
  saftydays: 22059.42,
  accountcode: 'geez',
  accountid: 16636,
  casepackqty: 24963,
  isregistered: false,
  defaultstocklocationid: 12048,
  rackno: 'until',
  barcodeimage: '../fake-data/blob/hipster.png',
  barcodeimageContentType: 'unknown',
  commissionempid: 549,
  checktypeid: 24577,
  checktype: 'when',
  reorderqty: 21146.12,
  notininvoice: true,
};

export const sampleWithNewData: NewInventory = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
