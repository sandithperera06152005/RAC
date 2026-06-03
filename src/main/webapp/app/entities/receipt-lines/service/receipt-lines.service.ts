import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, map } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IReceiptLines, NewReceiptLines } from '../receipt-lines.model';

export type PartialUpdateReceiptLines = Partial<IReceiptLines> & Pick<IReceiptLines, 'id'>;

type RestOf<T extends IReceiptLines | NewReceiptLines> = Omit<T, 'lmd'> & {
  lmd?: string | null;
};

export type RestReceiptLines = RestOf<IReceiptLines>;

export type NewRestReceiptLines = RestOf<NewReceiptLines>;

export type PartialUpdateRestReceiptLines = RestOf<PartialUpdateReceiptLines>;

export type EntityResponseType = HttpResponse<IReceiptLines>;
export type EntityArrayResponseType = HttpResponse<IReceiptLines[]>;

@Injectable({ providedIn: 'root' })
export class ReceiptLinesService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/receipt-lines');

  create(receiptLines: NewReceiptLines): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(receiptLines);
    return this.http
      .post<RestReceiptLines>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(receiptLines: IReceiptLines): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(receiptLines);
    return this.http
      .put<RestReceiptLines>(`${this.resourceUrl}/${this.getReceiptLinesIdentifier(receiptLines)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(receiptLines: PartialUpdateReceiptLines): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(receiptLines);
    return this.http
      .patch<RestReceiptLines>(`${this.resourceUrl}/${this.getReceiptLinesIdentifier(receiptLines)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestReceiptLines>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestReceiptLines[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getReceiptLinesIdentifier(receiptLines: Pick<IReceiptLines, 'id'>): number {
    return receiptLines.id;
  }

  compareReceiptLines(o1: Pick<IReceiptLines, 'id'> | null, o2: Pick<IReceiptLines, 'id'> | null): boolean {
    return o1 && o2 ? this.getReceiptLinesIdentifier(o1) === this.getReceiptLinesIdentifier(o2) : o1 === o2;
  }

  addReceiptLinesToCollectionIfMissing<Type extends Pick<IReceiptLines, 'id'>>(
    receiptLinesCollection: Type[],
    ...receiptLinesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const receiptLines: Type[] = receiptLinesToCheck.filter(isPresent);
    if (receiptLines.length > 0) {
      const receiptLinesCollectionIdentifiers = receiptLinesCollection.map(receiptLinesItem =>
        this.getReceiptLinesIdentifier(receiptLinesItem),
      );
      const receiptLinesToAdd = receiptLines.filter(receiptLinesItem => {
        const receiptLinesIdentifier = this.getReceiptLinesIdentifier(receiptLinesItem);
        if (receiptLinesCollectionIdentifiers.includes(receiptLinesIdentifier)) {
          return false;
        }
        receiptLinesCollectionIdentifiers.push(receiptLinesIdentifier);
        return true;
      });
      return [...receiptLinesToAdd, ...receiptLinesCollection];
    }
    return receiptLinesCollection;
  }

  protected convertDateFromClient<T extends IReceiptLines | NewReceiptLines | PartialUpdateReceiptLines>(receiptLines: T): RestOf<T> {
    return {
      ...receiptLines,
      lmd: receiptLines.lmd?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restReceiptLines: RestReceiptLines): IReceiptLines {
    return {
      ...restReceiptLines,
      lmd: restReceiptLines.lmd ? dayjs(restReceiptLines.lmd) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestReceiptLines>): HttpResponse<IReceiptLines> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestReceiptLines[]>): HttpResponse<IReceiptLines[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
