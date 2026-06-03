import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../invoice-service-charge.test-samples';

import { InvoiceServiceChargeFormService } from './invoice-service-charge-form.service';

describe('InvoiceServiceCharge Form Service', () => {
  let service: InvoiceServiceChargeFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(InvoiceServiceChargeFormService);
  });

  describe('Service methods', () => {
    describe('createInvoiceServiceChargeFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createInvoiceServiceChargeFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            invoiceId: expect.any(Object),
            lineId: expect.any(Object),
            optionId: expect.any(Object),
            serviceName: expect.any(Object),
            serviceDiscription: expect.any(Object),
            value: expect.any(Object),
            addedById: expect.any(Object),
            isCustomerSrvice: expect.any(Object),
            discount: expect.any(Object),
            servicePrice: expect.any(Object),
          }),
        );
      });

      it('passing IInvoiceServiceCharge should create a new form with FormGroup', () => {
        const formGroup = service.createInvoiceServiceChargeFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            invoiceId: expect.any(Object),
            lineId: expect.any(Object),
            optionId: expect.any(Object),
            serviceName: expect.any(Object),
            serviceDiscription: expect.any(Object),
            value: expect.any(Object),
            addedById: expect.any(Object),
            isCustomerSrvice: expect.any(Object),
            discount: expect.any(Object),
            servicePrice: expect.any(Object),
          }),
        );
      });
    });

    describe('getInvoiceServiceCharge', () => {
      it('should return NewInvoiceServiceCharge for default InvoiceServiceCharge initial value', () => {
        const formGroup = service.createInvoiceServiceChargeFormGroup(sampleWithNewData);

        const invoiceServiceCharge = service.getInvoiceServiceCharge(formGroup) as any;

        expect(invoiceServiceCharge).toMatchObject(sampleWithNewData);
      });

      it('should return NewInvoiceServiceCharge for empty InvoiceServiceCharge initial value', () => {
        const formGroup = service.createInvoiceServiceChargeFormGroup();

        const invoiceServiceCharge = service.getInvoiceServiceCharge(formGroup) as any;

        expect(invoiceServiceCharge).toMatchObject({});
      });

      it('should return IInvoiceServiceCharge', () => {
        const formGroup = service.createInvoiceServiceChargeFormGroup(sampleWithRequiredData);

        const invoiceServiceCharge = service.getInvoiceServiceCharge(formGroup) as any;

        expect(invoiceServiceCharge).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IInvoiceServiceCharge should not enable id FormControl', () => {
        const formGroup = service.createInvoiceServiceChargeFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewInvoiceServiceCharge should disable id FormControl', () => {
        const formGroup = service.createInvoiceServiceChargeFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
