import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISalesInvoiceLines } from '../sales-invoice-lines.model';
import { SalesInvoiceLinesService } from '../service/sales-invoice-lines.service';

const salesInvoiceLinesResolve = (route: ActivatedRouteSnapshot): Observable<null | ISalesInvoiceLines> => {
  const id = route.params.id;
  if (id) {
    return inject(SalesInvoiceLinesService)
      .find(id)
      .pipe(
        mergeMap((salesInvoiceLines: HttpResponse<ISalesInvoiceLines>) => {
          if (salesInvoiceLines.body) {
            return of(salesInvoiceLines.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default salesInvoiceLinesResolve;
