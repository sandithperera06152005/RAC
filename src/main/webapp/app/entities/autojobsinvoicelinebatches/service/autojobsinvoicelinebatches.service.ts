import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, map } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAutojobsinvoicelinebatches, NewAutojobsinvoicelinebatches } from '../autojobsinvoicelinebatches.model';

export type PartialUpdateAutojobsinvoicelinebatches = Partial<IAutojobsinvoicelinebatches> & Pick<IAutojobsinvoicelinebatches, 'id'>;

type RestOf<T extends IAutojobsinvoicelinebatches | NewAutojobsinvoicelinebatches> = Omit<
  T,
  'txdate' | 'manufacturedate' | 'expireddate' | 'lmd' | 'issueddatetime'
> & {
  txdate?: string | null;
  manufacturedate?: string | null;
  expireddate?: string | null;
  lmd?: string | null;
  issueddatetime?: string | null;
};

export type RestAutojobsinvoicelinebatches = RestOf<IAutojobsinvoicelinebatches>;

export type NewRestAutojobsinvoicelinebatches = RestOf<NewAutojobsinvoicelinebatches>;

export type PartialUpdateRestAutojobsinvoicelinebatches = RestOf<PartialUpdateAutojobsinvoicelinebatches>;

export type EntityResponseType = HttpResponse<IAutojobsinvoicelinebatches>;
export type EntityArrayResponseType = HttpResponse<IAutojobsinvoicelinebatches[]>;

@Injectable({ providedIn: 'root' })
export class AutojobsinvoicelinebatchesService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/autojobsinvoicelinebatches');

  create(autojobsinvoicelinebatches: NewAutojobsinvoicelinebatches): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(autojobsinvoicelinebatches);
    return this.http
      .post<RestAutojobsinvoicelinebatches>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(autojobsinvoicelinebatches: IAutojobsinvoicelinebatches): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(autojobsinvoicelinebatches);
    return this.http
      .put<RestAutojobsinvoicelinebatches>(
        `${this.resourceUrl}/${this.getAutojobsinvoicelinebatchesIdentifier(autojobsinvoicelinebatches)}`,
        copy,
        { observe: 'response' },
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(autojobsinvoicelinebatches: PartialUpdateAutojobsinvoicelinebatches): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(autojobsinvoicelinebatches);
    return this.http
      .patch<RestAutojobsinvoicelinebatches>(
        `${this.resourceUrl}/${this.getAutojobsinvoicelinebatchesIdentifier(autojobsinvoicelinebatches)}`,
        copy,
        { observe: 'response' },
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestAutojobsinvoicelinebatches>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestAutojobsinvoicelinebatches[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  queryByParentLineIds(ids: number[]): Observable<EntityArrayResponseType> {
    return this.http
      .get<RestAutojobsinvoicelinebatches[]>(`${this.resourceUrl}/parent-lines`, {
        params: { ids: ids.map(id => id.toString()) },
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  cancelBatch(
    batch: Pick<IAutojobsinvoicelinebatches, 'id' | 'lineid' | 'itemid' | 'code' | 'canceloptid' | 'cancelopt' | 'cancelby'>,
  ): Observable<EntityResponseType> {
    return this.http
      .post<RestAutojobsinvoicelinebatches>(`${this.resourceUrl}/cancel`, batch, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAutojobsinvoicelinebatchesIdentifier(autojobsinvoicelinebatches: Pick<IAutojobsinvoicelinebatches, 'id'>): number {
    return autojobsinvoicelinebatches.id;
  }

  compareAutojobsinvoicelinebatches(
    o1: Pick<IAutojobsinvoicelinebatches, 'id'> | null,
    o2: Pick<IAutojobsinvoicelinebatches, 'id'> | null,
  ): boolean {
    return o1 && o2 ? this.getAutojobsinvoicelinebatchesIdentifier(o1) === this.getAutojobsinvoicelinebatchesIdentifier(o2) : o1 === o2;
  }

  addAutojobsinvoicelinebatchesToCollectionIfMissing<Type extends Pick<IAutojobsinvoicelinebatches, 'id'>>(
    autojobsinvoicelinebatchesCollection: Type[],
    ...autojobsinvoicelinebatchesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const autojobsinvoicelinebatches: Type[] = autojobsinvoicelinebatchesToCheck.filter(isPresent);
    if (autojobsinvoicelinebatches.length > 0) {
      const autojobsinvoicelinebatchesCollectionIdentifiers = autojobsinvoicelinebatchesCollection.map(autojobsinvoicelinebatchesItem =>
        this.getAutojobsinvoicelinebatchesIdentifier(autojobsinvoicelinebatchesItem),
      );
      const autojobsinvoicelinebatchesToAdd = autojobsinvoicelinebatches.filter(autojobsinvoicelinebatchesItem => {
        const autojobsinvoicelinebatchesIdentifier = this.getAutojobsinvoicelinebatchesIdentifier(autojobsinvoicelinebatchesItem);
        if (autojobsinvoicelinebatchesCollectionIdentifiers.includes(autojobsinvoicelinebatchesIdentifier)) {
          return false;
        }
        autojobsinvoicelinebatchesCollectionIdentifiers.push(autojobsinvoicelinebatchesIdentifier);
        return true;
      });
      return [...autojobsinvoicelinebatchesToAdd, ...autojobsinvoicelinebatchesCollection];
    }
    return autojobsinvoicelinebatchesCollection;
  }

  protected convertDateFromClient<
    T extends IAutojobsinvoicelinebatches | NewAutojobsinvoicelinebatches | PartialUpdateAutojobsinvoicelinebatches,
  >(autojobsinvoicelinebatches: T): RestOf<T> {
    return {
      ...autojobsinvoicelinebatches,
      txdate: autojobsinvoicelinebatches.txdate?.toJSON() ?? null,
      manufacturedate: autojobsinvoicelinebatches.manufacturedate?.toJSON() ?? null,
      expireddate: autojobsinvoicelinebatches.expireddate?.toJSON() ?? null,
      lmd: autojobsinvoicelinebatches.lmd?.toJSON() ?? null,
      issueddatetime: autojobsinvoicelinebatches.issueddatetime?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restAutojobsinvoicelinebatches: RestAutojobsinvoicelinebatches): IAutojobsinvoicelinebatches {
    return {
      ...restAutojobsinvoicelinebatches,
      txdate: restAutojobsinvoicelinebatches.txdate ? dayjs(restAutojobsinvoicelinebatches.txdate) : undefined,
      manufacturedate: restAutojobsinvoicelinebatches.manufacturedate ? dayjs(restAutojobsinvoicelinebatches.manufacturedate) : undefined,
      expireddate: restAutojobsinvoicelinebatches.expireddate ? dayjs(restAutojobsinvoicelinebatches.expireddate) : undefined,
      lmd: restAutojobsinvoicelinebatches.lmd ? dayjs(restAutojobsinvoicelinebatches.lmd) : undefined,
      issueddatetime: restAutojobsinvoicelinebatches.issueddatetime ? dayjs(restAutojobsinvoicelinebatches.issueddatetime) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestAutojobsinvoicelinebatches>): HttpResponse<IAutojobsinvoicelinebatches> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(
    res: HttpResponse<RestAutojobsinvoicelinebatches[]>,
  ): HttpResponse<IAutojobsinvoicelinebatches[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
