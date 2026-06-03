import { TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { ActivatedRoute, ActivatedRouteSnapshot, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { ISalesInvoiceServiceChargeLine } from '../sales-invoice-service-charge-line.model';
import { SalesInvoiceServiceChargeLineService } from '../service/sales-invoice-service-charge-line.service';

import salesInvoiceServiceChargeLineResolve from './sales-invoice-service-charge-line-routing-resolve.service';

describe('SalesInvoiceServiceChargeLine routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: SalesInvoiceServiceChargeLineService;
  let resultSalesInvoiceServiceChargeLine: ISalesInvoiceServiceChargeLine | null | undefined;

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
    service = TestBed.inject(SalesInvoiceServiceChargeLineService);
    resultSalesInvoiceServiceChargeLine = undefined;
  });

  describe('resolve', () => {
    it('should return ISalesInvoiceServiceChargeLine returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        salesInvoiceServiceChargeLineResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultSalesInvoiceServiceChargeLine = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultSalesInvoiceServiceChargeLine).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        salesInvoiceServiceChargeLineResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultSalesInvoiceServiceChargeLine = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toHaveBeenCalled();
      expect(resultSalesInvoiceServiceChargeLine).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<ISalesInvoiceServiceChargeLine>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        salesInvoiceServiceChargeLineResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultSalesInvoiceServiceChargeLine = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultSalesInvoiceServiceChargeLine).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
