import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { ISalesInvoiceDummy } from '../sales-invoice-dummy.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../sales-invoice-dummy.test-samples';

import { RestSalesInvoiceDummy, SalesInvoiceDummyService } from './sales-invoice-dummy.service';

const requireRestSample: RestSalesInvoiceDummy = {
  ...sampleWithRequiredData,
  invoiceDate: sampleWithRequiredData.invoiceDate?.toJSON(),
  createdDate: sampleWithRequiredData.createdDate?.toJSON(),
  deliveryDate: sampleWithRequiredData.deliveryDate?.toJSON(),
  lmd: sampleWithRequiredData.lmd?.toJSON(),
  commissionPaidDate: sampleWithRequiredData.commissionPaidDate?.toJSON(),
};

describe('SalesInvoiceDummy Service', () => {
  let service: SalesInvoiceDummyService;
  let httpMock: HttpTestingController;
  let expectedResult: ISalesInvoiceDummy | ISalesInvoiceDummy[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(SalesInvoiceDummyService);
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

    it('should create a SalesInvoiceDummy', () => {
      const salesInvoiceDummy = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(salesInvoiceDummy).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a SalesInvoiceDummy', () => {
      const salesInvoiceDummy = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(salesInvoiceDummy).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a SalesInvoiceDummy', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of SalesInvoiceDummy', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a SalesInvoiceDummy', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addSalesInvoiceDummyToCollectionIfMissing', () => {
      it('should add a SalesInvoiceDummy to an empty array', () => {
        const salesInvoiceDummy: ISalesInvoiceDummy = sampleWithRequiredData;
        expectedResult = service.addSalesInvoiceDummyToCollectionIfMissing([], salesInvoiceDummy);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(salesInvoiceDummy);
      });

      it('should not add a SalesInvoiceDummy to an array that contains it', () => {
        const salesInvoiceDummy: ISalesInvoiceDummy = sampleWithRequiredData;
        const salesInvoiceDummyCollection: ISalesInvoiceDummy[] = [
          {
            ...salesInvoiceDummy,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addSalesInvoiceDummyToCollectionIfMissing(salesInvoiceDummyCollection, salesInvoiceDummy);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SalesInvoiceDummy to an array that doesn't contain it", () => {
        const salesInvoiceDummy: ISalesInvoiceDummy = sampleWithRequiredData;
        const salesInvoiceDummyCollection: ISalesInvoiceDummy[] = [sampleWithPartialData];
        expectedResult = service.addSalesInvoiceDummyToCollectionIfMissing(salesInvoiceDummyCollection, salesInvoiceDummy);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(salesInvoiceDummy);
      });

      it('should add only unique SalesInvoiceDummy to an array', () => {
        const salesInvoiceDummyArray: ISalesInvoiceDummy[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const salesInvoiceDummyCollection: ISalesInvoiceDummy[] = [sampleWithRequiredData];
        expectedResult = service.addSalesInvoiceDummyToCollectionIfMissing(salesInvoiceDummyCollection, ...salesInvoiceDummyArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const salesInvoiceDummy: ISalesInvoiceDummy = sampleWithRequiredData;
        const salesInvoiceDummy2: ISalesInvoiceDummy = sampleWithPartialData;
        expectedResult = service.addSalesInvoiceDummyToCollectionIfMissing([], salesInvoiceDummy, salesInvoiceDummy2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(salesInvoiceDummy);
        expect(expectedResult).toContain(salesInvoiceDummy2);
      });

      it('should accept null and undefined values', () => {
        const salesInvoiceDummy: ISalesInvoiceDummy = sampleWithRequiredData;
        expectedResult = service.addSalesInvoiceDummyToCollectionIfMissing([], null, salesInvoiceDummy, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(salesInvoiceDummy);
      });

      it('should return initial array if no SalesInvoiceDummy is added', () => {
        const salesInvoiceDummyCollection: ISalesInvoiceDummy[] = [sampleWithRequiredData];
        expectedResult = service.addSalesInvoiceDummyToCollectionIfMissing(salesInvoiceDummyCollection, undefined, null);
        expect(expectedResult).toEqual(salesInvoiceDummyCollection);
      });
    });

    describe('compareSalesInvoiceDummy', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareSalesInvoiceDummy(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareSalesInvoiceDummy(entity1, entity2);
        const compareResult2 = service.compareSalesInvoiceDummy(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareSalesInvoiceDummy(entity1, entity2);
        const compareResult2 = service.compareSalesInvoiceDummy(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareSalesInvoiceDummy(entity1, entity2);
        const compareResult2 = service.compareSalesInvoiceDummy(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
