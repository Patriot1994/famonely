import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FamonelyTestModule } from '../../../test.module';
import { PlannedOutcomesDetailComponent } from 'app/entities/planned-outcomes/planned-outcomes-detail.component';
import { PlannedOutcomes } from 'app/shared/model/planned-outcomes.model';

describe('Component Tests', () => {
  describe('PlannedOutcomes Management Detail Component', () => {
    let comp: PlannedOutcomesDetailComponent;
    let fixture: ComponentFixture<PlannedOutcomesDetailComponent>;
    const route = ({ data: of({ plannedOutcomes: new PlannedOutcomes(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FamonelyTestModule],
        declarations: [PlannedOutcomesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PlannedOutcomesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PlannedOutcomesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load plannedOutcomes on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.plannedOutcomes).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
