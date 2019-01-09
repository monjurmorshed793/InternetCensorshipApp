import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IBlockedWebsite } from 'app/shared/model/blocked-website.model';

type EntityResponseType = HttpResponse<IBlockedWebsite>;
type EntityArrayResponseType = HttpResponse<IBlockedWebsite[]>;

@Injectable({ providedIn: 'root' })
export class BlockedWebsiteService {
    public resourceUrl = SERVER_API_URL + 'api/blocked-websites';

    constructor(protected http: HttpClient) {}

    create(blockedWebsite: IBlockedWebsite): Observable<EntityResponseType> {
        return this.http.post<IBlockedWebsite>(this.resourceUrl, blockedWebsite, { observe: 'response' });
    }

    update(blockedWebsite: IBlockedWebsite): Observable<EntityResponseType> {
        return this.http.put<IBlockedWebsite>(this.resourceUrl, blockedWebsite, { observe: 'response' });
    }

    find(id: string): Observable<EntityResponseType> {
        return this.http.get<IBlockedWebsite>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IBlockedWebsite[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: string): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
