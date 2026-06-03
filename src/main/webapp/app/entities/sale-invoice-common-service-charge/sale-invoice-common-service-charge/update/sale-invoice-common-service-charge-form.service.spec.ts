import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../sale-invoice-common-service-charge.test-samples';

import { SaleInvoiceCommonServiceChargeFormService } from './sale-invoice-common-service-charge-form.service';

describe('SaleInvoiceCommonServiceCharge Form Service', () => {
  let service: SaleInvoiceCommonServiceChargeFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SaleInvoiceCommonServiceChargeFormService);
  });

  describe('Service methods', () => {
    describe('createSaleInvoiceCommonServiceChargeFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createSaleInvoiceCommonServiceChargeFormGroup();

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
            discount: expect.any(Object),
            servicePrice: expect.any(Object),
          }),
        );
      });

      it('passing ISaleInvoiceCommonServiceCharge should create a new form with FormGroup', () => {
        const formGroup = service.createSaleInvoiceCommonServiceChargeFormGroup(sampleWithRequiredData);

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
            discount: expect.any(Object),
            servicePrice: expect.any(Object),
          }),
        );
      });
    });

    describe('getSaleInvoiceCommonServiceCharge', () => {
      it('should return NewSaleInvoiceCommonServiceCharge for default SaleInvoiceCommonServiceCharge initial value', () => {
        const formGroup = service.createSaleInvoiceCommonServiceChargeFormGroup(sampleWithNewData);

        const saleInvoiceCommonServiceCharge = service.getSaleInvoiceCommonServiceCharge(formGroup) as any;

        expect(saleInvoiceCommonServiceCharge).toMatchObject(sampleWithNewData);
      });

      it('should return NewSaleInvoiceCommonServiceCharge for empty SaleInvoiceCommonServiceCharge initial value', () => {
        const formGroup = service.createSaleInvoiceCommonServiceChargeFormGroup();

        const saleInvoiceCommonServiceCharge = service.getSaleInvoiceCommonServiceCharge(formGroup) as any;

        expect(saleInvoiceCommonServiceCharge).toMatchObject({});
      });

      it('should return ISaleInvoiceCommonServiceCharge', () => {
        const formGroup = service.createSaleInvoiceCommonServiceChargeFormGroup(sampleWithRequiredData);

        const saleInvoiceCommonServiceCharge = service.getSaleInvoiceCommonServiceCharge(formGroup) as any;

        expect(saleInvoiceCommonServiceCharge).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ISaleInvoiceCommonServiceCharge should not enable id FormControl', () => {
        const formGroup = service.createSaleInvoiceCommonServiceChargeFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewSaleInvoiceCommonServiceCharge should disable id FormControl', () => {
        const formGroup = service.createSaleInvoiceCommonServiceChargeFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
