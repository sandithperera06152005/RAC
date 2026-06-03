import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IInvoiceServiceCommon } from '../invoice-service-common.model';
import {
  sampleWithFullData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithRequiredData,
} from '../invoice-service-common.test-samples';

import { InvoiceServiceCommonService } from './invoice-service-common.service';

const requireRestSample: IInvoiceServiceCommon = {
  ...sampleWithRequiredData,
};

describe('InvoiceServiceCommon Service', () => {
  let service: InvoiceServiceCommonService;
  let httpMock: HttpTestingController;
  let expectedResult: IInvoiceServiceCommon | IInvoiceServiceCommon[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(InvoiceServiceCommonService);
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

    it('should create a InvoiceServiceCommon', () => {
      const invoiceServiceCommon = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(invoiceServiceCommon).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a InvoiceServiceCommon', () => {
      const invoiceServiceCommon = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(invoiceServiceCommon).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a InvoiceServiceCommon', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of InvoiceServiceCommon', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a InvoiceServiceCommon', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addInvoiceServiceCommonToCollectionIfMissing', () => {
      it('should add a InvoiceServiceCommon to an empty array', () => {
        const invoiceServiceCommon: IInvoiceServiceCommon = sampleWithRequiredData;
        expectedResult = service.addInvoiceServiceCommonToCollectionIfMissing([], invoiceServiceCommon);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(invoiceServiceCommon);
      });

      it('should not add a InvoiceServiceCommon to an array that contains it', () => {
        const invoiceServiceCommon: IInvoiceServiceCommon = sampleWithRequiredData;
        const invoiceServiceCommonCollection: IInvoiceServiceCommon[] = [
          {
            ...invoiceServiceCommon,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addInvoiceServiceCommonToCollectionIfMissing(invoiceServiceCommonCollection, invoiceServiceCommon);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a InvoiceServiceCommon to an array that doesn't contain it", () => {
        const invoiceServiceCommon: IInvoiceServiceCommon = sampleWithRequiredData;
        const invoiceServiceCommonCollection: IInvoiceServiceCommon[] = [sampleWithPartialData];
        expectedResult = service.addInvoiceServiceCommonToCollectionIfMissing(invoiceServiceCommonCollection, invoiceServiceCommon);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(invoiceServiceCommon);
      });

      it('should add only unique InvoiceServiceCommon to an array', () => {
        const invoiceServiceCommonArray: IInvoiceServiceCommon[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const invoiceServiceCommonCollection: IInvoiceServiceCommon[] = [sampleWithRequiredData];
        expectedResult = service.addInvoiceServiceCommonToCollectionIfMissing(invoiceServiceCommonCollection, ...invoiceServiceCommonArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const invoiceServiceCommon: IInvoiceServiceCommon = sampleWithRequiredData;
        const invoiceServiceCommon2: IInvoiceServiceCommon = sampleWithPartialData;
        expectedResult = service.addInvoiceServiceCommonToCollectionIfMissing([], invoiceServiceCommon, invoiceServiceCommon2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(invoiceServiceCommon);
        expect(expectedResult).toContain(invoiceServiceCommon2);
      });

      it('should accept null and undefined values', () => {
        const invoiceServiceCommon: IInvoiceServiceCommon = sampleWithRequiredData;
        expectedResult = service.addInvoiceServiceCommonToCollectionIfMissing([], null, invoiceServiceCommon, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(invoiceServiceCommon);
      });

      it('should return initial array if no InvoiceServiceCommon is added', () => {
        const invoiceServiceCommonCollection: IInvoiceServiceCommon[] = [sampleWithRequiredData];
        expectedResult = service.addInvoiceServiceCommonToCollectionIfMissing(invoiceServiceCommonCollection, undefined, null);
        expect(expectedResult).toEqual(invoiceServiceCommonCollection);
      });
    });

    describe('compareInvoiceServiceCommon', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareInvoiceServiceCommon(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareInvoiceServiceCommon(entity1, entity2);
        const compareResult2 = service.compareInvoiceServiceCommon(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareInvoiceServiceCommon(entity1, entity2);
        const compareResult2 = service.compareInvoiceServiceCommon(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareInvoiceServiceCommon(entity1, entity2);
        const compareResult2 = service.compareInvoiceServiceCommon(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
