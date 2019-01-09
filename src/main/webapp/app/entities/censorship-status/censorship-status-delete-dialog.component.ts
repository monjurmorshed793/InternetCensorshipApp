import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICensorshipStatus } from 'app/shared/model/censorship-status.model';
import { CensorshipStatusService } from './censorship-status.service';

@Component({
    selector: 'jhi-censorship-status-delete-dialog',
    templateUrl: './censorship-status-delete-dialog.component.html'
})
export class CensorshipStatusDeleteDialogComponent {
    censorshipStatus: ICensorshipStatus;

    constructor(
        protected censorshipStatusService: CensorshipStatusService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: string) {
        this.censorshipStatusService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'censorshipStatusListModification',
                content: 'Deleted an censorshipStatus'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-censorship-status-delete-popup',
    template: ''
})
export class CensorshipStatusDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ censorshipStatus }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CensorshipStatusDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.censorshipStatus = censorshipStatus;
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
