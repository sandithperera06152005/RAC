import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import SalesInvoiceLinesResolve from './route/sales-invoice-lines-routing-resolve.service';

const salesInvoiceLinesRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/sales-invoice-lines.component').then(m => m.SalesInvoiceLinesComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/sales-invoice-lines-detail.component').then(m => m.SalesInvoiceLinesDetailComponent),
    resolve: {
      salesInvoiceLines: SalesInvoiceLinesResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/sales-invoice-lines-update.component').then(m => m.SalesInvoiceLinesUpdateComponent),
    resolve: {
      salesInvoiceLines: SalesInvoiceLinesResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/sales-invoice-lines-update.component').then(m => m.SalesInvoiceLinesUpdateComponent),
    resolve: {
      salesInvoiceLines: SalesInvoiceLinesResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default salesInvoiceLinesRoute;
