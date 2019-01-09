import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICensorshipStatus } from 'app/shared/model/censorship-status.model';

type EntityResponseType = HttpResponse<ICensorshipStatus>;
type EntityArrayResponseType = HttpResponse<ICensorshipStatus[]>;

@Injectable({ providedIn: 'root' })
export class CensorshipStatusService {
    public resourceUrl = SERVER_API_URL + 'api/censorship-statuses';

    constructor(protected http: HttpClient) {}

    create(censorshipStatus: ICensorshipStatus): Observable<EntityResponseType> {
        return this.http.post<ICensorshipStatus>(this.resourceUrl, censorshipStatus, { observe: 'response' });
    }

    update(censorshipStatus: ICensorshipStatus): Observable<EntityResponseType> {
        return this.http.put<ICensorshipStatus>(this.resourceUrl, censorshipStatus, { observe: 'response' });
    }

    find(id: string): Observable<EntityResponseType> {
        return this.http.get<ICensorshipStatus>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICensorshipStatus[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: string): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
