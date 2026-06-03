import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, map } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAutojobsinvoicelines, NewAutojobsinvoicelines } from '../autojobsinvoicelines.model';

export type PartialUpdateAutojobsinvoicelines = Partial<IAutojobsinvoicelines> & Pick<IAutojobsinvoicelines, 'id'>;

type RestOf<T extends IAutojobsinvoicelines | NewAutojobsinvoicelines> = Omit<T, 'lmd'> & {
  lmd?: string | null;
};

export type RestAutojobsinvoicelines = RestOf<IAutojobsinvoicelines>;

export type NewRestAutojobsinvoicelines = RestOf<NewAutojobsinvoicelines>;

export type PartialUpdateRestAutojobsinvoicelines = RestOf<PartialUpdateAutojobsinvoicelines>;

export type EntityResponseType = HttpResponse<IAutojobsinvoicelines>;
export type EntityArrayResponseType = HttpResponse<IAutojobsinvoicelines[]>;

@Injectable({ providedIn: 'root' })
export class AutojobsinvoicelinesService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/autojobsinvoicelines');

  create(autojobsinvoicelines: NewAutojobsinvoicelines): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(autojobsinvoicelines);
    return this.http
      .post<RestAutojobsinvoicelines>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(autojobsinvoicelines: IAutojobsinvoicelines): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(autojobsinvoicelines);
    return this.http
      .put<RestAutojobsinvoicelines>(`${this.resourceUrl}/${this.getAutojobsinvoicelinesIdentifier(autojobsinvoicelines)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(autojobsinvoicelines: PartialUpdateAutojobsinvoicelines): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(autojobsinvoicelines);
    return this.http
      .patch<RestAutojobsinvoicelines>(`${this.resourceUrl}/${this.getAutojobsinvoicelinesIdentifier(autojobsinvoicelines)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestAutojobsinvoicelines>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestAutojobsinvoicelines[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  queryByInvoiceId(invoiceId: number): Observable<EntityArrayResponseType> {
    return this.http
      .get<RestAutojobsinvoicelines[]>(`${this.resourceUrl}/invoice/${invoiceId}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAutojobsinvoicelinesIdentifier(autojobsinvoicelines: Pick<IAutojobsinvoicelines, 'id'>): number {
    return autojobsinvoicelines.id;
  }

  compareAutojobsinvoicelines(o1: Pick<IAutojobsinvoicelines, 'id'> | null, o2: Pick<IAutojobsinvoicelines, 'id'> | null): boolean {
    return o1 && o2 ? this.getAutojobsinvoicelinesIdentifier(o1) === this.getAutojobsinvoicelinesIdentifier(o2) : o1 === o2;
  }

  addAutojobsinvoicelinesToCollectionIfMissing<Type extends Pick<IAutojobsinvoicelines, 'id'>>(
    autojobsinvoicelinesCollection: Type[],
    ...autojobsinvoicelinesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const autojobsinvoicelines: Type[] = autojobsinvoicelinesToCheck.filter(isPresent);
    if (autojobsinvoicelines.length > 0) {
      const autojobsinvoicelinesCollectionIdentifiers = autojobsinvoicelinesCollection.map(autojobsinvoicelinesItem =>
        this.getAutojobsinvoicelinesIdentifier(autojobsinvoicelinesItem),
      );
      const autojobsinvoicelinesToAdd = autojobsinvoicelines.filter(autojobsinvoicelinesItem => {
        const autojobsinvoicelinesIdentifier = this.getAutojobsinvoicelinesIdentifier(autojobsinvoicelinesItem);
        if (autojobsinvoicelinesCollectionIdentifiers.includes(autojobsinvoicelinesIdentifier)) {
          return false;
        }
        autojobsinvoicelinesCollectionIdentifiers.push(autojobsinvoicelinesIdentifier);
        return true;
      });
      return [...autojobsinvoicelinesToAdd, ...autojobsinvoicelinesCollection];
    }
    return autojobsinvoicelinesCollection;
  }

  protected convertDateFromClient<T extends IAutojobsinvoicelines | NewAutojobsinvoicelines | PartialUpdateAutojobsinvoicelines>(
    autojobsinvoicelines: T,
  ): RestOf<T> {
    return {
      ...autojobsinvoicelines,
      lmd: autojobsinvoicelines.lmd?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restAutojobsinvoicelines: RestAutojobsinvoicelines): IAutojobsinvoicelines {
    return {
      ...restAutojobsinvoicelines,
      lmd: restAutojobsinvoicelines.lmd ? dayjs(restAutojobsinvoicelines.lmd) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestAutojobsinvoicelines>): HttpResponse<IAutojobsinvoicelines> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestAutojobsinvoicelines[]>): HttpResponse<IAutojobsinvoicelines[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
