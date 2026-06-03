import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAutojobsinvoicelines } from '../autojobsinvoicelines.model';
import { AutojobsinvoicelinesService } from '../service/autojobsinvoicelines.service';

const autojobsinvoicelinesResolve = (route: ActivatedRouteSnapshot): Observable<null | IAutojobsinvoicelines> => {
  const id = route.params.id;
  if (id) {
    return inject(AutojobsinvoicelinesService)
      .find(id)
      .pipe(
        mergeMap((autojobsinvoicelines: HttpResponse<IAutojobsinvoicelines>) => {
          if (autojobsinvoicelines.body) {
            return of(autojobsinvoicelines.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default autojobsinvoicelinesResolve;
