import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { IServicesubcategory } from 'app/entities/servicesubcategory/servicesubcategory.model';
import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISaleInvoiceCommonServiceCharge, NewSaleInvoiceCommonServiceCharge } from '../sale-invoice-common-service-charge.model';
import { RestServicesubcategory } from 'app/entities/servicesubcategory/service/servicesubcategory.service';
import dayjs from 'dayjs/esm';
import { map } from 'rxjs/operators';
export type PartialUpdateSaleInvoiceCommonServiceCharge = Partial<ISaleInvoiceCommonServiceCharge> &
  Pick<ISaleInvoiceCommonServiceCharge, 'id'>;
export type RestOf<T> = {
  [P in keyof T]?: T[P];
};
export type EntityResponseType = HttpResponse<ISaleInvoiceCommonServiceCharge>;
export type EntityArrayResponseType = HttpResponse<ISaleInvoiceCommonServiceCharge[]>;

@Injectable({ providedIn: 'root' })
export class SaleInvoiceCommonServiceChargeService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);
  protected convertResponseArrayFromServer(res: HttpResponse<RestServicesubcategory[]>): HttpResponse<IServicesubcategory[]> {
    const body: IServicesubcategory[] = (res.body || []).map(item => ({
      ...item,
      lmd: item.lmd ? dayjs(item.lmd) : undefined,
    }));
    return res.clone({ body });
  }
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/sale-invoice-common-service-charges');

  create(saleInvoiceCommonServiceCharge: NewSaleInvoiceCommonServiceCharge): Observable<EntityResponseType> {
    return this.http.post<ISaleInvoiceCommonServiceCharge>(this.resourceUrl, saleInvoiceCommonServiceCharge, { observe: 'response' });
  }

  update(saleInvoiceCommonServiceCharge: ISaleInvoiceCommonServiceCharge): Observable<EntityResponseType> {
    return this.http.put<ISaleInvoiceCommonServiceCharge>(
      `${this.resourceUrl}/${this.getSaleInvoiceCommonServiceChargeIdentifier(saleInvoiceCommonServiceCharge)}`,
      saleInvoiceCommonServiceCharge,
      { observe: 'response' },
    );
  }
  getElementsByUserInputCode(userInputCode: string): Observable<EntityArrayResponseType> {
    const url = this.applicationConfigService.getEndpointFor(`/api/servicesubcategories?name.contains=${userInputCode}&page=0&size=20`);
    return this.http
      .get<IServicesubcategory[]>(url, { observe: 'response' })
      .pipe(
        map((res: HttpResponse<IServicesubcategory[]>) =>
          this.convertResponseArrayFromServer(res as HttpResponse<RestServicesubcategory[]>),
        ),
      );
  }
  partialUpdate(saleInvoiceCommonServiceCharge: PartialUpdateSaleInvoiceCommonServiceCharge): Observable<EntityResponseType> {
    return this.http.patch<ISaleInvoiceCommonServiceCharge>(
      `${this.resourceUrl}/${this.getSaleInvoiceCommonServiceChargeIdentifier(saleInvoiceCommonServiceCharge)}`,
      saleInvoiceCommonServiceCharge,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISaleInvoiceCommonServiceCharge>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISaleInvoiceCommonServiceCharge[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getSaleInvoiceCommonServiceChargeIdentifier(saleInvoiceCommonServiceCharge: Pick<ISaleInvoiceCommonServiceCharge, 'id'>): number {
    return saleInvoiceCommonServiceCharge.id;
  }

  compareSaleInvoiceCommonServiceCharge(
    o1: Pick<ISaleInvoiceCommonServiceCharge, 'id'> | null,
    o2: Pick<ISaleInvoiceCommonServiceCharge, 'id'> | null,
  ): boolean {
    return o1 && o2
      ? this.getSaleInvoiceCommonServiceChargeIdentifier(o1) === this.getSaleInvoiceCommonServiceChargeIdentifier(o2)
      : o1 === o2;
  }

  addSaleInvoiceCommonServiceChargeToCollectionIfMissing<Type extends Pick<ISaleInvoiceCommonServiceCharge, 'id'>>(
    saleInvoiceCommonServiceChargeCollection: Type[],
    ...saleInvoiceCommonServiceChargesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const saleInvoiceCommonServiceCharges: Type[] = saleInvoiceCommonServiceChargesToCheck.filter(isPresent);
    if (saleInvoiceCommonServiceCharges.length > 0) {
      const saleInvoiceCommonServiceChargeCollectionIdentifiers = saleInvoiceCommonServiceChargeCollection.map(
        saleInvoiceCommonServiceChargeItem => this.getSaleInvoiceCommonServiceChargeIdentifier(saleInvoiceCommonServiceChargeItem),
      );
      const saleInvoiceCommonServiceChargesToAdd = saleInvoiceCommonServiceCharges.filter(saleInvoiceCommonServiceChargeItem => {
        const saleInvoiceCommonServiceChargeIdentifier =
          this.getSaleInvoiceCommonServiceChargeIdentifier(saleInvoiceCommonServiceChargeItem);
        if (saleInvoiceCommonServiceChargeCollectionIdentifiers.includes(saleInvoiceCommonServiceChargeIdentifier)) {
          return false;
        }
        saleInvoiceCommonServiceChargeCollectionIdentifiers.push(saleInvoiceCommonServiceChargeIdentifier);
        return true;
      });
      return [...saleInvoiceCommonServiceChargesToAdd, ...saleInvoiceCommonServiceChargeCollection];
    }
    return saleInvoiceCommonServiceChargeCollection;
  }
}
