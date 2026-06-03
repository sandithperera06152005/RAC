import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISalesInvoiceServiceChargeLineDummy } from '../sales-invoice-service-charge-line-dummy.model';
import { SalesInvoiceServiceChargeLineDummyService } from '../service/sales-invoice-service-charge-line-dummy.service';

const salesInvoiceServiceChargeLineDummyResolve = (
  route: ActivatedRouteSnapshot,
): Observable<null | ISalesInvoiceServiceChargeLineDummy> => {
  const id = route.params.id;
  if (id) {
    return inject(SalesInvoiceServiceChargeLineDummyService)
      .find(id)
      .pipe(
        mergeMap((salesInvoiceServiceChargeLineDummy: HttpResponse<ISalesInvoiceServiceChargeLineDummy>) => {
          if (salesInvoiceServiceChargeLineDummy.body) {
            return of(salesInvoiceServiceChargeLineDummy.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default salesInvoiceServiceChargeLineDummyResolve;
