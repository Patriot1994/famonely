import { Moment } from 'moment';
import { IIncomeType } from 'app/shared/model/income-type.model';
import { IUsersFamonely } from 'app/shared/model/users-famonely.model';

export interface IIncomes {
  id?: number;
  date?: Moment;
  spentMoney?: number;
  incomeType?: IIncomeType;
  userSpendMoney?: IUsersFamonely;
}

export class Incomes implements IIncomes {
  constructor(
    public id?: number,
    public date?: Moment,
    public spentMoney?: number,
    public incomeType?: IIncomeType,
    public userSpendMoney?: IUsersFamonely
  ) {}
}
