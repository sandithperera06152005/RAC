import { TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { ActivatedRoute, ActivatedRouteSnapshot, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { ISaleInvoiceCommonServiceChargeDummy } from '../sale-invoice-common-service-charge-dummy.model';
import { SaleInvoiceCommonServiceChargeDummyService } from '../service/sale-invoice-common-service-charge-dummy.service';

import saleInvoiceCommonServiceChargeDummyResolve from './sale-invoice-common-service-charge-dummy-routing-resolve.service';

describe('SaleInvoiceCommonServiceChargeDummy routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: SaleInvoiceCommonServiceChargeDummyService;
  let resultSaleInvoiceCommonServiceChargeDummy: ISaleInvoiceCommonServiceChargeDummy | null | undefined;

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
    service = TestBed.inject(SaleInvoiceCommonServiceChargeDummyService);
    resultSaleInvoiceCommonServiceChargeDummy = undefined;
  });

  describe('resolve', () => {
    it('should return ISaleInvoiceCommonServiceChargeDummy returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        saleInvoiceCommonServiceChargeDummyResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultSaleInvoiceCommonServiceChargeDummy = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultSaleInvoiceCommonServiceChargeDummy).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        saleInvoiceCommonServiceChargeDummyResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultSaleInvoiceCommonServiceChargeDummy = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toHaveBeenCalled();
      expect(resultSaleInvoiceCommonServiceChargeDummy).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<ISaleInvoiceCommonServiceChargeDummy>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        saleInvoiceCommonServiceChargeDummyResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultSaleInvoiceCommonServiceChargeDummy = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultSaleInvoiceCommonServiceChargeDummy).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
