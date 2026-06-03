import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { IBillingserviceoption } from 'app/entities/billingserviceoption/billingserviceoption.model';
import { IBillingserviceoptionvalues } from 'app/entities/billingserviceoptionvalues/billingserviceoptionvalues.model';
import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import {
  ISalesInvoiceServiceChargeLineDummy,
  NewSalesInvoiceServiceChargeLineDummy,
} from '../sales-invoice-service-charge-line-dummy.model';
import { RestBillingserviceoption } from 'app/entities/billingserviceoption/service/billingserviceoption.service';
import { RestBillingserviceoptionvalues } from 'app/entities/billingserviceoptionvalues/service/billingserviceoptionvalues.service';
import dayjs from 'dayjs/esm';
import { map } from 'rxjs/operators';
export type PartialUpdateSalesInvoiceServiceChargeLineDummy = Partial<ISalesInvoiceServiceChargeLineDummy> &
  Pick<ISalesInvoiceServiceChargeLineDummy, 'id'>;

export type EntityResponseType = HttpResponse<ISalesInvoiceServiceChargeLineDummy>;
export type EntityArrayResponseType = HttpResponse<ISalesInvoiceServiceChargeLineDummy[]>;

@Injectable({ providedIn: 'root' })
export class SalesInvoiceServiceChargeLineDummyService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);
  protected convertResponseArrayFromServer(res: HttpResponse<RestBillingserviceoption[]>): HttpResponse<IBillingserviceoption[]> {
    const body: IBillingserviceoption[] = (res.body || []).map(item => ({
      ...item,
      lmd: item.lmd ? dayjs(item.lmd) : undefined,
    }));
    return res.clone({ body });
  }
  protected convertResponseArrayFromServe(
    res: HttpResponse<RestBillingserviceoptionvalues[]>,
  ): HttpResponse<IBillingserviceoptionvalues[]> {
    const body: IBillingserviceoptionvalues[] = (res.body || []).map(item => ({
      ...item,
      lmd: item.lmd ? dayjs(item.lmd) : undefined,
    }));
    return res.clone({ body });
  }
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/sales-invoice-service-charge-line-dummies');

  create(salesInvoiceServiceChargeLineDummy: NewSalesInvoiceServiceChargeLineDummy): Observable<EntityResponseType> {
    return this.http.post<ISalesInvoiceServiceChargeLineDummy>(this.resourceUrl, salesInvoiceServiceChargeLineDummy, {
      observe: 'response',
    });
  }
  getElementsByUserInputCode(userInputCode: string): Observable<HttpResponse<IBillingserviceoption[]>> {
    const url = this.applicationConfigService.getEndpointFor(
      `/api/billingserviceoptions?servicename.contains=${userInputCode}&page=0&size=20`,
    );
    return this.http
      .get<IBillingserviceoption[]>(url, { observe: 'response' })
      .pipe(
        map((res: HttpResponse<IBillingserviceoption[]>) =>
          this.convertResponseArrayFromServer(res as HttpResponse<RestBillingserviceoption[]>),
        ),
      );
  }

  getElementsByID(typeid: number): Observable<HttpResponse<IBillingserviceoptionvalues[]>> {
    const url = this.applicationConfigService.getEndpointFor(
      `/api/billingserviceoptionvalues?vehicletypeid.equals=${typeid}&page=0&size=20`,
    );
    return this.http
      .get<IBillingserviceoptionvalues[]>(url, { observe: 'response' })
      .pipe(
        map((res: HttpResponse<IBillingserviceoptionvalues[]>) =>
          this.convertResponseArrayFromServe(res as HttpResponse<RestBillingserviceoptionvalues[]>),
        ),
      );
  }
  getbillingid(id: number): Observable<HttpResponse<IBillingserviceoption[]>> {
    const url = this.applicationConfigService.getEndpointFor(`/api/billingserviceoptions/${id}`);
    return this.http.get<IBillingserviceoption[]>(url, { observe: 'response' }).pipe(
      map((res: HttpResponse<IBillingserviceoption[]>) => {
        // Check if the response body is an object instead of an array
        if (res.body && Array.isArray(res.body)) {
          return this.convertResponseArrayFromServer(res as HttpResponse<RestBillingserviceoption[]>);
        } else if (res.body && typeof res.body === 'object') {
          // If it's an object, wrap it in an array
          return res.clone({ body: [res.body] });
        } else {
          console.error('Expected an object or array but received:', res.body);
          return res.clone({ body: [] }); // Return an empty HttpResponse as a fallback
        }
      }),
    );
  }
  biliingvalues(serviceid: number, typeid: number): Observable<HttpResponse<IBillingserviceoptionvalues[]>> {
    const url = this.applicationConfigService.getEndpointFor(
      `/api/billingserviceoptionvalues?billingserviceoptionid.equals=${serviceid}&vehicletypeid.equals=${typeid}&page=0&size=20`,
    );
    return this.http
      .get<IBillingserviceoptionvalues[]>(url, { observe: 'response' })
      .pipe(
        map((res: HttpResponse<IBillingserviceoptionvalues[]>) =>
          this.convertResponseArrayFromServe(res as HttpResponse<RestBillingserviceoptionvalues[]>),
        ),
      );
  }

  update(salesInvoiceServiceChargeLineDummy: ISalesInvoiceServiceChargeLineDummy): Observable<EntityResponseType> {
    return this.http.put<ISalesInvoiceServiceChargeLineDummy>(
      `${this.resourceUrl}/${this.getSalesInvoiceServiceChargeLineDummyIdentifier(salesInvoiceServiceChargeLineDummy)}`,
      salesInvoiceServiceChargeLineDummy,
      { observe: 'response' },
    );
  }

  partialUpdate(salesInvoiceServiceChargeLineDummy: PartialUpdateSalesInvoiceServiceChargeLineDummy): Observable<EntityResponseType> {
    return this.http.patch<ISalesInvoiceServiceChargeLineDummy>(
      `${this.resourceUrl}/${this.getSalesInvoiceServiceChargeLineDummyIdentifier(salesInvoiceServiceChargeLineDummy)}`,
      salesInvoiceServiceChargeLineDummy,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISalesInvoiceServiceChargeLineDummy>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISalesInvoiceServiceChargeLineDummy[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getSalesInvoiceServiceChargeLineDummyIdentifier(
    salesInvoiceServiceChargeLineDummy: Pick<ISalesInvoiceServiceChargeLineDummy, 'id'>,
  ): number {
    return salesInvoiceServiceChargeLineDummy.id;
  }

  compareSalesInvoiceServiceChargeLineDummy(
    o1: Pick<ISalesInvoiceServiceChargeLineDummy, 'id'> | null,
    o2: Pick<ISalesInvoiceServiceChargeLineDummy, 'id'> | null,
  ): boolean {
    return o1 && o2
      ? this.getSalesInvoiceServiceChargeLineDummyIdentifier(o1) === this.getSalesInvoiceServiceChargeLineDummyIdentifier(o2)
      : o1 === o2;
  }

  addSalesInvoiceServiceChargeLineDummyToCollectionIfMissing<Type extends Pick<ISalesInvoiceServiceChargeLineDummy, 'id'>>(
    salesInvoiceServiceChargeLineDummyCollection: Type[],
    ...salesInvoiceServiceChargeLineDummiesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const salesInvoiceServiceChargeLineDummies: Type[] = salesInvoiceServiceChargeLineDummiesToCheck.filter(isPresent);
    if (salesInvoiceServiceChargeLineDummies.length > 0) {
      const salesInvoiceServiceChargeLineDummyCollectionIdentifiers = salesInvoiceServiceChargeLineDummyCollection.map(
        salesInvoiceServiceChargeLineDummyItem =>
          this.getSalesInvoiceServiceChargeLineDummyIdentifier(salesInvoiceServiceChargeLineDummyItem),
      );
      const salesInvoiceServiceChargeLineDummiesToAdd = salesInvoiceServiceChargeLineDummies.filter(
        salesInvoiceServiceChargeLineDummyItem => {
          const salesInvoiceServiceChargeLineDummyIdentifier = this.getSalesInvoiceServiceChargeLineDummyIdentifier(
            salesInvoiceServiceChargeLineDummyItem,
          );
          if (salesInvoiceServiceChargeLineDummyCollectionIdentifiers.includes(salesInvoiceServiceChargeLineDummyIdentifier)) {
            return false;
          }
          salesInvoiceServiceChargeLineDummyCollectionIdentifiers.push(salesInvoiceServiceChargeLineDummyIdentifier);
          return true;
        },
      );
      return [...salesInvoiceServiceChargeLineDummiesToAdd, ...salesInvoiceServiceChargeLineDummyCollection];
    }
    return salesInvoiceServiceChargeLineDummyCollection;
  }
}
