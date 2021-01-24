import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IOutcomeType, OutcomeType } from 'app/shared/model/outcome-type.model';
import { OutcomeTypeService } from './outcome-type.service';
import { OutcomeTypeComponent } from './outcome-type.component';
import { OutcomeTypeDetailComponent } from './outcome-type-detail.component';
import { OutcomeTypeUpdateComponent } from './outcome-type-update.component';

@Injectable({ providedIn: 'root' })
export class OutcomeTypeResolve implements Resolve<IOutcomeType> {
  constructor(private service: OutcomeTypeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IOutcomeType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((outcomeType: HttpResponse<OutcomeType>) => {
          if (outcomeType.body) {
            return of(outcomeType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new OutcomeType());
  }
}

export const outcomeTypeRoute: Routes = [
  {
    path: '',
    component: OutcomeTypeComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'famonelyApp.outcomeType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: OutcomeTypeDetailComponent,
    resolve: {
      outcomeType: OutcomeTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'famonelyApp.outcomeType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: OutcomeTypeUpdateComponent,
    resolve: {
      outcomeType: OutcomeTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'famonelyApp.outcomeType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: OutcomeTypeUpdateComponent,
    resolve: {
      outcomeType: OutcomeTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'famonelyApp.outcomeType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
