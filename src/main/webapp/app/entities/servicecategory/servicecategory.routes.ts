import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import ServicecategoryResolve from './route/servicecategory-routing-resolve.service';

const servicecategoryRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/servicecategory.component').then(m => m.ServicecategoryComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/servicecategory-detail.component').then(m => m.ServicecategoryDetailComponent),
    resolve: {
      servicecategory: ServicecategoryResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/servicecategory-update.component').then(m => m.ServicecategoryUpdateComponent),
    resolve: {
      servicecategory: ServicecategoryResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/servicecategory-update.component').then(m => m.ServicecategoryUpdateComponent),
    resolve: {
      servicecategory: ServicecategoryResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default servicecategoryRoute;
