import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { FamonelyTestModule } from '../../../test.module';
import { StateOfMoneyComponent } from 'app/entities/state-of-money/state-of-money.component';
import { StateOfMoneyService } from 'app/entities/state-of-money/state-of-money.service';
import { StateOfMoney } from 'app/shared/model/state-of-money.model';

describe('Component Tests', () => {
  describe('StateOfMoney Management Component', () => {
    let comp: StateOfMoneyComponent;
    let fixture: ComponentFixture<StateOfMoneyComponent>;
    let service: StateOfMoneyService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FamonelyTestModule],
        declarations: [StateOfMoneyComponent],
      })
        .overrideTemplate(StateOfMoneyComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(StateOfMoneyComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(StateOfMoneyService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new StateOfMoney(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.stateOfMonies && comp.stateOfMonies[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
