import { Route } from '@angular/router';
import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';

import { RightFamonelyAppComponent } from './right-famonely-app.component';

export const RFA_ROUTE: Route = {
  path: 'famonelyApp',
  component: RightFamonelyAppComponent,
  data: {
    authorities: [Authority.USER],
    pageTitle: 'global.menu.famonelyApp',
  },
  canActivate: [UserRouteAccessService],
};
