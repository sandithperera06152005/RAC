import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, map } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISalesInvoiceLines, NewSalesInvoiceLines } from '../sales-invoice-lines.model';
import { IInventory } from 'app/entities/inventory/inventory.model';
import { RestInventory } from 'app/entities/inventory/service/inventory.service';
export type PartialUpdateSalesInvoiceLines = Partial<ISalesInvoiceLines> & Pick<ISalesInvoiceLines, 'id'>;

type RestOf<T extends ISalesInvoiceLines | NewSalesInvoiceLines> = Omit<T, 'lmd'> & {
  lmd?: string | null;
};

export type RestSalesInvoiceLines = RestOf<ISalesInvoiceLines>;

export type NewRestSalesInvoiceLines = RestOf<NewSalesInvoiceLines>;

export type PartialUpdateRestSalesInvoiceLines = RestOf<PartialUpdateSalesInvoiceLines>;

export type EntityResponseType = HttpResponse<ISalesInvoiceLines>;
export type EntityArrayResponseType = HttpResponse<ISalesInvoiceLines[]>;

@Injectable({ providedIn: 'root' })
export class SalesInvoiceLinesService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  private totalinvoicelines: number = 0;
  gettotalinvoicelines(): number {
    return this.totalinvoicelines;
  }
  settotalinvoicelines(val: number): void {
    this.totalinvoicelines = val;
  }
  private profit: number = 0;
  getprofit(): number {
    return this.profit;
  }
  setprofit(val: number): void {
    this.profit = val;
  }
  private subId: string = '';
  setSubId(id: string): void {
    this.subId = id;
  }
  getSubId(): string {
    return this.subId;
  }

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/sales-invoice-lines');

  create(salesInvoiceLines: NewSalesInvoiceLines): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(salesInvoiceLines);
    return this.http
      .post<RestSalesInvoiceLines>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }
  getElementsByUserInputCode(userInputCode: string): Observable<EntityArrayResponseType> {
    const url = this.applicationConfigService.getEndpointFor(`api/inventories?code.contains=${userInputCode}&page=0&size=20`);
    return this.http
      .get<IInventory[]>(url, { observe: 'response' })
      .pipe(map((res: HttpResponse<IInventory[]>) => this.convertResponseArrayFromServer(res as HttpResponse<RestInventory[]>)));
  }
  getElementsByUserInputName(userInputCode: string): Observable<EntityArrayResponseType> {
    const url = this.applicationConfigService.getEndpointFor(`api/inventories?name.contains=${userInputCode}&page=0&size=20`);
    return this.http
      .get<IInventory[]>(url, { observe: 'response' })
      .pipe(map((res: HttpResponse<IInventory[]>) => this.convertResponseArrayFromServer(res as HttpResponse<RestInventory[]>)));
  }
  update(salesInvoiceLines: ISalesInvoiceLines): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(salesInvoiceLines);
    return this.http
      .put<RestSalesInvoiceLines>(`${this.resourceUrl}/${this.getSalesInvoiceLinesIdentifier(salesInvoiceLines)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(salesInvoiceLines: PartialUpdateSalesInvoiceLines): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(salesInvoiceLines);
    return this.http
      .patch<RestSalesInvoiceLines>(`${this.resourceUrl}/${this.getSalesInvoiceLinesIdentifier(salesInvoiceLines)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestSalesInvoiceLines>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestSalesInvoiceLines[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  queryByInvoiceId(invoiceId: number): Observable<EntityArrayResponseType> {
    return this.http
      .get<RestSalesInvoiceLines[]>(`${this.resourceUrl}/invoice/${invoiceId}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getSalesInvoiceLinesIdentifier(salesInvoiceLines: Pick<ISalesInvoiceLines, 'id'>): number {
    return salesInvoiceLines.id;
  }

  compareSalesInvoiceLines(o1: Pick<ISalesInvoiceLines, 'id'> | null, o2: Pick<ISalesInvoiceLines, 'id'> | null): boolean {
    return o1 && o2 ? this.getSalesInvoiceLinesIdentifier(o1) === this.getSalesInvoiceLinesIdentifier(o2) : o1 === o2;
  }

  addSalesInvoiceLinesToCollectionIfMissing<Type extends Pick<ISalesInvoiceLines, 'id'>>(
    salesInvoiceLinesCollection: Type[],
    ...salesInvoiceLinesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const salesInvoiceLines: Type[] = salesInvoiceLinesToCheck.filter(isPresent);
    if (salesInvoiceLines.length > 0) {
      const salesInvoiceLinesCollectionIdentifiers = salesInvoiceLinesCollection.map(salesInvoiceLinesItem =>
        this.getSalesInvoiceLinesIdentifier(salesInvoiceLinesItem),
      );
      const salesInvoiceLinesToAdd = salesInvoiceLines.filter(salesInvoiceLinesItem => {
        const salesInvoiceLinesIdentifier = this.getSalesInvoiceLinesIdentifier(salesInvoiceLinesItem);
        if (salesInvoiceLinesCollectionIdentifiers.includes(salesInvoiceLinesIdentifier)) {
          return false;
        }
        salesInvoiceLinesCollectionIdentifiers.push(salesInvoiceLinesIdentifier);
        return true;
      });
      return [...salesInvoiceLinesToAdd, ...salesInvoiceLinesCollection];
    }
    return salesInvoiceLinesCollection;
  }

  protected convertDateFromClient<T extends ISalesInvoiceLines | NewSalesInvoiceLines | PartialUpdateSalesInvoiceLines>(
    salesInvoiceLines: T,
  ): RestOf<T> {
    return {
      ...salesInvoiceLines,
      lmd: salesInvoiceLines.lmd?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restSalesInvoiceLines: RestSalesInvoiceLines): ISalesInvoiceLines {
    return {
      ...restSalesInvoiceLines,
      lmd: restSalesInvoiceLines.lmd ? dayjs(restSalesInvoiceLines.lmd) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestSalesInvoiceLines>): HttpResponse<ISalesInvoiceLines> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestSalesInvoiceLines[]>): HttpResponse<ISalesInvoiceLines[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
