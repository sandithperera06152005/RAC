import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../invoice-service-common.test-samples';

import { InvoiceServiceCommonFormService } from './invoice-service-common-form.service';

describe('InvoiceServiceCommon Form Service', () => {
  let service: InvoiceServiceCommonFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(InvoiceServiceCommonFormService);
  });

  describe('Service methods', () => {
    describe('createInvoiceServiceCommonFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createInvoiceServiceCommonFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            invoiceId: expect.any(Object),
            lineId: expect.any(Object),
            optionId: expect.any(Object),
            mainId: expect.any(Object),
            code: expect.any(Object),
            name: expect.any(Object),
            description: expect.any(Object),
            value: expect.any(Object),
            addedById: expect.any(Object),
            discount: expect.any(Object),
            servicePrice: expect.any(Object),
          }),
        );
      });

      it('passing IInvoiceServiceCommon should create a new form with FormGroup', () => {
        const formGroup = service.createInvoiceServiceCommonFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            invoiceId: expect.any(Object),
            lineId: expect.any(Object),
            optionId: expect.any(Object),
            mainId: expect.any(Object),
            code: expect.any(Object),
            name: expect.any(Object),
            description: expect.any(Object),
            value: expect.any(Object),
            addedById: expect.any(Object),
            discount: expect.any(Object),
            servicePrice: expect.any(Object),
          }),
        );
      });
    });

    describe('getInvoiceServiceCommon', () => {
      it('should return NewInvoiceServiceCommon for default InvoiceServiceCommon initial value', () => {
        const formGroup = service.createInvoiceServiceCommonFormGroup(sampleWithNewData);

        const invoiceServiceCommon = service.getInvoiceServiceCommon(formGroup) as any;

        expect(invoiceServiceCommon).toMatchObject(sampleWithNewData);
      });

      it('should return NewInvoiceServiceCommon for empty InvoiceServiceCommon initial value', () => {
        const formGroup = service.createInvoiceServiceCommonFormGroup();

        const invoiceServiceCommon = service.getInvoiceServiceCommon(formGroup) as any;

        expect(invoiceServiceCommon).toMatchObject({});
      });

      it('should return IInvoiceServiceCommon', () => {
        const formGroup = service.createInvoiceServiceCommonFormGroup(sampleWithRequiredData);

        const invoiceServiceCommon = service.getInvoiceServiceCommon(formGroup) as any;

        expect(invoiceServiceCommon).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IInvoiceServiceCommon should not enable id FormControl', () => {
        const formGroup = service.createInvoiceServiceCommonFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewInvoiceServiceCommon should disable id FormControl', () => {
        const formGroup = service.createInvoiceServiceCommonFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
