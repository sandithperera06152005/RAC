import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { ISaleInvoiceCommonServiceCharge } from '../sale-invoice-common-service-charge.model';
import {
  sampleWithFullData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithRequiredData,
} from '../sale-invoice-common-service-charge.test-samples';

import { SaleInvoiceCommonServiceChargeService } from './sale-invoice-common-service-charge.service';

const requireRestSample: ISaleInvoiceCommonServiceCharge = {
  ...sampleWithRequiredData,
};

describe('SaleInvoiceCommonServiceCharge Service', () => {
  let service: SaleInvoiceCommonServiceChargeService;
  let httpMock: HttpTestingController;
  let expectedResult: ISaleInvoiceCommonServiceCharge | ISaleInvoiceCommonServiceCharge[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(SaleInvoiceCommonServiceChargeService);
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

    it('should create a SaleInvoiceCommonServiceCharge', () => {
      const saleInvoiceCommonServiceCharge = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(saleInvoiceCommonServiceCharge).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a SaleInvoiceCommonServiceCharge', () => {
      const saleInvoiceCommonServiceCharge = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(saleInvoiceCommonServiceCharge).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a SaleInvoiceCommonServiceCharge', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of SaleInvoiceCommonServiceCharge', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a SaleInvoiceCommonServiceCharge', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addSaleInvoiceCommonServiceChargeToCollectionIfMissing', () => {
      it('should add a SaleInvoiceCommonServiceCharge to an empty array', () => {
        const saleInvoiceCommonServiceCharge: ISaleInvoiceCommonServiceCharge = sampleWithRequiredData;
        expectedResult = service.addSaleInvoiceCommonServiceChargeToCollectionIfMissing([], saleInvoiceCommonServiceCharge);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(saleInvoiceCommonServiceCharge);
      });

      it('should not add a SaleInvoiceCommonServiceCharge to an array that contains it', () => {
        const saleInvoiceCommonServiceCharge: ISaleInvoiceCommonServiceCharge = sampleWithRequiredData;
        const saleInvoiceCommonServiceChargeCollection: ISaleInvoiceCommonServiceCharge[] = [
          {
            ...saleInvoiceCommonServiceCharge,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addSaleInvoiceCommonServiceChargeToCollectionIfMissing(
          saleInvoiceCommonServiceChargeCollection,
          saleInvoiceCommonServiceCharge,
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SaleInvoiceCommonServiceCharge to an array that doesn't contain it", () => {
        const saleInvoiceCommonServiceCharge: ISaleInvoiceCommonServiceCharge = sampleWithRequiredData;
        const saleInvoiceCommonServiceChargeCollection: ISaleInvoiceCommonServiceCharge[] = [sampleWithPartialData];
        expectedResult = service.addSaleInvoiceCommonServiceChargeToCollectionIfMissing(
          saleInvoiceCommonServiceChargeCollection,
          saleInvoiceCommonServiceCharge,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(saleInvoiceCommonServiceCharge);
      });

      it('should add only unique SaleInvoiceCommonServiceCharge to an array', () => {
        const saleInvoiceCommonServiceChargeArray: ISaleInvoiceCommonServiceCharge[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const saleInvoiceCommonServiceChargeCollection: ISaleInvoiceCommonServiceCharge[] = [sampleWithRequiredData];
        expectedResult = service.addSaleInvoiceCommonServiceChargeToCollectionIfMissing(
          saleInvoiceCommonServiceChargeCollection,
          ...saleInvoiceCommonServiceChargeArray,
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const saleInvoiceCommonServiceCharge: ISaleInvoiceCommonServiceCharge = sampleWithRequiredData;
        const saleInvoiceCommonServiceCharge2: ISaleInvoiceCommonServiceCharge = sampleWithPartialData;
        expectedResult = service.addSaleInvoiceCommonServiceChargeToCollectionIfMissing(
          [],
          saleInvoiceCommonServiceCharge,
          saleInvoiceCommonServiceCharge2,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(saleInvoiceCommonServiceCharge);
        expect(expectedResult).toContain(saleInvoiceCommonServiceCharge2);
      });

      it('should accept null and undefined values', () => {
        const saleInvoiceCommonServiceCharge: ISaleInvoiceCommonServiceCharge = sampleWithRequiredData;
        expectedResult = service.addSaleInvoiceCommonServiceChargeToCollectionIfMissing(
          [],
          null,
          saleInvoiceCommonServiceCharge,
          undefined,
        );
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(saleInvoiceCommonServiceCharge);
      });

      it('should return initial array if no SaleInvoiceCommonServiceCharge is added', () => {
        const saleInvoiceCommonServiceChargeCollection: ISaleInvoiceCommonServiceCharge[] = [sampleWithRequiredData];
        expectedResult = service.addSaleInvoiceCommonServiceChargeToCollectionIfMissing(
          saleInvoiceCommonServiceChargeCollection,
          undefined,
          null,
        );
        expect(expectedResult).toEqual(saleInvoiceCommonServiceChargeCollection);
      });
    });

    describe('compareSaleInvoiceCommonServiceCharge', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareSaleInvoiceCommonServiceCharge(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareSaleInvoiceCommonServiceCharge(entity1, entity2);
        const compareResult2 = service.compareSaleInvoiceCommonServiceCharge(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareSaleInvoiceCommonServiceCharge(entity1, entity2);
        const compareResult2 = service.compareSaleInvoiceCommonServiceCharge(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareSaleInvoiceCommonServiceCharge(entity1, entity2);
        const compareResult2 = service.compareSaleInvoiceCommonServiceCharge(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
