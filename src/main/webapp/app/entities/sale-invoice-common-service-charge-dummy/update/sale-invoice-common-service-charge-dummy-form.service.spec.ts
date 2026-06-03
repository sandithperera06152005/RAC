import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../sale-invoice-common-service-charge-dummy.test-samples';

import { SaleInvoiceCommonServiceChargeDummyFormService } from './sale-invoice-common-service-charge-dummy-form.service';

describe('SaleInvoiceCommonServiceChargeDummy Form Service', () => {
  let service: SaleInvoiceCommonServiceChargeDummyFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SaleInvoiceCommonServiceChargeDummyFormService);
  });

  describe('Service methods', () => {
    describe('createSaleInvoiceCommonServiceChargeDummyFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createSaleInvoiceCommonServiceChargeDummyFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            invoiceid: expect.any(Object),
            lineid: expect.any(Object),
            optionid: expect.any(Object),
            mainid: expect.any(Object),
            code: expect.any(Object),
            name: expect.any(Object),
            description: expect.any(Object),
            value: expect.any(Object),
          }),
        );
      });

      it('passing ISaleInvoiceCommonServiceChargeDummy should create a new form with FormGroup', () => {
        const formGroup = service.createSaleInvoiceCommonServiceChargeDummyFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            invoiceid: expect.any(Object),
            lineid: expect.any(Object),
            optionid: expect.any(Object),
            mainid: expect.any(Object),
            code: expect.any(Object),
            name: expect.any(Object),
            description: expect.any(Object),
            value: expect.any(Object),
          }),
        );
      });
    });

    describe('getSaleInvoiceCommonServiceChargeDummy', () => {
      it('should return NewSaleInvoiceCommonServiceChargeDummy for default SaleInvoiceCommonServiceChargeDummy initial value', () => {
        const formGroup = service.createSaleInvoiceCommonServiceChargeDummyFormGroup(sampleWithNewData);

        const saleInvoiceCommonServiceChargeDummy = service.getSaleInvoiceCommonServiceChargeDummy(formGroup) as any;

        expect(saleInvoiceCommonServiceChargeDummy).toMatchObject(sampleWithNewData);
      });

      it('should return NewSaleInvoiceCommonServiceChargeDummy for empty SaleInvoiceCommonServiceChargeDummy initial value', () => {
        const formGroup = service.createSaleInvoiceCommonServiceChargeDummyFormGroup();

        const saleInvoiceCommonServiceChargeDummy = service.getSaleInvoiceCommonServiceChargeDummy(formGroup) as any;

        expect(saleInvoiceCommonServiceChargeDummy).toMatchObject({});
      });

      it('should return ISaleInvoiceCommonServiceChargeDummy', () => {
        const formGroup = service.createSaleInvoiceCommonServiceChargeDummyFormGroup(sampleWithRequiredData);

        const saleInvoiceCommonServiceChargeDummy = service.getSaleInvoiceCommonServiceChargeDummy(formGroup) as any;

        expect(saleInvoiceCommonServiceChargeDummy).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ISaleInvoiceCommonServiceChargeDummy should not enable id FormControl', () => {
        const formGroup = service.createSaleInvoiceCommonServiceChargeDummyFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewSaleInvoiceCommonServiceChargeDummy should disable id FormControl', () => {
        const formGroup = service.createSaleInvoiceCommonServiceChargeDummyFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
