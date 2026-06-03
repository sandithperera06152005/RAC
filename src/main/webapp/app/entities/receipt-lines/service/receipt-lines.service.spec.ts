import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IReceiptLines } from '../receipt-lines.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../receipt-lines.test-samples';

import { ReceiptLinesService, RestReceiptLines } from './receipt-lines.service';

const requireRestSample: RestReceiptLines = {
  ...sampleWithRequiredData,
  lmd: sampleWithRequiredData.lmd?.toJSON(),
};

describe('ReceiptLines Service', () => {
  let service: ReceiptLinesService;
  let httpMock: HttpTestingController;
  let expectedResult: IReceiptLines | IReceiptLines[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(ReceiptLinesService);
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

    it('should create a ReceiptLines', () => {
      const receiptLines = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(receiptLines).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ReceiptLines', () => {
      const receiptLines = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(receiptLines).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ReceiptLines', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ReceiptLines', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ReceiptLines', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addReceiptLinesToCollectionIfMissing', () => {
      it('should add a ReceiptLines to an empty array', () => {
        const receiptLines: IReceiptLines = sampleWithRequiredData;
        expectedResult = service.addReceiptLinesToCollectionIfMissing([], receiptLines);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(receiptLines);
      });

      it('should not add a ReceiptLines to an array that contains it', () => {
        const receiptLines: IReceiptLines = sampleWithRequiredData;
        const receiptLinesCollection: IReceiptLines[] = [
          {
            ...receiptLines,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addReceiptLinesToCollectionIfMissing(receiptLinesCollection, receiptLines);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ReceiptLines to an array that doesn't contain it", () => {
        const receiptLines: IReceiptLines = sampleWithRequiredData;
        const receiptLinesCollection: IReceiptLines[] = [sampleWithPartialData];
        expectedResult = service.addReceiptLinesToCollectionIfMissing(receiptLinesCollection, receiptLines);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(receiptLines);
      });

      it('should add only unique ReceiptLines to an array', () => {
        const receiptLinesArray: IReceiptLines[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const receiptLinesCollection: IReceiptLines[] = [sampleWithRequiredData];
        expectedResult = service.addReceiptLinesToCollectionIfMissing(receiptLinesCollection, ...receiptLinesArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const receiptLines: IReceiptLines = sampleWithRequiredData;
        const receiptLines2: IReceiptLines = sampleWithPartialData;
        expectedResult = service.addReceiptLinesToCollectionIfMissing([], receiptLines, receiptLines2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(receiptLines);
        expect(expectedResult).toContain(receiptLines2);
      });

      it('should accept null and undefined values', () => {
        const receiptLines: IReceiptLines = sampleWithRequiredData;
        expectedResult = service.addReceiptLinesToCollectionIfMissing([], null, receiptLines, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(receiptLines);
      });

      it('should return initial array if no ReceiptLines is added', () => {
        const receiptLinesCollection: IReceiptLines[] = [sampleWithRequiredData];
        expectedResult = service.addReceiptLinesToCollectionIfMissing(receiptLinesCollection, undefined, null);
        expect(expectedResult).toEqual(receiptLinesCollection);
      });
    });

    describe('compareReceiptLines', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareReceiptLines(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareReceiptLines(entity1, entity2);
        const compareResult2 = service.compareReceiptLines(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareReceiptLines(entity1, entity2);
        const compareResult2 = service.compareReceiptLines(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareReceiptLines(entity1, entity2);
        const compareResult2 = service.compareReceiptLines(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
