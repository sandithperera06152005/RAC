import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IInvoiceServiceCharge } from '../invoice-service-charge.model';
import {
  sampleWithFullData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithRequiredData,
} from '../invoice-service-charge.test-samples';

import { InvoiceServiceChargeService } from './invoice-service-charge.service';

const requireRestSample: IInvoiceServiceCharge = {
  ...sampleWithRequiredData,
};

describe('InvoiceServiceCharge Service', () => {
  let service: InvoiceServiceChargeService;
  let httpMock: HttpTestingController;
  let expectedResult: IInvoiceServiceCharge | IInvoiceServiceCharge[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(InvoiceServiceChargeService);
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

    it('should create a InvoiceServiceCharge', () => {
      const invoiceServiceCharge = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(invoiceServiceCharge).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a InvoiceServiceCharge', () => {
      const invoiceServiceCharge = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(invoiceServiceCharge).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a InvoiceServiceCharge', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of InvoiceServiceCharge', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a InvoiceServiceCharge', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addInvoiceServiceChargeToCollectionIfMissing', () => {
      it('should add a InvoiceServiceCharge to an empty array', () => {
        const invoiceServiceCharge: IInvoiceServiceCharge = sampleWithRequiredData;
        expectedResult = service.addInvoiceServiceChargeToCollectionIfMissing([], invoiceServiceCharge);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(invoiceServiceCharge);
      });

      it('should not add a InvoiceServiceCharge to an array that contains it', () => {
        const invoiceServiceCharge: IInvoiceServiceCharge = sampleWithRequiredData;
        const invoiceServiceChargeCollection: IInvoiceServiceCharge[] = [
          {
            ...invoiceServiceCharge,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addInvoiceServiceChargeToCollectionIfMissing(invoiceServiceChargeCollection, invoiceServiceCharge);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a InvoiceServiceCharge to an array that doesn't contain it", () => {
        const invoiceServiceCharge: IInvoiceServiceCharge = sampleWithRequiredData;
        const invoiceServiceChargeCollection: IInvoiceServiceCharge[] = [sampleWithPartialData];
        expectedResult = service.addInvoiceServiceChargeToCollectionIfMissing(invoiceServiceChargeCollection, invoiceServiceCharge);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(invoiceServiceCharge);
      });

      it('should add only unique InvoiceServiceCharge to an array', () => {
        const invoiceServiceChargeArray: IInvoiceServiceCharge[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const invoiceServiceChargeCollection: IInvoiceServiceCharge[] = [sampleWithRequiredData];
        expectedResult = service.addInvoiceServiceChargeToCollectionIfMissing(invoiceServiceChargeCollection, ...invoiceServiceChargeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const invoiceServiceCharge: IInvoiceServiceCharge = sampleWithRequiredData;
        const invoiceServiceCharge2: IInvoiceServiceCharge = sampleWithPartialData;
        expectedResult = service.addInvoiceServiceChargeToCollectionIfMissing([], invoiceServiceCharge, invoiceServiceCharge2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(invoiceServiceCharge);
        expect(expectedResult).toContain(invoiceServiceCharge2);
      });

      it('should accept null and undefined values', () => {
        const invoiceServiceCharge: IInvoiceServiceCharge = sampleWithRequiredData;
        expectedResult = service.addInvoiceServiceChargeToCollectionIfMissing([], null, invoiceServiceCharge, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(invoiceServiceCharge);
      });

      it('should return initial array if no InvoiceServiceCharge is added', () => {
        const invoiceServiceChargeCollection: IInvoiceServiceCharge[] = [sampleWithRequiredData];
        expectedResult = service.addInvoiceServiceChargeToCollectionIfMissing(invoiceServiceChargeCollection, undefined, null);
        expect(expectedResult).toEqual(invoiceServiceChargeCollection);
      });
    });

    describe('compareInvoiceServiceCharge', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareInvoiceServiceCharge(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareInvoiceServiceCharge(entity1, entity2);
        const compareResult2 = service.compareInvoiceServiceCharge(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareInvoiceServiceCharge(entity1, entity2);
        const compareResult2 = service.compareInvoiceServiceCharge(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareInvoiceServiceCharge(entity1, entity2);
        const compareResult2 = service.compareInvoiceServiceCharge(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
