import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../voucher-payments-details.test-samples';

import { VoucherPaymentsDetailsFormService } from './voucher-payments-details-form.service';

describe('VoucherPaymentsDetails Form Service', () => {
  let service: VoucherPaymentsDetailsFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(VoucherPaymentsDetailsFormService);
  });

  describe('Service methods', () => {
    describe('createVoucherPaymentsDetailsFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createVoucherPaymentsDetailsFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            lineID: expect.any(Object),
            paymentAmount: expect.any(Object),
            totalVoucherAmount: expect.any(Object),
            checkqueAmount: expect.any(Object),
            checkqueNo: expect.any(Object),
            checkqueDate: expect.any(Object),
            checkqueExpireDate: expect.any(Object),
            bankName: expect.any(Object),
            bankID: expect.any(Object),
            creditCardNo: expect.any(Object),
            creditCardAmount: expect.any(Object),
            reference: expect.any(Object),
            otherDetails: expect.any(Object),
            lmu: expect.any(Object),
            lmd: expect.any(Object),
            termID: expect.any(Object),
            termName: expect.any(Object),
            accountNo: expect.any(Object),
            accountNumber: expect.any(Object),
            accountId: expect.any(Object),
            accountCode: expect.any(Object),
            chequeStatusId: expect.any(Object),
            isDeposit: expect.any(Object),
            depositedDate: expect.any(Object),
            companyBankId: expect.any(Object),
            isBankReconciliation: expect.any(Object),
          }),
        );
      });

      it('passing IVoucherPaymentsDetails should create a new form with FormGroup', () => {
        const formGroup = service.createVoucherPaymentsDetailsFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            lineID: expect.any(Object),
            paymentAmount: expect.any(Object),
            totalVoucherAmount: expect.any(Object),
            checkqueAmount: expect.any(Object),
            checkqueNo: expect.any(Object),
            checkqueDate: expect.any(Object),
            checkqueExpireDate: expect.any(Object),
            bankName: expect.any(Object),
            bankID: expect.any(Object),
            creditCardNo: expect.any(Object),
            creditCardAmount: expect.any(Object),
            reference: expect.any(Object),
            otherDetails: expect.any(Object),
            lmu: expect.any(Object),
            lmd: expect.any(Object),
            termID: expect.any(Object),
            termName: expect.any(Object),
            accountNo: expect.any(Object),
            accountNumber: expect.any(Object),
            accountId: expect.any(Object),
            accountCode: expect.any(Object),
            chequeStatusId: expect.any(Object),
            isDeposit: expect.any(Object),
            depositedDate: expect.any(Object),
            companyBankId: expect.any(Object),
            isBankReconciliation: expect.any(Object),
          }),
        );
      });
    });

    describe('getVoucherPaymentsDetails', () => {
      it('should return NewVoucherPaymentsDetails for default VoucherPaymentsDetails initial value', () => {
        const formGroup = service.createVoucherPaymentsDetailsFormGroup(sampleWithNewData);

        const voucherPaymentsDetails = service.getVoucherPaymentsDetails(formGroup) as any;

        expect(voucherPaymentsDetails).toMatchObject(sampleWithNewData);
      });

      it('should return NewVoucherPaymentsDetails for empty VoucherPaymentsDetails initial value', () => {
        const formGroup = service.createVoucherPaymentsDetailsFormGroup();

        const voucherPaymentsDetails = service.getVoucherPaymentsDetails(formGroup) as any;

        expect(voucherPaymentsDetails).toMatchObject({});
      });

      it('should return IVoucherPaymentsDetails', () => {
        const formGroup = service.createVoucherPaymentsDetailsFormGroup(sampleWithRequiredData);

        const voucherPaymentsDetails = service.getVoucherPaymentsDetails(formGroup) as any;

        expect(voucherPaymentsDetails).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IVoucherPaymentsDetails should not enable id FormControl', () => {
        const formGroup = service.createVoucherPaymentsDetailsFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewVoucherPaymentsDetails should disable id FormControl', () => {
        const formGroup = service.createVoucherPaymentsDetailsFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
