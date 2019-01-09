import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { BlockedWebsite } from 'app/shared/model/blocked-website.model';
import { BlockedWebsiteService } from './blocked-website.service';
import { BlockedWebsiteComponent } from './blocked-website.component';
import { BlockedWebsiteDetailComponent } from './blocked-website-detail.component';
import { BlockedWebsiteUpdateComponent } from './blocked-website-update.component';
import { BlockedWebsiteDeletePopupComponent } from './blocked-website-delete-dialog.component';
import { IBlockedWebsite } from 'app/shared/model/blocked-website.model';

@Injectable({ providedIn: 'root' })
export class BlockedWebsiteResolve implements Resolve<IBlockedWebsite> {
    constructor(private service: BlockedWebsiteService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<BlockedWebsite> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<BlockedWebsite>) => response.ok),
                map((blockedWebsite: HttpResponse<BlockedWebsite>) => blockedWebsite.body)
            );
        }
        return of(new BlockedWebsite());
    }
}

export const blockedWebsiteRoute: Routes = [
    {
        path: 'blocked-website',
        component: BlockedWebsiteComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BlockedWebsites'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'blocked-website/:id/view',
        component: BlockedWebsiteDetailComponent,
        resolve: {
            blockedWebsite: BlockedWebsiteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BlockedWebsites'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'blocked-website/new',
        component: BlockedWebsiteUpdateComponent,
        resolve: {
            blockedWebsite: BlockedWebsiteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BlockedWebsites'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'blocked-website/:id/edit',
        component: BlockedWebsiteUpdateComponent,
        resolve: {
            blockedWebsite: BlockedWebsiteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BlockedWebsites'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const blockedWebsitePopupRoute: Routes = [
    {
        path: 'blocked-website/:id/delete',
        component: BlockedWebsiteDeletePopupComponent,
        resolve: {
            blockedWebsite: BlockedWebsiteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BlockedWebsites'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
