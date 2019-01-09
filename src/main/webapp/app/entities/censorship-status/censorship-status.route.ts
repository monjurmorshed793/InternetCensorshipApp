import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CensorshipStatus } from 'app/shared/model/censorship-status.model';
import { CensorshipStatusService } from './censorship-status.service';
import { CensorshipStatusComponent } from './censorship-status.component';
import { CensorshipStatusDetailComponent } from './censorship-status-detail.component';
import { CensorshipStatusUpdateComponent } from './censorship-status-update.component';
import { CensorshipStatusDeletePopupComponent } from './censorship-status-delete-dialog.component';
import { ICensorshipStatus } from 'app/shared/model/censorship-status.model';

@Injectable({ providedIn: 'root' })
export class CensorshipStatusResolve implements Resolve<ICensorshipStatus> {
    constructor(private service: CensorshipStatusService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<CensorshipStatus> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<CensorshipStatus>) => response.ok),
                map((censorshipStatus: HttpResponse<CensorshipStatus>) => censorshipStatus.body)
            );
        }
        return of(new CensorshipStatus());
    }
}

export const censorshipStatusRoute: Routes = [
    {
        path: 'censorship-status',
        component: CensorshipStatusComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CensorshipStatuses'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'censorship-status/:id/view',
        component: CensorshipStatusDetailComponent,
        resolve: {
            censorshipStatus: CensorshipStatusResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CensorshipStatuses'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'censorship-status/new',
        component: CensorshipStatusUpdateComponent,
        resolve: {
            censorshipStatus: CensorshipStatusResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CensorshipStatuses'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'censorship-status/:id/edit',
        component: CensorshipStatusUpdateComponent,
        resolve: {
            censorshipStatus: CensorshipStatusResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CensorshipStatuses'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const censorshipStatusPopupRoute: Routes = [
    {
        path: 'censorship-status/:id/delete',
        component: CensorshipStatusDeletePopupComponent,
        resolve: {
            censorshipStatus: CensorshipStatusResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CensorshipStatuses'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
