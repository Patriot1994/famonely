import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FamonelyTestModule } from '../../../test.module';
import { OutcomesDetailComponent } from 'app/entities/outcomes/outcomes-detail.component';
import { Outcomes } from 'app/shared/model/outcomes.model';

describe('Component Tests', () => {
  describe('Outcomes Management Detail Component', () => {
    let comp: OutcomesDetailComponent;
    let fixture: ComponentFixture<OutcomesDetailComponent>;
    const route = ({ data: of({ outcomes: new Outcomes(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FamonelyTestModule],
        declarations: [OutcomesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(OutcomesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OutcomesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load outcomes on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.outcomes).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
