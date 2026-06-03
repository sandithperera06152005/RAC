import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IWorkshopVehicleWorkList } from '../workshop-vehicle-work-list.model';
import { WorkshopVehicleWorkListService } from '../service/workshop-vehicle-work-list.service';

const workshopVehicleWorkListResolve = (route: ActivatedRouteSnapshot): Observable<null | IWorkshopVehicleWorkList> => {
  const id = route.params.id;
  if (id) {
    return inject(WorkshopVehicleWorkListService)
      .find(id)
      .pipe(
        mergeMap((workshopVehicleWorkList: HttpResponse<IWorkshopVehicleWorkList>) => {
          if (workshopVehicleWorkList.body) {
            return of(workshopVehicleWorkList.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default workshopVehicleWorkListResolve;
