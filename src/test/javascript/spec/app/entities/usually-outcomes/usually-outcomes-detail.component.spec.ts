import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FamonelyTestModule } from '../../../test.module';
import { UsuallyOutcomesDetailComponent } from 'app/entities/usually-outcomes/usually-outcomes-detail.component';
import { UsuallyOutcomes } from 'app/shared/model/usually-outcomes.model';

describe('Component Tests', () => {
  describe('UsuallyOutcomes Management Detail Component', () => {
    let comp: UsuallyOutcomesDetailComponent;
    let fixture: ComponentFixture<UsuallyOutcomesDetailComponent>;
    const route = ({ data: of({ usuallyOutcomes: new UsuallyOutcomes(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FamonelyTestModule],
        declarations: [UsuallyOutcomesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(UsuallyOutcomesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UsuallyOutcomesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load usuallyOutcomes on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.usuallyOutcomes).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
