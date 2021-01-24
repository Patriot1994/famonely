import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { FamonelyTestModule } from '../../../test.module';
import { IncomeTypeComponent } from 'app/entities/income-type/income-type.component';
import { IncomeTypeService } from 'app/entities/income-type/income-type.service';
import { IncomeType } from 'app/shared/model/income-type.model';

describe('Component Tests', () => {
  describe('IncomeType Management Component', () => {
    let comp: IncomeTypeComponent;
    let fixture: ComponentFixture<IncomeTypeComponent>;
    let service: IncomeTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FamonelyTestModule],
        declarations: [IncomeTypeComponent],
      })
        .overrideTemplate(IncomeTypeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(IncomeTypeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(IncomeTypeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new IncomeType(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.incomeTypes && comp.incomeTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
