import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { FamonelyTestModule } from '../../../test.module';
import { IncomeTypeUpdateComponent } from 'app/entities/income-type/income-type-update.component';
import { IncomeTypeService } from 'app/entities/income-type/income-type.service';
import { IncomeType } from 'app/shared/model/income-type.model';

describe('Component Tests', () => {
  describe('IncomeType Management Update Component', () => {
    let comp: IncomeTypeUpdateComponent;
    let fixture: ComponentFixture<IncomeTypeUpdateComponent>;
    let service: IncomeTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FamonelyTestModule],
        declarations: [IncomeTypeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(IncomeTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(IncomeTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(IncomeTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new IncomeType(123);
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
        const entity = new IncomeType();
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
