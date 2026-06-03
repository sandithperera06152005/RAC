import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import VoucherPaymentsDetailsResolve from './route/voucher-payments-details-routing-resolve.service';

const voucherPaymentsDetailsRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/voucher-payments-details.component').then(m => m.VoucherPaymentsDetailsComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/voucher-payments-details-detail.component').then(m => m.VoucherPaymentsDetailsDetailComponent),
    resolve: {
      voucherPaymentsDetails: VoucherPaymentsDetailsResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/voucher-payments-details-update.component').then(m => m.VoucherPaymentsDetailsUpdateComponent),
    resolve: {
      voucherPaymentsDetails: VoucherPaymentsDetailsResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/voucher-payments-details-update.component').then(m => m.VoucherPaymentsDetailsUpdateComponent),
    resolve: {
      voucherPaymentsDetails: VoucherPaymentsDetailsResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default voucherPaymentsDetailsRoute;
