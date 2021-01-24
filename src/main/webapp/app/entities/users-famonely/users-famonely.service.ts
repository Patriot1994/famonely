import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IUsersFamonely } from 'app/shared/model/users-famonely.model';

type EntityResponseType = HttpResponse<IUsersFamonely>;
type EntityArrayResponseType = HttpResponse<IUsersFamonely[]>;

@Injectable({ providedIn: 'root' })
export class UsersFamonelyService {
  public resourceUrl = SERVER_API_URL + 'api/users-famonelies';

  constructor(protected http: HttpClient) {}

  create(usersFamonely: IUsersFamonely): Observable<EntityResponseType> {
    return this.http.post<IUsersFamonely>(this.resourceUrl, usersFamonely, { observe: 'response' });
  }

  update(usersFamonely: IUsersFamonely): Observable<EntityResponseType> {
    return this.http.put<IUsersFamonely>(this.resourceUrl, usersFamonely, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IUsersFamonely>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IUsersFamonely[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
