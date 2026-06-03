import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IInvoiceServiceCharge } from '../invoice-service-charge.model';
import { InvoiceServiceChargeService } from '../service/invoice-service-charge.service';

const invoiceServiceChargeResolve = (route: ActivatedRouteSnapshot): Observable<null | IInvoiceServiceCharge> => {
  const id = route.params.id;
  if (id) {
    return inject(InvoiceServiceChargeService)
      .find(id)
      .pipe(
        mergeMap((invoiceServiceCharge: HttpResponse<IInvoiceServiceCharge>) => {
          if (invoiceServiceCharge.body) {
            return of(invoiceServiceCharge.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default invoiceServiceChargeResolve;
