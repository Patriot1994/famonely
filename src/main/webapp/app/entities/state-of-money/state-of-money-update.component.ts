import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IStateOfMoney, StateOfMoney } from 'app/shared/model/state-of-money.model';
import { StateOfMoneyService } from './state-of-money.service';

@Component({
  selector: 'jhi-state-of-money-update',
  templateUrl: './state-of-money-update.component.html',
})
export class StateOfMoneyUpdateComponent implements OnInit {
  isSaving = false;
  dateDp: any;

  editForm = this.fb.group({
    id: [],
    date: [],
    money: [],
  });

  constructor(protected stateOfMoneyService: StateOfMoneyService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ stateOfMoney }) => {
      this.updateForm(stateOfMoney);
    });
  }

  updateForm(stateOfMoney: IStateOfMoney): void {
    this.editForm.patchValue({
      id: stateOfMoney.id,
      date: stateOfMoney.date,
      money: stateOfMoney.money,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const stateOfMoney = this.createFromForm();
    if (stateOfMoney.id !== undefined) {
      this.subscribeToSaveResponse(this.stateOfMoneyService.update(stateOfMoney));
    } else {
      this.subscribeToSaveResponse(this.stateOfMoneyService.create(stateOfMoney));
    }
  }

  private createFromForm(): IStateOfMoney {
    return {
      ...new StateOfMoney(),
      id: this.editForm.get(['id'])!.value,
      date: this.editForm.get(['date'])!.value,
      money: this.editForm.get(['money'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStateOfMoney>>): void {
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
