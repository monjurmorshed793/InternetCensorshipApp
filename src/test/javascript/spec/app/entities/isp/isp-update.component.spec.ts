/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { InternetCensorshipTestModule } from '../../../test.module';
import { IspUpdateComponent } from 'app/entities/isp/isp-update.component';
import { IspService } from 'app/entities/isp/isp.service';
import { Isp } from 'app/shared/model/isp.model';

describe('Component Tests', () => {
    describe('Isp Management Update Component', () => {
        let comp: IspUpdateComponent;
        let fixture: ComponentFixture<IspUpdateComponent>;
        let service: IspService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [InternetCensorshipTestModule],
                declarations: [IspUpdateComponent]
            })
                .overrideTemplate(IspUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(IspUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IspService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Isp('123');
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.isp = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Isp();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.isp = entity;
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
