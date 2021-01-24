import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { FamonelyTestModule } from '../../../test.module';
import { OutcomesUpdateComponent } from 'app/entities/outcomes/outcomes-update.component';
import { OutcomesService } from 'app/entities/outcomes/outcomes.service';
import { Outcomes } from 'app/shared/model/outcomes.model';

describe('Component Tests', () => {
  describe('Outcomes Management Update Component', () => {
    let comp: OutcomesUpdateComponent;
    let fixture: ComponentFixture<OutcomesUpdateComponent>;
    let service: OutcomesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FamonelyTestModule],
        declarations: [OutcomesUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(OutcomesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OutcomesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OutcomesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Outcomes(123);
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
        const entity = new Outcomes();
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
