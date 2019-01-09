/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { InternetCensorshipTestModule } from '../../../test.module';
import { BlockedWebsiteUpdateComponent } from 'app/entities/blocked-website/blocked-website-update.component';
import { BlockedWebsiteService } from 'app/entities/blocked-website/blocked-website.service';
import { BlockedWebsite } from 'app/shared/model/blocked-website.model';

describe('Component Tests', () => {
    describe('BlockedWebsite Management Update Component', () => {
        let comp: BlockedWebsiteUpdateComponent;
        let fixture: ComponentFixture<BlockedWebsiteUpdateComponent>;
        let service: BlockedWebsiteService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [InternetCensorshipTestModule],
                declarations: [BlockedWebsiteUpdateComponent]
            })
                .overrideTemplate(BlockedWebsiteUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(BlockedWebsiteUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BlockedWebsiteService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new BlockedWebsite('123');
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.blockedWebsite = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new BlockedWebsite();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.blockedWebsite = entity;
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
