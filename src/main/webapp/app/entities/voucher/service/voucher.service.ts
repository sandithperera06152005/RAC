import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, map } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IVoucher, NewVoucher } from '../voucher.model';

export type PartialUpdateVoucher = Partial<IVoucher> & Pick<IVoucher, 'id'>;

type RestOf<T extends IVoucher | NewVoucher> = Omit<T, 'voucherDate' | 'lmd' | 'date'> & {
  voucherDate?: string | null;
  lmd?: string | null;
  date?: string | null;
};

export type RestVoucher = RestOf<IVoucher>;

export type NewRestVoucher = RestOf<NewVoucher>;

export type PartialUpdateRestVoucher = RestOf<PartialUpdateVoucher>;

export type EntityResponseType = HttpResponse<IVoucher>;
export type EntityArrayResponseType = HttpResponse<IVoucher[]>;

@Injectable({ providedIn: 'root' })
export class VoucherService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/vouchers');
  protected resourceUrli = this.applicationConfigService.getEndpointFor('api/sales-invoice-dummies');

  searchaccount(name: string): Observable<any> {
    const url = `${this.applicationConfigService.getEndpointFor('api/accounts')}?name.contains=${name}`;
    return this.http.get<any>(url, { observe: 'response' });
  }

  create(voucher: NewVoucher): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(voucher);
    return this.http
      .post<RestVoucher>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }
  fetchVoucherLines(): Observable<any> {
    const url = `${this.applicationConfigService.getEndpointFor('api/vouchers')}?page=0&size=20&sort=id,desc`;
    return this.http.get<any>(url, { observe: 'response' });
  }

  update(voucher: IVoucher): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(voucher);
    return this.http
      .put<RestVoucher>(`${this.resourceUrl}/${this.getVoucherIdentifier(voucher)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(voucher: PartialUpdateVoucher): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(voucher);
    return this.http
      .patch<RestVoucher>(`${this.resourceUrl}/${this.getVoucherIdentifier(voucher)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestVoucher>(`${this.resourceUrli}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestVoucher[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getVoucherIdentifier(voucher: Pick<IVoucher, 'id'>): number {
    return voucher.id;
  }

  compareVoucher(o1: Pick<IVoucher, 'id'> | null, o2: Pick<IVoucher, 'id'> | null): boolean {
    return o1 && o2 ? this.getVoucherIdentifier(o1) === this.getVoucherIdentifier(o2) : o1 === o2;
  }

  addVoucherToCollectionIfMissing<Type extends Pick<IVoucher, 'id'>>(
    voucherCollection: Type[],
    ...vouchersToCheck: (Type | null | undefined)[]
  ): Type[] {
    const vouchers: Type[] = vouchersToCheck.filter(isPresent);
    if (vouchers.length > 0) {
      const voucherCollectionIdentifiers = voucherCollection.map(voucherItem => this.getVoucherIdentifier(voucherItem));
      const vouchersToAdd = vouchers.filter(voucherItem => {
        const voucherIdentifier = this.getVoucherIdentifier(voucherItem);
        if (voucherCollectionIdentifiers.includes(voucherIdentifier)) {
          return false;
        }
        voucherCollectionIdentifiers.push(voucherIdentifier);
        return true;
      });
      return [...vouchersToAdd, ...voucherCollection];
    }
    return voucherCollection;
  }

  protected convertDateFromClient<T extends IVoucher | NewVoucher | PartialUpdateVoucher>(voucher: T): RestOf<T> {
    return {
      ...voucher,
      voucherDate: voucher.voucherDate?.toJSON() ?? null,
      lmd: voucher.lmd?.toJSON() ?? null,
      date: voucher.date?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restVoucher: RestVoucher): IVoucher {
    return {
      ...restVoucher,
      voucherDate: restVoucher.voucherDate ? dayjs(restVoucher.voucherDate) : undefined,
      lmd: restVoucher.lmd ? dayjs(restVoucher.lmd) : undefined,
      date: restVoucher.date ? dayjs(restVoucher.date) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestVoucher>): HttpResponse<IVoucher> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestVoucher[]>): HttpResponse<IVoucher[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
