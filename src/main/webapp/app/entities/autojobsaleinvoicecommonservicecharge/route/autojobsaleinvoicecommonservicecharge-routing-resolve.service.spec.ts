import { TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { ActivatedRoute, ActivatedRouteSnapshot, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IAutojobsaleinvoicecommonservicecharge } from '../autojobsaleinvoicecommonservicecharge.model';
import { AutojobsaleinvoicecommonservicechargeService } from '../service/autojobsaleinvoicecommonservicecharge.service';

import autojobsaleinvoicecommonservicechargeResolve from './autojobsaleinvoicecommonservicecharge-routing-resolve.service';

describe('Autojobsaleinvoicecommonservicecharge routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: AutojobsaleinvoicecommonservicechargeService;
  let resultAutojobsaleinvoicecommonservicecharge: IAutojobsaleinvoicecommonservicecharge | null | undefined;

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
    service = TestBed.inject(AutojobsaleinvoicecommonservicechargeService);
    resultAutojobsaleinvoicecommonservicecharge = undefined;
  });

  describe('resolve', () => {
    it('should return IAutojobsaleinvoicecommonservicecharge returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        autojobsaleinvoicecommonservicechargeResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultAutojobsaleinvoicecommonservicecharge = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultAutojobsaleinvoicecommonservicecharge).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        autojobsaleinvoicecommonservicechargeResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultAutojobsaleinvoicecommonservicecharge = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toHaveBeenCalled();
      expect(resultAutojobsaleinvoicecommonservicecharge).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IAutojobsaleinvoicecommonservicecharge>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        autojobsaleinvoicecommonservicechargeResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultAutojobsaleinvoicecommonservicecharge = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultAutojobsaleinvoicecommonservicecharge).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
