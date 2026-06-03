import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import BillingserviceoptionvaluesResolve from './route/billingserviceoptionvalues-routing-resolve.service';

const billingserviceoptionvaluesRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/billingserviceoptionvalues.component').then(m => m.BillingserviceoptionvaluesComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () =>
      import('./detail/billingserviceoptionvalues-detail.component').then(m => m.BillingserviceoptionvaluesDetailComponent),
    resolve: {
      billingserviceoptionvalues: BillingserviceoptionvaluesResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () =>
      import('./update/billingserviceoptionvalues-update.component').then(m => m.BillingserviceoptionvaluesUpdateComponent),
    resolve: {
      billingserviceoptionvalues: BillingserviceoptionvaluesResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () =>
      import('./update/billingserviceoptionvalues-update.component').then(m => m.BillingserviceoptionvaluesUpdateComponent),
    resolve: {
      billingserviceoptionvalues: BillingserviceoptionvaluesResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default billingserviceoptionvaluesRoute;
