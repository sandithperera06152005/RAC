import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../sales-invoice-lines.test-samples';

import { SalesInvoiceLinesFormService } from './sales-invoice-lines-form.service';

describe('SalesInvoiceLines Form Service', () => {
  let service: SalesInvoiceLinesFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SalesInvoiceLinesFormService);
  });

  describe('Service methods', () => {
    describe('createSalesInvoiceLinesFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createSalesInvoiceLinesFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            invoiceid: expect.any(Object),
            lineid: expect.any(Object),
            itemid: expect.any(Object),
            itemcode: expect.any(Object),
            itemname: expect.any(Object),
            description: expect.any(Object),
            unitofmeasurement: expect.any(Object),
            quantity: expect.any(Object),
            itemcost: expect.any(Object),
            itemprice: expect.any(Object),
            discount: expect.any(Object),
            tax: expect.any(Object),
            sellingprice: expect.any(Object),
            linetotal: expect.any(Object),
            lmu: expect.any(Object),
            lmd: expect.any(Object),
            nbt: expect.any(Object),
            vat: expect.any(Object),
          }),
        );
      });

      it('passing ISalesInvoiceLines should create a new form with FormGroup', () => {
        const formGroup = service.createSalesInvoiceLinesFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            invoiceid: expect.any(Object),
            lineid: expect.any(Object),
            itemid: expect.any(Object),
            itemcode: expect.any(Object),
            itemname: expect.any(Object),
            description: expect.any(Object),
            unitofmeasurement: expect.any(Object),
            quantity: expect.any(Object),
            itemcost: expect.any(Object),
            itemprice: expect.any(Object),
            discount: expect.any(Object),
            tax: expect.any(Object),
            sellingprice: expect.any(Object),
            linetotal: expect.any(Object),
            lmu: expect.any(Object),
            lmd: expect.any(Object),
            nbt: expect.any(Object),
            vat: expect.any(Object),
          }),
        );
      });
    });

    describe('getSalesInvoiceLines', () => {
      it('should return NewSalesInvoiceLines for default SalesInvoiceLines initial value', () => {
        const formGroup = service.createSalesInvoiceLinesFormGroup(sampleWithNewData);

        const salesInvoiceLines = service.getSalesInvoiceLines(formGroup) as any;

        expect(salesInvoiceLines).toMatchObject(sampleWithNewData);
      });

      it('should return NewSalesInvoiceLines for empty SalesInvoiceLines initial value', () => {
        const formGroup = service.createSalesInvoiceLinesFormGroup();

        const salesInvoiceLines = service.getSalesInvoiceLines(formGroup) as any;

        expect(salesInvoiceLines).toMatchObject({});
      });

      it('should return ISalesInvoiceLines', () => {
        const formGroup = service.createSalesInvoiceLinesFormGroup(sampleWithRequiredData);

        const salesInvoiceLines = service.getSalesInvoiceLines(formGroup) as any;

        expect(salesInvoiceLines).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ISalesInvoiceLines should not enable id FormControl', () => {
        const formGroup = service.createSalesInvoiceLinesFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewSalesInvoiceLines should disable id FormControl', () => {
        const formGroup = service.createSalesInvoiceLinesFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
