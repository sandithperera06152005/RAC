import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { IBillingserviceoption } from 'app/entities/billingserviceoption/billingserviceoption.model';
import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISalesInvoiceServiceChargeLine, NewSalesInvoiceServiceChargeLine } from '../sales-invoice-service-charge-line.model';
import { RestBillingserviceoption } from 'app/entities/billingserviceoption/service/billingserviceoption.service';
import dayjs from 'dayjs/esm';
import { map } from 'rxjs/operators';
export type PartialUpdateSalesInvoiceServiceChargeLine = Partial<ISalesInvoiceServiceChargeLine> &
  Pick<ISalesInvoiceServiceChargeLine, 'id'>;

export type EntityResponseType = HttpResponse<ISalesInvoiceServiceChargeLine>;
export type EntityArrayResponseType = HttpResponse<ISalesInvoiceServiceChargeLine[]>;

@Injectable({ providedIn: 'root' })
export class SalesInvoiceServiceChargeLineService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);
  protected convertResponseArrayFromServer(res: HttpResponse<RestBillingserviceoption[]>): HttpResponse<IBillingserviceoption[]> {
    const body: IBillingserviceoption[] = (res.body || []).map(item => ({
      ...item,
      lmd: item.lmd ? dayjs(item.lmd) : undefined,
    }));
    return res.clone({ body });
  }
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/sales-invoice-service-charge-lines');

  create(salesInvoiceServiceChargeLine: NewSalesInvoiceServiceChargeLine): Observable<EntityResponseType> {
    return this.http.post<ISalesInvoiceServiceChargeLine>(this.resourceUrl, salesInvoiceServiceChargeLine, { observe: 'response' });
  }
  getElementsByUserInputCode(userInputCode: string): Observable<EntityArrayResponseType> {
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
  update(salesInvoiceServiceChargeLine: ISalesInvoiceServiceChargeLine): Observable<EntityResponseType> {
    return this.http.put<ISalesInvoiceServiceChargeLine>(
      `${this.resourceUrl}/${this.getSalesInvoiceServiceChargeLineIdentifier(salesInvoiceServiceChargeLine)}`,
      salesInvoiceServiceChargeLine,
      { observe: 'response' },
    );
  }

  partialUpdate(salesInvoiceServiceChargeLine: PartialUpdateSalesInvoiceServiceChargeLine): Observable<EntityResponseType> {
    return this.http.patch<ISalesInvoiceServiceChargeLine>(
      `${this.resourceUrl}/${this.getSalesInvoiceServiceChargeLineIdentifier(salesInvoiceServiceChargeLine)}`,
      salesInvoiceServiceChargeLine,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISalesInvoiceServiceChargeLine>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISalesInvoiceServiceChargeLine[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getSalesInvoiceServiceChargeLineIdentifier(salesInvoiceServiceChargeLine: Pick<ISalesInvoiceServiceChargeLine, 'id'>): number {
    return salesInvoiceServiceChargeLine.id;
  }

  compareSalesInvoiceServiceChargeLine(
    o1: Pick<ISalesInvoiceServiceChargeLine, 'id'> | null,
    o2: Pick<ISalesInvoiceServiceChargeLine, 'id'> | null,
  ): boolean {
    return o1 && o2
      ? this.getSalesInvoiceServiceChargeLineIdentifier(o1) === this.getSalesInvoiceServiceChargeLineIdentifier(o2)
      : o1 === o2;
  }

  addSalesInvoiceServiceChargeLineToCollectionIfMissing<Type extends Pick<ISalesInvoiceServiceChargeLine, 'id'>>(
    salesInvoiceServiceChargeLineCollection: Type[],
    ...salesInvoiceServiceChargeLinesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const salesInvoiceServiceChargeLines: Type[] = salesInvoiceServiceChargeLinesToCheck.filter(isPresent);
    if (salesInvoiceServiceChargeLines.length > 0) {
      const salesInvoiceServiceChargeLineCollectionIdentifiers = salesInvoiceServiceChargeLineCollection.map(
        salesInvoiceServiceChargeLineItem => this.getSalesInvoiceServiceChargeLineIdentifier(salesInvoiceServiceChargeLineItem),
      );
      const salesInvoiceServiceChargeLinesToAdd = salesInvoiceServiceChargeLines.filter(salesInvoiceServiceChargeLineItem => {
        const salesInvoiceServiceChargeLineIdentifier = this.getSalesInvoiceServiceChargeLineIdentifier(salesInvoiceServiceChargeLineItem);
        if (salesInvoiceServiceChargeLineCollectionIdentifiers.includes(salesInvoiceServiceChargeLineIdentifier)) {
          return false;
        }
        salesInvoiceServiceChargeLineCollectionIdentifiers.push(salesInvoiceServiceChargeLineIdentifier);
        return true;
      });
      return [...salesInvoiceServiceChargeLinesToAdd, ...salesInvoiceServiceChargeLineCollection];
    }
    return salesInvoiceServiceChargeLineCollection;
  }
}
