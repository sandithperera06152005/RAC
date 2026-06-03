import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, map } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IVoucherLines, NewVoucherLines } from '../voucher-lines.model';

export type PartialUpdateVoucherLines = Partial<IVoucherLines> & Pick<IVoucherLines, 'id'>;

type RestOf<T extends IVoucherLines | NewVoucherLines> = Omit<T, 'lmd'> & {
  lmd?: string | null;
};

export type RestVoucherLines = RestOf<IVoucherLines>;

export type NewRestVoucherLines = RestOf<NewVoucherLines>;

export type PartialUpdateRestVoucherLines = RestOf<PartialUpdateVoucherLines>;

export type EntityResponseType = HttpResponse<IVoucherLines>;
export type EntityArrayResponseType = HttpResponse<IVoucherLines[]>;

@Injectable({ providedIn: 'root' })
export class VoucherLinesService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/voucher-lines');

  create(voucherLines: NewVoucherLines): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(voucherLines);
    return this.http
      .post<RestVoucherLines>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(voucherLines: IVoucherLines): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(voucherLines);
    return this.http
      .put<RestVoucherLines>(`${this.resourceUrl}/${this.getVoucherLinesIdentifier(voucherLines)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(voucherLines: PartialUpdateVoucherLines): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(voucherLines);
    return this.http
      .patch<RestVoucherLines>(`${this.resourceUrl}/${this.getVoucherLinesIdentifier(voucherLines)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestVoucherLines>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestVoucherLines[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getVoucherLinesIdentifier(voucherLines: Pick<IVoucherLines, 'id'>): number {
    return voucherLines.id;
  }

  compareVoucherLines(o1: Pick<IVoucherLines, 'id'> | null, o2: Pick<IVoucherLines, 'id'> | null): boolean {
    return o1 && o2 ? this.getVoucherLinesIdentifier(o1) === this.getVoucherLinesIdentifier(o2) : o1 === o2;
  }

  addVoucherLinesToCollectionIfMissing<Type extends Pick<IVoucherLines, 'id'>>(
    voucherLinesCollection: Type[],
    ...voucherLinesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const voucherLines: Type[] = voucherLinesToCheck.filter(isPresent);
    if (voucherLines.length > 0) {
      const voucherLinesCollectionIdentifiers = voucherLinesCollection.map(voucherLinesItem =>
        this.getVoucherLinesIdentifier(voucherLinesItem),
      );
      const voucherLinesToAdd = voucherLines.filter(voucherLinesItem => {
        const voucherLinesIdentifier = this.getVoucherLinesIdentifier(voucherLinesItem);
        if (voucherLinesCollectionIdentifiers.includes(voucherLinesIdentifier)) {
          return false;
        }
        voucherLinesCollectionIdentifiers.push(voucherLinesIdentifier);
        return true;
      });
      return [...voucherLinesToAdd, ...voucherLinesCollection];
    }
    return voucherLinesCollection;
  }

  protected convertDateFromClient<T extends IVoucherLines | NewVoucherLines | PartialUpdateVoucherLines>(voucherLines: T): RestOf<T> {
    return {
      ...voucherLines,
      lmd: voucherLines.lmd?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restVoucherLines: RestVoucherLines): IVoucherLines {
    return {
      ...restVoucherLines,
      lmd: restVoucherLines.lmd ? dayjs(restVoucherLines.lmd) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestVoucherLines>): HttpResponse<IVoucherLines> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestVoucherLines[]>): HttpResponse<IVoucherLines[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
