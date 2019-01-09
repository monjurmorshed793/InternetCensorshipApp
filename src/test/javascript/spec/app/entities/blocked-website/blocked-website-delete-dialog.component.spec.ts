/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { InternetCensorshipTestModule } from '../../../test.module';
import { BlockedWebsiteDeleteDialogComponent } from 'app/entities/blocked-website/blocked-website-delete-dialog.component';
import { BlockedWebsiteService } from 'app/entities/blocked-website/blocked-website.service';

describe('Component Tests', () => {
    describe('BlockedWebsite Management Delete Component', () => {
        let comp: BlockedWebsiteDeleteDialogComponent;
        let fixture: ComponentFixture<BlockedWebsiteDeleteDialogComponent>;
        let service: BlockedWebsiteService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [InternetCensorshipTestModule],
                declarations: [BlockedWebsiteDeleteDialogComponent]
            })
                .overrideTemplate(BlockedWebsiteDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(BlockedWebsiteDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BlockedWebsiteService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete('123');
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith('123');
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
