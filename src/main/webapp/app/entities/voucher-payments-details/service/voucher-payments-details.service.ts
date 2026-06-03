import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, map } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IVoucherPaymentsDetails, NewVoucherPaymentsDetails } from '../voucher-payments-details.model';

export type PartialUpdateVoucherPaymentsDetails = Partial<IVoucherPaymentsDetails> & Pick<IVoucherPaymentsDetails, 'id'>;

type RestOf<T extends IVoucherPaymentsDetails | NewVoucherPaymentsDetails> = Omit<
  T,
  'checkqueDate' | 'checkqueExpireDate' | 'lmd' | 'depositedDate'
> & {
  checkqueDate?: string | null;
  checkqueExpireDate?: string | null;
  lmd?: string | null;
  depositedDate?: string | null;
};

export type RestVoucherPaymentsDetails = RestOf<IVoucherPaymentsDetails>;

export type NewRestVoucherPaymentsDetails = RestOf<NewVoucherPaymentsDetails>;

export type PartialUpdateRestVoucherPaymentsDetails = RestOf<PartialUpdateVoucherPaymentsDetails>;

export type EntityResponseType = HttpResponse<IVoucherPaymentsDetails>;
export type EntityArrayResponseType = HttpResponse<IVoucherPaymentsDetails[]>;

@Injectable({ providedIn: 'root' })
export class VoucherPaymentsDetailsService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/voucher-payments-details');

  create(voucherPaymentsDetails: NewVoucherPaymentsDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(voucherPaymentsDetails);
    return this.http
      .post<RestVoucherPaymentsDetails>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(voucherPaymentsDetails: IVoucherPaymentsDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(voucherPaymentsDetails);
    return this.http
      .put<RestVoucherPaymentsDetails>(`${this.resourceUrl}/${this.getVoucherPaymentsDetailsIdentifier(voucherPaymentsDetails)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(voucherPaymentsDetails: PartialUpdateVoucherPaymentsDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(voucherPaymentsDetails);
    return this.http
      .patch<RestVoucherPaymentsDetails>(`${this.resourceUrl}/${this.getVoucherPaymentsDetailsIdentifier(voucherPaymentsDetails)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestVoucherPaymentsDetails>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestVoucherPaymentsDetails[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getVoucherPaymentsDetailsIdentifier(voucherPaymentsDetails: Pick<IVoucherPaymentsDetails, 'id'>): number {
    return voucherPaymentsDetails.id;
  }

  compareVoucherPaymentsDetails(o1: Pick<IVoucherPaymentsDetails, 'id'> | null, o2: Pick<IVoucherPaymentsDetails, 'id'> | null): boolean {
    return o1 && o2 ? this.getVoucherPaymentsDetailsIdentifier(o1) === this.getVoucherPaymentsDetailsIdentifier(o2) : o1 === o2;
  }

  addVoucherPaymentsDetailsToCollectionIfMissing<Type extends Pick<IVoucherPaymentsDetails, 'id'>>(
    voucherPaymentsDetailsCollection: Type[],
    ...voucherPaymentsDetailsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const voucherPaymentsDetails: Type[] = voucherPaymentsDetailsToCheck.filter(isPresent);
    if (voucherPaymentsDetails.length > 0) {
      const voucherPaymentsDetailsCollectionIdentifiers = voucherPaymentsDetailsCollection.map(voucherPaymentsDetailsItem =>
        this.getVoucherPaymentsDetailsIdentifier(voucherPaymentsDetailsItem),
      );
      const voucherPaymentsDetailsToAdd = voucherPaymentsDetails.filter(voucherPaymentsDetailsItem => {
        const voucherPaymentsDetailsIdentifier = this.getVoucherPaymentsDetailsIdentifier(voucherPaymentsDetailsItem);
        if (voucherPaymentsDetailsCollectionIdentifiers.includes(voucherPaymentsDetailsIdentifier)) {
          return false;
        }
        voucherPaymentsDetailsCollectionIdentifiers.push(voucherPaymentsDetailsIdentifier);
        return true;
      });
      return [...voucherPaymentsDetailsToAdd, ...voucherPaymentsDetailsCollection];
    }
    return voucherPaymentsDetailsCollection;
  }

  protected convertDateFromClient<T extends IVoucherPaymentsDetails | NewVoucherPaymentsDetails | PartialUpdateVoucherPaymentsDetails>(
    voucherPaymentsDetails: T,
  ): RestOf<T> {
    return {
      ...voucherPaymentsDetails,
      checkqueDate: voucherPaymentsDetails.checkqueDate?.toJSON() ?? null,
      checkqueExpireDate: voucherPaymentsDetails.checkqueExpireDate?.toJSON() ?? null,
      lmd: voucherPaymentsDetails.lmd?.toJSON() ?? null,
      depositedDate: voucherPaymentsDetails.depositedDate?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restVoucherPaymentsDetails: RestVoucherPaymentsDetails): IVoucherPaymentsDetails {
    return {
      ...restVoucherPaymentsDetails,
      checkqueDate: restVoucherPaymentsDetails.checkqueDate ? dayjs(restVoucherPaymentsDetails.checkqueDate) : undefined,
      checkqueExpireDate: restVoucherPaymentsDetails.checkqueExpireDate ? dayjs(restVoucherPaymentsDetails.checkqueExpireDate) : undefined,
      lmd: restVoucherPaymentsDetails.lmd ? dayjs(restVoucherPaymentsDetails.lmd) : undefined,
      depositedDate: restVoucherPaymentsDetails.depositedDate ? dayjs(restVoucherPaymentsDetails.depositedDate) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestVoucherPaymentsDetails>): HttpResponse<IVoucherPaymentsDetails> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestVoucherPaymentsDetails[]>): HttpResponse<IVoucherPaymentsDetails[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
