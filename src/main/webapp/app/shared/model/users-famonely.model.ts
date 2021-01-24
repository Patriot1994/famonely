import { IOutcomes } from 'app/shared/model/outcomes.model';
import { IIncomes } from 'app/shared/model/incomes.model';

export interface IUsersFamonely {
  id?: number;
  name?: string;
  surname?: string;
  sweetShortname?: string;
  outcomes?: IOutcomes[];
  incomes?: IIncomes[];
}

export class UsersFamonely implements IUsersFamonely {
  constructor(
    public id?: number,
    public name?: string,
    public surname?: string,
    public sweetShortname?: string,
    public outcomes?: IOutcomes[],
    public incomes?: IIncomes[]
  ) {}
}
