import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import AutojobsalesinvoiceservicechargelineResolve from './route/autojobsalesinvoiceservicechargeline-routing-resolve.service';

const autojobsalesinvoiceservicechargelineRoute: Routes = [
  {
    path: '',
    loadComponent: () =>
      import('./list/autojobsalesinvoiceservicechargeline.component').then(m => m.AutojobsalesinvoiceservicechargelineComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () =>
      import('./detail/autojobsalesinvoiceservicechargeline-detail.component').then(
        m => m.AutojobsalesinvoiceservicechargelineDetailComponent,
      ),
    resolve: {
      autojobsalesinvoiceservicechargeline: AutojobsalesinvoiceservicechargelineResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () =>
      import('./update/autojobsalesinvoiceservicechargeline-update.component').then(
        m => m.AutojobsalesinvoiceservicechargelineUpdateComponent,
      ),
    resolve: {
      autojobsalesinvoiceservicechargeline: AutojobsalesinvoiceservicechargelineResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () =>
      import('./update/autojobsalesinvoiceservicechargeline-update.component').then(
        m => m.AutojobsalesinvoiceservicechargelineUpdateComponent,
      ),
    resolve: {
      autojobsalesinvoiceservicechargeline: AutojobsalesinvoiceservicechargelineResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default autojobsalesinvoiceservicechargelineRoute;
