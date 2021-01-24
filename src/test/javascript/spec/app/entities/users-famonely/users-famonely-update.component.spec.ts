import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { FamonelyTestModule } from '../../../test.module';
import { UsersFamonelyUpdateComponent } from 'app/entities/users-famonely/users-famonely-update.component';
import { UsersFamonelyService } from 'app/entities/users-famonely/users-famonely.service';
import { UsersFamonely } from 'app/shared/model/users-famonely.model';

describe('Component Tests', () => {
  describe('UsersFamonely Management Update Component', () => {
    let comp: UsersFamonelyUpdateComponent;
    let fixture: ComponentFixture<UsersFamonelyUpdateComponent>;
    let service: UsersFamonelyService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FamonelyTestModule],
        declarations: [UsersFamonelyUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(UsersFamonelyUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UsersFamonelyUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UsersFamonelyService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new UsersFamonely(123);
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
        const entity = new UsersFamonely();
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
