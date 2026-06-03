import { TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { ActivatedRoute, ActivatedRouteSnapshot, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { ISalesInvoiceServiceChargeLineDummy } from '../sales-invoice-service-charge-line-dummy.model';
import { SalesInvoiceServiceChargeLineDummyService } from '../service/sales-invoice-service-charge-line-dummy.service';

import salesInvoiceServiceChargeLineDummyResolve from './sales-invoice-service-charge-line-dummy-routing-resolve.service';

describe('SalesInvoiceServiceChargeLineDummy routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: SalesInvoiceServiceChargeLineDummyService;
  let resultSalesInvoiceServiceChargeLineDummy: ISalesInvoiceServiceChargeLineDummy | null | undefined;

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
    service = TestBed.inject(SalesInvoiceServiceChargeLineDummyService);
    resultSalesInvoiceServiceChargeLineDummy = undefined;
  });

  describe('resolve', () => {
    it('should return ISalesInvoiceServiceChargeLineDummy returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        salesInvoiceServiceChargeLineDummyResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultSalesInvoiceServiceChargeLineDummy = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultSalesInvoiceServiceChargeLineDummy).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        salesInvoiceServiceChargeLineDummyResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultSalesInvoiceServiceChargeLineDummy = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toHaveBeenCalled();
      expect(resultSalesInvoiceServiceChargeLineDummy).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<ISalesInvoiceServiceChargeLineDummy>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        salesInvoiceServiceChargeLineDummyResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultSalesInvoiceServiceChargeLineDummy = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultSalesInvoiceServiceChargeLineDummy).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
