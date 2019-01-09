import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IIsp } from 'app/shared/model/isp.model';

@Component({
    selector: 'jhi-isp-detail',
    templateUrl: './isp-detail.component.html'
})
export class IspDetailComponent implements OnInit {
    isp: IIsp;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ isp }) => {
            this.isp = isp;
        });
    }

    previousState() {
        window.history.back();
    }
}
