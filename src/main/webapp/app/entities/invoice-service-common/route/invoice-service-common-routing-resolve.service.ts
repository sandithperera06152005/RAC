import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IInvoiceServiceCommon } from '../invoice-service-common.model';
import { InvoiceServiceCommonService } from '../service/invoice-service-common.service';

const invoiceServiceCommonResolve = (route: ActivatedRouteSnapshot): Observable<null | IInvoiceServiceCommon> => {
  const id = route.params.id;
  if (id) {
    return inject(InvoiceServiceCommonService)
      .find(id)
      .pipe(
        mergeMap((invoiceServiceCommon: HttpResponse<IInvoiceServiceCommon>) => {
          if (invoiceServiceCommon.body) {
            return of(invoiceServiceCommon.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default invoiceServiceCommonResolve;
