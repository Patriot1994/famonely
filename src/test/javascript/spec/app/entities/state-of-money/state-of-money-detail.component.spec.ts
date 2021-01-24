import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FamonelyTestModule } from '../../../test.module';
import { StateOfMoneyDetailComponent } from 'app/entities/state-of-money/state-of-money-detail.component';
import { StateOfMoney } from 'app/shared/model/state-of-money.model';

describe('Component Tests', () => {
  describe('StateOfMoney Management Detail Component', () => {
    let comp: StateOfMoneyDetailComponent;
    let fixture: ComponentFixture<StateOfMoneyDetailComponent>;
    const route = ({ data: of({ stateOfMoney: new StateOfMoney(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FamonelyTestModule],
        declarations: [StateOfMoneyDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(StateOfMoneyDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(StateOfMoneyDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load stateOfMoney on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.stateOfMoney).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
