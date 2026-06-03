import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import InvoiceServiceCommonResolve from './route/invoice-service-common-routing-resolve.service';

const invoiceServiceCommonRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/invoice-service-common.component').then(m => m.InvoiceServiceCommonComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/invoice-service-common-detail.component').then(m => m.InvoiceServiceCommonDetailComponent),
    resolve: {
      invoiceServiceCommon: InvoiceServiceCommonResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/invoice-service-common-update.component').then(m => m.InvoiceServiceCommonUpdateComponent),
    resolve: {
      invoiceServiceCommon: InvoiceServiceCommonResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/invoice-service-common-update.component').then(m => m.InvoiceServiceCommonUpdateComponent),
    resolve: {
      invoiceServiceCommon: InvoiceServiceCommonResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default invoiceServiceCommonRoute;
