import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FamonelyTestModule } from '../../../test.module';
import { OutcomeTypeDetailComponent } from 'app/entities/outcome-type/outcome-type-detail.component';
import { OutcomeType } from 'app/shared/model/outcome-type.model';

describe('Component Tests', () => {
  describe('OutcomeType Management Detail Component', () => {
    let comp: OutcomeTypeDetailComponent;
    let fixture: ComponentFixture<OutcomeTypeDetailComponent>;
    const route = ({ data: of({ outcomeType: new OutcomeType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FamonelyTestModule],
        declarations: [OutcomeTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(OutcomeTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OutcomeTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load outcomeType on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.outcomeType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
