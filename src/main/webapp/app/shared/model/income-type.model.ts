import { IIncomes } from 'app/shared/model/incomes.model';

export interface IIncomeType {
  id?: number;
  nameOfOutcome?: string;
  incomes?: IIncomes[];
}

export class IncomeType implements IIncomeType {
  constructor(public id?: number, public nameOfOutcome?: string, public incomes?: IIncomes[]) {}
}
