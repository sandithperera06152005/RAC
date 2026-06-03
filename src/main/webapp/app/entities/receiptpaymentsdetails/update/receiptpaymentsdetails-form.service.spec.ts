import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../receiptpaymentsdetails.test-samples';

import { ReceiptpaymentsdetailsFormService } from './receiptpaymentsdetails-form.service';

describe('Receiptpaymentsdetails Form Service', () => {
  let service: ReceiptpaymentsdetailsFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ReceiptpaymentsdetailsFormService);
  });

  describe('Service methods', () => {
    describe('createReceiptpaymentsdetailsFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createReceiptpaymentsdetailsFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            lineid: expect.any(Object),
            paymentamount: expect.any(Object),
            totalreceiptamount: expect.any(Object),
            checkqueamount: expect.any(Object),
            checkqueno: expect.any(Object),
            checkquedate: expect.any(Object),
            checkqueexpiredate: expect.any(Object),
            bankname: expect.any(Object),
            bankid: expect.any(Object),
            bankbranchname: expect.any(Object),
            bankbranchid: expect.any(Object),
            creditcardno: expect.any(Object),
            creditcardamount: expect.any(Object),
            reference: expect.any(Object),
            otherdetails: expect.any(Object),
            lmu: expect.any(Object),
            lmd: expect.any(Object),
            termid: expect.any(Object),
            termname: expect.any(Object),
            accountno: expect.any(Object),
            accountnumber: expect.any(Object),
            chequereturndate: expect.any(Object),
            isdeposit: expect.any(Object),
            depositeddate: expect.any(Object),
            chequestatuschangeddate: expect.any(Object),
            returnchequesttledate: expect.any(Object),
            chequestatusid: expect.any(Object),
            isPdCheque: expect.any(Object),
            depositdate: expect.any(Object),
            accountid: expect.any(Object),
            accountcode: expect.any(Object),
            bankdepositbankname: expect.any(Object),
            bankdepositbankid: expect.any(Object),
            bankdepositbankbranchname: expect.any(Object),
            bankdepositbankbranchid: expect.any(Object),
            returnchequefine: expect.any(Object),
            companybankid: expect.any(Object),
            isbankreconciliation: expect.any(Object),
          }),
        );
      });

      it('passing IReceiptpaymentsdetails should create a new form with FormGroup', () => {
        const formGroup = service.createReceiptpaymentsdetailsFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            lineid: expect.any(Object),
            paymentamount: expect.any(Object),
            totalreceiptamount: expect.any(Object),
            checkqueamount: expect.any(Object),
            checkqueno: expect.any(Object),
            checkquedate: expect.any(Object),
            checkqueexpiredate: expect.any(Object),
            bankname: expect.any(Object),
            bankid: expect.any(Object),
            bankbranchname: expect.any(Object),
            bankbranchid: expect.any(Object),
            creditcardno: expect.any(Object),
            creditcardamount: expect.any(Object),
            reference: expect.any(Object),
            otherdetails: expect.any(Object),
            lmu: expect.any(Object),
            lmd: expect.any(Object),
            termid: expect.any(Object),
            termname: expect.any(Object),
            accountno: expect.any(Object),
            accountnumber: expect.any(Object),
            chequereturndate: expect.any(Object),
            isdeposit: expect.any(Object),
            depositeddate: expect.any(Object),
            chequestatuschangeddate: expect.any(Object),
            returnchequesttledate: expect.any(Object),
            chequestatusid: expect.any(Object),
            isPdCheque: expect.any(Object),
            depositdate: expect.any(Object),
            accountid: expect.any(Object),
            accountcode: expect.any(Object),
            bankdepositbankname: expect.any(Object),
            bankdepositbankid: expect.any(Object),
            bankdepositbankbranchname: expect.any(Object),
            bankdepositbankbranchid: expect.any(Object),
            returnchequefine: expect.any(Object),
            companybankid: expect.any(Object),
            isbankreconciliation: expect.any(Object),
          }),
        );
      });
    });

    describe('getReceiptpaymentsdetails', () => {
      it('should return NewReceiptpaymentsdetails for default Receiptpaymentsdetails initial value', () => {
        const formGroup = service.createReceiptpaymentsdetailsFormGroup(sampleWithNewData);

        const receiptpaymentsdetails = service.getReceiptpaymentsdetails(formGroup) as any;

        expect(receiptpaymentsdetails).toMatchObject(sampleWithNewData);
      });

      it('should return NewReceiptpaymentsdetails for empty Receiptpaymentsdetails initial value', () => {
        const formGroup = service.createReceiptpaymentsdetailsFormGroup();

        const receiptpaymentsdetails = service.getReceiptpaymentsdetails(formGroup) as any;

        expect(receiptpaymentsdetails).toMatchObject({});
      });

      it('should return IReceiptpaymentsdetails', () => {
        const formGroup = service.createReceiptpaymentsdetailsFormGroup(sampleWithRequiredData);

        const receiptpaymentsdetails = service.getReceiptpaymentsdetails(formGroup) as any;

        expect(receiptpaymentsdetails).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IReceiptpaymentsdetails should not enable id FormControl', () => {
        const formGroup = service.createReceiptpaymentsdetailsFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewReceiptpaymentsdetails should disable id FormControl', () => {
        const formGroup = service.createReceiptpaymentsdetailsFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
