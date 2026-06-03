import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAutojobsalesinvoiceservicechargeline } from '../autojobsalesinvoiceservicechargeline.model';
import { AutojobsalesinvoiceservicechargelineService } from '../service/autojobsalesinvoiceservicechargeline.service';

const autojobsalesinvoiceservicechargelineResolve = (
  route: ActivatedRouteSnapshot,
): Observable<null | IAutojobsalesinvoiceservicechargeline> => {
  const id = route.params.id;
  if (id) {
    return inject(AutojobsalesinvoiceservicechargelineService)
      .find(id)
      .pipe(
        mergeMap((autojobsalesinvoiceservicechargeline: HttpResponse<IAutojobsalesinvoiceservicechargeline>) => {
          if (autojobsalesinvoiceservicechargeline.body) {
            return of(autojobsalesinvoiceservicechargeline.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default autojobsalesinvoiceservicechargelineResolve;
