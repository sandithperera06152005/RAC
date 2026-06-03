import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IAutojobsinvoicelines } from '../autojobsinvoicelines.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../autojobsinvoicelines.test-samples';

import { AutojobsinvoicelinesService, RestAutojobsinvoicelines } from './autojobsinvoicelines.service';

const requireRestSample: RestAutojobsinvoicelines = {
  ...sampleWithRequiredData,
  lmd: sampleWithRequiredData.lmd?.toJSON(),
};

describe('Autojobsinvoicelines Service', () => {
  let service: AutojobsinvoicelinesService;
  let httpMock: HttpTestingController;
  let expectedResult: IAutojobsinvoicelines | IAutojobsinvoicelines[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(AutojobsinvoicelinesService);
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

    it('should create a Autojobsinvoicelines', () => {
      const autojobsinvoicelines = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(autojobsinvoicelines).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Autojobsinvoicelines', () => {
      const autojobsinvoicelines = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(autojobsinvoicelines).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Autojobsinvoicelines', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Autojobsinvoicelines', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Autojobsinvoicelines', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addAutojobsinvoicelinesToCollectionIfMissing', () => {
      it('should add a Autojobsinvoicelines to an empty array', () => {
        const autojobsinvoicelines: IAutojobsinvoicelines = sampleWithRequiredData;
        expectedResult = service.addAutojobsinvoicelinesToCollectionIfMissing([], autojobsinvoicelines);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(autojobsinvoicelines);
      });

      it('should not add a Autojobsinvoicelines to an array that contains it', () => {
        const autojobsinvoicelines: IAutojobsinvoicelines = sampleWithRequiredData;
        const autojobsinvoicelinesCollection: IAutojobsinvoicelines[] = [
          {
            ...autojobsinvoicelines,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addAutojobsinvoicelinesToCollectionIfMissing(autojobsinvoicelinesCollection, autojobsinvoicelines);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Autojobsinvoicelines to an array that doesn't contain it", () => {
        const autojobsinvoicelines: IAutojobsinvoicelines = sampleWithRequiredData;
        const autojobsinvoicelinesCollection: IAutojobsinvoicelines[] = [sampleWithPartialData];
        expectedResult = service.addAutojobsinvoicelinesToCollectionIfMissing(autojobsinvoicelinesCollection, autojobsinvoicelines);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(autojobsinvoicelines);
      });

      it('should add only unique Autojobsinvoicelines to an array', () => {
        const autojobsinvoicelinesArray: IAutojobsinvoicelines[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const autojobsinvoicelinesCollection: IAutojobsinvoicelines[] = [sampleWithRequiredData];
        expectedResult = service.addAutojobsinvoicelinesToCollectionIfMissing(autojobsinvoicelinesCollection, ...autojobsinvoicelinesArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const autojobsinvoicelines: IAutojobsinvoicelines = sampleWithRequiredData;
        const autojobsinvoicelines2: IAutojobsinvoicelines = sampleWithPartialData;
        expectedResult = service.addAutojobsinvoicelinesToCollectionIfMissing([], autojobsinvoicelines, autojobsinvoicelines2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(autojobsinvoicelines);
        expect(expectedResult).toContain(autojobsinvoicelines2);
      });

      it('should accept null and undefined values', () => {
        const autojobsinvoicelines: IAutojobsinvoicelines = sampleWithRequiredData;
        expectedResult = service.addAutojobsinvoicelinesToCollectionIfMissing([], null, autojobsinvoicelines, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(autojobsinvoicelines);
      });

      it('should return initial array if no Autojobsinvoicelines is added', () => {
        const autojobsinvoicelinesCollection: IAutojobsinvoicelines[] = [sampleWithRequiredData];
        expectedResult = service.addAutojobsinvoicelinesToCollectionIfMissing(autojobsinvoicelinesCollection, undefined, null);
        expect(expectedResult).toEqual(autojobsinvoicelinesCollection);
      });
    });

    describe('compareAutojobsinvoicelines', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareAutojobsinvoicelines(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareAutojobsinvoicelines(entity1, entity2);
        const compareResult2 = service.compareAutojobsinvoicelines(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareAutojobsinvoicelines(entity1, entity2);
        const compareResult2 = service.compareAutojobsinvoicelines(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareAutojobsinvoicelines(entity1, entity2);
        const compareResult2 = service.compareAutojobsinvoicelines(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
