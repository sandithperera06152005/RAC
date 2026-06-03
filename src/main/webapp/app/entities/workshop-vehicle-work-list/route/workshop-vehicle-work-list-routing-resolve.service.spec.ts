import { TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { ActivatedRoute, ActivatedRouteSnapshot, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IWorkshopVehicleWorkList } from '../workshop-vehicle-work-list.model';
import { WorkshopVehicleWorkListService } from '../service/workshop-vehicle-work-list.service';

import workshopVehicleWorkListResolve from './workshop-vehicle-work-list-routing-resolve.service';

describe('WorkshopVehicleWorkList routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: WorkshopVehicleWorkListService;
  let resultWorkshopVehicleWorkList: IWorkshopVehicleWorkList | null | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        provideHttpClient(),
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    service = TestBed.inject(WorkshopVehicleWorkListService);
    resultWorkshopVehicleWorkList = undefined;
  });

  describe('resolve', () => {
    it('should return IWorkshopVehicleWorkList returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        workshopVehicleWorkListResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultWorkshopVehicleWorkList = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultWorkshopVehicleWorkList).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        workshopVehicleWorkListResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultWorkshopVehicleWorkList = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toHaveBeenCalled();
      expect(resultWorkshopVehicleWorkList).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IWorkshopVehicleWorkList>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        workshopVehicleWorkListResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultWorkshopVehicleWorkList = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultWorkshopVehicleWorkList).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
