import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import SaleInvoiceCommonServiceChargeDummyResolve from './route/sale-invoice-common-service-charge-dummy-routing-resolve.service';

const saleInvoiceCommonServiceChargeDummyRoute: Routes = [
  {
    path: '',
    loadComponent: () =>
      import('./list/sale-invoice-common-service-charge-dummy.component').then(m => m.SaleInvoiceCommonServiceChargeDummyComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () =>
      import('./detail/sale-invoice-common-service-charge-dummy-detail.component').then(
        m => m.SaleInvoiceCommonServiceChargeDummyDetailComponent,
      ),
    resolve: {
      saleInvoiceCommonServiceChargeDummy: SaleInvoiceCommonServiceChargeDummyResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () =>
      import('./update/sale-invoice-common-service-charge-dummy-update.component').then(
        m => m.SaleInvoiceCommonServiceChargeDummyUpdateComponent,
      ),
    resolve: {
      saleInvoiceCommonServiceChargeDummy: SaleInvoiceCommonServiceChargeDummyResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () =>
      import('./update/sale-invoice-common-service-charge-dummy-update.component').then(
        m => m.SaleInvoiceCommonServiceChargeDummyUpdateComponent,
      ),
    resolve: {
      saleInvoiceCommonServiceChargeDummy: SaleInvoiceCommonServiceChargeDummyResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default saleInvoiceCommonServiceChargeDummyRoute;
