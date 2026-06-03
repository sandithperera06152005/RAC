import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import AutojobsinvoicelinebatchesResolve from './route/autojobsinvoicelinebatches-routing-resolve.service';

const autojobsinvoicelinebatchesRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/autojobsinvoicelinebatches.component').then(m => m.AutojobsinvoicelinebatchesComponent),
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () =>
      import('./detail/autojobsinvoicelinebatches-detail.component').then(m => m.AutojobsinvoicelinebatchesDetailComponent),
    resolve: {
      autojobsinvoicelinebatches: AutojobsinvoicelinebatchesResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () =>
      import('./update/autojobsinvoicelinebatches-update.component').then(m => m.AutojobsinvoicelinebatchesUpdateComponent),
    resolve: {
      autojobsinvoicelinebatches: AutojobsinvoicelinebatchesResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () =>
      import('./update/autojobsinvoicelinebatches-update.component').then(m => m.AutojobsinvoicelinebatchesUpdateComponent),
    resolve: {
      autojobsinvoicelinebatches: AutojobsinvoicelinebatchesResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default autojobsinvoicelinebatchesRoute;
