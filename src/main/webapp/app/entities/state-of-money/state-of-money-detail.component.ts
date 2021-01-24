import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStateOfMoney } from 'app/shared/model/state-of-money.model';

@Component({
  selector: 'jhi-state-of-money-detail',
  templateUrl: './state-of-money-detail.component.html',
})
export class StateOfMoneyDetailComponent implements OnInit {
  stateOfMoney: IStateOfMoney | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ stateOfMoney }) => (this.stateOfMoney = stateOfMoney));
  }

  previousState(): void {
    window.history.back();
  }
}
