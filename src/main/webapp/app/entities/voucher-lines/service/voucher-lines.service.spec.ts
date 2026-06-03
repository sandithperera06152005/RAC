import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IVoucherLines } from '../voucher-lines.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../voucher-lines.test-samples';

import { RestVoucherLines, VoucherLinesService } from './voucher-lines.service';

const requireRestSample: RestVoucherLines = {
  ...sampleWithRequiredData,
  lmd: sampleWithRequiredData.lmd?.toJSON(),
};

describe('VoucherLines Service', () => {
  let service: VoucherLinesService;
  let httpMock: HttpTestingController;
  let expectedResult: IVoucherLines | IVoucherLines[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(VoucherLinesService);
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

    it('should create a VoucherLines', () => {
      const voucherLines = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(voucherLines).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a VoucherLines', () => {
      const voucherLines = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(voucherLines).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a VoucherLines', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of VoucherLines', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a VoucherLines', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addVoucherLinesToCollectionIfMissing', () => {
      it('should add a VoucherLines to an empty array', () => {
        const voucherLines: IVoucherLines = sampleWithRequiredData;
        expectedResult = service.addVoucherLinesToCollectionIfMissing([], voucherLines);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(voucherLines);
      });

      it('should not add a VoucherLines to an array that contains it', () => {
        const voucherLines: IVoucherLines = sampleWithRequiredData;
        const voucherLinesCollection: IVoucherLines[] = [
          {
            ...voucherLines,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addVoucherLinesToCollectionIfMissing(voucherLinesCollection, voucherLines);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a VoucherLines to an array that doesn't contain it", () => {
        const voucherLines: IVoucherLines = sampleWithRequiredData;
        const voucherLinesCollection: IVoucherLines[] = [sampleWithPartialData];
        expectedResult = service.addVoucherLinesToCollectionIfMissing(voucherLinesCollection, voucherLines);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(voucherLines);
      });

      it('should add only unique VoucherLines to an array', () => {
        const voucherLinesArray: IVoucherLines[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const voucherLinesCollection: IVoucherLines[] = [sampleWithRequiredData];
        expectedResult = service.addVoucherLinesToCollectionIfMissing(voucherLinesCollection, ...voucherLinesArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const voucherLines: IVoucherLines = sampleWithRequiredData;
        const voucherLines2: IVoucherLines = sampleWithPartialData;
        expectedResult = service.addVoucherLinesToCollectionIfMissing([], voucherLines, voucherLines2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(voucherLines);
        expect(expectedResult).toContain(voucherLines2);
      });

      it('should accept null and undefined values', () => {
        const voucherLines: IVoucherLines = sampleWithRequiredData;
        expectedResult = service.addVoucherLinesToCollectionIfMissing([], null, voucherLines, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(voucherLines);
      });

      it('should return initial array if no VoucherLines is added', () => {
        const voucherLinesCollection: IVoucherLines[] = [sampleWithRequiredData];
        expectedResult = service.addVoucherLinesToCollectionIfMissing(voucherLinesCollection, undefined, null);
        expect(expectedResult).toEqual(voucherLinesCollection);
      });
    });

    describe('compareVoucherLines', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareVoucherLines(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareVoucherLines(entity1, entity2);
        const compareResult2 = service.compareVoucherLines(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareVoucherLines(entity1, entity2);
        const compareResult2 = service.compareVoucherLines(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareVoucherLines(entity1, entity2);
        const compareResult2 = service.compareVoucherLines(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
