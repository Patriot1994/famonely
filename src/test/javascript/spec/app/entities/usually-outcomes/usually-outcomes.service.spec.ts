import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { UsuallyOutcomesService } from 'app/entities/usually-outcomes/usually-outcomes.service';
import { IUsuallyOutcomes, UsuallyOutcomes } from 'app/shared/model/usually-outcomes.model';

describe('Service Tests', () => {
  describe('UsuallyOutcomes Service', () => {
    let injector: TestBed;
    let service: UsuallyOutcomesService;
    let httpMock: HttpTestingController;
    let elemDefault: IUsuallyOutcomes;
    let expectedResult: IUsuallyOutcomes | IUsuallyOutcomes[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(UsuallyOutcomesService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new UsuallyOutcomes(0, currentDate, 0, false);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            payDay: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a UsuallyOutcomes', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            payDay: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            payDay: currentDate,
          },
          returnedFromService
        );

        service.create(new UsuallyOutcomes()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a UsuallyOutcomes', () => {
        const returnedFromService = Object.assign(
          {
            payDay: currentDate.format(DATE_FORMAT),
            money: 1,
            isAlreadyPaid: true,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            payDay: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of UsuallyOutcomes', () => {
        const returnedFromService = Object.assign(
          {
            payDay: currentDate.format(DATE_FORMAT),
            money: 1,
            isAlreadyPaid: true,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            payDay: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a UsuallyOutcomes', () => {
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
