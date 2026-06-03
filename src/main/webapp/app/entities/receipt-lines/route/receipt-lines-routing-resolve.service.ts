import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IReceiptLines } from '../receipt-lines.model';
import { ReceiptLinesService } from '../service/receipt-lines.service';

const receiptLinesResolve = (route: ActivatedRouteSnapshot): Observable<null | IReceiptLines> => {
  const id = route.params.id;
  if (id) {
    return inject(ReceiptLinesService)
      .find(id)
      .pipe(
        mergeMap((receiptLines: HttpResponse<IReceiptLines>) => {
          if (receiptLines.body) {
            return of(receiptLines.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default receiptLinesResolve;
