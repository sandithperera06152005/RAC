import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { ISaleInvoiceCommonServiceChargeDummy } from '../sale-invoice-common-service-charge-dummy.model';
import {
  sampleWithFullData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithRequiredData,
} from '../sale-invoice-common-service-charge-dummy.test-samples';

import { SaleInvoiceCommonServiceChargeDummyService } from './sale-invoice-common-service-charge-dummy.service';

const requireRestSample: ISaleInvoiceCommonServiceChargeDummy = {
  ...sampleWithRequiredData,
};

describe('SaleInvoiceCommonServiceChargeDummy Service', () => {
  let service: SaleInvoiceCommonServiceChargeDummyService;
  let httpMock: HttpTestingController;
  let expectedResult: ISaleInvoiceCommonServiceChargeDummy | ISaleInvoiceCommonServiceChargeDummy[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(SaleInvoiceCommonServiceChargeDummyService);
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

    it('should create a SaleInvoiceCommonServiceChargeDummy', () => {
      const saleInvoiceCommonServiceChargeDummy = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(saleInvoiceCommonServiceChargeDummy).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a SaleInvoiceCommonServiceChargeDummy', () => {
      const saleInvoiceCommonServiceChargeDummy = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(saleInvoiceCommonServiceChargeDummy).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a SaleInvoiceCommonServiceChargeDummy', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of SaleInvoiceCommonServiceChargeDummy', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a SaleInvoiceCommonServiceChargeDummy', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addSaleInvoiceCommonServiceChargeDummyToCollectionIfMissing', () => {
      it('should add a SaleInvoiceCommonServiceChargeDummy to an empty array', () => {
        const saleInvoiceCommonServiceChargeDummy: ISaleInvoiceCommonServiceChargeDummy = sampleWithRequiredData;
        expectedResult = service.addSaleInvoiceCommonServiceChargeDummyToCollectionIfMissing([], saleInvoiceCommonServiceChargeDummy);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(saleInvoiceCommonServiceChargeDummy);
      });

      it('should not add a SaleInvoiceCommonServiceChargeDummy to an array that contains it', () => {
        const saleInvoiceCommonServiceChargeDummy: ISaleInvoiceCommonServiceChargeDummy = sampleWithRequiredData;
        const saleInvoiceCommonServiceChargeDummyCollection: ISaleInvoiceCommonServiceChargeDummy[] = [
          {
            ...saleInvoiceCommonServiceChargeDummy,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addSaleInvoiceCommonServiceChargeDummyToCollectionIfMissing(
          saleInvoiceCommonServiceChargeDummyCollection,
          saleInvoiceCommonServiceChargeDummy,
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SaleInvoiceCommonServiceChargeDummy to an array that doesn't contain it", () => {
        const saleInvoiceCommonServiceChargeDummy: ISaleInvoiceCommonServiceChargeDummy = sampleWithRequiredData;
        const saleInvoiceCommonServiceChargeDummyCollection: ISaleInvoiceCommonServiceChargeDummy[] = [sampleWithPartialData];
        expectedResult = service.addSaleInvoiceCommonServiceChargeDummyToCollectionIfMissing(
          saleInvoiceCommonServiceChargeDummyCollection,
          saleInvoiceCommonServiceChargeDummy,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(saleInvoiceCommonServiceChargeDummy);
      });

      it('should add only unique SaleInvoiceCommonServiceChargeDummy to an array', () => {
        const saleInvoiceCommonServiceChargeDummyArray: ISaleInvoiceCommonServiceChargeDummy[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const saleInvoiceCommonServiceChargeDummyCollection: ISaleInvoiceCommonServiceChargeDummy[] = [sampleWithRequiredData];
        expectedResult = service.addSaleInvoiceCommonServiceChargeDummyToCollectionIfMissing(
          saleInvoiceCommonServiceChargeDummyCollection,
          ...saleInvoiceCommonServiceChargeDummyArray,
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const saleInvoiceCommonServiceChargeDummy: ISaleInvoiceCommonServiceChargeDummy = sampleWithRequiredData;
        const saleInvoiceCommonServiceChargeDummy2: ISaleInvoiceCommonServiceChargeDummy = sampleWithPartialData;
        expectedResult = service.addSaleInvoiceCommonServiceChargeDummyToCollectionIfMissing(
          [],
          saleInvoiceCommonServiceChargeDummy,
          saleInvoiceCommonServiceChargeDummy2,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(saleInvoiceCommonServiceChargeDummy);
        expect(expectedResult).toContain(saleInvoiceCommonServiceChargeDummy2);
      });

      it('should accept null and undefined values', () => {
        const saleInvoiceCommonServiceChargeDummy: ISaleInvoiceCommonServiceChargeDummy = sampleWithRequiredData;
        expectedResult = service.addSaleInvoiceCommonServiceChargeDummyToCollectionIfMissing(
          [],
          null,
          saleInvoiceCommonServiceChargeDummy,
          undefined,
        );
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(saleInvoiceCommonServiceChargeDummy);
      });

      it('should return initial array if no SaleInvoiceCommonServiceChargeDummy is added', () => {
        const saleInvoiceCommonServiceChargeDummyCollection: ISaleInvoiceCommonServiceChargeDummy[] = [sampleWithRequiredData];
        expectedResult = service.addSaleInvoiceCommonServiceChargeDummyToCollectionIfMissing(
          saleInvoiceCommonServiceChargeDummyCollection,
          undefined,
          null,
        );
        expect(expectedResult).toEqual(saleInvoiceCommonServiceChargeDummyCollection);
      });
    });

    describe('compareSaleInvoiceCommonServiceChargeDummy', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareSaleInvoiceCommonServiceChargeDummy(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareSaleInvoiceCommonServiceChargeDummy(entity1, entity2);
        const compareResult2 = service.compareSaleInvoiceCommonServiceChargeDummy(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareSaleInvoiceCommonServiceChargeDummy(entity1, entity2);
        const compareResult2 = service.compareSaleInvoiceCommonServiceChargeDummy(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareSaleInvoiceCommonServiceChargeDummy(entity1, entity2);
        const compareResult2 = service.compareSaleInvoiceCommonServiceChargeDummy(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
