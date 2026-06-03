import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import SalesInvoiceDummyResolve from './route/sales-invoice-dummy-routing-resolve.service';

const salesInvoiceDummyRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/sales-invoice-dummy.component').then(m => m.SalesInvoiceDummyComponent),
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/sales-invoice-dummy-detail.component').then(m => m.SalesInvoiceDummyDetailComponent),
    resolve: {
      salesInvoiceDummy: SalesInvoiceDummyResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/sales-invoice-dummy-update.component').then(m => m.SalesInvoiceDummyUpdateComponent),
    resolve: {
      salesInvoiceDummy: SalesInvoiceDummyResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/sales-invoice-dummy-update.component').then(m => m.SalesInvoiceDummyUpdateComponent),
    resolve: {
      salesInvoiceDummy: SalesInvoiceDummyResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default salesInvoiceDummyRoute;
