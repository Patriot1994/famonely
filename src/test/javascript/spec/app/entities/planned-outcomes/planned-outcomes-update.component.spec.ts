import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { FamonelyTestModule } from '../../../test.module';
import { PlannedOutcomesUpdateComponent } from 'app/entities/planned-outcomes/planned-outcomes-update.component';
import { PlannedOutcomesService } from 'app/entities/planned-outcomes/planned-outcomes.service';
import { PlannedOutcomes } from 'app/shared/model/planned-outcomes.model';

describe('Component Tests', () => {
  describe('PlannedOutcomes Management Update Component', () => {
    let comp: PlannedOutcomesUpdateComponent;
    let fixture: ComponentFixture<PlannedOutcomesUpdateComponent>;
    let service: PlannedOutcomesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FamonelyTestModule],
        declarations: [PlannedOutcomesUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PlannedOutcomesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PlannedOutcomesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PlannedOutcomesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PlannedOutcomes(123);
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
        const entity = new PlannedOutcomes();
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
