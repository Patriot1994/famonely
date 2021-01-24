import { Moment } from 'moment';
import { IOutcomeType } from 'app/shared/model/outcome-type.model';

export interface IPlannedOutcomes {
  id?: number;
  plannedDate?: Moment;
  plannedMoney?: number;
  moneyToGoal?: number;
  isAlreadyPaid?: boolean;
  outcomeType?: IOutcomeType;
}

export class PlannedOutcomes implements IPlannedOutcomes {
  constructor(
    public id?: number,
    public plannedDate?: Moment,
    public plannedMoney?: number,
    public moneyToGoal?: number,
    public isAlreadyPaid?: boolean,
    public outcomeType?: IOutcomeType
  ) {
    this.isAlreadyPaid = this.isAlreadyPaid || false;
  }
}
