import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import InvoiceServiceChargeResolve from './route/invoice-service-charge-routing-resolve.service';

const invoiceServiceChargeRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/invoice-service-charge.component').then(m => m.InvoiceServiceChargeComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/invoice-service-charge-detail.component').then(m => m.InvoiceServiceChargeDetailComponent),
    resolve: {
      invoiceServiceCharge: InvoiceServiceChargeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/invoice-service-charge-update.component').then(m => m.InvoiceServiceChargeUpdateComponent),
    resolve: {
      invoiceServiceCharge: InvoiceServiceChargeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/invoice-service-charge-update.component').then(m => m.InvoiceServiceChargeUpdateComponent),
    resolve: {
      invoiceServiceCharge: InvoiceServiceChargeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default invoiceServiceChargeRoute;
