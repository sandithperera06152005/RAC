import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, catchError, map } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IWorkshopVehicleWorkList, NewWorkshopVehicleWorkList } from '../workshop-vehicle-work-list.model';

export type PartialUpdateWorkshopVehicleWorkList = Partial<IWorkshopVehicleWorkList> & Pick<IWorkshopVehicleWorkList, 'id'>;

type RestOf<T extends IWorkshopVehicleWorkList | NewWorkshopVehicleWorkList> = Omit<T, 'jobdonedate'> & {
  jobdonedate?: string | null;
};

export type RestWorkshopVehicleWorkList = RestOf<IWorkshopVehicleWorkList>;

export type NewRestWorkshopVehicleWorkList = RestOf<NewWorkshopVehicleWorkList>;

export type PartialUpdateRestWorkshopVehicleWorkList = RestOf<PartialUpdateWorkshopVehicleWorkList>;

export type EntityResponseType = HttpResponse<IWorkshopVehicleWorkList>;
export type EntityArrayResponseType = HttpResponse<IWorkshopVehicleWorkList[]>;

@Injectable({ providedIn: 'root' })
export class WorkshopVehicleWorkListService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/workshop-vehicle-work-lists');

  create(workshopVehicleWorkList: NewWorkshopVehicleWorkList): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(workshopVehicleWorkList);
    return this.http
      .post<RestWorkshopVehicleWorkList>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(workshopVehicleWorkList: IWorkshopVehicleWorkList): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(workshopVehicleWorkList);
    return this.http
      .put<RestWorkshopVehicleWorkList>(`${this.resourceUrl}/${this.getWorkshopVehicleWorkListIdentifier(workshopVehicleWorkList)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(workshopVehicleWorkList: PartialUpdateWorkshopVehicleWorkList): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(workshopVehicleWorkList);
    return this.http
      .patch<RestWorkshopVehicleWorkList>(
        `${this.resourceUrl}/${this.getWorkshopVehicleWorkListIdentifier(workshopVehicleWorkList)}`,
        copy,
        { observe: 'response' },
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<RestWorkshopVehicleWorkList>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestWorkshopVehicleWorkList[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  queryByVehicleWorkIds(vehicleWorkIds: number[]): Observable<EntityArrayResponseType> {
    const ids = vehicleWorkIds.join(',');
    return this.http.get<RestWorkshopVehicleWorkList[]>(`${this.resourceUrl}/vehicleworks`, { params: { ids }, observe: 'response' }).pipe(
      map(res => this.convertResponseArrayFromServer(res)),
      catchError(() =>
        this.query({ size: 1000 }).pipe(
          map((res: EntityArrayResponseType) =>
            res.clone({
              body: (res.body || []).filter(workshopVehicleWorkList =>
                vehicleWorkIds.includes(Number(workshopVehicleWorkList.vehicleworkid ?? 0)),
              ),
            }),
          ),
        ),
      ),
    );
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getWorkshopVehicleWorkListIdentifier(workshopVehicleWorkList: Pick<IWorkshopVehicleWorkList, 'id'>): string {
    return workshopVehicleWorkList.id;
  }

  compareWorkshopVehicleWorkList(
    o1: Pick<IWorkshopVehicleWorkList, 'id'> | null,
    o2: Pick<IWorkshopVehicleWorkList, 'id'> | null,
  ): boolean {
    return o1 && o2 ? this.getWorkshopVehicleWorkListIdentifier(o1) === this.getWorkshopVehicleWorkListIdentifier(o2) : o1 === o2;
  }

  addWorkshopVehicleWorkListToCollectionIfMissing<Type extends Pick<IWorkshopVehicleWorkList, 'id'>>(
    workshopVehicleWorkListCollection: Type[],
    ...workshopVehicleWorkListsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const workshopVehicleWorkLists: Type[] = workshopVehicleWorkListsToCheck.filter(isPresent);
    if (workshopVehicleWorkLists.length > 0) {
      const workshopVehicleWorkListCollectionIdentifiers = workshopVehicleWorkListCollection.map(workshopVehicleWorkListItem =>
        this.getWorkshopVehicleWorkListIdentifier(workshopVehicleWorkListItem),
      );
      const workshopVehicleWorkListsToAdd = workshopVehicleWorkLists.filter(workshopVehicleWorkListItem => {
        const workshopVehicleWorkListIdentifier = this.getWorkshopVehicleWorkListIdentifier(workshopVehicleWorkListItem);
        if (workshopVehicleWorkListCollectionIdentifiers.includes(workshopVehicleWorkListIdentifier)) {
          return false;
        }
        workshopVehicleWorkListCollectionIdentifiers.push(workshopVehicleWorkListIdentifier);
        return true;
      });
      return [...workshopVehicleWorkListsToAdd, ...workshopVehicleWorkListCollection];
    }
    return workshopVehicleWorkListCollection;
  }

  protected convertDateFromClient<T extends IWorkshopVehicleWorkList | NewWorkshopVehicleWorkList | PartialUpdateWorkshopVehicleWorkList>(
    workshopVehicleWorkList: T,
  ): RestOf<T> {
    return {
      ...workshopVehicleWorkList,
      jobdonedate: workshopVehicleWorkList.jobdonedate?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restWorkshopVehicleWorkList: RestWorkshopVehicleWorkList): IWorkshopVehicleWorkList {
    return {
      ...restWorkshopVehicleWorkList,
      jobdonedate: restWorkshopVehicleWorkList.jobdonedate ? dayjs(restWorkshopVehicleWorkList.jobdonedate) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestWorkshopVehicleWorkList>): HttpResponse<IWorkshopVehicleWorkList> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestWorkshopVehicleWorkList[]>): HttpResponse<IWorkshopVehicleWorkList[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
