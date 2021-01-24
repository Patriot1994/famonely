import { Moment } from 'moment';
import { IOutcomeType } from 'app/shared/model/outcome-type.model';

export interface IUsuallyOutcomes {
  id?: number;
  payDay?: Moment;
  money?: number;
  isAlreadyPaid?: boolean;
  outcomeType?: IOutcomeType;
}

export class UsuallyOutcomes implements IUsuallyOutcomes {
  constructor(
    public id?: number,
    public payDay?: Moment,
    public money?: number,
    public isAlreadyPaid?: boolean,
    public outcomeType?: IOutcomeType
  ) {
    this.isAlreadyPaid = this.isAlreadyPaid || false;
  }
}
