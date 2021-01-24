import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IUsersFamonely, UsersFamonely } from 'app/shared/model/users-famonely.model';
import { UsersFamonelyService } from './users-famonely.service';

@Component({
  selector: 'jhi-users-famonely-update',
  templateUrl: './users-famonely-update.component.html',
})
export class UsersFamonelyUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
    surname: [],
    sweetShortname: [],
  });

  constructor(protected usersFamonelyService: UsersFamonelyService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ usersFamonely }) => {
      this.updateForm(usersFamonely);
    });
  }

  updateForm(usersFamonely: IUsersFamonely): void {
    this.editForm.patchValue({
      id: usersFamonely.id,
      name: usersFamonely.name,
      surname: usersFamonely.surname,
      sweetShortname: usersFamonely.sweetShortname,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const usersFamonely = this.createFromForm();
    if (usersFamonely.id !== undefined) {
      this.subscribeToSaveResponse(this.usersFamonelyService.update(usersFamonely));
    } else {
      this.subscribeToSaveResponse(this.usersFamonelyService.create(usersFamonely));
    }
  }

  private createFromForm(): IUsersFamonely {
    return {
      ...new UsersFamonely(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      surname: this.editForm.get(['surname'])!.value,
      sweetShortname: this.editForm.get(['sweetShortname'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUsersFamonely>>): void {
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
