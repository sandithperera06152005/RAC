import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IInvoiceServiceCharge, NewInvoiceServiceCharge } from '../invoice-service-charge.model';

export type PartialUpdateInvoiceServiceCharge = Partial<IInvoiceServiceCharge> & Pick<IInvoiceServiceCharge, 'id'>;

export type EntityResponseType = HttpResponse<IInvoiceServiceCharge>;
export type EntityArrayResponseType = HttpResponse<IInvoiceServiceCharge[]>;

@Injectable({ providedIn: 'root' })
export class InvoiceServiceChargeService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/invoice-service-charges');

  create(invoiceServiceCharge: NewInvoiceServiceCharge): Observable<EntityResponseType> {
    return this.http.post<IInvoiceServiceCharge>(this.resourceUrl, invoiceServiceCharge, { observe: 'response' });
  }

  update(invoiceServiceCharge: IInvoiceServiceCharge): Observable<EntityResponseType> {
    return this.http.put<IInvoiceServiceCharge>(
      `${this.resourceUrl}/${this.getInvoiceServiceChargeIdentifier(invoiceServiceCharge)}`,
      invoiceServiceCharge,
      { observe: 'response' },
    );
  }

  partialUpdate(invoiceServiceCharge: PartialUpdateInvoiceServiceCharge): Observable<EntityResponseType> {
    return this.http.patch<IInvoiceServiceCharge>(
      `${this.resourceUrl}/${this.getInvoiceServiceChargeIdentifier(invoiceServiceCharge)}`,
      invoiceServiceCharge,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IInvoiceServiceCharge>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IInvoiceServiceCharge[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getInvoiceServiceChargeIdentifier(invoiceServiceCharge: Pick<IInvoiceServiceCharge, 'id'>): number {
    return invoiceServiceCharge.id;
  }

  compareInvoiceServiceCharge(o1: Pick<IInvoiceServiceCharge, 'id'> | null, o2: Pick<IInvoiceServiceCharge, 'id'> | null): boolean {
    return o1 && o2 ? this.getInvoiceServiceChargeIdentifier(o1) === this.getInvoiceServiceChargeIdentifier(o2) : o1 === o2;
  }

  addInvoiceServiceChargeToCollectionIfMissing<Type extends Pick<IInvoiceServiceCharge, 'id'>>(
    invoiceServiceChargeCollection: Type[],
    ...invoiceServiceChargesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const invoiceServiceCharges: Type[] = invoiceServiceChargesToCheck.filter(isPresent);
    if (invoiceServiceCharges.length > 0) {
      const invoiceServiceChargeCollectionIdentifiers = invoiceServiceChargeCollection.map(invoiceServiceChargeItem =>
        this.getInvoiceServiceChargeIdentifier(invoiceServiceChargeItem),
      );
      const invoiceServiceChargesToAdd = invoiceServiceCharges.filter(invoiceServiceChargeItem => {
        const invoiceServiceChargeIdentifier = this.getInvoiceServiceChargeIdentifier(invoiceServiceChargeItem);
        if (invoiceServiceChargeCollectionIdentifiers.includes(invoiceServiceChargeIdentifier)) {
          return false;
        }
        invoiceServiceChargeCollectionIdentifiers.push(invoiceServiceChargeIdentifier);
        return true;
      });
      return [...invoiceServiceChargesToAdd, ...invoiceServiceChargeCollection];
    }
    return invoiceServiceChargeCollection;
  }
}
