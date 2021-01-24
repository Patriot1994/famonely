import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IUsuallyOutcomes } from 'app/shared/model/usually-outcomes.model';

type EntityResponseType = HttpResponse<IUsuallyOutcomes>;
type EntityArrayResponseType = HttpResponse<IUsuallyOutcomes[]>;

@Injectable({ providedIn: 'root' })
export class UsuallyOutcomesService {
  public resourceUrl = SERVER_API_URL + 'api/usually-outcomes';

  constructor(protected http: HttpClient) {}

  create(usuallyOutcomes: IUsuallyOutcomes): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(usuallyOutcomes);
    return this.http
      .post<IUsuallyOutcomes>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(usuallyOutcomes: IUsuallyOutcomes): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(usuallyOutcomes);
    return this.http
      .put<IUsuallyOutcomes>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IUsuallyOutcomes>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IUsuallyOutcomes[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(usuallyOutcomes: IUsuallyOutcomes): IUsuallyOutcomes {
    const copy: IUsuallyOutcomes = Object.assign({}, usuallyOutcomes, {
      payDay: usuallyOutcomes.payDay && usuallyOutcomes.payDay.isValid() ? usuallyOutcomes.payDay.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.payDay = res.body.payDay ? moment(res.body.payDay) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((usuallyOutcomes: IUsuallyOutcomes) => {
        usuallyOutcomes.payDay = usuallyOutcomes.payDay ? moment(usuallyOutcomes.payDay) : undefined;
      });
    }
    return res;
  }
}
