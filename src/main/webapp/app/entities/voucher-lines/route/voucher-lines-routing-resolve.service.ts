import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IVoucherLines } from '../voucher-lines.model';
import { VoucherLinesService } from '../service/voucher-lines.service';

const voucherLinesResolve = (route: ActivatedRouteSnapshot): Observable<null | IVoucherLines> => {
  const id = route.params.id;
  if (id) {
    return inject(VoucherLinesService)
      .find(id)
      .pipe(
        mergeMap((voucherLines: HttpResponse<IVoucherLines>) => {
          if (voucherLines.body) {
            return of(voucherLines.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default voucherLinesResolve;
