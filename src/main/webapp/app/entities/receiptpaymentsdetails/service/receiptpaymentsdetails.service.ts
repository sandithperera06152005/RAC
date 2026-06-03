import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, map } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IReceiptpaymentsdetails, NewReceiptpaymentsdetails } from '../receiptpaymentsdetails.model';

export type PartialUpdateReceiptpaymentsdetails = Partial<IReceiptpaymentsdetails> & Pick<IReceiptpaymentsdetails, 'id'>;

type RestOf<T extends IReceiptpaymentsdetails | NewReceiptpaymentsdetails> = Omit<
  T,
  | 'checkquedate'
  | 'checkqueexpiredate'
  | 'lmd'
  | 'chequereturndate'
  | 'depositeddate'
  | 'chequestatuschangeddate'
  | 'returnchequesttledate'
  | 'depositdate'
> & {
  checkquedate?: string | null;
  checkqueexpiredate?: string | null;
  lmd?: string | null;
  chequereturndate?: string | null;
  depositeddate?: string | null;
  chequestatuschangeddate?: string | null;
  returnchequesttledate?: string | null;
  depositdate?: string | null;
};

export type RestReceiptpaymentsdetails = RestOf<IReceiptpaymentsdetails>;

export type NewRestReceiptpaymentsdetails = RestOf<NewReceiptpaymentsdetails>;

export type PartialUpdateRestReceiptpaymentsdetails = RestOf<PartialUpdateReceiptpaymentsdetails>;

export type EntityResponseType = HttpResponse<IReceiptpaymentsdetails>;
export type EntityArrayResponseType = HttpResponse<IReceiptpaymentsdetails[]>;

@Injectable({ providedIn: 'root' })
export class ReceiptpaymentsdetailsService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/receiptpaymentsdetails');

  create(receiptpaymentsdetails: NewReceiptpaymentsdetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(receiptpaymentsdetails);
    return this.http
      .post<RestReceiptpaymentsdetails>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(receiptpaymentsdetails: IReceiptpaymentsdetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(receiptpaymentsdetails);
    return this.http
      .put<RestReceiptpaymentsdetails>(`${this.resourceUrl}/${this.getReceiptpaymentsdetailsIdentifier(receiptpaymentsdetails)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(receiptpaymentsdetails: PartialUpdateReceiptpaymentsdetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(receiptpaymentsdetails);
    return this.http
      .patch<RestReceiptpaymentsdetails>(`${this.resourceUrl}/${this.getReceiptpaymentsdetailsIdentifier(receiptpaymentsdetails)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestReceiptpaymentsdetails>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestReceiptpaymentsdetails[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getReceiptpaymentsdetailsIdentifier(receiptpaymentsdetails: Pick<IReceiptpaymentsdetails, 'id'>): number {
    return receiptpaymentsdetails.id;
  }

  compareReceiptpaymentsdetails(o1: Pick<IReceiptpaymentsdetails, 'id'> | null, o2: Pick<IReceiptpaymentsdetails, 'id'> | null): boolean {
    return o1 && o2 ? this.getReceiptpaymentsdetailsIdentifier(o1) === this.getReceiptpaymentsdetailsIdentifier(o2) : o1 === o2;
  }

  addReceiptpaymentsdetailsToCollectionIfMissing<Type extends Pick<IReceiptpaymentsdetails, 'id'>>(
    receiptpaymentsdetailsCollection: Type[],
    ...receiptpaymentsdetailsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const receiptpaymentsdetails: Type[] = receiptpaymentsdetailsToCheck.filter(isPresent);
    if (receiptpaymentsdetails.length > 0) {
      const receiptpaymentsdetailsCollectionIdentifiers = receiptpaymentsdetailsCollection.map(receiptpaymentsdetailsItem =>
        this.getReceiptpaymentsdetailsIdentifier(receiptpaymentsdetailsItem),
      );
      const receiptpaymentsdetailsToAdd = receiptpaymentsdetails.filter(receiptpaymentsdetailsItem => {
        const receiptpaymentsdetailsIdentifier = this.getReceiptpaymentsdetailsIdentifier(receiptpaymentsdetailsItem);
        if (receiptpaymentsdetailsCollectionIdentifiers.includes(receiptpaymentsdetailsIdentifier)) {
          return false;
        }
        receiptpaymentsdetailsCollectionIdentifiers.push(receiptpaymentsdetailsIdentifier);
        return true;
      });
      return [...receiptpaymentsdetailsToAdd, ...receiptpaymentsdetailsCollection];
    }
    return receiptpaymentsdetailsCollection;
  }

  protected convertDateFromClient<T extends IReceiptpaymentsdetails | NewReceiptpaymentsdetails | PartialUpdateReceiptpaymentsdetails>(
    receiptpaymentsdetails: T,
  ): RestOf<T> {
    return {
      ...receiptpaymentsdetails,
      checkquedate: receiptpaymentsdetails.checkquedate?.toJSON() ?? null,
      checkqueexpiredate: receiptpaymentsdetails.checkqueexpiredate?.toJSON() ?? null,
      lmd: receiptpaymentsdetails.lmd?.toJSON() ?? null,
      chequereturndate: receiptpaymentsdetails.chequereturndate?.toJSON() ?? null,
      depositeddate: receiptpaymentsdetails.depositeddate?.toJSON() ?? null,
      chequestatuschangeddate: receiptpaymentsdetails.chequestatuschangeddate?.toJSON() ?? null,
      returnchequesttledate: receiptpaymentsdetails.returnchequesttledate?.toJSON() ?? null,
      depositdate: receiptpaymentsdetails.depositdate?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restReceiptpaymentsdetails: RestReceiptpaymentsdetails): IReceiptpaymentsdetails {
    return {
      ...restReceiptpaymentsdetails,
      checkquedate: restReceiptpaymentsdetails.checkquedate ? dayjs(restReceiptpaymentsdetails.checkquedate) : undefined,
      checkqueexpiredate: restReceiptpaymentsdetails.checkqueexpiredate ? dayjs(restReceiptpaymentsdetails.checkqueexpiredate) : undefined,
      lmd: restReceiptpaymentsdetails.lmd ? dayjs(restReceiptpaymentsdetails.lmd) : undefined,
      chequereturndate: restReceiptpaymentsdetails.chequereturndate ? dayjs(restReceiptpaymentsdetails.chequereturndate) : undefined,
      depositeddate: restReceiptpaymentsdetails.depositeddate ? dayjs(restReceiptpaymentsdetails.depositeddate) : undefined,
      chequestatuschangeddate: restReceiptpaymentsdetails.chequestatuschangeddate
        ? dayjs(restReceiptpaymentsdetails.chequestatuschangeddate)
        : undefined,
      returnchequesttledate: restReceiptpaymentsdetails.returnchequesttledate
        ? dayjs(restReceiptpaymentsdetails.returnchequesttledate)
        : undefined,
      depositdate: restReceiptpaymentsdetails.depositdate ? dayjs(restReceiptpaymentsdetails.depositdate) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestReceiptpaymentsdetails>): HttpResponse<IReceiptpaymentsdetails> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestReceiptpaymentsdetails[]>): HttpResponse<IReceiptpaymentsdetails[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
