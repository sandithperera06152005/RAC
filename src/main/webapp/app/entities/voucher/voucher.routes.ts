import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import VoucherResolve from './route/voucher-routing-resolve.service';

const voucherRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/voucher.component').then(m => m.VoucherComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/voucher-detail.component').then(m => m.VoucherDetailComponent),
    resolve: {
      voucher: VoucherResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/voucher-update.component').then(m => m.VoucherUpdateComponent),
    resolve: {
      voucher: VoucherResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/voucher-update.component').then(m => m.VoucherUpdateComponent),
    resolve: {
      voucher: VoucherResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default voucherRoute;
