import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import InventoryResolve from './route/inventory-routing-resolve.service';

const inventoryRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/inventory.component').then(m => m.InventoryComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/inventory-detail.component').then(m => m.InventoryDetailComponent),
    resolve: {
      inventory: InventoryResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/inventory-update.component').then(m => m.InventoryUpdateComponent),
    resolve: {
      inventory: InventoryResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/inventory-update.component').then(m => m.InventoryUpdateComponent),
    resolve: {
      inventory: InventoryResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default inventoryRoute;
