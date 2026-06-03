import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IInvoiceServiceCommon, NewInvoiceServiceCommon } from '../invoice-service-common.model';

export type PartialUpdateInvoiceServiceCommon = Partial<IInvoiceServiceCommon> & Pick<IInvoiceServiceCommon, 'id'>;

export type EntityResponseType = HttpResponse<IInvoiceServiceCommon>;
export type EntityArrayResponseType = HttpResponse<IInvoiceServiceCommon[]>;

@Injectable({ providedIn: 'root' })
export class InvoiceServiceCommonService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/invoice-service-commons');

  create(invoiceServiceCommon: NewInvoiceServiceCommon): Observable<EntityResponseType> {
    return this.http.post<IInvoiceServiceCommon>(this.resourceUrl, invoiceServiceCommon, { observe: 'response' });
  }

  update(invoiceServiceCommon: IInvoiceServiceCommon): Observable<EntityResponseType> {
    return this.http.put<IInvoiceServiceCommon>(
      `${this.resourceUrl}/${this.getInvoiceServiceCommonIdentifier(invoiceServiceCommon)}`,
      invoiceServiceCommon,
      { observe: 'response' },
    );
  }

  partialUpdate(invoiceServiceCommon: PartialUpdateInvoiceServiceCommon): Observable<EntityResponseType> {
    return this.http.patch<IInvoiceServiceCommon>(
      `${this.resourceUrl}/${this.getInvoiceServiceCommonIdentifier(invoiceServiceCommon)}`,
      invoiceServiceCommon,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IInvoiceServiceCommon>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IInvoiceServiceCommon[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getInvoiceServiceCommonIdentifier(invoiceServiceCommon: Pick<IInvoiceServiceCommon, 'id'>): number {
    return invoiceServiceCommon.id;
  }

  compareInvoiceServiceCommon(o1: Pick<IInvoiceServiceCommon, 'id'> | null, o2: Pick<IInvoiceServiceCommon, 'id'> | null): boolean {
    return o1 && o2 ? this.getInvoiceServiceCommonIdentifier(o1) === this.getInvoiceServiceCommonIdentifier(o2) : o1 === o2;
  }

  addInvoiceServiceCommonToCollectionIfMissing<Type extends Pick<IInvoiceServiceCommon, 'id'>>(
    invoiceServiceCommonCollection: Type[],
    ...invoiceServiceCommonsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const invoiceServiceCommons: Type[] = invoiceServiceCommonsToCheck.filter(isPresent);
    if (invoiceServiceCommons.length > 0) {
      const invoiceServiceCommonCollectionIdentifiers = invoiceServiceCommonCollection.map(invoiceServiceCommonItem =>
        this.getInvoiceServiceCommonIdentifier(invoiceServiceCommonItem),
      );
      const invoiceServiceCommonsToAdd = invoiceServiceCommons.filter(invoiceServiceCommonItem => {
        const invoiceServiceCommonIdentifier = this.getInvoiceServiceCommonIdentifier(invoiceServiceCommonItem);
        if (invoiceServiceCommonCollectionIdentifiers.includes(invoiceServiceCommonIdentifier)) {
          return false;
        }
        invoiceServiceCommonCollectionIdentifiers.push(invoiceServiceCommonIdentifier);
        return true;
      });
      return [...invoiceServiceCommonsToAdd, ...invoiceServiceCommonCollection];
    }
    return invoiceServiceCommonCollection;
  }
}
