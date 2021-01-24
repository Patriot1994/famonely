import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPlannedOutcomes } from 'app/shared/model/planned-outcomes.model';

@Component({
  selector: 'jhi-planned-outcomes-detail',
  templateUrl: './planned-outcomes-detail.component.html',
})
export class PlannedOutcomesDetailComponent implements OnInit {
  plannedOutcomes: IPlannedOutcomes | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ plannedOutcomes }) => (this.plannedOutcomes = plannedOutcomes));
  }

  previousState(): void {
    window.history.back();
  }
}
