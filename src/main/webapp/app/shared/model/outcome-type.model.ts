import { IOutcomes } from 'app/shared/model/outcomes.model';
import { IPlannedOutcomes } from 'app/shared/model/planned-outcomes.model';
import { IUsuallyOutcomes } from 'app/shared/model/usually-outcomes.model';

export interface IOutcomeType {
  id?: number;
  nameOfOutcome?: string;
  outcomes?: IOutcomes[];
  plannedOutcomes?: IPlannedOutcomes[];
  usuallyOutcomes?: IUsuallyOutcomes[];
}

export class OutcomeType implements IOutcomeType {
  constructor(
    public id?: number,
    public nameOfOutcome?: string,
    public outcomes?: IOutcomes[],
    public plannedOutcomes?: IPlannedOutcomes[],
    public usuallyOutcomes?: IUsuallyOutcomes[]
  ) {}
}
