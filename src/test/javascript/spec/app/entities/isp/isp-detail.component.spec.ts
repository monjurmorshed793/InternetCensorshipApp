/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { InternetCensorshipTestModule } from '../../../test.module';
import { IspDetailComponent } from 'app/entities/isp/isp-detail.component';
import { Isp } from 'app/shared/model/isp.model';

describe('Component Tests', () => {
    describe('Isp Management Detail Component', () => {
        let comp: IspDetailComponent;
        let fixture: ComponentFixture<IspDetailComponent>;
        const route = ({ data: of({ isp: new Isp('123') }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [InternetCensorshipTestModule],
                declarations: [IspDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(IspDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(IspDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.isp).toEqual(jasmine.objectContaining({ id: '123' }));
            });
        });
    });
});
