import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IOutcomeType, OutcomeType } from 'app/shared/model/outcome-type.model';
import { OutcomeTypeService } from './outcome-type.service';

@Component({
  selector: 'jhi-outcome-type-update',
  templateUrl: './outcome-type-update.component.html',
})
export class OutcomeTypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nameOfOutcome: [],
  });

  constructor(protected outcomeTypeService: OutcomeTypeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ outcomeType }) => {
      this.updateForm(outcomeType);
    });
  }

  updateForm(outcomeType: IOutcomeType): void {
    this.editForm.patchValue({
      id: outcomeType.id,
      nameOfOutcome: outcomeType.nameOfOutcome,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const outcomeType = this.createFromForm();
    if (outcomeType.id !== undefined) {
      this.subscribeToSaveResponse(this.outcomeTypeService.update(outcomeType));
    } else {
      this.subscribeToSaveResponse(this.outcomeTypeService.create(outcomeType));
    }
  }

  private createFromForm(): IOutcomeType {
    return {
      ...new OutcomeType(),
      id: this.editForm.get(['id'])!.value,
      nameOfOutcome: this.editForm.get(['nameOfOutcome'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOutcomeType>>): void {
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
