import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { ISalesInvoiceServiceChargeLine } from '../sales-invoice-service-charge-line.model';
import {
  sampleWithFullData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithRequiredData,
} from '../sales-invoice-service-charge-line.test-samples';

import { SalesInvoiceServiceChargeLineService } from './sales-invoice-service-charge-line.service';

const requireRestSample: ISalesInvoiceServiceChargeLine = {
  ...sampleWithRequiredData,
};

describe('SalesInvoiceServiceChargeLine Service', () => {
  let service: SalesInvoiceServiceChargeLineService;
  let httpMock: HttpTestingController;
  let expectedResult: ISalesInvoiceServiceChargeLine | ISalesInvoiceServiceChargeLine[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(SalesInvoiceServiceChargeLineService);
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

    it('should create a SalesInvoiceServiceChargeLine', () => {
      const salesInvoiceServiceChargeLine = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(salesInvoiceServiceChargeLine).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a SalesInvoiceServiceChargeLine', () => {
      const salesInvoiceServiceChargeLine = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(salesInvoiceServiceChargeLine).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a SalesInvoiceServiceChargeLine', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of SalesInvoiceServiceChargeLine', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a SalesInvoiceServiceChargeLine', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addSalesInvoiceServiceChargeLineToCollectionIfMissing', () => {
      it('should add a SalesInvoiceServiceChargeLine to an empty array', () => {
        const salesInvoiceServiceChargeLine: ISalesInvoiceServiceChargeLine = sampleWithRequiredData;
        expectedResult = service.addSalesInvoiceServiceChargeLineToCollectionIfMissing([], salesInvoiceServiceChargeLine);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(salesInvoiceServiceChargeLine);
      });

      it('should not add a SalesInvoiceServiceChargeLine to an array that contains it', () => {
        const salesInvoiceServiceChargeLine: ISalesInvoiceServiceChargeLine = sampleWithRequiredData;
        const salesInvoiceServiceChargeLineCollection: ISalesInvoiceServiceChargeLine[] = [
          {
            ...salesInvoiceServiceChargeLine,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addSalesInvoiceServiceChargeLineToCollectionIfMissing(
          salesInvoiceServiceChargeLineCollection,
          salesInvoiceServiceChargeLine,
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SalesInvoiceServiceChargeLine to an array that doesn't contain it", () => {
        const salesInvoiceServiceChargeLine: ISalesInvoiceServiceChargeLine = sampleWithRequiredData;
        const salesInvoiceServiceChargeLineCollection: ISalesInvoiceServiceChargeLine[] = [sampleWithPartialData];
        expectedResult = service.addSalesInvoiceServiceChargeLineToCollectionIfMissing(
          salesInvoiceServiceChargeLineCollection,
          salesInvoiceServiceChargeLine,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(salesInvoiceServiceChargeLine);
      });

      it('should add only unique SalesInvoiceServiceChargeLine to an array', () => {
        const salesInvoiceServiceChargeLineArray: ISalesInvoiceServiceChargeLine[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const salesInvoiceServiceChargeLineCollection: ISalesInvoiceServiceChargeLine[] = [sampleWithRequiredData];
        expectedResult = service.addSalesInvoiceServiceChargeLineToCollectionIfMissing(
          salesInvoiceServiceChargeLineCollection,
          ...salesInvoiceServiceChargeLineArray,
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const salesInvoiceServiceChargeLine: ISalesInvoiceServiceChargeLine = sampleWithRequiredData;
        const salesInvoiceServiceChargeLine2: ISalesInvoiceServiceChargeLine = sampleWithPartialData;
        expectedResult = service.addSalesInvoiceServiceChargeLineToCollectionIfMissing(
          [],
          salesInvoiceServiceChargeLine,
          salesInvoiceServiceChargeLine2,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(salesInvoiceServiceChargeLine);
        expect(expectedResult).toContain(salesInvoiceServiceChargeLine2);
      });

      it('should accept null and undefined values', () => {
        const salesInvoiceServiceChargeLine: ISalesInvoiceServiceChargeLine = sampleWithRequiredData;
        expectedResult = service.addSalesInvoiceServiceChargeLineToCollectionIfMissing([], null, salesInvoiceServiceChargeLine, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(salesInvoiceServiceChargeLine);
      });

      it('should return initial array if no SalesInvoiceServiceChargeLine is added', () => {
        const salesInvoiceServiceChargeLineCollection: ISalesInvoiceServiceChargeLine[] = [sampleWithRequiredData];
        expectedResult = service.addSalesInvoiceServiceChargeLineToCollectionIfMissing(
          salesInvoiceServiceChargeLineCollection,
          undefined,
          null,
        );
        expect(expectedResult).toEqual(salesInvoiceServiceChargeLineCollection);
      });
    });

    describe('compareSalesInvoiceServiceChargeLine', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareSalesInvoiceServiceChargeLine(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareSalesInvoiceServiceChargeLine(entity1, entity2);
        const compareResult2 = service.compareSalesInvoiceServiceChargeLine(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareSalesInvoiceServiceChargeLine(entity1, entity2);
        const compareResult2 = service.compareSalesInvoiceServiceChargeLine(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareSalesInvoiceServiceChargeLine(entity1, entity2);
        const compareResult2 = service.compareSalesInvoiceServiceChargeLine(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
