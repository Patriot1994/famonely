import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FamonelyTestModule } from '../../../test.module';
import { IncomeTypeDetailComponent } from 'app/entities/income-type/income-type-detail.component';
import { IncomeType } from 'app/shared/model/income-type.model';

describe('Component Tests', () => {
  describe('IncomeType Management Detail Component', () => {
    let comp: IncomeTypeDetailComponent;
    let fixture: ComponentFixture<IncomeTypeDetailComponent>;
    const route = ({ data: of({ incomeType: new IncomeType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FamonelyTestModule],
        declarations: [IncomeTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(IncomeTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(IncomeTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load incomeType on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.incomeType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
