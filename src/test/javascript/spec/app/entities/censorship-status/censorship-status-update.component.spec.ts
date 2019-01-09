/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { InternetCensorshipTestModule } from '../../../test.module';
import { CensorshipStatusUpdateComponent } from 'app/entities/censorship-status/censorship-status-update.component';
import { CensorshipStatusService } from 'app/entities/censorship-status/censorship-status.service';
import { CensorshipStatus } from 'app/shared/model/censorship-status.model';

describe('Component Tests', () => {
    describe('CensorshipStatus Management Update Component', () => {
        let comp: CensorshipStatusUpdateComponent;
        let fixture: ComponentFixture<CensorshipStatusUpdateComponent>;
        let service: CensorshipStatusService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [InternetCensorshipTestModule],
                declarations: [CensorshipStatusUpdateComponent]
            })
                .overrideTemplate(CensorshipStatusUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CensorshipStatusUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CensorshipStatusService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new CensorshipStatus('123');
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.censorshipStatus = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new CensorshipStatus();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.censorshipStatus = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
