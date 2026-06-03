import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import SaleInvoiceCommonServiceChargeResolve from './route/sale-invoice-common-service-charge-routing-resolve.service';

const saleInvoiceCommonServiceChargeRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/sale-invoice-common-service-charge.component').then(m => m.SaleInvoiceCommonServiceChargeComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () =>
      import('./detail/sale-invoice-common-service-charge-detail.component').then(m => m.SaleInvoiceCommonServiceChargeDetailComponent),
    resolve: {
      saleInvoiceCommonServiceCharge: SaleInvoiceCommonServiceChargeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () =>
      import('./update/sale-invoice-common-service-charge-update.component').then(m => m.SaleInvoiceCommonServiceChargeUpdateComponent),
    resolve: {
      saleInvoiceCommonServiceCharge: SaleInvoiceCommonServiceChargeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () =>
      import('./update/sale-invoice-common-service-charge-update.component').then(m => m.SaleInvoiceCommonServiceChargeUpdateComponent),
    resolve: {
      saleInvoiceCommonServiceCharge: SaleInvoiceCommonServiceChargeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default saleInvoiceCommonServiceChargeRoute;
