import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import SalesinvoiceResolve from './route/salesinvoice-routing-resolve.service';

const salesinvoiceRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/salesinvoice.component').then(m => m.SalesinvoiceComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/salesinvoice-detail.component').then(m => m.SalesinvoiceDetailComponent),
    resolve: {
      salesinvoice: SalesinvoiceResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/salesinvoice-update.component').then(m => m.SalesinvoiceUpdateComponent),
    resolve: {
      salesinvoice: SalesinvoiceResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/salesinvoice-update.component').then(m => m.SalesinvoiceUpdateComponent),
    resolve: {
      salesinvoice: SalesinvoiceResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default salesinvoiceRoute;
