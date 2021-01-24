import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IOutcomes } from 'app/shared/model/outcomes.model';

type EntityResponseType = HttpResponse<IOutcomes>;
type EntityArrayResponseType = HttpResponse<IOutcomes[]>;

@Injectable({ providedIn: 'root' })
export class OutcomesService {
  public resourceUrl = SERVER_API_URL + 'api/outcomes';

  constructor(protected http: HttpClient) {}

  create(outcomes: IOutcomes): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(outcomes);
    return this.http
      .post<IOutcomes>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(outcomes: IOutcomes): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(outcomes);
    return this.http
      .put<IOutcomes>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IOutcomes>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IOutcomes[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(outcomes: IOutcomes): IOutcomes {
    const copy: IOutcomes = Object.assign({}, outcomes, {
      date: outcomes.date && outcomes.date.isValid() ? outcomes.date.format(DATE_FORMAT) : undefined,
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
      res.body.forEach((outcomes: IOutcomes) => {
        outcomes.date = outcomes.date ? moment(outcomes.date) : undefined;
      });
    }
    return res;
  }
}
