import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import ReceiptLinesResolve from './route/receipt-lines-routing-resolve.service';

const receiptLinesRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/receipt-lines.component').then(m => m.ReceiptLinesComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/receipt-lines-detail.component').then(m => m.ReceiptLinesDetailComponent),
    resolve: {
      receiptLines: ReceiptLinesResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/receipt-lines-update.component').then(m => m.ReceiptLinesUpdateComponent),
    resolve: {
      receiptLines: ReceiptLinesResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/receipt-lines-update.component').then(m => m.ReceiptLinesUpdateComponent),
    resolve: {
      receiptLines: ReceiptLinesResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default receiptLinesRoute;
