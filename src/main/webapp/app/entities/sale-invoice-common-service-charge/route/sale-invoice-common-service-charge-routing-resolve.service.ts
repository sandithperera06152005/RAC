import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISaleInvoiceCommonServiceCharge } from '../sale-invoice-common-service-charge.model';
import { SaleInvoiceCommonServiceChargeService } from '../service/sale-invoice-common-service-charge.service';

const saleInvoiceCommonServiceChargeResolve = (route: ActivatedRouteSnapshot): Observable<null | ISaleInvoiceCommonServiceCharge> => {
  const id = route.params.id;
  if (id) {
    return inject(SaleInvoiceCommonServiceChargeService)
      .find(id)
      .pipe(
        mergeMap((saleInvoiceCommonServiceCharge: HttpResponse<ISaleInvoiceCommonServiceCharge>) => {
          if (saleInvoiceCommonServiceCharge.body) {
            return of(saleInvoiceCommonServiceCharge.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default saleInvoiceCommonServiceChargeResolve;
