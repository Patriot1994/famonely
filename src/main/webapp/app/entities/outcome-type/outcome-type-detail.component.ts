import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOutcomeType } from 'app/shared/model/outcome-type.model';

@Component({
  selector: 'jhi-outcome-type-detail',
  templateUrl: './outcome-type-detail.component.html',
})
export class OutcomeTypeDetailComponent implements OnInit {
  outcomeType: IOutcomeType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ outcomeType }) => (this.outcomeType = outcomeType));
  }

  previousState(): void {
    window.history.back();
  }
}
