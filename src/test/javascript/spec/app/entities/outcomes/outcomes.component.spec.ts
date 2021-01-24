import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { FamonelyTestModule } from '../../../test.module';
import { OutcomesComponent } from 'app/entities/outcomes/outcomes.component';
import { OutcomesService } from 'app/entities/outcomes/outcomes.service';
import { Outcomes } from 'app/shared/model/outcomes.model';

describe('Component Tests', () => {
  describe('Outcomes Management Component', () => {
    let comp: OutcomesComponent;
    let fixture: ComponentFixture<OutcomesComponent>;
    let service: OutcomesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FamonelyTestModule],
        declarations: [OutcomesComponent],
      })
        .overrideTemplate(OutcomesComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OutcomesComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OutcomesService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Outcomes(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.outcomes && comp.outcomes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
