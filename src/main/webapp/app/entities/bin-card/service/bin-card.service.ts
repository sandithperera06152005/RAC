import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, map } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IBinCard, NewBinCard } from '../bin-card.model';

export type PartialUpdateBinCard = Partial<IBinCard> & Pick<IBinCard, 'id'>;

type RestOf<T extends IBinCard | NewBinCard> = Omit<T, 'txDate' | 'lMD' | 'recordDate'> & {
  txDate?: string | null;
  lMD?: string | null;
  recordDate?: string | null;
};

export type RestBinCard = RestOf<IBinCard>;

export type NewRestBinCard = RestOf<NewBinCard>;

export type PartialUpdateRestBinCard = RestOf<PartialUpdateBinCard>;

export type EntityResponseType = HttpResponse<IBinCard>;
export type EntityArrayResponseType = HttpResponse<IBinCard[]>;

@Injectable({ providedIn: 'root' })
export class BinCardService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/bin-cards');

  create(binCard: NewBinCard): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(binCard);
    return this.http
      .post<RestBinCard>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(binCard: IBinCard): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(binCard);
    return this.http
      .put<RestBinCard>(`${this.resourceUrl}/${this.getBinCardIdentifier(binCard)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(binCard: PartialUpdateBinCard): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(binCard);
    return this.http
      .patch<RestBinCard>(`${this.resourceUrl}/${this.getBinCardIdentifier(binCard)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestBinCard>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestBinCard[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getBinCardIdentifier(binCard: Pick<IBinCard, 'id'>): number {
    return binCard.id;
  }

  compareBinCard(o1: Pick<IBinCard, 'id'> | null, o2: Pick<IBinCard, 'id'> | null): boolean {
    return o1 && o2 ? this.getBinCardIdentifier(o1) === this.getBinCardIdentifier(o2) : o1 === o2;
  }

  addBinCardToCollectionIfMissing<Type extends Pick<IBinCard, 'id'>>(
    binCardCollection: Type[],
    ...binCardsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const binCards: Type[] = binCardsToCheck.filter(isPresent);
    if (binCards.length > 0) {
      const binCardIdentifiers = binCardCollection.map(binCardItem => this.getBinCardIdentifier(binCardItem));
      const binCardsToAdd = binCards.filter(binCardItem => {
        const binCardIdentifier = this.getBinCardIdentifier(binCardItem);
        if (binCardIdentifiers.includes(binCardIdentifier)) {
          return false;
        }
        binCardIdentifiers.push(binCardIdentifier);
        return true;
      });
      return [...binCardsToAdd, ...binCardCollection];
    }
    return binCardCollection;
  }

  protected convertDateFromClient<T extends IBinCard | NewBinCard | PartialUpdateBinCard>(binCard: T): RestOf<T> {
    return {
      ...binCard,
      txDate: binCard.txDate?.toJSON() ?? null,
      lMD: binCard.lMD?.toJSON() ?? null,
      recordDate: binCard.recordDate?.toJSON() ?? null,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestBinCard>): HttpResponse<IBinCard> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestBinCard[]>): HttpResponse<IBinCard[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }

  protected convertDateFromServer(restBinCard: RestBinCard): IBinCard {
    return {
      ...restBinCard,
      txDate: restBinCard.txDate ? dayjs(restBinCard.txDate) : null,
      lMD: restBinCard.lMD ? dayjs(restBinCard.lMD) : null,
      recordDate: restBinCard.recordDate ? dayjs(restBinCard.recordDate) : null,
    };
  }
}
