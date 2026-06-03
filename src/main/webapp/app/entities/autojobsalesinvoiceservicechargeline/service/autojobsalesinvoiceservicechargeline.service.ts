import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import {
  IAutojobsalesinvoiceservicechargeline,
  NewAutojobsalesinvoiceservicechargeline,
} from '../autojobsalesinvoiceservicechargeline.model';

export type PartialUpdateAutojobsalesinvoiceservicechargeline = Partial<IAutojobsalesinvoiceservicechargeline> &
  Pick<IAutojobsalesinvoiceservicechargeline, 'id'>;

export type EntityResponseType = HttpResponse<IAutojobsalesinvoiceservicechargeline>;
export type EntityArrayResponseType = HttpResponse<IAutojobsalesinvoiceservicechargeline[]>;

@Injectable({ providedIn: 'root' })
export class AutojobsalesinvoiceservicechargelineService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/autojobsalesinvoiceservicechargelines');

  create(autojobsalesinvoiceservicechargeline: NewAutojobsalesinvoiceservicechargeline): Observable<EntityResponseType> {
    return this.http.post<IAutojobsalesinvoiceservicechargeline>(this.resourceUrl, autojobsalesinvoiceservicechargeline, {
      observe: 'response',
    });
  }

  update(autojobsalesinvoiceservicechargeline: IAutojobsalesinvoiceservicechargeline): Observable<EntityResponseType> {
    return this.http.put<IAutojobsalesinvoiceservicechargeline>(
      `${this.resourceUrl}/${this.getAutojobsalesinvoiceservicechargelineIdentifier(autojobsalesinvoiceservicechargeline)}`,
      autojobsalesinvoiceservicechargeline,
      { observe: 'response' },
    );
  }

  partialUpdate(autojobsalesinvoiceservicechargeline: PartialUpdateAutojobsalesinvoiceservicechargeline): Observable<EntityResponseType> {
    return this.http.patch<IAutojobsalesinvoiceservicechargeline>(
      `${this.resourceUrl}/${this.getAutojobsalesinvoiceservicechargelineIdentifier(autojobsalesinvoiceservicechargeline)}`,
      autojobsalesinvoiceservicechargeline,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAutojobsalesinvoiceservicechargeline>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAutojobsalesinvoiceservicechargeline[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  queryByInvoiceId(invoiceId: number): Observable<HttpResponse<any[]>> {
    return this.http.get<any[]>(`${this.resourceUrl}/invoice/${invoiceId}`, { observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAutojobsalesinvoiceservicechargelineIdentifier(
    autojobsalesinvoiceservicechargeline: Pick<IAutojobsalesinvoiceservicechargeline, 'id'>,
  ): number {
    return autojobsalesinvoiceservicechargeline.id;
  }

  compareAutojobsalesinvoiceservicechargeline(
    o1: Pick<IAutojobsalesinvoiceservicechargeline, 'id'> | null,
    o2: Pick<IAutojobsalesinvoiceservicechargeline, 'id'> | null,
  ): boolean {
    return o1 && o2
      ? this.getAutojobsalesinvoiceservicechargelineIdentifier(o1) === this.getAutojobsalesinvoiceservicechargelineIdentifier(o2)
      : o1 === o2;
  }

  addAutojobsalesinvoiceservicechargelineToCollectionIfMissing<Type extends Pick<IAutojobsalesinvoiceservicechargeline, 'id'>>(
    autojobsalesinvoiceservicechargelineCollection: Type[],
    ...autojobsalesinvoiceservicechargelinesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const autojobsalesinvoiceservicechargelines: Type[] = autojobsalesinvoiceservicechargelinesToCheck.filter(isPresent);
    if (autojobsalesinvoiceservicechargelines.length > 0) {
      const autojobsalesinvoiceservicechargelineCollectionIdentifiers = autojobsalesinvoiceservicechargelineCollection.map(
        autojobsalesinvoiceservicechargelineItem =>
          this.getAutojobsalesinvoiceservicechargelineIdentifier(autojobsalesinvoiceservicechargelineItem),
      );
      const autojobsalesinvoiceservicechargelinesToAdd = autojobsalesinvoiceservicechargelines.filter(
        autojobsalesinvoiceservicechargelineItem => {
          const autojobsalesinvoiceservicechargelineIdentifier = this.getAutojobsalesinvoiceservicechargelineIdentifier(
            autojobsalesinvoiceservicechargelineItem,
          );
          if (autojobsalesinvoiceservicechargelineCollectionIdentifiers.includes(autojobsalesinvoiceservicechargelineIdentifier)) {
            return false;
          }
          autojobsalesinvoiceservicechargelineCollectionIdentifiers.push(autojobsalesinvoiceservicechargelineIdentifier);
          return true;
        },
      );
      return [...autojobsalesinvoiceservicechargelinesToAdd, ...autojobsalesinvoiceservicechargelineCollection];
    }
    return autojobsalesinvoiceservicechargelineCollection;
  }
}
