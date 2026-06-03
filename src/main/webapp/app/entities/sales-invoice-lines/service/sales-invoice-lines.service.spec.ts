import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { ISalesInvoiceLines } from '../sales-invoice-lines.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../sales-invoice-lines.test-samples';

import { RestSalesInvoiceLines, SalesInvoiceLinesService } from './sales-invoice-lines.service';

const requireRestSample: RestSalesInvoiceLines = {
  ...sampleWithRequiredData,
  lmd: sampleWithRequiredData.lmd?.toJSON(),
};

describe('SalesInvoiceLines Service', () => {
  let service: SalesInvoiceLinesService;
  let httpMock: HttpTestingController;
  let expectedResult: ISalesInvoiceLines | ISalesInvoiceLines[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(SalesInvoiceLinesService);
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

    it('should create a SalesInvoiceLines', () => {
      const salesInvoiceLines = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(salesInvoiceLines).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a SalesInvoiceLines', () => {
      const salesInvoiceLines = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(salesInvoiceLines).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a SalesInvoiceLines', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of SalesInvoiceLines', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a SalesInvoiceLines', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addSalesInvoiceLinesToCollectionIfMissing', () => {
      it('should add a SalesInvoiceLines to an empty array', () => {
        const salesInvoiceLines: ISalesInvoiceLines = sampleWithRequiredData;
        expectedResult = service.addSalesInvoiceLinesToCollectionIfMissing([], salesInvoiceLines);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(salesInvoiceLines);
      });

      it('should not add a SalesInvoiceLines to an array that contains it', () => {
        const salesInvoiceLines: ISalesInvoiceLines = sampleWithRequiredData;
        const salesInvoiceLinesCollection: ISalesInvoiceLines[] = [
          {
            ...salesInvoiceLines,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addSalesInvoiceLinesToCollectionIfMissing(salesInvoiceLinesCollection, salesInvoiceLines);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SalesInvoiceLines to an array that doesn't contain it", () => {
        const salesInvoiceLines: ISalesInvoiceLines = sampleWithRequiredData;
        const salesInvoiceLinesCollection: ISalesInvoiceLines[] = [sampleWithPartialData];
        expectedResult = service.addSalesInvoiceLinesToCollectionIfMissing(salesInvoiceLinesCollection, salesInvoiceLines);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(salesInvoiceLines);
      });

      it('should add only unique SalesInvoiceLines to an array', () => {
        const salesInvoiceLinesArray: ISalesInvoiceLines[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const salesInvoiceLinesCollection: ISalesInvoiceLines[] = [sampleWithRequiredData];
        expectedResult = service.addSalesInvoiceLinesToCollectionIfMissing(salesInvoiceLinesCollection, ...salesInvoiceLinesArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const salesInvoiceLines: ISalesInvoiceLines = sampleWithRequiredData;
        const salesInvoiceLines2: ISalesInvoiceLines = sampleWithPartialData;
        expectedResult = service.addSalesInvoiceLinesToCollectionIfMissing([], salesInvoiceLines, salesInvoiceLines2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(salesInvoiceLines);
        expect(expectedResult).toContain(salesInvoiceLines2);
      });

      it('should accept null and undefined values', () => {
        const salesInvoiceLines: ISalesInvoiceLines = sampleWithRequiredData;
        expectedResult = service.addSalesInvoiceLinesToCollectionIfMissing([], null, salesInvoiceLines, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(salesInvoiceLines);
      });

      it('should return initial array if no SalesInvoiceLines is added', () => {
        const salesInvoiceLinesCollection: ISalesInvoiceLines[] = [sampleWithRequiredData];
        expectedResult = service.addSalesInvoiceLinesToCollectionIfMissing(salesInvoiceLinesCollection, undefined, null);
        expect(expectedResult).toEqual(salesInvoiceLinesCollection);
      });
    });

    describe('compareSalesInvoiceLines', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareSalesInvoiceLines(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareSalesInvoiceLines(entity1, entity2);
        const compareResult2 = service.compareSalesInvoiceLines(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareSalesInvoiceLines(entity1, entity2);
        const compareResult2 = service.compareSalesInvoiceLines(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareSalesInvoiceLines(entity1, entity2);
        const compareResult2 = service.compareSalesInvoiceLines(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
