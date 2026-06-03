import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import {
  IAutojobsaleinvoicecommonservicecharge,
  NewAutojobsaleinvoicecommonservicecharge,
} from '../autojobsaleinvoicecommonservicecharge.model';

export type PartialUpdateAutojobsaleinvoicecommonservicecharge = Partial<IAutojobsaleinvoicecommonservicecharge> &
  Pick<IAutojobsaleinvoicecommonservicecharge, 'id'>;

export type EntityResponseType = HttpResponse<IAutojobsaleinvoicecommonservicecharge>;
export type EntityArrayResponseType = HttpResponse<IAutojobsaleinvoicecommonservicecharge[]>;

@Injectable({ providedIn: 'root' })
export class AutojobsaleinvoicecommonservicechargeService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/autojobsaleinvoicecommonservicecharges');

  create(autojobsaleinvoicecommonservicecharge: NewAutojobsaleinvoicecommonservicecharge): Observable<EntityResponseType> {
    return this.http.post<IAutojobsaleinvoicecommonservicecharge>(this.resourceUrl, autojobsaleinvoicecommonservicecharge, {
      observe: 'response',
    });
  }

  update(autojobsaleinvoicecommonservicecharge: IAutojobsaleinvoicecommonservicecharge): Observable<EntityResponseType> {
    return this.http.put<IAutojobsaleinvoicecommonservicecharge>(
      `${this.resourceUrl}/${this.getAutojobsaleinvoicecommonservicechargeIdentifier(autojobsaleinvoicecommonservicecharge)}`,
      autojobsaleinvoicecommonservicecharge,
      { observe: 'response' },
    );
  }

  partialUpdate(autojobsaleinvoicecommonservicecharge: PartialUpdateAutojobsaleinvoicecommonservicecharge): Observable<EntityResponseType> {
    return this.http.patch<IAutojobsaleinvoicecommonservicecharge>(
      `${this.resourceUrl}/${this.getAutojobsaleinvoicecommonservicechargeIdentifier(autojobsaleinvoicecommonservicecharge)}`,
      autojobsaleinvoicecommonservicecharge,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAutojobsaleinvoicecommonservicecharge>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAutojobsaleinvoicecommonservicecharge[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  queryByInvoiceId(invoiceId: number): Observable<HttpResponse<any[]>> {
    return this.http.get<any[]>(`${this.resourceUrl}/invoice/${invoiceId}`, { observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAutojobsaleinvoicecommonservicechargeIdentifier(
    autojobsaleinvoicecommonservicecharge: Pick<IAutojobsaleinvoicecommonservicecharge, 'id'>,
  ): number {
    return autojobsaleinvoicecommonservicecharge.id;
  }

  compareAutojobsaleinvoicecommonservicecharge(
    o1: Pick<IAutojobsaleinvoicecommonservicecharge, 'id'> | null,
    o2: Pick<IAutojobsaleinvoicecommonservicecharge, 'id'> | null,
  ): boolean {
    return o1 && o2
      ? this.getAutojobsaleinvoicecommonservicechargeIdentifier(o1) === this.getAutojobsaleinvoicecommonservicechargeIdentifier(o2)
      : o1 === o2;
  }

  addAutojobsaleinvoicecommonservicechargeToCollectionIfMissing<Type extends Pick<IAutojobsaleinvoicecommonservicecharge, 'id'>>(
    autojobsaleinvoicecommonservicechargeCollection: Type[],
    ...autojobsaleinvoicecommonservicechargesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const autojobsaleinvoicecommonservicecharges: Type[] = autojobsaleinvoicecommonservicechargesToCheck.filter(isPresent);
    if (autojobsaleinvoicecommonservicecharges.length > 0) {
      const autojobsaleinvoicecommonservicechargeCollectionIdentifiers = autojobsaleinvoicecommonservicechargeCollection.map(
        autojobsaleinvoicecommonservicechargeItem =>
          this.getAutojobsaleinvoicecommonservicechargeIdentifier(autojobsaleinvoicecommonservicechargeItem),
      );
      const autojobsaleinvoicecommonservicechargesToAdd = autojobsaleinvoicecommonservicecharges.filter(
        autojobsaleinvoicecommonservicechargeItem => {
          const autojobsaleinvoicecommonservicechargeIdentifier = this.getAutojobsaleinvoicecommonservicechargeIdentifier(
            autojobsaleinvoicecommonservicechargeItem,
          );
          if (autojobsaleinvoicecommonservicechargeCollectionIdentifiers.includes(autojobsaleinvoicecommonservicechargeIdentifier)) {
            return false;
          }
          autojobsaleinvoicecommonservicechargeCollectionIdentifiers.push(autojobsaleinvoicecommonservicechargeIdentifier);
          return true;
        },
      );
      return [...autojobsaleinvoicecommonservicechargesToAdd, ...autojobsaleinvoicecommonservicechargeCollection];
    }
    return autojobsaleinvoicecommonservicechargeCollection;
  }
}
