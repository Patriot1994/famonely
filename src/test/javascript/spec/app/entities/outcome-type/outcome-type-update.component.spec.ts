import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { FamonelyTestModule } from '../../../test.module';
import { OutcomeTypeUpdateComponent } from 'app/entities/outcome-type/outcome-type-update.component';
import { OutcomeTypeService } from 'app/entities/outcome-type/outcome-type.service';
import { OutcomeType } from 'app/shared/model/outcome-type.model';

describe('Component Tests', () => {
  describe('OutcomeType Management Update Component', () => {
    let comp: OutcomeTypeUpdateComponent;
    let fixture: ComponentFixture<OutcomeTypeUpdateComponent>;
    let service: OutcomeTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FamonelyTestModule],
        declarations: [OutcomeTypeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(OutcomeTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OutcomeTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OutcomeTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new OutcomeType(123);
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
        const entity = new OutcomeType();
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
