import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { InternetCensorshipSharedModule } from 'app/shared';
import {
    BlockedWebsiteComponent,
    BlockedWebsiteDetailComponent,
    BlockedWebsiteUpdateComponent,
    BlockedWebsiteDeletePopupComponent,
    BlockedWebsiteDeleteDialogComponent,
    blockedWebsiteRoute,
    blockedWebsitePopupRoute
} from './';

const ENTITY_STATES = [...blockedWebsiteRoute, ...blockedWebsitePopupRoute];

@NgModule({
    imports: [InternetCensorshipSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        BlockedWebsiteComponent,
        BlockedWebsiteDetailComponent,
        BlockedWebsiteUpdateComponent,
        BlockedWebsiteDeleteDialogComponent,
        BlockedWebsiteDeletePopupComponent
    ],
    entryComponents: [
        BlockedWebsiteComponent,
        BlockedWebsiteUpdateComponent,
        BlockedWebsiteDeleteDialogComponent,
        BlockedWebsiteDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class InternetCensorshipBlockedWebsiteModule {}
