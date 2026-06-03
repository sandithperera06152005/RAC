import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, map } from 'rxjs';

import dayjs from 'dayjs/esm';
import { IInventory } from 'app/entities/inventory/inventory.model';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISalesInvoiceLinesDummy, NewSalesInvoiceLinesDummy } from '../sales-invoice-lines-dummy.model';
import { RestInventory } from 'app/entities/inventory/service/inventory.service';
export type PartialUpdateSalesInvoiceLinesDummy = Partial<ISalesInvoiceLinesDummy> & Pick<ISalesInvoiceLinesDummy, 'id'>;

type RestOf<T extends ISalesInvoiceLinesDummy | NewSalesInvoiceLinesDummy> = Omit<T, 'lmd'> & {
  lmd?: string | null;
};

export type RestSalesInvoiceLinesDummy = RestOf<ISalesInvoiceLinesDummy>;

export type NewRestSalesInvoiceLinesDummy = RestOf<NewSalesInvoiceLinesDummy>;

export type PartialUpdateRestSalesInvoiceLinesDummy = RestOf<PartialUpdateSalesInvoiceLinesDummy>;

export type EntityResponseType = HttpResponse<ISalesInvoiceLinesDummy>;
export type EntityArrayResponseType = HttpResponse<ISalesInvoiceLinesDummy[]>;

@Injectable({ providedIn: 'root' })
export class SalesInvoiceLinesDummyService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/sales-invoice-lines-dummies');

  create(salesInvoiceLinesDummy: NewSalesInvoiceLinesDummy): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(salesInvoiceLinesDummy);
    return this.http
      .post<RestSalesInvoiceLinesDummy>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  getElementsByUserInputCode(userInputCode: string): Observable<EntityArrayResponseType> {
    const url = this.applicationConfigService.getEndpointFor(`api/inventories?code.contains=${userInputCode}&page=0&size=20`);
    return this.http
      .get<IInventory[]>(url, { observe: 'response' })
      .pipe(map((res: HttpResponse<IInventory[]>) => this.convertResponseArrayFromServer(res as HttpResponse<RestInventory[]>)));
  }
  update(salesInvoiceLinesDummy: ISalesInvoiceLinesDummy): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(salesInvoiceLinesDummy);
    return this.http
      .put<RestSalesInvoiceLinesDummy>(`${this.resourceUrl}/${this.getSalesInvoiceLinesDummyIdentifier(salesInvoiceLinesDummy)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(salesInvoiceLinesDummy: PartialUpdateSalesInvoiceLinesDummy): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(salesInvoiceLinesDummy);
    return this.http
      .patch<RestSalesInvoiceLinesDummy>(`${this.resourceUrl}/${this.getSalesInvoiceLinesDummyIdentifier(salesInvoiceLinesDummy)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestSalesInvoiceLinesDummy>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestSalesInvoiceLinesDummy[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getSalesInvoiceLinesDummyIdentifier(salesInvoiceLinesDummy: Pick<ISalesInvoiceLinesDummy, 'id'>): number {
    return salesInvoiceLinesDummy.id;
  }

  compareSalesInvoiceLinesDummy(o1: Pick<ISalesInvoiceLinesDummy, 'id'> | null, o2: Pick<ISalesInvoiceLinesDummy, 'id'> | null): boolean {
    return o1 && o2 ? this.getSalesInvoiceLinesDummyIdentifier(o1) === this.getSalesInvoiceLinesDummyIdentifier(o2) : o1 === o2;
  }

  addSalesInvoiceLinesDummyToCollectionIfMissing<Type extends Pick<ISalesInvoiceLinesDummy, 'id'>>(
    salesInvoiceLinesDummyCollection: Type[],
    ...salesInvoiceLinesDummiesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const salesInvoiceLinesDummies: Type[] = salesInvoiceLinesDummiesToCheck.filter(isPresent);
    if (salesInvoiceLinesDummies.length > 0) {
      const salesInvoiceLinesDummyCollectionIdentifiers = salesInvoiceLinesDummyCollection.map(salesInvoiceLinesDummyItem =>
        this.getSalesInvoiceLinesDummyIdentifier(salesInvoiceLinesDummyItem),
      );
      const salesInvoiceLinesDummiesToAdd = salesInvoiceLinesDummies.filter(salesInvoiceLinesDummyItem => {
        const salesInvoiceLinesDummyIdentifier = this.getSalesInvoiceLinesDummyIdentifier(salesInvoiceLinesDummyItem);
        if (salesInvoiceLinesDummyCollectionIdentifiers.includes(salesInvoiceLinesDummyIdentifier)) {
          return false;
        }
        salesInvoiceLinesDummyCollectionIdentifiers.push(salesInvoiceLinesDummyIdentifier);
        return true;
      });
      return [...salesInvoiceLinesDummiesToAdd, ...salesInvoiceLinesDummyCollection];
    }
    return salesInvoiceLinesDummyCollection;
  }

  protected convertDateFromClient<T extends ISalesInvoiceLinesDummy | NewSalesInvoiceLinesDummy | PartialUpdateSalesInvoiceLinesDummy>(
    salesInvoiceLinesDummy: T,
  ): RestOf<T> {
    return {
      ...salesInvoiceLinesDummy,
      lmd: salesInvoiceLinesDummy.lmd?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restSalesInvoiceLinesDummy: RestSalesInvoiceLinesDummy): ISalesInvoiceLinesDummy {
    return {
      ...restSalesInvoiceLinesDummy,
      lmd: restSalesInvoiceLinesDummy.lmd ? dayjs(restSalesInvoiceLinesDummy.lmd) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestSalesInvoiceLinesDummy>): HttpResponse<ISalesInvoiceLinesDummy> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestSalesInvoiceLinesDummy[]>): HttpResponse<ISalesInvoiceLinesDummy[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
