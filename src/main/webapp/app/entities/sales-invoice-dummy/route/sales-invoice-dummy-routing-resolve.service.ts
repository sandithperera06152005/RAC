import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISalesInvoiceDummy } from '../sales-invoice-dummy.model';
import { SalesInvoiceDummyService } from '../service/sales-invoice-dummy.service';

const salesInvoiceDummyResolve = (route: ActivatedRouteSnapshot): Observable<null | ISalesInvoiceDummy> => {
  const id = route.params.id;
  if (id) {
    return inject(SalesInvoiceDummyService)
      .find(id)
      .pipe(
        mergeMap((salesInvoiceDummy: HttpResponse<ISalesInvoiceDummy>) => {
          if (salesInvoiceDummy.body) {
            return of(salesInvoiceDummy.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default salesInvoiceDummyResolve;
