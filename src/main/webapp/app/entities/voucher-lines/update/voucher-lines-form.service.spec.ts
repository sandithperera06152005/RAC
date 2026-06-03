import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../voucher-lines.test-samples';

import { VoucherLinesFormService } from './voucher-lines-form.service';

describe('VoucherLines Form Service', () => {
  let service: VoucherLinesFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(VoucherLinesFormService);
  });

  describe('Service methods', () => {
    describe('createVoucherLinesFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createVoucherLinesFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            lineID: expect.any(Object),
            grnCode: expect.any(Object),
            grnType: expect.any(Object),
            originalAmount: expect.any(Object),
            amountOwing: expect.any(Object),
            discountAvailable: expect.any(Object),
            discountTaken: expect.any(Object),
            amountReceived: expect.any(Object),
            lmu: expect.any(Object),
            lmd: expect.any(Object),
            accountId: expect.any(Object),
          }),
        );
      });

      it('passing IVoucherLines should create a new form with FormGroup', () => {
        const formGroup = service.createVoucherLinesFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            lineID: expect.any(Object),
            grnCode: expect.any(Object),
            grnType: expect.any(Object),
            originalAmount: expect.any(Object),
            amountOwing: expect.any(Object),
            discountAvailable: expect.any(Object),
            discountTaken: expect.any(Object),
            amountReceived: expect.any(Object),
            lmu: expect.any(Object),
            lmd: expect.any(Object),
            accountId: expect.any(Object),
          }),
        );
      });
    });

    describe('getVoucherLines', () => {
      it('should return NewVoucherLines for default VoucherLines initial value', () => {
        const formGroup = service.createVoucherLinesFormGroup(sampleWithNewData);

        const voucherLines = service.getVoucherLines(formGroup) as any;

        expect(voucherLines).toMatchObject(sampleWithNewData);
      });

      it('should return NewVoucherLines for empty VoucherLines initial value', () => {
        const formGroup = service.createVoucherLinesFormGroup();

        const voucherLines = service.getVoucherLines(formGroup) as any;

        expect(voucherLines).toMatchObject({});
      });

      it('should return IVoucherLines', () => {
        const formGroup = service.createVoucherLinesFormGroup(sampleWithRequiredData);

        const voucherLines = service.getVoucherLines(formGroup) as any;

        expect(voucherLines).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IVoucherLines should not enable id FormControl', () => {
        const formGroup = service.createVoucherLinesFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewVoucherLines should disable id FormControl', () => {
        const formGroup = service.createVoucherLinesFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
