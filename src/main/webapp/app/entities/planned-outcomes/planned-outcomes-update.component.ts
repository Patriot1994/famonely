import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPlannedOutcomes, PlannedOutcomes } from 'app/shared/model/planned-outcomes.model';
import { PlannedOutcomesService } from './planned-outcomes.service';
import { IOutcomeType } from 'app/shared/model/outcome-type.model';
import { OutcomeTypeService } from 'app/entities/outcome-type/outcome-type.service';

@Component({
  selector: 'jhi-planned-outcomes-update',
  templateUrl: './planned-outcomes-update.component.html',
})
export class PlannedOutcomesUpdateComponent implements OnInit {
  isSaving = false;
  outcometypes: IOutcomeType[] = [];
  plannedDateDp: any;

  editForm = this.fb.group({
    id: [],
    plannedDate: [],
    plannedMoney: [],
    moneyToGoal: [],
    isAlreadyPaid: [],
    outcomeType: [],
  });

  constructor(
    protected plannedOutcomesService: PlannedOutcomesService,
    protected outcomeTypeService: OutcomeTypeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ plannedOutcomes }) => {
      this.updateForm(plannedOutcomes);

      this.outcomeTypeService.query().subscribe((res: HttpResponse<IOutcomeType[]>) => (this.outcometypes = res.body || []));
    });
  }

  updateForm(plannedOutcomes: IPlannedOutcomes): void {
    this.editForm.patchValue({
      id: plannedOutcomes.id,
      plannedDate: plannedOutcomes.plannedDate,
      plannedMoney: plannedOutcomes.plannedMoney,
      moneyToGoal: plannedOutcomes.moneyToGoal,
      isAlreadyPaid: plannedOutcomes.isAlreadyPaid,
      outcomeType: plannedOutcomes.outcomeType,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const plannedOutcomes = this.createFromForm();
    if (plannedOutcomes.id !== undefined) {
      this.subscribeToSaveResponse(this.plannedOutcomesService.update(plannedOutcomes));
    } else {
      this.subscribeToSaveResponse(this.plannedOutcomesService.create(plannedOutcomes));
    }
  }

  private createFromForm(): IPlannedOutcomes {
    return {
      ...new PlannedOutcomes(),
      id: this.editForm.get(['id'])!.value,
      plannedDate: this.editForm.get(['plannedDate'])!.value,
      plannedMoney: this.editForm.get(['plannedMoney'])!.value,
      moneyToGoal: this.editForm.get(['moneyToGoal'])!.value,
      isAlreadyPaid: this.editForm.get(['isAlreadyPaid'])!.value,
      outcomeType: this.editForm.get(['outcomeType'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPlannedOutcomes>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IOutcomeType): any {
    return item.id;
  }
}
