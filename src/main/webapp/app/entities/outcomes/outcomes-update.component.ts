import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IOutcomes, Outcomes } from 'app/shared/model/outcomes.model';
import { OutcomesService } from './outcomes.service';
import { IOutcomeType } from 'app/shared/model/outcome-type.model';
import { OutcomeTypeService } from 'app/entities/outcome-type/outcome-type.service';
import { IUsersFamonely } from 'app/shared/model/users-famonely.model';
import { UsersFamonelyService } from 'app/entities/users-famonely/users-famonely.service';

type SelectableEntity = IOutcomeType | IUsersFamonely;

@Component({
  selector: 'jhi-outcomes-update',
  templateUrl: './outcomes-update.component.html',
})
export class OutcomesUpdateComponent implements OnInit {
  isSaving = false;
  outcometypes: IOutcomeType[] = [];
  usersfamonelies: IUsersFamonely[] = [];
  dateDp: any;

  editForm = this.fb.group({
    id: [],
    date: [],
    spentMoney: [],
    isMonthlyOutCome: [],
    isPlannedOutCome: [],
    outcomeType: [],
    userSpendMoney: [],
  });

  constructor(
    protected outcomesService: OutcomesService,
    protected outcomeTypeService: OutcomeTypeService,
    protected usersFamonelyService: UsersFamonelyService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ outcomes }) => {
      this.updateForm(outcomes);

      this.outcomeTypeService.query().subscribe((res: HttpResponse<IOutcomeType[]>) => (this.outcometypes = res.body || []));

      this.usersFamonelyService.query().subscribe((res: HttpResponse<IUsersFamonely[]>) => (this.usersfamonelies = res.body || []));
    });
  }

  updateForm(outcomes: IOutcomes): void {
    this.editForm.patchValue({
      id: outcomes.id,
      date: outcomes.date,
      spentMoney: outcomes.spentMoney,
      isMonthlyOutCome: outcomes.isMonthlyOutCome,
      isPlannedOutCome: outcomes.isPlannedOutCome,
      outcomeType: outcomes.outcomeType,
      userSpendMoney: outcomes.userSpendMoney,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const outcomes = this.createFromForm();
    if (outcomes.id !== undefined) {
      this.subscribeToSaveResponse(this.outcomesService.update(outcomes));
    } else {
      this.subscribeToSaveResponse(this.outcomesService.create(outcomes));
    }
  }

  private createFromForm(): IOutcomes {
    return {
      ...new Outcomes(),
      id: this.editForm.get(['id'])!.value,
      date: this.editForm.get(['date'])!.value,
      spentMoney: this.editForm.get(['spentMoney'])!.value,
      isMonthlyOutCome: this.editForm.get(['isMonthlyOutCome'])!.value,
      isPlannedOutCome: this.editForm.get(['isPlannedOutCome'])!.value,
      outcomeType: this.editForm.get(['outcomeType'])!.value,
      userSpendMoney: this.editForm.get(['userSpendMoney'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOutcomes>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
