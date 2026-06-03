import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../sales-invoice-service-charge-line.test-samples';

import { SalesInvoiceServiceChargeLineFormService } from './sales-invoice-service-charge-line-form.service';

describe('SalesInvoiceServiceChargeLine Form Service', () => {
  let service: SalesInvoiceServiceChargeLineFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SalesInvoiceServiceChargeLineFormService);
  });

  describe('Service methods', () => {
    describe('createSalesInvoiceServiceChargeLineFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createSalesInvoiceServiceChargeLineFormGroup();

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
            discount: expect.any(Object),
            servicePrice: expect.any(Object),
          }),
        );
      });

      it('passing ISalesInvoiceServiceChargeLine should create a new form with FormGroup', () => {
        const formGroup = service.createSalesInvoiceServiceChargeLineFormGroup(sampleWithRequiredData);

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
            discount: expect.any(Object),
            servicePrice: expect.any(Object),
          }),
        );
      });
    });

    describe('getSalesInvoiceServiceChargeLine', () => {
      it('should return NewSalesInvoiceServiceChargeLine for default SalesInvoiceServiceChargeLine initial value', () => {
        const formGroup = service.createSalesInvoiceServiceChargeLineFormGroup(sampleWithNewData);

        const salesInvoiceServiceChargeLine = service.getSalesInvoiceServiceChargeLine(formGroup) as any;

        expect(salesInvoiceServiceChargeLine).toMatchObject(sampleWithNewData);
      });

      it('should return NewSalesInvoiceServiceChargeLine for empty SalesInvoiceServiceChargeLine initial value', () => {
        const formGroup = service.createSalesInvoiceServiceChargeLineFormGroup();

        const salesInvoiceServiceChargeLine = service.getSalesInvoiceServiceChargeLine(formGroup) as any;

        expect(salesInvoiceServiceChargeLine).toMatchObject({});
      });

      it('should return ISalesInvoiceServiceChargeLine', () => {
        const formGroup = service.createSalesInvoiceServiceChargeLineFormGroup(sampleWithRequiredData);

        const salesInvoiceServiceChargeLine = service.getSalesInvoiceServiceChargeLine(formGroup) as any;

        expect(salesInvoiceServiceChargeLine).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ISalesInvoiceServiceChargeLine should not enable id FormControl', () => {
        const formGroup = service.createSalesInvoiceServiceChargeLineFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewSalesInvoiceServiceChargeLine should disable id FormControl', () => {
        const formGroup = service.createSalesInvoiceServiceChargeLineFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
