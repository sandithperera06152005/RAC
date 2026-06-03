import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAutojobsaleinvoicecommonservicecharge } from '../autojobsaleinvoicecommonservicecharge.model';
import { AutojobsaleinvoicecommonservicechargeService } from '../service/autojobsaleinvoicecommonservicecharge.service';

const autojobsaleinvoicecommonservicechargeResolve = (
  route: ActivatedRouteSnapshot,
): Observable<null | IAutojobsaleinvoicecommonservicecharge> => {
  const id = route.params.id;
  if (id) {
    return inject(AutojobsaleinvoicecommonservicechargeService)
      .find(id)
      .pipe(
        mergeMap((autojobsaleinvoicecommonservicecharge: HttpResponse<IAutojobsaleinvoicecommonservicecharge>) => {
          if (autojobsaleinvoicecommonservicecharge.body) {
            return of(autojobsaleinvoicecommonservicecharge.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default autojobsaleinvoicecommonservicechargeResolve;
