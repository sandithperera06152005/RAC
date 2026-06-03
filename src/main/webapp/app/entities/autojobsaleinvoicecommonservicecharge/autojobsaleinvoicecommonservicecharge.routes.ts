import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import AutojobsaleinvoicecommonservicechargeResolve from './route/autojobsaleinvoicecommonservicecharge-routing-resolve.service';

const autojobsaleinvoicecommonservicechargeRoute: Routes = [
  {
    path: '',
    loadComponent: () =>
      import('./list/autojobsaleinvoicecommonservicecharge.component').then(m => m.AutojobsaleinvoicecommonservicechargeComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () =>
      import('./detail/autojobsaleinvoicecommonservicecharge-detail.component').then(
        m => m.AutojobsaleinvoicecommonservicechargeDetailComponent,
      ),
    resolve: {
      autojobsaleinvoicecommonservicecharge: AutojobsaleinvoicecommonservicechargeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () =>
      import('./update/autojobsaleinvoicecommonservicecharge-update.component').then(
        m => m.AutojobsaleinvoicecommonservicechargeUpdateComponent,
      ),
    resolve: {
      autojobsaleinvoicecommonservicecharge: AutojobsaleinvoicecommonservicechargeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () =>
      import('./update/autojobsaleinvoicecommonservicecharge-update.component').then(
        m => m.AutojobsaleinvoicecommonservicechargeUpdateComponent,
      ),
    resolve: {
      autojobsaleinvoicecommonservicecharge: AutojobsaleinvoicecommonservicechargeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default autojobsaleinvoicecommonservicechargeRoute;
