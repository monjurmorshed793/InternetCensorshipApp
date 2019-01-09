import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ICensorshipStatus } from 'app/shared/model/censorship-status.model';
import { CensorshipStatusService } from './censorship-status.service';

@Component({
    selector: 'jhi-censorship-status-update',
    templateUrl: './censorship-status-update.component.html'
})
export class CensorshipStatusUpdateComponent implements OnInit {
    censorshipStatus: ICensorshipStatus;
    isSaving: boolean;

    constructor(protected censorshipStatusService: CensorshipStatusService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ censorshipStatus }) => {
            this.censorshipStatus = censorshipStatus;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.censorshipStatus.id !== undefined) {
            this.subscribeToSaveResponse(this.censorshipStatusService.update(this.censorshipStatus));
        } else {
            this.subscribeToSaveResponse(this.censorshipStatusService.create(this.censorshipStatus));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICensorshipStatus>>) {
        result.subscribe((res: HttpResponse<ICensorshipStatus>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
