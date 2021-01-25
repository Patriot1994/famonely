import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IIncomes } from 'app/shared/model/incomes.model';

type EntityResponseType = HttpResponse<IIncomes>;
type EntityArrayResponseType = HttpResponse<IIncomes[]>;

@Injectable({ providedIn: 'root' })
export class RightFamonelyAppService {
  public resourceUrl = SERVER_API_URL + 'api/incomes';

  constructor(protected http: HttpClient) {}

  create(incomes: IIncomes): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(incomes);
    return this.http
      .post<IIncomes>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(incomes: IIncomes): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(incomes);
    return this.http
      .put<IIncomes>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IIncomes>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IIncomes[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(incomes: IIncomes): IIncomes {
    const copy: IIncomes = Object.assign({}, incomes, {
      date: incomes.date && incomes.date.isValid() ? incomes.date.format(DATE_FORMAT) : undefined,
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
      res.body.forEach((incomes: IIncomes) => {
        incomes.date = incomes.date ? moment(incomes.date) : undefined;
      });
    }
    return res;
  }
}
