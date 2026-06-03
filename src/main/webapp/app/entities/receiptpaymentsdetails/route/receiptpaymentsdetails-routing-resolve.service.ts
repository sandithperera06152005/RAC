import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IReceiptpaymentsdetails } from '../receiptpaymentsdetails.model';
import { ReceiptpaymentsdetailsService } from '../service/receiptpaymentsdetails.service';

const receiptpaymentsdetailsResolve = (route: ActivatedRouteSnapshot): Observable<null | IReceiptpaymentsdetails> => {
  const id = route.params.id;
  if (id) {
    return inject(ReceiptpaymentsdetailsService)
      .find(id)
      .pipe(
        mergeMap((receiptpaymentsdetails: HttpResponse<IReceiptpaymentsdetails>) => {
          if (receiptpaymentsdetails.body) {
            return of(receiptpaymentsdetails.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default receiptpaymentsdetailsResolve;
