import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import SalesInvoiceLinesDummyResolve from './route/sales-invoice-lines-dummy-routing-resolve.service';

const salesInvoiceLinesDummyRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/sales-invoice-lines-dummy.component').then(m => m.SalesInvoiceLinesDummyComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/sales-invoice-lines-dummy-detail.component').then(m => m.SalesInvoiceLinesDummyDetailComponent),
    resolve: {
      salesInvoiceLinesDummy: SalesInvoiceLinesDummyResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/sales-invoice-lines-dummy-update.component').then(m => m.SalesInvoiceLinesDummyUpdateComponent),
    resolve: {
      salesInvoiceLinesDummy: SalesInvoiceLinesDummyResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/sales-invoice-lines-dummy-update.component').then(m => m.SalesInvoiceLinesDummyUpdateComponent),
    resolve: {
      salesInvoiceLinesDummy: SalesInvoiceLinesDummyResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default salesInvoiceLinesDummyRoute;
