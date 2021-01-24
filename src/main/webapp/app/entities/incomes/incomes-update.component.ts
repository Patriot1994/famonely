import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IIncomes, Incomes } from 'app/shared/model/incomes.model';
import { IncomesService } from './incomes.service';
import { IIncomeType } from 'app/shared/model/income-type.model';
import { IncomeTypeService } from 'app/entities/income-type/income-type.service';
import { IUsersFamonely } from 'app/shared/model/users-famonely.model';
import { UsersFamonelyService } from 'app/entities/users-famonely/users-famonely.service';

type SelectableEntity = IIncomeType | IUsersFamonely;

@Component({
  selector: 'jhi-incomes-update',
  templateUrl: './incomes-update.component.html',
})
export class IncomesUpdateComponent implements OnInit {
  isSaving = false;
  incometypes: IIncomeType[] = [];
  usersfamonelies: IUsersFamonely[] = [];
  dateDp: any;

  editForm = this.fb.group({
    id: [],
    date: [],
    spentMoney: [],
    incomeType: [],
    userSpendMoney: [],
  });

  constructor(
    protected incomesService: IncomesService,
    protected incomeTypeService: IncomeTypeService,
    protected usersFamonelyService: UsersFamonelyService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ incomes }) => {
      this.updateForm(incomes);

      this.incomeTypeService.query().subscribe((res: HttpResponse<IIncomeType[]>) => (this.incometypes = res.body || []));

      this.usersFamonelyService.query().subscribe((res: HttpResponse<IUsersFamonely[]>) => (this.usersfamonelies = res.body || []));
    });
  }

  updateForm(incomes: IIncomes): void {
    this.editForm.patchValue({
      id: incomes.id,
      date: incomes.date,
      spentMoney: incomes.spentMoney,
      incomeType: incomes.incomeType,
      userSpendMoney: incomes.userSpendMoney,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const incomes = this.createFromForm();
    if (incomes.id !== undefined) {
      this.subscribeToSaveResponse(this.incomesService.update(incomes));
    } else {
      this.subscribeToSaveResponse(this.incomesService.create(incomes));
    }
  }

  private createFromForm(): IIncomes {
    return {
      ...new Incomes(),
      id: this.editForm.get(['id'])!.value,
      date: this.editForm.get(['date'])!.value,
      spentMoney: this.editForm.get(['spentMoney'])!.value,
      incomeType: this.editForm.get(['incomeType'])!.value,
      userSpendMoney: this.editForm.get(['userSpendMoney'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IIncomes>>): void {
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
