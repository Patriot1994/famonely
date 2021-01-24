import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { FamonelyTestModule } from '../../../test.module';
import { UsuallyOutcomesUpdateComponent } from 'app/entities/usually-outcomes/usually-outcomes-update.component';
import { UsuallyOutcomesService } from 'app/entities/usually-outcomes/usually-outcomes.service';
import { UsuallyOutcomes } from 'app/shared/model/usually-outcomes.model';

describe('Component Tests', () => {
  describe('UsuallyOutcomes Management Update Component', () => {
    let comp: UsuallyOutcomesUpdateComponent;
    let fixture: ComponentFixture<UsuallyOutcomesUpdateComponent>;
    let service: UsuallyOutcomesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FamonelyTestModule],
        declarations: [UsuallyOutcomesUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(UsuallyOutcomesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UsuallyOutcomesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UsuallyOutcomesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new UsuallyOutcomes(123);
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
        const entity = new UsuallyOutcomes();
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
