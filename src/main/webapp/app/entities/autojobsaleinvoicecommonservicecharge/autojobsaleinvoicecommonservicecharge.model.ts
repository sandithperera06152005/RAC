export interface IAutojobsaleinvoicecommonservicecharge {
  id: number;
  invoiceid?: number | null;
  lineid?: number | null;
  optionid?: number | null;
  mainid?: number | null;
  code?: string | null;
  name?: string | null;
  description?: string | null;
  value?: number | null;
  addedbyid?: number | null;
  discount?: number | null;
  serviceprice?: number | null;
}

export type NewAutojobsaleinvoicecommonservicecharge = Omit<IAutojobsaleinvoicecommonservicecharge, 'id'> & { id: null };
