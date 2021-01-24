import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUsuallyOutcomes } from 'app/shared/model/usually-outcomes.model';

@Component({
  selector: 'jhi-usually-outcomes-detail',
  templateUrl: './usually-outcomes-detail.component.html',
})
export class UsuallyOutcomesDetailComponent implements OnInit {
  usuallyOutcomes: IUsuallyOutcomes | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ usuallyOutcomes }) => (this.usuallyOutcomes = usuallyOutcomes));
  }

  previousState(): void {
    window.history.back();
  }
}
