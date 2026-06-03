import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISalesInvoiceServiceChargeLine } from '../sales-invoice-service-charge-line.model';
import { SalesInvoiceServiceChargeLineService } from '../service/sales-invoice-service-charge-line.service';

const salesInvoiceServiceChargeLineResolve = (route: ActivatedRouteSnapshot): Observable<null | ISalesInvoiceServiceChargeLine> => {
  const id = route.params.id;
  if (id) {
    return inject(SalesInvoiceServiceChargeLineService)
      .find(id)
      .pipe(
        mergeMap((salesInvoiceServiceChargeLine: HttpResponse<ISalesInvoiceServiceChargeLine>) => {
          if (salesInvoiceServiceChargeLine.body) {
            return of(salesInvoiceServiceChargeLine.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default salesInvoiceServiceChargeLineResolve;
