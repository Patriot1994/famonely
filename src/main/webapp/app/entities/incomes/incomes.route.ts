import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IIncomes, Incomes } from 'app/shared/model/incomes.model';
import { IncomesService } from './incomes.service';
import { IncomesComponent } from './incomes.component';
import { IncomesDetailComponent } from './incomes-detail.component';
import { IncomesUpdateComponent } from './incomes-update.component';

@Injectable({ providedIn: 'root' })
export class IncomesResolve implements Resolve<IIncomes> {
  constructor(private service: IncomesService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IIncomes> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((incomes: HttpResponse<Incomes>) => {
          if (incomes.body) {
            return of(incomes.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Incomes());
  }
}

export const incomesRoute: Routes = [
  {
    path: '',
    component: IncomesComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'famonelyApp.incomes.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: IncomesDetailComponent,
    resolve: {
      incomes: IncomesResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'famonelyApp.incomes.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: IncomesUpdateComponent,
    resolve: {
      incomes: IncomesResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'famonelyApp.incomes.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: IncomesUpdateComponent,
    resolve: {
      incomes: IncomesResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'famonelyApp.incomes.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
