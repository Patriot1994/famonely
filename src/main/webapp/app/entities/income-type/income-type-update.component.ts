import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IIncomeType, IncomeType } from 'app/shared/model/income-type.model';
import { IncomeTypeService } from './income-type.service';

@Component({
  selector: 'jhi-income-type-update',
  templateUrl: './income-type-update.component.html',
})
export class IncomeTypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nameOfOutcome: [],
  });

  constructor(protected incomeTypeService: IncomeTypeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ incomeType }) => {
      this.updateForm(incomeType);
    });
  }

  updateForm(incomeType: IIncomeType): void {
    this.editForm.patchValue({
      id: incomeType.id,
      nameOfOutcome: incomeType.nameOfOutcome,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const incomeType = this.createFromForm();
    if (incomeType.id !== undefined) {
      this.subscribeToSaveResponse(this.incomeTypeService.update(incomeType));
    } else {
      this.subscribeToSaveResponse(this.incomeTypeService.create(incomeType));
    }
  }

  private createFromForm(): IIncomeType {
    return {
      ...new IncomeType(),
      id: this.editForm.get(['id'])!.value,
      nameOfOutcome: this.editForm.get(['nameOfOutcome'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IIncomeType>>): void {
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
}
