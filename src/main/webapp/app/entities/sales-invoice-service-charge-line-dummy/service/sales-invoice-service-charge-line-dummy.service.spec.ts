import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { ISalesInvoiceServiceChargeLineDummy } from '../sales-invoice-service-charge-line-dummy.model';
import {
  sampleWithFullData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithRequiredData,
} from '../sales-invoice-service-charge-line-dummy.test-samples';

import { SalesInvoiceServiceChargeLineDummyService } from './sales-invoice-service-charge-line-dummy.service';

const requireRestSample: ISalesInvoiceServiceChargeLineDummy = {
  ...sampleWithRequiredData,
};

describe('SalesInvoiceServiceChargeLineDummy Service', () => {
  let service: SalesInvoiceServiceChargeLineDummyService;
  let httpMock: HttpTestingController;
  let expectedResult: ISalesInvoiceServiceChargeLineDummy | ISalesInvoiceServiceChargeLineDummy[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(SalesInvoiceServiceChargeLineDummyService);
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

    it('should create a SalesInvoiceServiceChargeLineDummy', () => {
      const salesInvoiceServiceChargeLineDummy = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(salesInvoiceServiceChargeLineDummy).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a SalesInvoiceServiceChargeLineDummy', () => {
      const salesInvoiceServiceChargeLineDummy = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(salesInvoiceServiceChargeLineDummy).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a SalesInvoiceServiceChargeLineDummy', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of SalesInvoiceServiceChargeLineDummy', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a SalesInvoiceServiceChargeLineDummy', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addSalesInvoiceServiceChargeLineDummyToCollectionIfMissing', () => {
      it('should add a SalesInvoiceServiceChargeLineDummy to an empty array', () => {
        const salesInvoiceServiceChargeLineDummy: ISalesInvoiceServiceChargeLineDummy = sampleWithRequiredData;
        expectedResult = service.addSalesInvoiceServiceChargeLineDummyToCollectionIfMissing([], salesInvoiceServiceChargeLineDummy);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(salesInvoiceServiceChargeLineDummy);
      });

      it('should not add a SalesInvoiceServiceChargeLineDummy to an array that contains it', () => {
        const salesInvoiceServiceChargeLineDummy: ISalesInvoiceServiceChargeLineDummy = sampleWithRequiredData;
        const salesInvoiceServiceChargeLineDummyCollection: ISalesInvoiceServiceChargeLineDummy[] = [
          {
            ...salesInvoiceServiceChargeLineDummy,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addSalesInvoiceServiceChargeLineDummyToCollectionIfMissing(
          salesInvoiceServiceChargeLineDummyCollection,
          salesInvoiceServiceChargeLineDummy,
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SalesInvoiceServiceChargeLineDummy to an array that doesn't contain it", () => {
        const salesInvoiceServiceChargeLineDummy: ISalesInvoiceServiceChargeLineDummy = sampleWithRequiredData;
        const salesInvoiceServiceChargeLineDummyCollection: ISalesInvoiceServiceChargeLineDummy[] = [sampleWithPartialData];
        expectedResult = service.addSalesInvoiceServiceChargeLineDummyToCollectionIfMissing(
          salesInvoiceServiceChargeLineDummyCollection,
          salesInvoiceServiceChargeLineDummy,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(salesInvoiceServiceChargeLineDummy);
      });

      it('should add only unique SalesInvoiceServiceChargeLineDummy to an array', () => {
        const salesInvoiceServiceChargeLineDummyArray: ISalesInvoiceServiceChargeLineDummy[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const salesInvoiceServiceChargeLineDummyCollection: ISalesInvoiceServiceChargeLineDummy[] = [sampleWithRequiredData];
        expectedResult = service.addSalesInvoiceServiceChargeLineDummyToCollectionIfMissing(
          salesInvoiceServiceChargeLineDummyCollection,
          ...salesInvoiceServiceChargeLineDummyArray,
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const salesInvoiceServiceChargeLineDummy: ISalesInvoiceServiceChargeLineDummy = sampleWithRequiredData;
        const salesInvoiceServiceChargeLineDummy2: ISalesInvoiceServiceChargeLineDummy = sampleWithPartialData;
        expectedResult = service.addSalesInvoiceServiceChargeLineDummyToCollectionIfMissing(
          [],
          salesInvoiceServiceChargeLineDummy,
          salesInvoiceServiceChargeLineDummy2,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(salesInvoiceServiceChargeLineDummy);
        expect(expectedResult).toContain(salesInvoiceServiceChargeLineDummy2);
      });

      it('should accept null and undefined values', () => {
        const salesInvoiceServiceChargeLineDummy: ISalesInvoiceServiceChargeLineDummy = sampleWithRequiredData;
        expectedResult = service.addSalesInvoiceServiceChargeLineDummyToCollectionIfMissing(
          [],
          null,
          salesInvoiceServiceChargeLineDummy,
          undefined,
        );
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(salesInvoiceServiceChargeLineDummy);
      });

      it('should return initial array if no SalesInvoiceServiceChargeLineDummy is added', () => {
        const salesInvoiceServiceChargeLineDummyCollection: ISalesInvoiceServiceChargeLineDummy[] = [sampleWithRequiredData];
        expectedResult = service.addSalesInvoiceServiceChargeLineDummyToCollectionIfMissing(
          salesInvoiceServiceChargeLineDummyCollection,
          undefined,
          null,
        );
        expect(expectedResult).toEqual(salesInvoiceServiceChargeLineDummyCollection);
      });
    });

    describe('compareSalesInvoiceServiceChargeLineDummy', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareSalesInvoiceServiceChargeLineDummy(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareSalesInvoiceServiceChargeLineDummy(entity1, entity2);
        const compareResult2 = service.compareSalesInvoiceServiceChargeLineDummy(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareSalesInvoiceServiceChargeLineDummy(entity1, entity2);
        const compareResult2 = service.compareSalesInvoiceServiceChargeLineDummy(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareSalesInvoiceServiceChargeLineDummy(entity1, entity2);
        const compareResult2 = service.compareSalesInvoiceServiceChargeLineDummy(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
