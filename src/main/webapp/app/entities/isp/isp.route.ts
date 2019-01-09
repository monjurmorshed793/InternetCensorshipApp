import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Isp } from 'app/shared/model/isp.model';
import { IspService } from './isp.service';
import { IspComponent } from './isp.component';
import { IspDetailComponent } from './isp-detail.component';
import { IspUpdateComponent } from './isp-update.component';
import { IspDeletePopupComponent } from './isp-delete-dialog.component';
import { IIsp } from 'app/shared/model/isp.model';

@Injectable({ providedIn: 'root' })
export class IspResolve implements Resolve<IIsp> {
    constructor(private service: IspService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Isp> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Isp>) => response.ok),
                map((isp: HttpResponse<Isp>) => isp.body)
            );
        }
        return of(new Isp());
    }
}

export const ispRoute: Routes = [
    {
        path: 'isp',
        component: IspComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Isps'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'isp/:id/view',
        component: IspDetailComponent,
        resolve: {
            isp: IspResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Isps'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'isp/new',
        component: IspUpdateComponent,
        resolve: {
            isp: IspResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Isps'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'isp/:id/edit',
        component: IspUpdateComponent,
        resolve: {
            isp: IspResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Isps'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const ispPopupRoute: Routes = [
    {
        path: 'isp/:id/delete',
        component: IspDeletePopupComponent,
        resolve: {
            isp: IspResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Isps'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
