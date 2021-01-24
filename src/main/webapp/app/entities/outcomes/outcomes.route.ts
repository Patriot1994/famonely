import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IOutcomes, Outcomes } from 'app/shared/model/outcomes.model';
import { OutcomesService } from './outcomes.service';
import { OutcomesComponent } from './outcomes.component';
import { OutcomesDetailComponent } from './outcomes-detail.component';
import { OutcomesUpdateComponent } from './outcomes-update.component';

@Injectable({ providedIn: 'root' })
export class OutcomesResolve implements Resolve<IOutcomes> {
  constructor(private service: OutcomesService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IOutcomes> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((outcomes: HttpResponse<Outcomes>) => {
          if (outcomes.body) {
            return of(outcomes.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Outcomes());
  }
}

export const outcomesRoute: Routes = [
  {
    path: '',
    component: OutcomesComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'famonelyApp.outcomes.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: OutcomesDetailComponent,
    resolve: {
      outcomes: OutcomesResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'famonelyApp.outcomes.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: OutcomesUpdateComponent,
    resolve: {
      outcomes: OutcomesResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'famonelyApp.outcomes.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: OutcomesUpdateComponent,
    resolve: {
      outcomes: OutcomesResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'famonelyApp.outcomes.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
