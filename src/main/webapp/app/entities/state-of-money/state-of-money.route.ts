import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IStateOfMoney, StateOfMoney } from 'app/shared/model/state-of-money.model';
import { StateOfMoneyService } from './state-of-money.service';
import { StateOfMoneyComponent } from './state-of-money.component';
import { StateOfMoneyDetailComponent } from './state-of-money-detail.component';
import { StateOfMoneyUpdateComponent } from './state-of-money-update.component';

@Injectable({ providedIn: 'root' })
export class StateOfMoneyResolve implements Resolve<IStateOfMoney> {
  constructor(private service: StateOfMoneyService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IStateOfMoney> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((stateOfMoney: HttpResponse<StateOfMoney>) => {
          if (stateOfMoney.body) {
            return of(stateOfMoney.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new StateOfMoney());
  }
}

export const stateOfMoneyRoute: Routes = [
  {
    path: '',
    component: StateOfMoneyComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'famonelyApp.stateOfMoney.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: StateOfMoneyDetailComponent,
    resolve: {
      stateOfMoney: StateOfMoneyResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'famonelyApp.stateOfMoney.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: StateOfMoneyUpdateComponent,
    resolve: {
      stateOfMoney: StateOfMoneyResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'famonelyApp.stateOfMoney.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: StateOfMoneyUpdateComponent,
    resolve: {
      stateOfMoney: StateOfMoneyResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'famonelyApp.stateOfMoney.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
