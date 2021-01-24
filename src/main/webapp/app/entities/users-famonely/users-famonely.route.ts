import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IUsersFamonely, UsersFamonely } from 'app/shared/model/users-famonely.model';
import { UsersFamonelyService } from './users-famonely.service';
import { UsersFamonelyComponent } from './users-famonely.component';
import { UsersFamonelyDetailComponent } from './users-famonely-detail.component';
import { UsersFamonelyUpdateComponent } from './users-famonely-update.component';

@Injectable({ providedIn: 'root' })
export class UsersFamonelyResolve implements Resolve<IUsersFamonely> {
  constructor(private service: UsersFamonelyService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUsersFamonely> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((usersFamonely: HttpResponse<UsersFamonely>) => {
          if (usersFamonely.body) {
            return of(usersFamonely.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new UsersFamonely());
  }
}

export const usersFamonelyRoute: Routes = [
  {
    path: '',
    component: UsersFamonelyComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'famonelyApp.usersFamonely.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: UsersFamonelyDetailComponent,
    resolve: {
      usersFamonely: UsersFamonelyResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'famonelyApp.usersFamonely.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: UsersFamonelyUpdateComponent,
    resolve: {
      usersFamonely: UsersFamonelyResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'famonelyApp.usersFamonely.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: UsersFamonelyUpdateComponent,
    resolve: {
      usersFamonely: UsersFamonelyResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'famonelyApp.usersFamonely.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
