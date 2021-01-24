import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { FamonelyTestModule } from '../../../test.module';
import { IncomesUpdateComponent } from 'app/entities/incomes/incomes-update.component';
import { IncomesService } from 'app/entities/incomes/incomes.service';
import { Incomes } from 'app/shared/model/incomes.model';

describe('Component Tests', () => {
  describe('Incomes Management Update Component', () => {
    let comp: IncomesUpdateComponent;
    let fixture: ComponentFixture<IncomesUpdateComponent>;
    let service: IncomesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FamonelyTestModule],
        declarations: [IncomesUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(IncomesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(IncomesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(IncomesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Incomes(123);
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
        const entity = new Incomes();
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
