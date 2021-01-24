import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOutcomes } from 'app/shared/model/outcomes.model';

@Component({
  selector: 'jhi-outcomes-detail',
  templateUrl: './outcomes-detail.component.html',
})
export class OutcomesDetailComponent implements OnInit {
  outcomes: IOutcomes | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ outcomes }) => (this.outcomes = outcomes));
  }

  previousState(): void {
    window.history.back();
  }
}
