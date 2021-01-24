import { Moment } from 'moment';

export interface IStateOfMoney {
  id?: number;
  date?: Moment;
  money?: number;
}

export class StateOfMoney implements IStateOfMoney {
  constructor(public id?: number, public date?: Moment, public money?: number) {}
}
