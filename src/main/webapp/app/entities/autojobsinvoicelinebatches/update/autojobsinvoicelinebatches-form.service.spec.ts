import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../autojobsinvoicelinebatches.test-samples';

import { AutojobsinvoicelinebatchesFormService } from './autojobsinvoicelinebatches-form.service';

describe('Autojobsinvoicelinebatches Form Service', () => {
  let service: AutojobsinvoicelinebatchesFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AutojobsinvoicelinebatchesFormService);
  });

  describe('Service methods', () => {
    describe('createAutojobsinvoicelinebatchesFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAutojobsinvoicelinebatchesFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            lineid: expect.any(Object),
            batchlineid: expect.any(Object),
            itemid: expect.any(Object),
            code: expect.any(Object),
            batchid: expect.any(Object),
            batchcode: expect.any(Object),
            txdate: expect.any(Object),
            manufacturedate: expect.any(Object),
            expireddate: expect.any(Object),
            qty: expect.any(Object),
            cost: expect.any(Object),
            price: expect.any(Object),
            notes: expect.any(Object),
            lmu: expect.any(Object),
            lmd: expect.any(Object),
            nbt: expect.any(Object),
            vat: expect.any(Object),
            discount: expect.any(Object),
            total: expect.any(Object),
            issued: expect.any(Object),
            issuedby: expect.any(Object),
            issueddatetime: expect.any(Object),
            addedbyid: expect.any(Object),
            canceloptid: expect.any(Object),
            cancelopt: expect.any(Object),
            cancelby: expect.any(Object),
          }),
        );
      });

      it('passing IAutojobsinvoicelinebatches should create a new form with FormGroup', () => {
        const formGroup = service.createAutojobsinvoicelinebatchesFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            lineid: expect.any(Object),
            batchlineid: expect.any(Object),
            itemid: expect.any(Object),
            code: expect.any(Object),
            batchid: expect.any(Object),
            batchcode: expect.any(Object),
            txdate: expect.any(Object),
            manufacturedate: expect.any(Object),
            expireddate: expect.any(Object),
            qty: expect.any(Object),
            cost: expect.any(Object),
            price: expect.any(Object),
            notes: expect.any(Object),
            lmu: expect.any(Object),
            lmd: expect.any(Object),
            nbt: expect.any(Object),
            vat: expect.any(Object),
            discount: expect.any(Object),
            total: expect.any(Object),
            issued: expect.any(Object),
            issuedby: expect.any(Object),
            issueddatetime: expect.any(Object),
            addedbyid: expect.any(Object),
            canceloptid: expect.any(Object),
            cancelopt: expect.any(Object),
            cancelby: expect.any(Object),
          }),
        );
      });
    });

    describe('getAutojobsinvoicelinebatches', () => {
      it('should return NewAutojobsinvoicelinebatches for default Autojobsinvoicelinebatches initial value', () => {
        const formGroup = service.createAutojobsinvoicelinebatchesFormGroup(sampleWithNewData);

        const autojobsinvoicelinebatches = service.getAutojobsinvoicelinebatches(formGroup) as any;

        expect(autojobsinvoicelinebatches).toMatchObject(sampleWithNewData);
      });

      it('should return NewAutojobsinvoicelinebatches for empty Autojobsinvoicelinebatches initial value', () => {
        const formGroup = service.createAutojobsinvoicelinebatchesFormGroup();

        const autojobsinvoicelinebatches = service.getAutojobsinvoicelinebatches(formGroup) as any;

        expect(autojobsinvoicelinebatches).toMatchObject({});
      });

      it('should return IAutojobsinvoicelinebatches', () => {
        const formGroup = service.createAutojobsinvoicelinebatchesFormGroup(sampleWithRequiredData);

        const autojobsinvoicelinebatches = service.getAutojobsinvoicelinebatches(formGroup) as any;

        expect(autojobsinvoicelinebatches).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAutojobsinvoicelinebatches should not enable id FormControl', () => {
        const formGroup = service.createAutojobsinvoicelinebatchesFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewAutojobsinvoicelinebatches should disable id FormControl', () => {
        const formGroup = service.createAutojobsinvoicelinebatchesFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
