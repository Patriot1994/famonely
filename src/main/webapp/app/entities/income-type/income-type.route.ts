import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IIncomeType, IncomeType } from 'app/shared/model/income-type.model';
import { IncomeTypeService } from './income-type.service';
import { IncomeTypeComponent } from './income-type.component';
import { IncomeTypeDetailComponent } from './income-type-detail.component';
import { IncomeTypeUpdateComponent } from './income-type-update.component';

@Injectable({ providedIn: 'root' })
export class IncomeTypeResolve implements Resolve<IIncomeType> {
  constructor(private service: IncomeTypeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IIncomeType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((incomeType: HttpResponse<IncomeType>) => {
          if (incomeType.body) {
            return of(incomeType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new IncomeType());
  }
}

export const incomeTypeRoute: Routes = [
  {
    path: '',
    component: IncomeTypeComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'famonelyApp.incomeType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: IncomeTypeDetailComponent,
    resolve: {
      incomeType: IncomeTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'famonelyApp.incomeType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: IncomeTypeUpdateComponent,
    resolve: {
      incomeType: IncomeTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'famonelyApp.incomeType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: IncomeTypeUpdateComponent,
    resolve: {
      incomeType: IncomeTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'famonelyApp.incomeType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
