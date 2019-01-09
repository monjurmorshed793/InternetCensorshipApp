import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IBlockedWebsite } from 'app/shared/model/blocked-website.model';
import { BlockedWebsiteService } from './blocked-website.service';

@Component({
    selector: 'jhi-blocked-website-update',
    templateUrl: './blocked-website-update.component.html'
})
export class BlockedWebsiteUpdateComponent implements OnInit {
    blockedWebsite: IBlockedWebsite;
    isSaving: boolean;

    constructor(protected blockedWebsiteService: BlockedWebsiteService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ blockedWebsite }) => {
            this.blockedWebsite = blockedWebsite;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.blockedWebsite.id !== undefined) {
            this.subscribeToSaveResponse(this.blockedWebsiteService.update(this.blockedWebsite));
        } else {
            this.subscribeToSaveResponse(this.blockedWebsiteService.create(this.blockedWebsite));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IBlockedWebsite>>) {
        result.subscribe((res: HttpResponse<IBlockedWebsite>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
