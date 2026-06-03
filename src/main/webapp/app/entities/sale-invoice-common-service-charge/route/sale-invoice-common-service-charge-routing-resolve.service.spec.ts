import { TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { ActivatedRoute, ActivatedRouteSnapshot, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { ISaleInvoiceCommonServiceCharge } from '../sale-invoice-common-service-charge.model';
import { SaleInvoiceCommonServiceChargeService } from '../service/sale-invoice-common-service-charge.service';

import saleInvoiceCommonServiceChargeResolve from './sale-invoice-common-service-charge-routing-resolve.service';

describe('SaleInvoiceCommonServiceCharge routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: SaleInvoiceCommonServiceChargeService;
  let resultSaleInvoiceCommonServiceCharge: ISaleInvoiceCommonServiceCharge | null | undefined;

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
    service = TestBed.inject(SaleInvoiceCommonServiceChargeService);
    resultSaleInvoiceCommonServiceCharge = undefined;
  });

  describe('resolve', () => {
    it('should return ISaleInvoiceCommonServiceCharge returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        saleInvoiceCommonServiceChargeResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultSaleInvoiceCommonServiceCharge = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultSaleInvoiceCommonServiceCharge).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        saleInvoiceCommonServiceChargeResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultSaleInvoiceCommonServiceCharge = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toHaveBeenCalled();
      expect(resultSaleInvoiceCommonServiceCharge).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<ISaleInvoiceCommonServiceCharge>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        saleInvoiceCommonServiceChargeResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultSaleInvoiceCommonServiceCharge = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultSaleInvoiceCommonServiceCharge).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
