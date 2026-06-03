import { TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { ActivatedRoute, ActivatedRouteSnapshot, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IAutojobsinvoicelines } from '../autojobsinvoicelines.model';
import { AutojobsinvoicelinesService } from '../service/autojobsinvoicelines.service';

import autojobsinvoicelinesResolve from './autojobsinvoicelines-routing-resolve.service';

describe('Autojobsinvoicelines routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: AutojobsinvoicelinesService;
  let resultAutojobsinvoicelines: IAutojobsinvoicelines | null | undefined;

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
    service = TestBed.inject(AutojobsinvoicelinesService);
    resultAutojobsinvoicelines = undefined;
  });

  describe('resolve', () => {
    it('should return IAutojobsinvoicelines returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        autojobsinvoicelinesResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultAutojobsinvoicelines = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultAutojobsinvoicelines).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        autojobsinvoicelinesResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultAutojobsinvoicelines = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toHaveBeenCalled();
      expect(resultAutojobsinvoicelines).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IAutojobsinvoicelines>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        autojobsinvoicelinesResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultAutojobsinvoicelines = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultAutojobsinvoicelines).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
