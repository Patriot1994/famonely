import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUsersFamonely } from 'app/shared/model/users-famonely.model';

@Component({
  selector: 'jhi-users-famonely-detail',
  templateUrl: './users-famonely-detail.component.html',
})
export class UsersFamonelyDetailComponent implements OnInit {
  usersFamonely: IUsersFamonely | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ usersFamonely }) => (this.usersFamonely = usersFamonely));
  }

  previousState(): void {
    window.history.back();
  }
}
