import { TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { ActivatedRoute, ActivatedRouteSnapshot, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IInvoiceServiceCharge } from '../invoice-service-charge.model';
import { InvoiceServiceChargeService } from '../service/invoice-service-charge.service';

import invoiceServiceChargeResolve from './invoice-service-charge-routing-resolve.service';

describe('InvoiceServiceCharge routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: InvoiceServiceChargeService;
  let resultInvoiceServiceCharge: IInvoiceServiceCharge | null | undefined;

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
    service = TestBed.inject(InvoiceServiceChargeService);
    resultInvoiceServiceCharge = undefined;
  });

  describe('resolve', () => {
    it('should return IInvoiceServiceCharge returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        invoiceServiceChargeResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultInvoiceServiceCharge = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultInvoiceServiceCharge).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        invoiceServiceChargeResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultInvoiceServiceCharge = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toHaveBeenCalled();
      expect(resultInvoiceServiceCharge).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IInvoiceServiceCharge>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        invoiceServiceChargeResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultInvoiceServiceCharge = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultInvoiceServiceCharge).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
