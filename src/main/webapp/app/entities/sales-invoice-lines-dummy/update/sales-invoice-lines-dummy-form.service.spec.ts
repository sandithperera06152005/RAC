import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../sales-invoice-lines-dummy.test-samples';

import { SalesInvoiceLinesDummyFormService } from './sales-invoice-lines-dummy-form.service';

describe('SalesInvoiceLinesDummy Form Service', () => {
  let service: SalesInvoiceLinesDummyFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SalesInvoiceLinesDummyFormService);
  });

  describe('Service methods', () => {
    describe('createSalesInvoiceLinesDummyFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createSalesInvoiceLinesDummyFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            invoiceId: expect.any(Object),
            lineId: expect.any(Object),
            itemId: expect.any(Object),
            itemCode: expect.any(Object),
            itemName: expect.any(Object),
            description: expect.any(Object),
            unitOfMeasurement: expect.any(Object),
            quantity: expect.any(Object),
            itemCost: expect.any(Object),
            itemPrice: expect.any(Object),
            discount: expect.any(Object),
            tax: expect.any(Object),
            sellingPrice: expect.any(Object),
            lineTotal: expect.any(Object),
            lmu: expect.any(Object),
            lmd: expect.any(Object),
            nbt: expect.any(Object),
            vat: expect.any(Object),
          }),
        );
      });

      it('passing ISalesInvoiceLinesDummy should create a new form with FormGroup', () => {
        const formGroup = service.createSalesInvoiceLinesDummyFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            invoiceId: expect.any(Object),
            lineId: expect.any(Object),
            itemId: expect.any(Object),
            itemCode: expect.any(Object),
            itemName: expect.any(Object),
            description: expect.any(Object),
            unitOfMeasurement: expect.any(Object),
            quantity: expect.any(Object),
            itemCost: expect.any(Object),
            itemPrice: expect.any(Object),
            discount: expect.any(Object),
            tax: expect.any(Object),
            sellingPrice: expect.any(Object),
            lineTotal: expect.any(Object),
            lmu: expect.any(Object),
            lmd: expect.any(Object),
            nbt: expect.any(Object),
            vat: expect.any(Object),
          }),
        );
      });
    });

    describe('getSalesInvoiceLinesDummy', () => {
      it('should return NewSalesInvoiceLinesDummy for default SalesInvoiceLinesDummy initial value', () => {
        const formGroup = service.createSalesInvoiceLinesDummyFormGroup(sampleWithNewData);

        const salesInvoiceLinesDummy = service.getSalesInvoiceLinesDummy(formGroup) as any;

        expect(salesInvoiceLinesDummy).toMatchObject(sampleWithNewData);
      });

      it('should return NewSalesInvoiceLinesDummy for empty SalesInvoiceLinesDummy initial value', () => {
        const formGroup = service.createSalesInvoiceLinesDummyFormGroup();

        const salesInvoiceLinesDummy = service.getSalesInvoiceLinesDummy(formGroup) as any;

        expect(salesInvoiceLinesDummy).toMatchObject({});
      });

      it('should return ISalesInvoiceLinesDummy', () => {
        const formGroup = service.createSalesInvoiceLinesDummyFormGroup(sampleWithRequiredData);

        const salesInvoiceLinesDummy = service.getSalesInvoiceLinesDummy(formGroup) as any;

        expect(salesInvoiceLinesDummy).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ISalesInvoiceLinesDummy should not enable id FormControl', () => {
        const formGroup = service.createSalesInvoiceLinesDummyFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewSalesInvoiceLinesDummy should disable id FormControl', () => {
        const formGroup = service.createSalesInvoiceLinesDummyFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
