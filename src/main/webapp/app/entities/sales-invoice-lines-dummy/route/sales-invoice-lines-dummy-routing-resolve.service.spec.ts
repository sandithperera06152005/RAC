import { TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { ActivatedRoute, ActivatedRouteSnapshot, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { ISalesInvoiceLinesDummy } from '../sales-invoice-lines-dummy.model';
import { SalesInvoiceLinesDummyService } from '../service/sales-invoice-lines-dummy.service';

import salesInvoiceLinesDummyResolve from './sales-invoice-lines-dummy-routing-resolve.service';

describe('SalesInvoiceLinesDummy routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: SalesInvoiceLinesDummyService;
  let resultSalesInvoiceLinesDummy: ISalesInvoiceLinesDummy | null | undefined;

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
    service = TestBed.inject(SalesInvoiceLinesDummyService);
    resultSalesInvoiceLinesDummy = undefined;
  });

  describe('resolve', () => {
    it('should return ISalesInvoiceLinesDummy returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        salesInvoiceLinesDummyResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultSalesInvoiceLinesDummy = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultSalesInvoiceLinesDummy).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        salesInvoiceLinesDummyResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultSalesInvoiceLinesDummy = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toHaveBeenCalled();
      expect(resultSalesInvoiceLinesDummy).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<ISalesInvoiceLinesDummy>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        salesInvoiceLinesDummyResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultSalesInvoiceLinesDummy = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultSalesInvoiceLinesDummy).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
