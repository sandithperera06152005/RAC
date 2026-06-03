import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, map } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISalesInvoiceDummy, NewSalesInvoiceDummy } from '../sales-invoice-dummy.model';

export type PartialUpdateSalesInvoiceDummy = Partial<ISalesInvoiceDummy> & Pick<ISalesInvoiceDummy, 'id'>;

type RestOf<T extends ISalesInvoiceDummy | NewSalesInvoiceDummy> = Omit<
  T,
  'invoiceDate' | 'createdDate' | 'deliveryDate' | 'lmd' | 'commissionPaidDate'
> & {
  invoiceDate?: string | null;
  createdDate?: string | null;
  deliveryDate?: string | null;
  lmd?: string | null;
  commissionPaidDate?: string | null;
};

export type RestSalesInvoiceDummy = RestOf<ISalesInvoiceDummy>;

export type NewRestSalesInvoiceDummy = RestOf<NewSalesInvoiceDummy>;

export type PartialUpdateRestSalesInvoiceDummy = RestOf<PartialUpdateSalesInvoiceDummy>;

export type EntityResponseType = HttpResponse<ISalesInvoiceDummy>;
export type EntityArrayResponseType = HttpResponse<ISalesInvoiceDummy[]>;

@Injectable({ providedIn: 'root' })
export class SalesInvoiceDummyService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/sales-invoice-dummies');
  protected resourceUrli = this.applicationConfigService.getEndpointFor('api/salesinvoices');
  protected resourceInvoiceLinesUrl = this.applicationConfigService.getEndpointFor('api/sales-invoice-lines'); // Set the endpoint for invoice lines
  protected resourceInvoiceLinesUrli = this.applicationConfigService.getEndpointFor('api/sales-invoice-service-charge-lines');

  protected resourceInvoiceLinesUrlsercom = this.applicationConfigService.getEndpointFor('api/sale-invoice-common-service-charges');
  protected resourceInvoiceLinesUrlsercomd = this.applicationConfigService.getEndpointFor(
    '/api/sale-invoice-common-service-charge-dummies',
  );

  fetchService(id: number): Observable<HttpResponse<any>> {
    return this.http.get<any>(`${this.resourceInvoiceLinesUrli}/invoice/${id}`, { observe: 'response' });
  }

  fetchServiceCommon(id: number): Observable<HttpResponse<any>> {
    return this.http.get<any>(`${this.resourceInvoiceLinesUrlsercom}/invoice/${id}`, { observe: 'response' });
  }

  fetchServiceCommondummy(id: number): Observable<HttpResponse<any>> {
    const options = createRequestOption({ 'invoiceid.equals': id });
    return this.http.get<any>(`${this.resourceInvoiceLinesUrlsercomd}`, { params: options, observe: 'response' });
  }

  fetchInvoiceLines(id: number): Observable<HttpResponse<any>> {
    return this.http.get<any>(`${this.resourceInvoiceLinesUrl}/invoice/${id}`, { observe: 'response' });
  }
  create(salesInvoiceDummy: NewSalesInvoiceDummy): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(salesInvoiceDummy);
    return this.http
      .post<RestSalesInvoiceDummy>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(salesInvoiceDummy: ISalesInvoiceDummy): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(salesInvoiceDummy);
    return this.http
      .put<RestSalesInvoiceDummy>(`${this.resourceUrl}/${this.getSalesInvoiceDummyIdentifier(salesInvoiceDummy)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(salesInvoiceDummy: PartialUpdateSalesInvoiceDummy): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(salesInvoiceDummy);
    return this.http
      .patch<RestSalesInvoiceDummy>(`${this.resourceUrl}/${this.getSalesInvoiceDummyIdentifier(salesInvoiceDummy)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestSalesInvoiceDummy>(`${this.resourceUrli}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestSalesInvoiceDummy[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getSalesInvoiceDummyIdentifier(salesInvoiceDummy: Pick<ISalesInvoiceDummy, 'id'>): number {
    return salesInvoiceDummy.id;
  }

  compareSalesInvoiceDummy(o1: Pick<ISalesInvoiceDummy, 'id'> | null, o2: Pick<ISalesInvoiceDummy, 'id'> | null): boolean {
    return o1 && o2 ? this.getSalesInvoiceDummyIdentifier(o1) === this.getSalesInvoiceDummyIdentifier(o2) : o1 === o2;
  }

  addSalesInvoiceDummyToCollectionIfMissing<Type extends Pick<ISalesInvoiceDummy, 'id'>>(
    salesInvoiceDummyCollection: Type[],
    ...salesInvoiceDummiesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const salesInvoiceDummies: Type[] = salesInvoiceDummiesToCheck.filter(isPresent);
    if (salesInvoiceDummies.length > 0) {
      const salesInvoiceDummyCollectionIdentifiers = salesInvoiceDummyCollection.map(salesInvoiceDummyItem =>
        this.getSalesInvoiceDummyIdentifier(salesInvoiceDummyItem),
      );
      const salesInvoiceDummiesToAdd = salesInvoiceDummies.filter(salesInvoiceDummyItem => {
        const salesInvoiceDummyIdentifier = this.getSalesInvoiceDummyIdentifier(salesInvoiceDummyItem);
        if (salesInvoiceDummyCollectionIdentifiers.includes(salesInvoiceDummyIdentifier)) {
          return false;
        }
        salesInvoiceDummyCollectionIdentifiers.push(salesInvoiceDummyIdentifier);
        return true;
      });
      return [...salesInvoiceDummiesToAdd, ...salesInvoiceDummyCollection];
    }
    return salesInvoiceDummyCollection;
  }

  protected convertDateFromClient<T extends ISalesInvoiceDummy | NewSalesInvoiceDummy>(salesInvoiceDummy: T): RestOf<T> {
    return {
      ...salesInvoiceDummy,
      invoiceDate: salesInvoiceDummy.invoiceDate?.toJSON() ?? null,
      createdDate: salesInvoiceDummy.createdDate?.toJSON() ?? null,
      deliveryDate: salesInvoiceDummy.deliveryDate?.toJSON() ?? null,
      lmd: salesInvoiceDummy.lmd?.toJSON() ?? null,
      commissionPaidDate: salesInvoiceDummy.commissionPaidDate?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restSalesInvoiceDummy: RestSalesInvoiceDummy): ISalesInvoiceDummy {
    return {
      ...restSalesInvoiceDummy,
      invoiceDate: restSalesInvoiceDummy.invoiceDate ? dayjs(restSalesInvoiceDummy.invoiceDate) : undefined,
      createdDate: restSalesInvoiceDummy.createdDate ? dayjs(restSalesInvoiceDummy.createdDate) : undefined,
      deliveryDate: restSalesInvoiceDummy.deliveryDate ? dayjs(restSalesInvoiceDummy.deliveryDate) : undefined,
      lmd: restSalesInvoiceDummy.lmd ? dayjs(restSalesInvoiceDummy.lmd) : undefined,
      commissionPaidDate: restSalesInvoiceDummy.commissionPaidDate ? dayjs(restSalesInvoiceDummy.commissionPaidDate) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestSalesInvoiceDummy>): HttpResponse<ISalesInvoiceDummy> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestSalesInvoiceDummy[]>): HttpResponse<ISalesInvoiceDummy[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
