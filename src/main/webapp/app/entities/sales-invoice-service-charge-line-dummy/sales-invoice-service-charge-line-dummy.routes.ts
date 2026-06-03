import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import SalesInvoiceServiceChargeLineDummyResolve from './route/sales-invoice-service-charge-line-dummy-routing-resolve.service';

const salesInvoiceServiceChargeLineDummyRoute: Routes = [
  {
    path: '',
    loadComponent: () =>
      import('./list/sales-invoice-service-charge-line-dummy.component').then(m => m.SalesInvoiceServiceChargeLineDummyComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () =>
      import('./detail/sales-invoice-service-charge-line-dummy-detail.component').then(
        m => m.SalesInvoiceServiceChargeLineDummyDetailComponent,
      ),
    resolve: {
      salesInvoiceServiceChargeLineDummy: SalesInvoiceServiceChargeLineDummyResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () =>
      import('./update/sales-invoice-service-charge-line-dummy-update.component').then(
        m => m.SalesInvoiceServiceChargeLineDummyUpdateComponent,
      ),
    resolve: {
      salesInvoiceServiceChargeLineDummy: SalesInvoiceServiceChargeLineDummyResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () =>
      import('./update/sales-invoice-service-charge-line-dummy-update.component').then(
        m => m.SalesInvoiceServiceChargeLineDummyUpdateComponent,
      ),
    resolve: {
      salesInvoiceServiceChargeLineDummy: SalesInvoiceServiceChargeLineDummyResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default salesInvoiceServiceChargeLineDummyRoute;
