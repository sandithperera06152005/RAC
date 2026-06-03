import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ICommonserviceoption } from 'app/entities/commonserviceoption/commonserviceoption.model';
import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import {
  ISaleInvoiceCommonServiceChargeDummy,
  NewSaleInvoiceCommonServiceChargeDummy,
} from '../sale-invoice-common-service-charge-dummy.model';
import { RestCommonserviceoption } from 'app/entities/commonserviceoption/service/commonserviceoption.service';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

export type PartialUpdateSaleInvoiceCommonServiceChargeDummy = Partial<ISaleInvoiceCommonServiceChargeDummy> &
  Pick<ISaleInvoiceCommonServiceChargeDummy, 'id'>;
export type RestOf<T> = {
  [P in keyof T]?: T[P];
};

export type RestSaleInvoiceCommonServiceChargeDummy = RestOf<ISaleInvoiceCommonServiceChargeDummy>;
export type EntityResponseType = HttpResponse<ISaleInvoiceCommonServiceChargeDummy>;
export type EntityArrayResponseType = HttpResponse<ISaleInvoiceCommonServiceChargeDummy[]>;

@Injectable({ providedIn: 'root' })
export class SaleInvoiceCommonServiceChargeDummyService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);
  protected convertResponseArrayFromServer(res: HttpResponse<RestCommonserviceoption[]>): HttpResponse<ICommonserviceoption[]> {
    const body: ICommonserviceoption[] = (res.body || []).map(item => ({
      ...item,
      lmd: item.lmd ? dayjs(item.lmd) : undefined,
    }));
    return res.clone({ body });
  }

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/sale-invoice-common-service-charge-dummies');

  create(saleInvoiceCommonServiceChargeDummy: NewSaleInvoiceCommonServiceChargeDummy): Observable<EntityResponseType> {
    return this.http.post<ISaleInvoiceCommonServiceChargeDummy>(this.resourceUrl, saleInvoiceCommonServiceChargeDummy, {
      observe: 'response',
    });
  }
  getElementsByUserInputCode(): Observable<HttpResponse<ICommonserviceoption[]>> {
    const url = this.applicationConfigService.getEndpointFor(`/api/commonserviceoptions?page=0&size=20`);
    return this.http
      .get<ICommonserviceoption[]>(url, { observe: 'response' })
      .pipe(
        map((res: HttpResponse<ICommonserviceoption[]>) =>
          this.convertResponseArrayFromServer(res as HttpResponse<RestCommonserviceoption[]>),
        ),
      );
  }
  update(saleInvoiceCommonServiceChargeDummy: ISaleInvoiceCommonServiceChargeDummy): Observable<EntityResponseType> {
    return this.http.put<ISaleInvoiceCommonServiceChargeDummy>(
      `${this.resourceUrl}/${this.getSaleInvoiceCommonServiceChargeDummyIdentifier(saleInvoiceCommonServiceChargeDummy)}`,
      saleInvoiceCommonServiceChargeDummy,
      { observe: 'response' },
    );
  }

  partialUpdate(saleInvoiceCommonServiceChargeDummy: PartialUpdateSaleInvoiceCommonServiceChargeDummy): Observable<EntityResponseType> {
    return this.http.patch<ISaleInvoiceCommonServiceChargeDummy>(
      `${this.resourceUrl}/${this.getSaleInvoiceCommonServiceChargeDummyIdentifier(saleInvoiceCommonServiceChargeDummy)}`,
      saleInvoiceCommonServiceChargeDummy,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISaleInvoiceCommonServiceChargeDummy>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISaleInvoiceCommonServiceChargeDummy[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getSaleInvoiceCommonServiceChargeDummyIdentifier(
    saleInvoiceCommonServiceChargeDummy: Pick<ISaleInvoiceCommonServiceChargeDummy, 'id'>,
  ): number {
    return saleInvoiceCommonServiceChargeDummy.id;
  }

  compareSaleInvoiceCommonServiceChargeDummy(
    o1: Pick<ISaleInvoiceCommonServiceChargeDummy, 'id'> | null,
    o2: Pick<ISaleInvoiceCommonServiceChargeDummy, 'id'> | null,
  ): boolean {
    return o1 && o2
      ? this.getSaleInvoiceCommonServiceChargeDummyIdentifier(o1) === this.getSaleInvoiceCommonServiceChargeDummyIdentifier(o2)
      : o1 === o2;
  }

  addSaleInvoiceCommonServiceChargeDummyToCollectionIfMissing<Type extends Pick<ISaleInvoiceCommonServiceChargeDummy, 'id'>>(
    saleInvoiceCommonServiceChargeDummyCollection: Type[],
    ...saleInvoiceCommonServiceChargeDummiesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const saleInvoiceCommonServiceChargeDummies: Type[] = saleInvoiceCommonServiceChargeDummiesToCheck.filter(isPresent);
    if (saleInvoiceCommonServiceChargeDummies.length > 0) {
      const saleInvoiceCommonServiceChargeDummyCollectionIdentifiers = saleInvoiceCommonServiceChargeDummyCollection.map(
        saleInvoiceCommonServiceChargeDummyItem =>
          this.getSaleInvoiceCommonServiceChargeDummyIdentifier(saleInvoiceCommonServiceChargeDummyItem),
      );
      const saleInvoiceCommonServiceChargeDummiesToAdd = saleInvoiceCommonServiceChargeDummies.filter(
        saleInvoiceCommonServiceChargeDummyItem => {
          const saleInvoiceCommonServiceChargeDummyIdentifier = this.getSaleInvoiceCommonServiceChargeDummyIdentifier(
            saleInvoiceCommonServiceChargeDummyItem,
          );
          if (saleInvoiceCommonServiceChargeDummyCollectionIdentifiers.includes(saleInvoiceCommonServiceChargeDummyIdentifier)) {
            return false;
          }
          saleInvoiceCommonServiceChargeDummyCollectionIdentifiers.push(saleInvoiceCommonServiceChargeDummyIdentifier);
          return true;
        },
      );
      return [...saleInvoiceCommonServiceChargeDummiesToAdd, ...saleInvoiceCommonServiceChargeDummyCollection];
    }
    return saleInvoiceCommonServiceChargeDummyCollection;
  }
}
