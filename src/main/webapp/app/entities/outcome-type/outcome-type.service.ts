import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IOutcomeType } from 'app/shared/model/outcome-type.model';

type EntityResponseType = HttpResponse<IOutcomeType>;
type EntityArrayResponseType = HttpResponse<IOutcomeType[]>;

@Injectable({ providedIn: 'root' })
export class OutcomeTypeService {
  public resourceUrl = SERVER_API_URL + 'api/outcome-types';

  constructor(protected http: HttpClient) {}

  create(outcomeType: IOutcomeType): Observable<EntityResponseType> {
    return this.http.post<IOutcomeType>(this.resourceUrl, outcomeType, { observe: 'response' });
  }

  update(outcomeType: IOutcomeType): Observable<EntityResponseType> {
    return this.http.put<IOutcomeType>(this.resourceUrl, outcomeType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IOutcomeType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IOutcomeType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
