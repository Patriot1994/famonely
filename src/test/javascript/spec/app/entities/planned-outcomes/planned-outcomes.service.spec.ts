import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { PlannedOutcomesService } from 'app/entities/planned-outcomes/planned-outcomes.service';
import { IPlannedOutcomes, PlannedOutcomes } from 'app/shared/model/planned-outcomes.model';

describe('Service Tests', () => {
  describe('PlannedOutcomes Service', () => {
    let injector: TestBed;
    let service: PlannedOutcomesService;
    let httpMock: HttpTestingController;
    let elemDefault: IPlannedOutcomes;
    let expectedResult: IPlannedOutcomes | IPlannedOutcomes[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(PlannedOutcomesService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new PlannedOutcomes(0, currentDate, 0, 0, false);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            plannedDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a PlannedOutcomes', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            plannedDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            plannedDate: currentDate,
          },
          returnedFromService
        );

        service.create(new PlannedOutcomes()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a PlannedOutcomes', () => {
        const returnedFromService = Object.assign(
          {
            plannedDate: currentDate.format(DATE_FORMAT),
            plannedMoney: 1,
            moneyToGoal: 1,
            isAlreadyPaid: true,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            plannedDate: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of PlannedOutcomes', () => {
        const returnedFromService = Object.assign(
          {
            plannedDate: currentDate.format(DATE_FORMAT),
            plannedMoney: 1,
            moneyToGoal: 1,
            isAlreadyPaid: true,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            plannedDate: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a PlannedOutcomes', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
