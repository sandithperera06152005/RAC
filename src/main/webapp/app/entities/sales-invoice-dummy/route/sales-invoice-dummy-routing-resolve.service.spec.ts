import { TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { ActivatedRoute, ActivatedRouteSnapshot, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { ISalesInvoiceDummy } from '../sales-invoice-dummy.model';
import { SalesInvoiceDummyService } from '../service/sales-invoice-dummy.service';

import salesInvoiceDummyResolve from './sales-invoice-dummy-routing-resolve.service';

describe('SalesInvoiceDummy routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: SalesInvoiceDummyService;
  let resultSalesInvoiceDummy: ISalesInvoiceDummy | null | undefined;

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
    service = TestBed.inject(SalesInvoiceDummyService);
    resultSalesInvoiceDummy = undefined;
  });

  describe('resolve', () => {
    it('should return ISalesInvoiceDummy returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        salesInvoiceDummyResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultSalesInvoiceDummy = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultSalesInvoiceDummy).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        salesInvoiceDummyResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultSalesInvoiceDummy = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toHaveBeenCalled();
      expect(resultSalesInvoiceDummy).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<ISalesInvoiceDummy>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        salesInvoiceDummyResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultSalesInvoiceDummy = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultSalesInvoiceDummy).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
