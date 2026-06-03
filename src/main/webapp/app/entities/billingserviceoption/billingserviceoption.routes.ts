import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import BillingserviceoptionResolve from './route/billingserviceoption-routing-resolve.service';

const billingserviceoptionRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/billingserviceoption.component').then(m => m.BillingserviceoptionComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/billingserviceoption-detail.component').then(m => m.BillingserviceoptionDetailComponent),
    resolve: {
      billingserviceoption: BillingserviceoptionResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/billingserviceoption-update.component').then(m => m.BillingserviceoptionUpdateComponent),
    resolve: {
      billingserviceoption: BillingserviceoptionResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/billingserviceoption-update.component').then(m => m.BillingserviceoptionUpdateComponent),
    resolve: {
      billingserviceoption: BillingserviceoptionResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default billingserviceoptionRoute;
