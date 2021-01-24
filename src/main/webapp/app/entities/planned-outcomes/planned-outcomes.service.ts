import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPlannedOutcomes } from 'app/shared/model/planned-outcomes.model';

type EntityResponseType = HttpResponse<IPlannedOutcomes>;
type EntityArrayResponseType = HttpResponse<IPlannedOutcomes[]>;

@Injectable({ providedIn: 'root' })
export class PlannedOutcomesService {
  public resourceUrl = SERVER_API_URL + 'api/planned-outcomes';

  constructor(protected http: HttpClient) {}

  create(plannedOutcomes: IPlannedOutcomes): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(plannedOutcomes);
    return this.http
      .post<IPlannedOutcomes>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(plannedOutcomes: IPlannedOutcomes): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(plannedOutcomes);
    return this.http
      .put<IPlannedOutcomes>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPlannedOutcomes>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPlannedOutcomes[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(plannedOutcomes: IPlannedOutcomes): IPlannedOutcomes {
    const copy: IPlannedOutcomes = Object.assign({}, plannedOutcomes, {
      plannedDate:
        plannedOutcomes.plannedDate && plannedOutcomes.plannedDate.isValid() ? plannedOutcomes.plannedDate.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.plannedDate = res.body.plannedDate ? moment(res.body.plannedDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((plannedOutcomes: IPlannedOutcomes) => {
        plannedOutcomes.plannedDate = plannedOutcomes.plannedDate ? moment(plannedOutcomes.plannedDate) : undefined;
      });
    }
    return res;
  }
}
