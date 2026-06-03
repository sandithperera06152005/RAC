import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../sales-invoice-dummy.test-samples';

import { SalesInvoiceDummyFormService } from './sales-invoice-dummy-form.service';

describe('SalesInvoiceDummy Form Service', () => {
  let service: SalesInvoiceDummyFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SalesInvoiceDummyFormService);
  });

  describe('Service methods', () => {
    describe('createSalesInvoiceDummyFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createSalesInvoiceDummyFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            originalInvoiceId: expect.any(Object),
            code: expect.any(Object),
            invoiceDate: expect.any(Object),
            createdDate: expect.any(Object),
            quoteID: expect.any(Object),
            orderID: expect.any(Object),
            deliveryDate: expect.any(Object),
            salesRepID: expect.any(Object),
            salesRepName: expect.any(Object),
            deliverFrom: expect.any(Object),
            customerID: expect.any(Object),
            customerName: expect.any(Object),
            customerAddress: expect.any(Object),
            deliveryAddress: expect.any(Object),
            subTotal: expect.any(Object),
            totalTax: expect.any(Object),
            totalDiscount: expect.any(Object),
            netTotal: expect.any(Object),
            message: expect.any(Object),
            lmu: expect.any(Object),
            lmd: expect.any(Object),
            paidAmount: expect.any(Object),
            amountOwing: expect.any(Object),
            isActive: expect.any(Object),
            locationID: expect.any(Object),
            locationCode: expect.any(Object),
            referenceCode: expect.any(Object),
            createdById: expect.any(Object),
            createdByName: expect.any(Object),
            autoCareCharges: expect.any(Object),
            autoCareJobId: expect.any(Object),
            vehicleNo: expect.any(Object),
            nbtAmount: expect.any(Object),
            vatAmount: expect.any(Object),
            dummyCommission: expect.any(Object),
            commissionPaidDate: expect.any(Object),
            paidCommission: expect.any(Object),
            paidBy: expect.any(Object),
            originalInvoiceCode: expect.any(Object),
          }),
        );
      });

      it('passing ISalesInvoiceDummy should create a new form with FormGroup', () => {
        const formGroup = service.createSalesInvoiceDummyFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            originalInvoiceId: expect.any(Object),
            code: expect.any(Object),
            invoiceDate: expect.any(Object),
            createdDate: expect.any(Object),
            quoteID: expect.any(Object),
            orderID: expect.any(Object),
            deliveryDate: expect.any(Object),
            salesRepID: expect.any(Object),
            salesRepName: expect.any(Object),
            deliverFrom: expect.any(Object),
            customerID: expect.any(Object),
            customerName: expect.any(Object),
            customerAddress: expect.any(Object),
            deliveryAddress: expect.any(Object),
            subTotal: expect.any(Object),
            totalTax: expect.any(Object),
            totalDiscount: expect.any(Object),
            netTotal: expect.any(Object),
            message: expect.any(Object),
            lmu: expect.any(Object),
            lmd: expect.any(Object),
            paidAmount: expect.any(Object),
            amountOwing: expect.any(Object),
            isActive: expect.any(Object),
            locationID: expect.any(Object),
            locationCode: expect.any(Object),
            referenceCode: expect.any(Object),
            createdById: expect.any(Object),
            createdByName: expect.any(Object),
            autoCareCharges: expect.any(Object),
            autoCareJobId: expect.any(Object),
            vehicleNo: expect.any(Object),
            nbtAmount: expect.any(Object),
            vatAmount: expect.any(Object),
            dummyCommission: expect.any(Object),
            commissionPaidDate: expect.any(Object),
            paidCommission: expect.any(Object),
            paidBy: expect.any(Object),
            originalInvoiceCode: expect.any(Object),
          }),
        );
      });
    });

    describe('getSalesInvoiceDummy', () => {
      it('should return NewSalesInvoiceDummy for default SalesInvoiceDummy initial value', () => {
        const formGroup = service.createSalesInvoiceDummyFormGroup(sampleWithNewData);

        const salesInvoiceDummy = service.getSalesInvoiceDummy(formGroup) as any;

        expect(salesInvoiceDummy).toMatchObject(sampleWithNewData);
      });

      it('should return NewSalesInvoiceDummy for empty SalesInvoiceDummy initial value', () => {
        const formGroup = service.createSalesInvoiceDummyFormGroup();

        const salesInvoiceDummy = service.getSalesInvoiceDummy(formGroup) as any;

        expect(salesInvoiceDummy).toMatchObject({});
      });

      it('should return ISalesInvoiceDummy', () => {
        const formGroup = service.createSalesInvoiceDummyFormGroup(sampleWithRequiredData);

        const salesInvoiceDummy = service.getSalesInvoiceDummy(formGroup) as any;

        expect(salesInvoiceDummy).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ISalesInvoiceDummy should not enable id FormControl', () => {
        const formGroup = service.createSalesInvoiceDummyFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewSalesInvoiceDummy should disable id FormControl', () => {
        const formGroup = service.createSalesInvoiceDummyFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
