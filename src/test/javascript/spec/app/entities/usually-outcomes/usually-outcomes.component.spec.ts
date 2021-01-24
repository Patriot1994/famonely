import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { FamonelyTestModule } from '../../../test.module';
import { UsuallyOutcomesComponent } from 'app/entities/usually-outcomes/usually-outcomes.component';
import { UsuallyOutcomesService } from 'app/entities/usually-outcomes/usually-outcomes.service';
import { UsuallyOutcomes } from 'app/shared/model/usually-outcomes.model';

describe('Component Tests', () => {
  describe('UsuallyOutcomes Management Component', () => {
    let comp: UsuallyOutcomesComponent;
    let fixture: ComponentFixture<UsuallyOutcomesComponent>;
    let service: UsuallyOutcomesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FamonelyTestModule],
        declarations: [UsuallyOutcomesComponent],
      })
        .overrideTemplate(UsuallyOutcomesComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UsuallyOutcomesComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UsuallyOutcomesService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new UsuallyOutcomes(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.usuallyOutcomes && comp.usuallyOutcomes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
