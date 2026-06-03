import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import CommonserviceoptionResolve from './route/commonserviceoption-routing-resolve.service';

const commonserviceoptionRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/commonserviceoption.component').then(m => m.CommonserviceoptionComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/commonserviceoption-detail.component').then(m => m.CommonserviceoptionDetailComponent),
    resolve: {
      commonserviceoption: CommonserviceoptionResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/commonserviceoption-update.component').then(m => m.CommonserviceoptionUpdateComponent),
    resolve: {
      commonserviceoption: CommonserviceoptionResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/commonserviceoption-update.component').then(m => m.CommonserviceoptionUpdateComponent),
    resolve: {
      commonserviceoption: CommonserviceoptionResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default commonserviceoptionRoute;
