import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IWorkshopVehicleWorkList } from '../workshop-vehicle-work-list.model';
import {
  sampleWithFullData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithRequiredData,
} from '../workshop-vehicle-work-list.test-samples';

import { RestWorkshopVehicleWorkList, WorkshopVehicleWorkListService } from './workshop-vehicle-work-list.service';

const requireRestSample: RestWorkshopVehicleWorkList = {
  ...sampleWithRequiredData,
  jobdonedate: sampleWithRequiredData.jobdonedate?.toJSON(),
};

describe('WorkshopVehicleWorkList Service', () => {
  let service: WorkshopVehicleWorkListService;
  let httpMock: HttpTestingController;
  let expectedResult: IWorkshopVehicleWorkList | IWorkshopVehicleWorkList[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(WorkshopVehicleWorkListService);
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

    it('should create a WorkshopVehicleWorkList', () => {
      const workshopVehicleWorkList = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(workshopVehicleWorkList).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a WorkshopVehicleWorkList', () => {
      const workshopVehicleWorkList = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(workshopVehicleWorkList).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a WorkshopVehicleWorkList', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of WorkshopVehicleWorkList', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a WorkshopVehicleWorkList', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addWorkshopVehicleWorkListToCollectionIfMissing', () => {
      it('should add a WorkshopVehicleWorkList to an empty array', () => {
        const workshopVehicleWorkList: IWorkshopVehicleWorkList = sampleWithRequiredData;
        expectedResult = service.addWorkshopVehicleWorkListToCollectionIfMissing([], workshopVehicleWorkList);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(workshopVehicleWorkList);
      });

      it('should not add a WorkshopVehicleWorkList to an array that contains it', () => {
        const workshopVehicleWorkList: IWorkshopVehicleWorkList = sampleWithRequiredData;
        const workshopVehicleWorkListCollection: IWorkshopVehicleWorkList[] = [
          {
            ...workshopVehicleWorkList,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addWorkshopVehicleWorkListToCollectionIfMissing(
          workshopVehicleWorkListCollection,
          workshopVehicleWorkList,
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a WorkshopVehicleWorkList to an array that doesn't contain it", () => {
        const workshopVehicleWorkList: IWorkshopVehicleWorkList = sampleWithRequiredData;
        const workshopVehicleWorkListCollection: IWorkshopVehicleWorkList[] = [sampleWithPartialData];
        expectedResult = service.addWorkshopVehicleWorkListToCollectionIfMissing(
          workshopVehicleWorkListCollection,
          workshopVehicleWorkList,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(workshopVehicleWorkList);
      });

      it('should add only unique WorkshopVehicleWorkList to an array', () => {
        const workshopVehicleWorkListArray: IWorkshopVehicleWorkList[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const workshopVehicleWorkListCollection: IWorkshopVehicleWorkList[] = [sampleWithRequiredData];
        expectedResult = service.addWorkshopVehicleWorkListToCollectionIfMissing(
          workshopVehicleWorkListCollection,
          ...workshopVehicleWorkListArray,
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const workshopVehicleWorkList: IWorkshopVehicleWorkList = sampleWithRequiredData;
        const workshopVehicleWorkList2: IWorkshopVehicleWorkList = sampleWithPartialData;
        expectedResult = service.addWorkshopVehicleWorkListToCollectionIfMissing([], workshopVehicleWorkList, workshopVehicleWorkList2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(workshopVehicleWorkList);
        expect(expectedResult).toContain(workshopVehicleWorkList2);
      });

      it('should accept null and undefined values', () => {
        const workshopVehicleWorkList: IWorkshopVehicleWorkList = sampleWithRequiredData;
        expectedResult = service.addWorkshopVehicleWorkListToCollectionIfMissing([], null, workshopVehicleWorkList, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(workshopVehicleWorkList);
      });

      it('should return initial array if no WorkshopVehicleWorkList is added', () => {
        const workshopVehicleWorkListCollection: IWorkshopVehicleWorkList[] = [sampleWithRequiredData];
        expectedResult = service.addWorkshopVehicleWorkListToCollectionIfMissing(workshopVehicleWorkListCollection, undefined, null);
        expect(expectedResult).toEqual(workshopVehicleWorkListCollection);
      });
    });

    describe('compareWorkshopVehicleWorkList', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareWorkshopVehicleWorkList(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareWorkshopVehicleWorkList(entity1, entity2);
        const compareResult2 = service.compareWorkshopVehicleWorkList(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareWorkshopVehicleWorkList(entity1, entity2);
        const compareResult2 = service.compareWorkshopVehicleWorkList(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareWorkshopVehicleWorkList(entity1, entity2);
        const compareResult2 = service.compareWorkshopVehicleWorkList(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
