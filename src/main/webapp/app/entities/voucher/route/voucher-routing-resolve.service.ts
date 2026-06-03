import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IVoucher } from '../voucher.model';
import { VoucherService } from '../service/voucher.service';

const voucherResolve = (route: ActivatedRouteSnapshot): Observable<null | IVoucher> => {
  const id = route.params.id;
  if (id) {
    return inject(VoucherService)
      .find(id)
      .pipe(
        mergeMap((voucher: HttpResponse<IVoucher>) => {
          if (voucher.body) {
            return of(voucher.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default voucherResolve;
