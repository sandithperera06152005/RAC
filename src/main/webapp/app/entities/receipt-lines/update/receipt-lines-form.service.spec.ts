import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../receipt-lines.test-samples';

import { ReceiptLinesFormService } from './receipt-lines-form.service';

describe('ReceiptLines Form Service', () => {
  let service: ReceiptLinesFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ReceiptLinesFormService);
  });

  describe('Service methods', () => {
    describe('createReceiptLinesFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createReceiptLinesFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            lineid: expect.any(Object),
            invoicecode: expect.any(Object),
            invoicetype: expect.any(Object),
            originalamount: expect.any(Object),
            amountowing: expect.any(Object),
            discountavailable: expect.any(Object),
            discounttaken: expect.any(Object),
            amountreceived: expect.any(Object),
            lmu: expect.any(Object),
            lmd: expect.any(Object),
            accountid: expect.any(Object),
          }),
        );
      });

      it('passing IReceiptLines should create a new form with FormGroup', () => {
        const formGroup = service.createReceiptLinesFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            lineid: expect.any(Object),
            invoicecode: expect.any(Object),
            invoicetype: expect.any(Object),
            originalamount: expect.any(Object),
            amountowing: expect.any(Object),
            discountavailable: expect.any(Object),
            discounttaken: expect.any(Object),
            amountreceived: expect.any(Object),
            lmu: expect.any(Object),
            lmd: expect.any(Object),
            accountid: expect.any(Object),
          }),
        );
      });
    });

    describe('getReceiptLines', () => {
      it('should return NewReceiptLines for default ReceiptLines initial value', () => {
        const formGroup = service.createReceiptLinesFormGroup(sampleWithNewData);

        const receiptLines = service.getReceiptLines(formGroup) as any;

        expect(receiptLines).toMatchObject(sampleWithNewData);
      });

      it('should return NewReceiptLines for empty ReceiptLines initial value', () => {
        const formGroup = service.createReceiptLinesFormGroup();

        const receiptLines = service.getReceiptLines(formGroup) as any;

        expect(receiptLines).toMatchObject({});
      });

      it('should return IReceiptLines', () => {
        const formGroup = service.createReceiptLinesFormGroup(sampleWithRequiredData);

        const receiptLines = service.getReceiptLines(formGroup) as any;

        expect(receiptLines).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IReceiptLines should not enable id FormControl', () => {
        const formGroup = service.createReceiptLinesFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewReceiptLines should disable id FormControl', () => {
        const formGroup = service.createReceiptLinesFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
