import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { FamonelyTestModule } from '../../../test.module';
import { PlannedOutcomesComponent } from 'app/entities/planned-outcomes/planned-outcomes.component';
import { PlannedOutcomesService } from 'app/entities/planned-outcomes/planned-outcomes.service';
import { PlannedOutcomes } from 'app/shared/model/planned-outcomes.model';

describe('Component Tests', () => {
  describe('PlannedOutcomes Management Component', () => {
    let comp: PlannedOutcomesComponent;
    let fixture: ComponentFixture<PlannedOutcomesComponent>;
    let service: PlannedOutcomesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FamonelyTestModule],
        declarations: [PlannedOutcomesComponent],
      })
        .overrideTemplate(PlannedOutcomesComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PlannedOutcomesComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PlannedOutcomesService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PlannedOutcomes(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.plannedOutcomes && comp.plannedOutcomes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
