export interface IAutojobsalesinvoiceservicechargeline {
  id: number;
  invoiceid?: number | null;
  lineid?: number | null;
  optionid?: number | null;
  servicename?: string | null;
  servicediscription?: string | null;
  value?: number | null;
  addedbyid?: number | null;
  iscustomersrvice?: boolean | null;
  discount?: number | null;
  serviceprice?: number | null;
}

export type NewAutojobsalesinvoiceservicechargeline = Omit<IAutojobsalesinvoiceservicechargeline, 'id'> & { id: null };
