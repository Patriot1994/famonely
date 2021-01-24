import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IIncomeType } from 'app/shared/model/income-type.model';

type EntityResponseType = HttpResponse<IIncomeType>;
type EntityArrayResponseType = HttpResponse<IIncomeType[]>;

@Injectable({ providedIn: 'root' })
export class IncomeTypeService {
  public resourceUrl = SERVER_API_URL + 'api/income-types';

  constructor(protected http: HttpClient) {}

  create(incomeType: IIncomeType): Observable<EntityResponseType> {
    return this.http.post<IIncomeType>(this.resourceUrl, incomeType, { observe: 'response' });
  }

  update(incomeType: IIncomeType): Observable<EntityResponseType> {
    return this.http.put<IIncomeType>(this.resourceUrl, incomeType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IIncomeType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IIncomeType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
