import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import AccountsResolve from './route/accounts-routing-resolve.service';

const accountsRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/accounts.component').then(m => m.AccountsComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/accounts-detail.component').then(m => m.AccountsDetailComponent),
    resolve: {
      accounts: AccountsResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/accounts-update.component').then(m => m.AccountsUpdateComponent),
    resolve: {
      accounts: AccountsResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/accounts-update.component').then(m => m.AccountsUpdateComponent),
    resolve: {
      accounts: AccountsResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default accountsRoute;
