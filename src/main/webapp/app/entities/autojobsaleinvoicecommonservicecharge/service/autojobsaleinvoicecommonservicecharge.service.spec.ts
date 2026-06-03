import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IAutojobsaleinvoicecommonservicecharge } from '../autojobsaleinvoicecommonservicecharge.model';
import {
  sampleWithFullData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithRequiredData,
} from '../autojobsaleinvoicecommonservicecharge.test-samples';

import { AutojobsaleinvoicecommonservicechargeService } from './autojobsaleinvoicecommonservicecharge.service';

const requireRestSample: IAutojobsaleinvoicecommonservicecharge = {
  ...sampleWithRequiredData,
};

describe('Autojobsaleinvoicecommonservicecharge Service', () => {
  let service: AutojobsaleinvoicecommonservicechargeService;
  let httpMock: HttpTestingController;
  let expectedResult: IAutojobsaleinvoicecommonservicecharge | IAutojobsaleinvoicecommonservicecharge[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(AutojobsaleinvoicecommonservicechargeService);
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

    it('should create a Autojobsaleinvoicecommonservicecharge', () => {
      const autojobsaleinvoicecommonservicecharge = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(autojobsaleinvoicecommonservicecharge).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Autojobsaleinvoicecommonservicecharge', () => {
      const autojobsaleinvoicecommonservicecharge = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(autojobsaleinvoicecommonservicecharge).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Autojobsaleinvoicecommonservicecharge', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Autojobsaleinvoicecommonservicecharge', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Autojobsaleinvoicecommonservicecharge', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addAutojobsaleinvoicecommonservicechargeToCollectionIfMissing', () => {
      it('should add a Autojobsaleinvoicecommonservicecharge to an empty array', () => {
        const autojobsaleinvoicecommonservicecharge: IAutojobsaleinvoicecommonservicecharge = sampleWithRequiredData;
        expectedResult = service.addAutojobsaleinvoicecommonservicechargeToCollectionIfMissing([], autojobsaleinvoicecommonservicecharge);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(autojobsaleinvoicecommonservicecharge);
      });

      it('should not add a Autojobsaleinvoicecommonservicecharge to an array that contains it', () => {
        const autojobsaleinvoicecommonservicecharge: IAutojobsaleinvoicecommonservicecharge = sampleWithRequiredData;
        const autojobsaleinvoicecommonservicechargeCollection: IAutojobsaleinvoicecommonservicecharge[] = [
          {
            ...autojobsaleinvoicecommonservicecharge,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addAutojobsaleinvoicecommonservicechargeToCollectionIfMissing(
          autojobsaleinvoicecommonservicechargeCollection,
          autojobsaleinvoicecommonservicecharge,
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Autojobsaleinvoicecommonservicecharge to an array that doesn't contain it", () => {
        const autojobsaleinvoicecommonservicecharge: IAutojobsaleinvoicecommonservicecharge = sampleWithRequiredData;
        const autojobsaleinvoicecommonservicechargeCollection: IAutojobsaleinvoicecommonservicecharge[] = [sampleWithPartialData];
        expectedResult = service.addAutojobsaleinvoicecommonservicechargeToCollectionIfMissing(
          autojobsaleinvoicecommonservicechargeCollection,
          autojobsaleinvoicecommonservicecharge,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(autojobsaleinvoicecommonservicecharge);
      });

      it('should add only unique Autojobsaleinvoicecommonservicecharge to an array', () => {
        const autojobsaleinvoicecommonservicechargeArray: IAutojobsaleinvoicecommonservicecharge[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const autojobsaleinvoicecommonservicechargeCollection: IAutojobsaleinvoicecommonservicecharge[] = [sampleWithRequiredData];
        expectedResult = service.addAutojobsaleinvoicecommonservicechargeToCollectionIfMissing(
          autojobsaleinvoicecommonservicechargeCollection,
          ...autojobsaleinvoicecommonservicechargeArray,
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const autojobsaleinvoicecommonservicecharge: IAutojobsaleinvoicecommonservicecharge = sampleWithRequiredData;
        const autojobsaleinvoicecommonservicecharge2: IAutojobsaleinvoicecommonservicecharge = sampleWithPartialData;
        expectedResult = service.addAutojobsaleinvoicecommonservicechargeToCollectionIfMissing(
          [],
          autojobsaleinvoicecommonservicecharge,
          autojobsaleinvoicecommonservicecharge2,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(autojobsaleinvoicecommonservicecharge);
        expect(expectedResult).toContain(autojobsaleinvoicecommonservicecharge2);
      });

      it('should accept null and undefined values', () => {
        const autojobsaleinvoicecommonservicecharge: IAutojobsaleinvoicecommonservicecharge = sampleWithRequiredData;
        expectedResult = service.addAutojobsaleinvoicecommonservicechargeToCollectionIfMissing(
          [],
          null,
          autojobsaleinvoicecommonservicecharge,
          undefined,
        );
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(autojobsaleinvoicecommonservicecharge);
      });

      it('should return initial array if no Autojobsaleinvoicecommonservicecharge is added', () => {
        const autojobsaleinvoicecommonservicechargeCollection: IAutojobsaleinvoicecommonservicecharge[] = [sampleWithRequiredData];
        expectedResult = service.addAutojobsaleinvoicecommonservicechargeToCollectionIfMissing(
          autojobsaleinvoicecommonservicechargeCollection,
          undefined,
          null,
        );
        expect(expectedResult).toEqual(autojobsaleinvoicecommonservicechargeCollection);
      });
    });

    describe('compareAutojobsaleinvoicecommonservicecharge', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareAutojobsaleinvoicecommonservicecharge(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareAutojobsaleinvoicecommonservicecharge(entity1, entity2);
        const compareResult2 = service.compareAutojobsaleinvoicecommonservicecharge(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareAutojobsaleinvoicecommonservicecharge(entity1, entity2);
        const compareResult2 = service.compareAutojobsaleinvoicecommonservicecharge(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareAutojobsaleinvoicecommonservicecharge(entity1, entity2);
        const compareResult2 = service.compareAutojobsaleinvoicecommonservicecharge(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
