import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import SalesInvoiceServiceChargeLineResolve from './route/sales-invoice-service-charge-line-routing-resolve.service';

const salesInvoiceServiceChargeLineRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/sales-invoice-service-charge-line.component').then(m => m.SalesInvoiceServiceChargeLineComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () =>
      import('./detail/sales-invoice-service-charge-line-detail.component').then(m => m.SalesInvoiceServiceChargeLineDetailComponent),
    resolve: {
      salesInvoiceServiceChargeLine: SalesInvoiceServiceChargeLineResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () =>
      import('./update/sales-invoice-service-charge-line-update.component').then(m => m.SalesInvoiceServiceChargeLineUpdateComponent),
    resolve: {
      salesInvoiceServiceChargeLine: SalesInvoiceServiceChargeLineResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () =>
      import('./update/sales-invoice-service-charge-line-update.component').then(m => m.SalesInvoiceServiceChargeLineUpdateComponent),
    resolve: {
      salesInvoiceServiceChargeLine: SalesInvoiceServiceChargeLineResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default salesInvoiceServiceChargeLineRoute;
