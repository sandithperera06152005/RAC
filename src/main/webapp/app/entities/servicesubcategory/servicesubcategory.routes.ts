import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import ServicesubcategoryResolve from './route/servicesubcategory-routing-resolve.service';

const servicesubcategoryRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/servicesubcategory.component').then(m => m.ServicesubcategoryComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/servicesubcategory-detail.component').then(m => m.ServicesubcategoryDetailComponent),
    resolve: {
      servicesubcategory: ServicesubcategoryResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/servicesubcategory-update.component').then(m => m.ServicesubcategoryUpdateComponent),
    resolve: {
      servicesubcategory: ServicesubcategoryResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/servicesubcategory-update.component').then(m => m.ServicesubcategoryUpdateComponent),
    resolve: {
      servicesubcategory: ServicesubcategoryResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default servicesubcategoryRoute;
