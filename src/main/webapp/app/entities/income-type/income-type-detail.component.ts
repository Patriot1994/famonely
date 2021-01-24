import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IIncomeType } from 'app/shared/model/income-type.model';

@Component({
  selector: 'jhi-income-type-detail',
  templateUrl: './income-type-detail.component.html',
})
export class IncomeTypeDetailComponent implements OnInit {
  incomeType: IIncomeType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ incomeType }) => (this.incomeType = incomeType));
  }

  previousState(): void {
    window.history.back();
  }
}
