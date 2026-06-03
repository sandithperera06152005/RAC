import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IVoucherPaymentsDetails } from '../voucher-payments-details.model';
import { VoucherPaymentsDetailsService } from '../service/voucher-payments-details.service';

const voucherPaymentsDetailsResolve = (route: ActivatedRouteSnapshot): Observable<null | IVoucherPaymentsDetails> => {
  const id = route.params.id;
  if (id) {
    return inject(VoucherPaymentsDetailsService)
      .find(id)
      .pipe(
        mergeMap((voucherPaymentsDetails: HttpResponse<IVoucherPaymentsDetails>) => {
          if (voucherPaymentsDetails.body) {
            return of(voucherPaymentsDetails.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default voucherPaymentsDetailsResolve;
