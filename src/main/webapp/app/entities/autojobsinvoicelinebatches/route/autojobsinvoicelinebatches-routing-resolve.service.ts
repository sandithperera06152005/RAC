import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAutojobsinvoicelinebatches } from '../autojobsinvoicelinebatches.model';
import { AutojobsinvoicelinebatchesService } from '../service/autojobsinvoicelinebatches.service';

const autojobsinvoicelinebatchesResolve = (route: ActivatedRouteSnapshot): Observable<null | IAutojobsinvoicelinebatches> => {
  const id = route.params.id;
  if (id) {
    return inject(AutojobsinvoicelinebatchesService)
      .find(id)
      .pipe(
        mergeMap((autojobsinvoicelinebatches: HttpResponse<IAutojobsinvoicelinebatches>) => {
          if (autojobsinvoicelinebatches.body) {
            return of(autojobsinvoicelinebatches.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default autojobsinvoicelinebatchesResolve;
