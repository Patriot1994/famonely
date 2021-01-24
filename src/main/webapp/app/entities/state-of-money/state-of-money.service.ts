import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IStateOfMoney } from 'app/shared/model/state-of-money.model';

type EntityResponseType = HttpResponse<IStateOfMoney>;
type EntityArrayResponseType = HttpResponse<IStateOfMoney[]>;

@Injectable({ providedIn: 'root' })
export class StateOfMoneyService {
  public resourceUrl = SERVER_API_URL + 'api/state-of-monies';

  constructor(protected http: HttpClient) {}

  create(stateOfMoney: IStateOfMoney): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(stateOfMoney);
    return this.http
      .post<IStateOfMoney>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(stateOfMoney: IStateOfMoney): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(stateOfMoney);
    return this.http
      .put<IStateOfMoney>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IStateOfMoney>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IStateOfMoney[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(stateOfMoney: IStateOfMoney): IStateOfMoney {
    const copy: IStateOfMoney = Object.assign({}, stateOfMoney, {
      date: stateOfMoney.date && stateOfMoney.date.isValid() ? stateOfMoney.date.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.date = res.body.date ? moment(res.body.date) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((stateOfMoney: IStateOfMoney) => {
        stateOfMoney.date = stateOfMoney.date ? moment(stateOfMoney.date) : undefined;
      });
    }
    return res;
  }
}
