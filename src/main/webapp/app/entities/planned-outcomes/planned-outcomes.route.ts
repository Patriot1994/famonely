import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPlannedOutcomes, PlannedOutcomes } from 'app/shared/model/planned-outcomes.model';
import { PlannedOutcomesService } from './planned-outcomes.service';
import { PlannedOutcomesComponent } from './planned-outcomes.component';
import { PlannedOutcomesDetailComponent } from './planned-outcomes-detail.component';
import { PlannedOutcomesUpdateComponent } from './planned-outcomes-update.component';

@Injectable({ providedIn: 'root' })
export class PlannedOutcomesResolve implements Resolve<IPlannedOutcomes> {
  constructor(private service: PlannedOutcomesService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPlannedOutcomes> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((plannedOutcomes: HttpResponse<PlannedOutcomes>) => {
          if (plannedOutcomes.body) {
            return of(plannedOutcomes.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PlannedOutcomes());
  }
}

export const plannedOutcomesRoute: Routes = [
  {
    path: '',
    component: PlannedOutcomesComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'famonelyApp.plannedOutcomes.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PlannedOutcomesDetailComponent,
    resolve: {
      plannedOutcomes: PlannedOutcomesResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'famonelyApp.plannedOutcomes.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PlannedOutcomesUpdateComponent,
    resolve: {
      plannedOutcomes: PlannedOutcomesResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'famonelyApp.plannedOutcomes.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PlannedOutcomesUpdateComponent,
    resolve: {
      plannedOutcomes: PlannedOutcomesResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'famonelyApp.plannedOutcomes.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
