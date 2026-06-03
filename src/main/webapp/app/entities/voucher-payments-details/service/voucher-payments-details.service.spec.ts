import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IVoucherPaymentsDetails } from '../voucher-payments-details.model';
import {
  sampleWithFullData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithRequiredData,
} from '../voucher-payments-details.test-samples';

import { RestVoucherPaymentsDetails, VoucherPaymentsDetailsService } from './voucher-payments-details.service';

const requireRestSample: RestVoucherPaymentsDetails = {
  ...sampleWithRequiredData,
  checkqueDate: sampleWithRequiredData.checkqueDate?.toJSON(),
  checkqueExpireDate: sampleWithRequiredData.checkqueExpireDate?.toJSON(),
  lmd: sampleWithRequiredData.lmd?.toJSON(),
  depositedDate: sampleWithRequiredData.depositedDate?.toJSON(),
};

describe('VoucherPaymentsDetails Service', () => {
  let service: VoucherPaymentsDetailsService;
  let httpMock: HttpTestingController;
  let expectedResult: IVoucherPaymentsDetails | IVoucherPaymentsDetails[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(VoucherPaymentsDetailsService);
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

    it('should create a VoucherPaymentsDetails', () => {
      const voucherPaymentsDetails = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(voucherPaymentsDetails).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a VoucherPaymentsDetails', () => {
      const voucherPaymentsDetails = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(voucherPaymentsDetails).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a VoucherPaymentsDetails', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of VoucherPaymentsDetails', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a VoucherPaymentsDetails', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addVoucherPaymentsDetailsToCollectionIfMissing', () => {
      it('should add a VoucherPaymentsDetails to an empty array', () => {
        const voucherPaymentsDetails: IVoucherPaymentsDetails = sampleWithRequiredData;
        expectedResult = service.addVoucherPaymentsDetailsToCollectionIfMissing([], voucherPaymentsDetails);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(voucherPaymentsDetails);
      });

      it('should not add a VoucherPaymentsDetails to an array that contains it', () => {
        const voucherPaymentsDetails: IVoucherPaymentsDetails = sampleWithRequiredData;
        const voucherPaymentsDetailsCollection: IVoucherPaymentsDetails[] = [
          {
            ...voucherPaymentsDetails,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addVoucherPaymentsDetailsToCollectionIfMissing(voucherPaymentsDetailsCollection, voucherPaymentsDetails);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a VoucherPaymentsDetails to an array that doesn't contain it", () => {
        const voucherPaymentsDetails: IVoucherPaymentsDetails = sampleWithRequiredData;
        const voucherPaymentsDetailsCollection: IVoucherPaymentsDetails[] = [sampleWithPartialData];
        expectedResult = service.addVoucherPaymentsDetailsToCollectionIfMissing(voucherPaymentsDetailsCollection, voucherPaymentsDetails);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(voucherPaymentsDetails);
      });

      it('should add only unique VoucherPaymentsDetails to an array', () => {
        const voucherPaymentsDetailsArray: IVoucherPaymentsDetails[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const voucherPaymentsDetailsCollection: IVoucherPaymentsDetails[] = [sampleWithRequiredData];
        expectedResult = service.addVoucherPaymentsDetailsToCollectionIfMissing(
          voucherPaymentsDetailsCollection,
          ...voucherPaymentsDetailsArray,
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const voucherPaymentsDetails: IVoucherPaymentsDetails = sampleWithRequiredData;
        const voucherPaymentsDetails2: IVoucherPaymentsDetails = sampleWithPartialData;
        expectedResult = service.addVoucherPaymentsDetailsToCollectionIfMissing([], voucherPaymentsDetails, voucherPaymentsDetails2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(voucherPaymentsDetails);
        expect(expectedResult).toContain(voucherPaymentsDetails2);
      });

      it('should accept null and undefined values', () => {
        const voucherPaymentsDetails: IVoucherPaymentsDetails = sampleWithRequiredData;
        expectedResult = service.addVoucherPaymentsDetailsToCollectionIfMissing([], null, voucherPaymentsDetails, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(voucherPaymentsDetails);
      });

      it('should return initial array if no VoucherPaymentsDetails is added', () => {
        const voucherPaymentsDetailsCollection: IVoucherPaymentsDetails[] = [sampleWithRequiredData];
        expectedResult = service.addVoucherPaymentsDetailsToCollectionIfMissing(voucherPaymentsDetailsCollection, undefined, null);
        expect(expectedResult).toEqual(voucherPaymentsDetailsCollection);
      });
    });

    describe('compareVoucherPaymentsDetails', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareVoucherPaymentsDetails(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareVoucherPaymentsDetails(entity1, entity2);
        const compareResult2 = service.compareVoucherPaymentsDetails(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareVoucherPaymentsDetails(entity1, entity2);
        const compareResult2 = service.compareVoucherPaymentsDetails(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareVoucherPaymentsDetails(entity1, entity2);
        const compareResult2 = service.compareVoucherPaymentsDetails(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
