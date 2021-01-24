import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { FamonelyTestModule } from '../../../test.module';
import { UsersFamonelyComponent } from 'app/entities/users-famonely/users-famonely.component';
import { UsersFamonelyService } from 'app/entities/users-famonely/users-famonely.service';
import { UsersFamonely } from 'app/shared/model/users-famonely.model';

describe('Component Tests', () => {
  describe('UsersFamonely Management Component', () => {
    let comp: UsersFamonelyComponent;
    let fixture: ComponentFixture<UsersFamonelyComponent>;
    let service: UsersFamonelyService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FamonelyTestModule],
        declarations: [UsersFamonelyComponent],
      })
        .overrideTemplate(UsersFamonelyComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UsersFamonelyComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UsersFamonelyService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new UsersFamonely(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.usersFamonelies && comp.usersFamonelies[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
