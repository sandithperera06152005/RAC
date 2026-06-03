import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import WorkshopVehicleWorkListResolve from './route/workshop-vehicle-work-list-routing-resolve.service';

const workshopVehicleWorkListRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/workshop-vehicle-work-list.component').then(m => m.WorkshopVehicleWorkListComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/workshop-vehicle-work-list-detail.component').then(m => m.WorkshopVehicleWorkListDetailComponent),
    resolve: {
      workshopVehicleWorkList: WorkshopVehicleWorkListResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/workshop-vehicle-work-list-update.component').then(m => m.WorkshopVehicleWorkListUpdateComponent),
    resolve: {
      workshopVehicleWorkList: WorkshopVehicleWorkListResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/workshop-vehicle-work-list-update.component').then(m => m.WorkshopVehicleWorkListUpdateComponent),
    resolve: {
      workshopVehicleWorkList: WorkshopVehicleWorkListResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default workshopVehicleWorkListRoute;
