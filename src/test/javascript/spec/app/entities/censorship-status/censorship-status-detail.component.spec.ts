/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { InternetCensorshipTestModule } from '../../../test.module';
import { CensorshipStatusDetailComponent } from 'app/entities/censorship-status/censorship-status-detail.component';
import { CensorshipStatus } from 'app/shared/model/censorship-status.model';

describe('Component Tests', () => {
    describe('CensorshipStatus Management Detail Component', () => {
        let comp: CensorshipStatusDetailComponent;
        let fixture: ComponentFixture<CensorshipStatusDetailComponent>;
        const route = ({ data: of({ censorshipStatus: new CensorshipStatus('123') }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [InternetCensorshipTestModule],
                declarations: [CensorshipStatusDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CensorshipStatusDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CensorshipStatusDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.censorshipStatus).toEqual(jasmine.objectContaining({ id: '123' }));
            });
        });
    });
});
