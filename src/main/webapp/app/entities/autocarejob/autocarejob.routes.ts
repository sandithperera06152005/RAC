import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { AutocarejobComponent } from './list/autocarejob.component';
import { AutocarejobDetailComponent } from './detail/autocarejob-detail.component';
import { AutocarejobUpdateComponent } from './update/autocarejob-update.component';
import { AutocarejobInstructionComponent } from './update/autocarejob-instruction.component';
import { AutocareopenjobComponent } from './list/autocareopenjob.component';
import AutocarejobResolve from './route/autocarejob-routing-resolve.service';
import { AutocareclosejobComponent } from './list/autocareclosejob.component';
import { AutocarejobhistoryComponent } from './history/autocarejob-history.component';
import { AutocarejobitemissueComponent } from './itemissue/autocarejob-itemissue.component';

const autocarejobRoute: Routes = [
  {
    path: '',
    component: AutocarejobComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'autocareopenjob',
    component: AutocareopenjobComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },

  {
    path: 'autocareclosejob',
    component: AutocareclosejobComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },

  {
    path: ':id/view',
    component: AutocarejobDetailComponent,
    resolve: {
      autocarejob: AutocarejobResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AutocarejobUpdateComponent,
    resolve: {
      autocarejob: AutocarejobResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/instructions',
    component: AutocarejobInstructionComponent,
    resolve: {
      autocarejob: AutocarejobResolve,
    },
    canActivate: [UserRouteAccessService],
  },

  {
    path: ':id/history',
    component: AutocarejobhistoryComponent,
    resolve: {
      autocarejob: AutocarejobResolve,
    },
    canActivate: [UserRouteAccessService],
  },

  {
    path: ':id/itemissue',
    component: AutocarejobitemissueComponent,
    resolve: {
      autocarejob: AutocarejobResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AutocarejobUpdateComponent,
    resolve: {
      autocarejob: AutocarejobResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default autocarejobRoute;
