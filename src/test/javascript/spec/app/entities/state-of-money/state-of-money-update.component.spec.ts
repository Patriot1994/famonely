import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { FamonelyTestModule } from '../../../test.module';
import { StateOfMoneyUpdateComponent } from 'app/entities/state-of-money/state-of-money-update.component';
import { StateOfMoneyService } from 'app/entities/state-of-money/state-of-money.service';
import { StateOfMoney } from 'app/shared/model/state-of-money.model';

describe('Component Tests', () => {
  describe('StateOfMoney Management Update Component', () => {
    let comp: StateOfMoneyUpdateComponent;
    let fixture: ComponentFixture<StateOfMoneyUpdateComponent>;
    let service: StateOfMoneyService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FamonelyTestModule],
        declarations: [StateOfMoneyUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(StateOfMoneyUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(StateOfMoneyUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(StateOfMoneyService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new StateOfMoney(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new StateOfMoney();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
