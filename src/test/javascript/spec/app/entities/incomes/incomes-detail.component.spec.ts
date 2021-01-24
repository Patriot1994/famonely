import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FamonelyTestModule } from '../../../test.module';
import { IncomesDetailComponent } from 'app/entities/incomes/incomes-detail.component';
import { Incomes } from 'app/shared/model/incomes.model';

describe('Component Tests', () => {
  describe('Incomes Management Detail Component', () => {
    let comp: IncomesDetailComponent;
    let fixture: ComponentFixture<IncomesDetailComponent>;
    const route = ({ data: of({ incomes: new Incomes(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FamonelyTestModule],
        declarations: [IncomesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(IncomesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(IncomesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load incomes on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.incomes).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
