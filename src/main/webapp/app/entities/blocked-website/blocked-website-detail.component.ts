import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBlockedWebsite } from 'app/shared/model/blocked-website.model';

@Component({
    selector: 'jhi-blocked-website-detail',
    templateUrl: './blocked-website-detail.component.html'
})
export class BlockedWebsiteDetailComponent implements OnInit {
    blockedWebsite: IBlockedWebsite;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ blockedWebsite }) => {
            this.blockedWebsite = blockedWebsite;
        });
    }

    previousState() {
        window.history.back();
    }
}
