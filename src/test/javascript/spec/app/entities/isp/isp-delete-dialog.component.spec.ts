/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { InternetCensorshipTestModule } from '../../../test.module';
import { IspDeleteDialogComponent } from 'app/entities/isp/isp-delete-dialog.component';
import { IspService } from 'app/entities/isp/isp.service';

describe('Component Tests', () => {
    describe('Isp Management Delete Component', () => {
        let comp: IspDeleteDialogComponent;
        let fixture: ComponentFixture<IspDeleteDialogComponent>;
        let service: IspService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [InternetCensorshipTestModule],
                declarations: [IspDeleteDialogComponent]
            })
                .overrideTemplate(IspDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(IspDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IspService);
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
