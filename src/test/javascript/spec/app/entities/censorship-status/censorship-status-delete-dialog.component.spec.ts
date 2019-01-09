/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { InternetCensorshipTestModule } from '../../../test.module';
import { CensorshipStatusDeleteDialogComponent } from 'app/entities/censorship-status/censorship-status-delete-dialog.component';
import { CensorshipStatusService } from 'app/entities/censorship-status/censorship-status.service';

describe('Component Tests', () => {
    describe('CensorshipStatus Management Delete Component', () => {
        let comp: CensorshipStatusDeleteDialogComponent;
        let fixture: ComponentFixture<CensorshipStatusDeleteDialogComponent>;
        let service: CensorshipStatusService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [InternetCensorshipTestModule],
                declarations: [CensorshipStatusDeleteDialogComponent]
            })
                .overrideTemplate(CensorshipStatusDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CensorshipStatusDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CensorshipStatusService);
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
