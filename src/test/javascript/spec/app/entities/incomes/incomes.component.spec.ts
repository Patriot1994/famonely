import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { FamonelyTestModule } from '../../../test.module';
import { IncomesComponent } from 'app/entities/incomes/incomes.component';
import { IncomesService } from 'app/entities/incomes/incomes.service';
import { Incomes } from 'app/shared/model/incomes.model';

describe('Component Tests', () => {
  describe('Incomes Management Component', () => {
    let comp: IncomesComponent;
    let fixture: ComponentFixture<IncomesComponent>;
    let service: IncomesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FamonelyTestModule],
        declarations: [IncomesComponent],
      })
        .overrideTemplate(IncomesComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(IncomesComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(IncomesService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Incomes(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.incomes && comp.incomes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
