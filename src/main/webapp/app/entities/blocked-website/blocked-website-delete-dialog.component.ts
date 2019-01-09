import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBlockedWebsite } from 'app/shared/model/blocked-website.model';
import { BlockedWebsiteService } from './blocked-website.service';

@Component({
    selector: 'jhi-blocked-website-delete-dialog',
    templateUrl: './blocked-website-delete-dialog.component.html'
})
export class BlockedWebsiteDeleteDialogComponent {
    blockedWebsite: IBlockedWebsite;

    constructor(
        protected blockedWebsiteService: BlockedWebsiteService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: string) {
        this.blockedWebsiteService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'blockedWebsiteListModification',
                content: 'Deleted an blockedWebsite'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-blocked-website-delete-popup',
    template: ''
})
export class BlockedWebsiteDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ blockedWebsite }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(BlockedWebsiteDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.blockedWebsite = blockedWebsite;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
