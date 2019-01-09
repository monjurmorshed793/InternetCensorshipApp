/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { InternetCensorshipTestModule } from '../../../test.module';
import { BlockedWebsiteDetailComponent } from 'app/entities/blocked-website/blocked-website-detail.component';
import { BlockedWebsite } from 'app/shared/model/blocked-website.model';

describe('Component Tests', () => {
    describe('BlockedWebsite Management Detail Component', () => {
        let comp: BlockedWebsiteDetailComponent;
        let fixture: ComponentFixture<BlockedWebsiteDetailComponent>;
        const route = ({ data: of({ blockedWebsite: new BlockedWebsite('123') }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [InternetCensorshipTestModule],
                declarations: [BlockedWebsiteDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(BlockedWebsiteDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(BlockedWebsiteDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.blockedWebsite).toEqual(jasmine.objectContaining({ id: '123' }));
            });
        });
    });
});
