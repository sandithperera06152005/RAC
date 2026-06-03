import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISaleInvoiceCommonServiceChargeDummy } from '../sale-invoice-common-service-charge-dummy.model';
import { SaleInvoiceCommonServiceChargeDummyService } from '../service/sale-invoice-common-service-charge-dummy.service';

const saleInvoiceCommonServiceChargeDummyResolve = (
  route: ActivatedRouteSnapshot,
): Observable<null | ISaleInvoiceCommonServiceChargeDummy> => {
  const id = route.params.id;
  if (id) {
    return inject(SaleInvoiceCommonServiceChargeDummyService)
      .find(id)
      .pipe(
        mergeMap((saleInvoiceCommonServiceChargeDummy: HttpResponse<ISaleInvoiceCommonServiceChargeDummy>) => {
          if (saleInvoiceCommonServiceChargeDummy.body) {
            return of(saleInvoiceCommonServiceChargeDummy.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default saleInvoiceCommonServiceChargeDummyResolve;
