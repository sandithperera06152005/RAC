import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IAutojobsinvoicelinebatches } from '../autojobsinvoicelinebatches.model';
import {
  sampleWithFullData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithRequiredData,
} from '../autojobsinvoicelinebatches.test-samples';

import { AutojobsinvoicelinebatchesService, RestAutojobsinvoicelinebatches } from './autojobsinvoicelinebatches.service';

const requireRestSample: RestAutojobsinvoicelinebatches = {
  ...sampleWithRequiredData,
  txdate: sampleWithRequiredData.txdate?.toJSON(),
  manufacturedate: sampleWithRequiredData.manufacturedate?.toJSON(),
  expireddate: sampleWithRequiredData.expireddate?.toJSON(),
  lmd: sampleWithRequiredData.lmd?.toJSON(),
  issueddatetime: sampleWithRequiredData.issueddatetime?.toJSON(),
};

describe('Autojobsinvoicelinebatches Service', () => {
  let service: AutojobsinvoicelinebatchesService;
  let httpMock: HttpTestingController;
  let expectedResult: IAutojobsinvoicelinebatches | IAutojobsinvoicelinebatches[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(AutojobsinvoicelinebatchesService);
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

    it('should create a Autojobsinvoicelinebatches', () => {
      const autojobsinvoicelinebatches = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(autojobsinvoicelinebatches).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Autojobsinvoicelinebatches', () => {
      const autojobsinvoicelinebatches = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(autojobsinvoicelinebatches).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Autojobsinvoicelinebatches', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Autojobsinvoicelinebatches', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Autojobsinvoicelinebatches', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addAutojobsinvoicelinebatchesToCollectionIfMissing', () => {
      it('should add a Autojobsinvoicelinebatches to an empty array', () => {
        const autojobsinvoicelinebatches: IAutojobsinvoicelinebatches = sampleWithRequiredData;
        expectedResult = service.addAutojobsinvoicelinebatchesToCollectionIfMissing([], autojobsinvoicelinebatches);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(autojobsinvoicelinebatches);
      });

      it('should not add a Autojobsinvoicelinebatches to an array that contains it', () => {
        const autojobsinvoicelinebatches: IAutojobsinvoicelinebatches = sampleWithRequiredData;
        const autojobsinvoicelinebatchesCollection: IAutojobsinvoicelinebatches[] = [
          {
            ...autojobsinvoicelinebatches,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addAutojobsinvoicelinebatchesToCollectionIfMissing(
          autojobsinvoicelinebatchesCollection,
          autojobsinvoicelinebatches,
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Autojobsinvoicelinebatches to an array that doesn't contain it", () => {
        const autojobsinvoicelinebatches: IAutojobsinvoicelinebatches = sampleWithRequiredData;
        const autojobsinvoicelinebatchesCollection: IAutojobsinvoicelinebatches[] = [sampleWithPartialData];
        expectedResult = service.addAutojobsinvoicelinebatchesToCollectionIfMissing(
          autojobsinvoicelinebatchesCollection,
          autojobsinvoicelinebatches,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(autojobsinvoicelinebatches);
      });

      it('should add only unique Autojobsinvoicelinebatches to an array', () => {
        const autojobsinvoicelinebatchesArray: IAutojobsinvoicelinebatches[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const autojobsinvoicelinebatchesCollection: IAutojobsinvoicelinebatches[] = [sampleWithRequiredData];
        expectedResult = service.addAutojobsinvoicelinebatchesToCollectionIfMissing(
          autojobsinvoicelinebatchesCollection,
          ...autojobsinvoicelinebatchesArray,
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const autojobsinvoicelinebatches: IAutojobsinvoicelinebatches = sampleWithRequiredData;
        const autojobsinvoicelinebatches2: IAutojobsinvoicelinebatches = sampleWithPartialData;
        expectedResult = service.addAutojobsinvoicelinebatchesToCollectionIfMissing(
          [],
          autojobsinvoicelinebatches,
          autojobsinvoicelinebatches2,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(autojobsinvoicelinebatches);
        expect(expectedResult).toContain(autojobsinvoicelinebatches2);
      });

      it('should accept null and undefined values', () => {
        const autojobsinvoicelinebatches: IAutojobsinvoicelinebatches = sampleWithRequiredData;
        expectedResult = service.addAutojobsinvoicelinebatchesToCollectionIfMissing([], null, autojobsinvoicelinebatches, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(autojobsinvoicelinebatches);
      });

      it('should return initial array if no Autojobsinvoicelinebatches is added', () => {
        const autojobsinvoicelinebatchesCollection: IAutojobsinvoicelinebatches[] = [sampleWithRequiredData];
        expectedResult = service.addAutojobsinvoicelinebatchesToCollectionIfMissing(autojobsinvoicelinebatchesCollection, undefined, null);
        expect(expectedResult).toEqual(autojobsinvoicelinebatchesCollection);
      });
    });

    describe('compareAutojobsinvoicelinebatches', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareAutojobsinvoicelinebatches(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareAutojobsinvoicelinebatches(entity1, entity2);
        const compareResult2 = service.compareAutojobsinvoicelinebatches(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareAutojobsinvoicelinebatches(entity1, entity2);
        const compareResult2 = service.compareAutojobsinvoicelinebatches(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareAutojobsinvoicelinebatches(entity1, entity2);
        const compareResult2 = service.compareAutojobsinvoicelinebatches(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
