import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IReceiptpaymentsdetails } from '../receiptpaymentsdetails.model';
import {
  sampleWithFullData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithRequiredData,
} from '../receiptpaymentsdetails.test-samples';

import { ReceiptpaymentsdetailsService, RestReceiptpaymentsdetails } from './receiptpaymentsdetails.service';

const requireRestSample: RestReceiptpaymentsdetails = {
  ...sampleWithRequiredData,
  checkquedate: sampleWithRequiredData.checkquedate?.toJSON(),
  checkqueexpiredate: sampleWithRequiredData.checkqueexpiredate?.toJSON(),
  lmd: sampleWithRequiredData.lmd?.toJSON(),
  chequereturndate: sampleWithRequiredData.chequereturndate?.toJSON(),
  depositeddate: sampleWithRequiredData.depositeddate?.toJSON(),
  chequestatuschangeddate: sampleWithRequiredData.chequestatuschangeddate?.toJSON(),
  returnchequesttledate: sampleWithRequiredData.returnchequesttledate?.toJSON(),
  depositdate: sampleWithRequiredData.depositdate?.toJSON(),
};

describe('Receiptpaymentsdetails Service', () => {
  let service: ReceiptpaymentsdetailsService;
  let httpMock: HttpTestingController;
  let expectedResult: IReceiptpaymentsdetails | IReceiptpaymentsdetails[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(ReceiptpaymentsdetailsService);
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

    it('should create a Receiptpaymentsdetails', () => {
      const receiptpaymentsdetails = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(receiptpaymentsdetails).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Receiptpaymentsdetails', () => {
      const receiptpaymentsdetails = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(receiptpaymentsdetails).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Receiptpaymentsdetails', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Receiptpaymentsdetails', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Receiptpaymentsdetails', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addReceiptpaymentsdetailsToCollectionIfMissing', () => {
      it('should add a Receiptpaymentsdetails to an empty array', () => {
        const receiptpaymentsdetails: IReceiptpaymentsdetails = sampleWithRequiredData;
        expectedResult = service.addReceiptpaymentsdetailsToCollectionIfMissing([], receiptpaymentsdetails);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(receiptpaymentsdetails);
      });

      it('should not add a Receiptpaymentsdetails to an array that contains it', () => {
        const receiptpaymentsdetails: IReceiptpaymentsdetails = sampleWithRequiredData;
        const receiptpaymentsdetailsCollection: IReceiptpaymentsdetails[] = [
          {
            ...receiptpaymentsdetails,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addReceiptpaymentsdetailsToCollectionIfMissing(receiptpaymentsdetailsCollection, receiptpaymentsdetails);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Receiptpaymentsdetails to an array that doesn't contain it", () => {
        const receiptpaymentsdetails: IReceiptpaymentsdetails = sampleWithRequiredData;
        const receiptpaymentsdetailsCollection: IReceiptpaymentsdetails[] = [sampleWithPartialData];
        expectedResult = service.addReceiptpaymentsdetailsToCollectionIfMissing(receiptpaymentsdetailsCollection, receiptpaymentsdetails);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(receiptpaymentsdetails);
      });

      it('should add only unique Receiptpaymentsdetails to an array', () => {
        const receiptpaymentsdetailsArray: IReceiptpaymentsdetails[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const receiptpaymentsdetailsCollection: IReceiptpaymentsdetails[] = [sampleWithRequiredData];
        expectedResult = service.addReceiptpaymentsdetailsToCollectionIfMissing(
          receiptpaymentsdetailsCollection,
          ...receiptpaymentsdetailsArray,
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const receiptpaymentsdetails: IReceiptpaymentsdetails = sampleWithRequiredData;
        const receiptpaymentsdetails2: IReceiptpaymentsdetails = sampleWithPartialData;
        expectedResult = service.addReceiptpaymentsdetailsToCollectionIfMissing([], receiptpaymentsdetails, receiptpaymentsdetails2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(receiptpaymentsdetails);
        expect(expectedResult).toContain(receiptpaymentsdetails2);
      });

      it('should accept null and undefined values', () => {
        const receiptpaymentsdetails: IReceiptpaymentsdetails = sampleWithRequiredData;
        expectedResult = service.addReceiptpaymentsdetailsToCollectionIfMissing([], null, receiptpaymentsdetails, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(receiptpaymentsdetails);
      });

      it('should return initial array if no Receiptpaymentsdetails is added', () => {
        const receiptpaymentsdetailsCollection: IReceiptpaymentsdetails[] = [sampleWithRequiredData];
        expectedResult = service.addReceiptpaymentsdetailsToCollectionIfMissing(receiptpaymentsdetailsCollection, undefined, null);
        expect(expectedResult).toEqual(receiptpaymentsdetailsCollection);
      });
    });

    describe('compareReceiptpaymentsdetails', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareReceiptpaymentsdetails(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareReceiptpaymentsdetails(entity1, entity2);
        const compareResult2 = service.compareReceiptpaymentsdetails(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareReceiptpaymentsdetails(entity1, entity2);
        const compareResult2 = service.compareReceiptpaymentsdetails(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareReceiptpaymentsdetails(entity1, entity2);
        const compareResult2 = service.compareReceiptpaymentsdetails(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
