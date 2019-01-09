import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IIsp } from 'app/shared/model/isp.model';
import { IspService } from './isp.service';

@Component({
    selector: 'jhi-isp-update',
    templateUrl: './isp-update.component.html'
})
export class IspUpdateComponent implements OnInit {
    isp: IIsp;
    isSaving: boolean;

    constructor(protected ispService: IspService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ isp }) => {
            this.isp = isp;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.isp.id !== undefined) {
            this.subscribeToSaveResponse(this.ispService.update(this.isp));
        } else {
            this.subscribeToSaveResponse(this.ispService.create(this.isp));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IIsp>>) {
        result.subscribe((res: HttpResponse<IIsp>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
