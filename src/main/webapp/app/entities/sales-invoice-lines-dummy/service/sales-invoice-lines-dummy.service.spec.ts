import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { ISalesInvoiceLinesDummy } from '../sales-invoice-lines-dummy.model';
import {
  sampleWithFullData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithRequiredData,
} from '../sales-invoice-lines-dummy.test-samples';

import { RestSalesInvoiceLinesDummy, SalesInvoiceLinesDummyService } from './sales-invoice-lines-dummy.service';

const requireRestSample: RestSalesInvoiceLinesDummy = {
  ...sampleWithRequiredData,
  lmd: sampleWithRequiredData.lmd?.toJSON(),
};

describe('SalesInvoiceLinesDummy Service', () => {
  let service: SalesInvoiceLinesDummyService;
  let httpMock: HttpTestingController;
  let expectedResult: ISalesInvoiceLinesDummy | ISalesInvoiceLinesDummy[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(SalesInvoiceLinesDummyService);
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

    it('should create a SalesInvoiceLinesDummy', () => {
      const salesInvoiceLinesDummy = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(salesInvoiceLinesDummy).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a SalesInvoiceLinesDummy', () => {
      const salesInvoiceLinesDummy = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(salesInvoiceLinesDummy).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a SalesInvoiceLinesDummy', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of SalesInvoiceLinesDummy', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a SalesInvoiceLinesDummy', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addSalesInvoiceLinesDummyToCollectionIfMissing', () => {
      it('should add a SalesInvoiceLinesDummy to an empty array', () => {
        const salesInvoiceLinesDummy: ISalesInvoiceLinesDummy = sampleWithRequiredData;
        expectedResult = service.addSalesInvoiceLinesDummyToCollectionIfMissing([], salesInvoiceLinesDummy);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(salesInvoiceLinesDummy);
      });

      it('should not add a SalesInvoiceLinesDummy to an array that contains it', () => {
        const salesInvoiceLinesDummy: ISalesInvoiceLinesDummy = sampleWithRequiredData;
        const salesInvoiceLinesDummyCollection: ISalesInvoiceLinesDummy[] = [
          {
            ...salesInvoiceLinesDummy,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addSalesInvoiceLinesDummyToCollectionIfMissing(salesInvoiceLinesDummyCollection, salesInvoiceLinesDummy);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SalesInvoiceLinesDummy to an array that doesn't contain it", () => {
        const salesInvoiceLinesDummy: ISalesInvoiceLinesDummy = sampleWithRequiredData;
        const salesInvoiceLinesDummyCollection: ISalesInvoiceLinesDummy[] = [sampleWithPartialData];
        expectedResult = service.addSalesInvoiceLinesDummyToCollectionIfMissing(salesInvoiceLinesDummyCollection, salesInvoiceLinesDummy);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(salesInvoiceLinesDummy);
      });

      it('should add only unique SalesInvoiceLinesDummy to an array', () => {
        const salesInvoiceLinesDummyArray: ISalesInvoiceLinesDummy[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const salesInvoiceLinesDummyCollection: ISalesInvoiceLinesDummy[] = [sampleWithRequiredData];
        expectedResult = service.addSalesInvoiceLinesDummyToCollectionIfMissing(
          salesInvoiceLinesDummyCollection,
          ...salesInvoiceLinesDummyArray,
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const salesInvoiceLinesDummy: ISalesInvoiceLinesDummy = sampleWithRequiredData;
        const salesInvoiceLinesDummy2: ISalesInvoiceLinesDummy = sampleWithPartialData;
        expectedResult = service.addSalesInvoiceLinesDummyToCollectionIfMissing([], salesInvoiceLinesDummy, salesInvoiceLinesDummy2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(salesInvoiceLinesDummy);
        expect(expectedResult).toContain(salesInvoiceLinesDummy2);
      });

      it('should accept null and undefined values', () => {
        const salesInvoiceLinesDummy: ISalesInvoiceLinesDummy = sampleWithRequiredData;
        expectedResult = service.addSalesInvoiceLinesDummyToCollectionIfMissing([], null, salesInvoiceLinesDummy, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(salesInvoiceLinesDummy);
      });

      it('should return initial array if no SalesInvoiceLinesDummy is added', () => {
        const salesInvoiceLinesDummyCollection: ISalesInvoiceLinesDummy[] = [sampleWithRequiredData];
        expectedResult = service.addSalesInvoiceLinesDummyToCollectionIfMissing(salesInvoiceLinesDummyCollection, undefined, null);
        expect(expectedResult).toEqual(salesInvoiceLinesDummyCollection);
      });
    });

    describe('compareSalesInvoiceLinesDummy', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareSalesInvoiceLinesDummy(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareSalesInvoiceLinesDummy(entity1, entity2);
        const compareResult2 = service.compareSalesInvoiceLinesDummy(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareSalesInvoiceLinesDummy(entity1, entity2);
        const compareResult2 = service.compareSalesInvoiceLinesDummy(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareSalesInvoiceLinesDummy(entity1, entity2);
        const compareResult2 = service.compareSalesInvoiceLinesDummy(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
