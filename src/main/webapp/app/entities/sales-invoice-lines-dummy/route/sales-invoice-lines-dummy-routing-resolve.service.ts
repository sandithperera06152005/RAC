import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISalesInvoiceLinesDummy } from '../sales-invoice-lines-dummy.model';
import { SalesInvoiceLinesDummyService } from '../service/sales-invoice-lines-dummy.service';

const salesInvoiceLinesDummyResolve = (route: ActivatedRouteSnapshot): Observable<null | ISalesInvoiceLinesDummy> => {
  const id = route.params.id;
  if (id) {
    return inject(SalesInvoiceLinesDummyService)
      .find(id)
      .pipe(
        mergeMap((salesInvoiceLinesDummy: HttpResponse<ISalesInvoiceLinesDummy>) => {
          if (salesInvoiceLinesDummy.body) {
            return of(salesInvoiceLinesDummy.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default salesInvoiceLinesDummyResolve;
