import dayjs from 'dayjs/esm';

export interface IBinCard {
  id: number;
  itemID?: number | null;
  itemCode?: string | null;
  reference?: string | null;
  txDate?: dayjs.Dayjs | null;
  qtyIn?: number | null;
  qtyOut?: number | null;
  price?: number | null;
  lMU?: number | null;
  lMD?: dayjs.Dayjs | null;
  referenceCode?: string | null;
  recordDate?: dayjs.Dayjs | null;
  batchId?: number | null;
  locationID?: number | null;
  locationCode?: string | null;
  opening?: number | null;
  description?: string | null;
  referenceDoc?: string | null;
}

export type NewBinCard = Omit<IBinCard, 'id'> & { id: null };
