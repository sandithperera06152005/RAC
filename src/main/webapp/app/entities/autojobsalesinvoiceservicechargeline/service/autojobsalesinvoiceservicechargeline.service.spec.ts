import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IAutojobsalesinvoiceservicechargeline } from '../autojobsalesinvoiceservicechargeline.model';
import {
  sampleWithFullData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithRequiredData,
} from '../autojobsalesinvoiceservicechargeline.test-samples';

import { AutojobsalesinvoiceservicechargelineService } from './autojobsalesinvoiceservicechargeline.service';

const requireRestSample: IAutojobsalesinvoiceservicechargeline = {
  ...sampleWithRequiredData,
};

describe('Autojobsalesinvoiceservicechargeline Service', () => {
  let service: AutojobsalesinvoiceservicechargelineService;
  let httpMock: HttpTestingController;
  let expectedResult: IAutojobsalesinvoiceservicechargeline | IAutojobsalesinvoiceservicechargeline[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(AutojobsalesinvoiceservicechargelineService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a Autojobsalesinvoiceservicechargeline', () => {
      const autojobsalesinvoiceservicechargeline = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(autojobsalesinvoiceservicechargeline).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Autojobsalesinvoiceservicechargeline', () => {
      const autojobsalesinvoiceservicechargeline = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(autojobsalesinvoiceservicechargeline).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Autojobsalesinvoiceservicechargeline', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Autojobsalesinvoiceservicechargeline', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Autojobsalesinvoiceservicechargeline', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addAutojobsalesinvoiceservicechargelineToCollectionIfMissing', () => {
      it('should add a Autojobsalesinvoiceservicechargeline to an empty array', () => {
        const autojobsalesinvoiceservicechargeline: IAutojobsalesinvoiceservicechargeline = sampleWithRequiredData;
        expectedResult = service.addAutojobsalesinvoiceservicechargelineToCollectionIfMissing([], autojobsalesinvoiceservicechargeline);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(autojobsalesinvoiceservicechargeline);
      });

      it('should not add a Autojobsalesinvoiceservicechargeline to an array that contains it', () => {
        const autojobsalesinvoiceservicechargeline: IAutojobsalesinvoiceservicechargeline = sampleWithRequiredData;
        const autojobsalesinvoiceservicechargelineCollection: IAutojobsalesinvoiceservicechargeline[] = [
          {
            ...autojobsalesinvoiceservicechargeline,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addAutojobsalesinvoiceservicechargelineToCollectionIfMissing(
          autojobsalesinvoiceservicechargelineCollection,
          autojobsalesinvoiceservicechargeline,
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Autojobsalesinvoiceservicechargeline to an array that doesn't contain it", () => {
        const autojobsalesinvoiceservicechargeline: IAutojobsalesinvoiceservicechargeline = sampleWithRequiredData;
        const autojobsalesinvoiceservicechargelineCollection: IAutojobsalesinvoiceservicechargeline[] = [sampleWithPartialData];
        expectedResult = service.addAutojobsalesinvoiceservicechargelineToCollectionIfMissing(
          autojobsalesinvoiceservicechargelineCollection,
          autojobsalesinvoiceservicechargeline,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(autojobsalesinvoiceservicechargeline);
      });

      it('should add only unique Autojobsalesinvoiceservicechargeline to an array', () => {
        const autojobsalesinvoiceservicechargelineArray: IAutojobsalesinvoiceservicechargeline[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const autojobsalesinvoiceservicechargelineCollection: IAutojobsalesinvoiceservicechargeline[] = [sampleWithRequiredData];
        expectedResult = service.addAutojobsalesinvoiceservicechargelineToCollectionIfMissing(
          autojobsalesinvoiceservicechargelineCollection,
          ...autojobsalesinvoiceservicechargelineArray,
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const autojobsalesinvoiceservicechargeline: IAutojobsalesinvoiceservicechargeline = sampleWithRequiredData;
        const autojobsalesinvoiceservicechargeline2: IAutojobsalesinvoiceservicechargeline = sampleWithPartialData;
        expectedResult = service.addAutojobsalesinvoiceservicechargelineToCollectionIfMissing(
          [],
          autojobsalesinvoiceservicechargeline,
          autojobsalesinvoiceservicechargeline2,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(autojobsalesinvoiceservicechargeline);
        expect(expectedResult).toContain(autojobsalesinvoiceservicechargeline2);
      });

      it('should accept null and undefined values', () => {
        const autojobsalesinvoiceservicechargeline: IAutojobsalesinvoiceservicechargeline = sampleWithRequiredData;
        expectedResult = service.addAutojobsalesinvoiceservicechargelineToCollectionIfMissing(
          [],
          null,
          autojobsalesinvoiceservicechargeline,
          undefined,
        );
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(autojobsalesinvoiceservicechargeline);
      });

      it('should return initial array if no Autojobsalesinvoiceservicechargeline is added', () => {
        const autojobsalesinvoiceservicechargelineCollection: IAutojobsalesinvoiceservicechargeline[] = [sampleWithRequiredData];
        expectedResult = service.addAutojobsalesinvoiceservicechargelineToCollectionIfMissing(
          autojobsalesinvoiceservicechargelineCollection,
          undefined,
          null,
        );
        expect(expectedResult).toEqual(autojobsalesinvoiceservicechargelineCollection);
      });
    });

    describe('compareAutojobsalesinvoiceservicechargeline', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareAutojobsalesinvoiceservicechargeline(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareAutojobsalesinvoiceservicechargeline(entity1, entity2);
        const compareResult2 = service.compareAutojobsalesinvoiceservicechargeline(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareAutojobsalesinvoiceservicechargeline(entity1, entity2);
        const compareResult2 = service.compareAutojobsalesinvoiceservicechargeline(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareAutojobsalesinvoiceservicechargeline(entity1, entity2);
        const compareResult2 = service.compareAutojobsalesinvoiceservicechargeline(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
