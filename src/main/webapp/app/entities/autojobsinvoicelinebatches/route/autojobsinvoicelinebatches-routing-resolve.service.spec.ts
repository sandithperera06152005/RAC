import { TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { ActivatedRoute, ActivatedRouteSnapshot, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IAutojobsinvoicelinebatches } from '../autojobsinvoicelinebatches.model';
import { AutojobsinvoicelinebatchesService } from '../service/autojobsinvoicelinebatches.service';

import autojobsinvoicelinebatchesResolve from './autojobsinvoicelinebatches-routing-resolve.service';

describe('Autojobsinvoicelinebatches routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: AutojobsinvoicelinebatchesService;
  let resultAutojobsinvoicelinebatches: IAutojobsinvoicelinebatches | null | undefined;

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
    service = TestBed.inject(AutojobsinvoicelinebatchesService);
    resultAutojobsinvoicelinebatches = undefined;
  });

  describe('resolve', () => {
    it('should return IAutojobsinvoicelinebatches returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        autojobsinvoicelinebatchesResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultAutojobsinvoicelinebatches = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultAutojobsinvoicelinebatches).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        autojobsinvoicelinebatchesResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultAutojobsinvoicelinebatches = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toHaveBeenCalled();
      expect(resultAutojobsinvoicelinebatches).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IAutojobsinvoicelinebatches>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        autojobsinvoicelinebatchesResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultAutojobsinvoicelinebatches = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultAutojobsinvoicelinebatches).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
