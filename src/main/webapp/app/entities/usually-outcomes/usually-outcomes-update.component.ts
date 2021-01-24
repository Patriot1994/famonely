import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IUsuallyOutcomes, UsuallyOutcomes } from 'app/shared/model/usually-outcomes.model';
import { UsuallyOutcomesService } from './usually-outcomes.service';
import { IOutcomeType } from 'app/shared/model/outcome-type.model';
import { OutcomeTypeService } from 'app/entities/outcome-type/outcome-type.service';

@Component({
  selector: 'jhi-usually-outcomes-update',
  templateUrl: './usually-outcomes-update.component.html',
})
export class UsuallyOutcomesUpdateComponent implements OnInit {
  isSaving = false;
  outcometypes: IOutcomeType[] = [];
  payDayDp: any;

  editForm = this.fb.group({
    id: [],
    payDay: [],
    money: [],
    isAlreadyPaid: [],
    outcomeType: [],
  });

  constructor(
    protected usuallyOutcomesService: UsuallyOutcomesService,
    protected outcomeTypeService: OutcomeTypeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ usuallyOutcomes }) => {
      this.updateForm(usuallyOutcomes);

      this.outcomeTypeService.query().subscribe((res: HttpResponse<IOutcomeType[]>) => (this.outcometypes = res.body || []));
    });
  }

  updateForm(usuallyOutcomes: IUsuallyOutcomes): void {
    this.editForm.patchValue({
      id: usuallyOutcomes.id,
      payDay: usuallyOutcomes.payDay,
      money: usuallyOutcomes.money,
      isAlreadyPaid: usuallyOutcomes.isAlreadyPaid,
      outcomeType: usuallyOutcomes.outcomeType,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const usuallyOutcomes = this.createFromForm();
    if (usuallyOutcomes.id !== undefined) {
      this.subscribeToSaveResponse(this.usuallyOutcomesService.update(usuallyOutcomes));
    } else {
      this.subscribeToSaveResponse(this.usuallyOutcomesService.create(usuallyOutcomes));
    }
  }

  private createFromForm(): IUsuallyOutcomes {
    return {
      ...new UsuallyOutcomes(),
      id: this.editForm.get(['id'])!.value,
      payDay: this.editForm.get(['payDay'])!.value,
      money: this.editForm.get(['money'])!.value,
      isAlreadyPaid: this.editForm.get(['isAlreadyPaid'])!.value,
      outcomeType: this.editForm.get(['outcomeType'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUsuallyOutcomes>>): void {
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
