import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IVoucher } from '../voucher.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../voucher.test-samples';

import { RestVoucher, VoucherService } from './voucher.service';

const requireRestSample: RestVoucher = {
  ...sampleWithRequiredData,
  voucherDate: sampleWithRequiredData.voucherDate?.toJSON(),
  lmd: sampleWithRequiredData.lmd?.toJSON(),
  date: sampleWithRequiredData.date?.toJSON(),
};

describe('Voucher Service', () => {
  let service: VoucherService;
  let httpMock: HttpTestingController;
  let expectedResult: IVoucher | IVoucher[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(VoucherService);
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

    it('should create a Voucher', () => {
      const voucher = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(voucher).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Voucher', () => {
      const voucher = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(voucher).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Voucher', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Voucher', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Voucher', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addVoucherToCollectionIfMissing', () => {
      it('should add a Voucher to an empty array', () => {
        const voucher: IVoucher = sampleWithRequiredData;
        expectedResult = service.addVoucherToCollectionIfMissing([], voucher);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(voucher);
      });

      it('should not add a Voucher to an array that contains it', () => {
        const voucher: IVoucher = sampleWithRequiredData;
        const voucherCollection: IVoucher[] = [
          {
            ...voucher,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addVoucherToCollectionIfMissing(voucherCollection, voucher);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Voucher to an array that doesn't contain it", () => {
        const voucher: IVoucher = sampleWithRequiredData;
        const voucherCollection: IVoucher[] = [sampleWithPartialData];
        expectedResult = service.addVoucherToCollectionIfMissing(voucherCollection, voucher);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(voucher);
      });

      it('should add only unique Voucher to an array', () => {
        const voucherArray: IVoucher[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const voucherCollection: IVoucher[] = [sampleWithRequiredData];
        expectedResult = service.addVoucherToCollectionIfMissing(voucherCollection, ...voucherArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const voucher: IVoucher = sampleWithRequiredData;
        const voucher2: IVoucher = sampleWithPartialData;
        expectedResult = service.addVoucherToCollectionIfMissing([], voucher, voucher2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(voucher);
        expect(expectedResult).toContain(voucher2);
      });

      it('should accept null and undefined values', () => {
        const voucher: IVoucher = sampleWithRequiredData;
        expectedResult = service.addVoucherToCollectionIfMissing([], null, voucher, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(voucher);
      });

      it('should return initial array if no Voucher is added', () => {
        const voucherCollection: IVoucher[] = [sampleWithRequiredData];
        expectedResult = service.addVoucherToCollectionIfMissing(voucherCollection, undefined, null);
        expect(expectedResult).toEqual(voucherCollection);
      });
    });

    describe('compareVoucher', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareVoucher(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareVoucher(entity1, entity2);
        const compareResult2 = service.compareVoucher(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareVoucher(entity1, entity2);
        const compareResult2 = service.compareVoucher(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareVoucher(entity1, entity2);
        const compareResult2 = service.compareVoucher(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
