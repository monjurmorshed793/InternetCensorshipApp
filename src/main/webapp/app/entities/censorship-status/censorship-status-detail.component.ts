import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICensorshipStatus } from 'app/shared/model/censorship-status.model';

@Component({
    selector: 'jhi-censorship-status-detail',
    templateUrl: './censorship-status-detail.component.html'
})
export class CensorshipStatusDetailComponent implements OnInit {
    censorshipStatus: ICensorshipStatus;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ censorshipStatus }) => {
            this.censorshipStatus = censorshipStatus;
        });
    }

    previousState() {
        window.history.back();
    }
}
