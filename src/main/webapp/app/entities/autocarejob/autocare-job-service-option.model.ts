export interface IAutocareJobServiceOption {
  id: number;
  jobid?: number | null;
  servicesubcategoryid?: number | null;
  pendding?: boolean | null;
  ongoing?: boolean | null;
  finished?: boolean | null;
  lmu?: number | null;
  lmd?: string | null;
  starttime?: string | null;
  endtime?: string | null;
}

export type NewAutocareJobServiceOption = Omit<IAutocareJobServiceOption, 'id'> & { id: null };
