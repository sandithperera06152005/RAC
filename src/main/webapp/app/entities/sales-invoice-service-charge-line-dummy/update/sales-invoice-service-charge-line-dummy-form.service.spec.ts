import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../sales-invoice-service-charge-line-dummy.test-samples';

import { SalesInvoiceServiceChargeLineDummyFormService } from './sales-invoice-service-charge-line-dummy-form.service';

describe('SalesInvoiceServiceChargeLineDummy Form Service', () => {
  let service: SalesInvoiceServiceChargeLineDummyFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SalesInvoiceServiceChargeLineDummyFormService);
  });

  describe('Service methods', () => {
    describe('createSalesInvoiceServiceChargeLineDummyFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createSalesInvoiceServiceChargeLineDummyFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            invoiceId: expect.any(Object),
            lineId: expect.any(Object),
            optionId: expect.any(Object),
            serviceName: expect.any(Object),
            serviceDescription: expect.any(Object),
            value: expect.any(Object),
            isCustomerService: expect.any(Object),
          }),
        );
      });

      it('passing ISalesInvoiceServiceChargeLineDummy should create a new form with FormGroup', () => {
        const formGroup = service.createSalesInvoiceServiceChargeLineDummyFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            invoiceId: expect.any(Object),
            lineId: expect.any(Object),
            optionId: expect.any(Object),
            serviceName: expect.any(Object),
            serviceDescription: expect.any(Object),
            value: expect.any(Object),
            isCustomerService: expect.any(Object),
          }),
        );
      });
    });

    describe('getSalesInvoiceServiceChargeLineDummy', () => {
      it('should return NewSalesInvoiceServiceChargeLineDummy for default SalesInvoiceServiceChargeLineDummy initial value', () => {
        const formGroup = service.createSalesInvoiceServiceChargeLineDummyFormGroup(sampleWithNewData);

        const salesInvoiceServiceChargeLineDummy = service.getSalesInvoiceServiceChargeLineDummy(formGroup) as any;

        expect(salesInvoiceServiceChargeLineDummy).toMatchObject(sampleWithNewData);
      });

      it('should return NewSalesInvoiceServiceChargeLineDummy for empty SalesInvoiceServiceChargeLineDummy initial value', () => {
        const formGroup = service.createSalesInvoiceServiceChargeLineDummyFormGroup();

        const salesInvoiceServiceChargeLineDummy = service.getSalesInvoiceServiceChargeLineDummy(formGroup) as any;

        expect(salesInvoiceServiceChargeLineDummy).toMatchObject({});
      });

      it('should return ISalesInvoiceServiceChargeLineDummy', () => {
        const formGroup = service.createSalesInvoiceServiceChargeLineDummyFormGroup(sampleWithRequiredData);

        const salesInvoiceServiceChargeLineDummy = service.getSalesInvoiceServiceChargeLineDummy(formGroup) as any;

        expect(salesInvoiceServiceChargeLineDummy).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ISalesInvoiceServiceChargeLineDummy should not enable id FormControl', () => {
        const formGroup = service.createSalesInvoiceServiceChargeLineDummyFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewSalesInvoiceServiceChargeLineDummy should disable id FormControl', () => {
        const formGroup = service.createSalesInvoiceServiceChargeLineDummyFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
