import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import ReceiptpaymentsdetailsResolve from './route/receiptpaymentsdetails-routing-resolve.service';

const receiptpaymentsdetailsRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/receiptpaymentsdetails.component').then(m => m.ReceiptpaymentsdetailsComponent),
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/receiptpaymentsdetails-detail.component').then(m => m.ReceiptpaymentsdetailsDetailComponent),
    resolve: {
      receiptpaymentsdetails: ReceiptpaymentsdetailsResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/receiptpaymentsdetails-update.component').then(m => m.ReceiptpaymentsdetailsUpdateComponent),
    resolve: {
      receiptpaymentsdetails: ReceiptpaymentsdetailsResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/receiptpaymentsdetails-update.component').then(m => m.ReceiptpaymentsdetailsUpdateComponent),
    resolve: {
      receiptpaymentsdetails: ReceiptpaymentsdetailsResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default receiptpaymentsdetailsRoute;
