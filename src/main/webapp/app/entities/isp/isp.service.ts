import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IIsp } from 'app/shared/model/isp.model';

type EntityResponseType = HttpResponse<IIsp>;
type EntityArrayResponseType = HttpResponse<IIsp[]>;

@Injectable({ providedIn: 'root' })
export class IspService {
    public resourceUrl = SERVER_API_URL + 'api/isps';

    constructor(protected http: HttpClient) {}

    create(isp: IIsp): Observable<EntityResponseType> {
        return this.http.post<IIsp>(this.resourceUrl, isp, { observe: 'response' });
    }

    update(isp: IIsp): Observable<EntityResponseType> {
        return this.http.put<IIsp>(this.resourceUrl, isp, { observe: 'response' });
    }

    find(id: string): Observable<EntityResponseType> {
        return this.http.get<IIsp>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IIsp[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: string): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
