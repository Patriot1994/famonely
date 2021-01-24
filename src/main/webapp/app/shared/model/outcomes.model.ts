import { Moment } from 'moment';
import { IOutcomeType } from 'app/shared/model/outcome-type.model';
import { IUsersFamonely } from 'app/shared/model/users-famonely.model';

export interface IOutcomes {
  id?: number;
  date?: Moment;
  spentMoney?: number;
  isMonthlyOutCome?: boolean;
  isPlannedOutCome?: boolean;
  outcomeType?: IOutcomeType;
  userSpendMoney?: IUsersFamonely;
}

export class Outcomes implements IOutcomes {
  constructor(
    public id?: number,
    public date?: Moment,
    public spentMoney?: number,
    public isMonthlyOutCome?: boolean,
    public isPlannedOutCome?: boolean,
    public outcomeType?: IOutcomeType,
    public userSpendMoney?: IUsersFamonely
  ) {
    this.isMonthlyOutCome = this.isMonthlyOutCome || false;
    this.isPlannedOutCome = this.isPlannedOutCome || false;
  }
}
