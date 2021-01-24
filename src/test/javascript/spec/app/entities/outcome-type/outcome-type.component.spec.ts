import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { FamonelyTestModule } from '../../../test.module';
import { OutcomeTypeComponent } from 'app/entities/outcome-type/outcome-type.component';
import { OutcomeTypeService } from 'app/entities/outcome-type/outcome-type.service';
import { OutcomeType } from 'app/shared/model/outcome-type.model';

describe('Component Tests', () => {
  describe('OutcomeType Management Component', () => {
    let comp: OutcomeTypeComponent;
    let fixture: ComponentFixture<OutcomeTypeComponent>;
    let service: OutcomeTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FamonelyTestModule],
        declarations: [OutcomeTypeComponent],
      })
        .overrideTemplate(OutcomeTypeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OutcomeTypeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OutcomeTypeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new OutcomeType(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.outcomeTypes && comp.outcomeTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
