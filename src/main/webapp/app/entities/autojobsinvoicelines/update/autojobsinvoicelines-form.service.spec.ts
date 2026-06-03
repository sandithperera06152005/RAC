import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../autojobsinvoicelines.test-samples';

import { AutojobsinvoicelinesFormService } from './autojobsinvoicelines-form.service';

describe('Autojobsinvoicelines Form Service', () => {
  let service: AutojobsinvoicelinesFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AutojobsinvoicelinesFormService);
  });

  describe('Service methods', () => {
    describe('createAutojobsinvoicelinesFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAutojobsinvoicelinesFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            invocieid: expect.any(Object),
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

      it('passing IAutojobsinvoicelines should create a new form with FormGroup', () => {
        const formGroup = service.createAutojobsinvoicelinesFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            invocieid: expect.any(Object),
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

    describe('getAutojobsinvoicelines', () => {
      it('should return NewAutojobsinvoicelines for default Autojobsinvoicelines initial value', () => {
        const formGroup = service.createAutojobsinvoicelinesFormGroup(sampleWithNewData);

        const autojobsinvoicelines = service.getAutojobsinvoicelines(formGroup) as any;

        expect(autojobsinvoicelines).toMatchObject(sampleWithNewData);
      });

      it('should return NewAutojobsinvoicelines for empty Autojobsinvoicelines initial value', () => {
        const formGroup = service.createAutojobsinvoicelinesFormGroup();

        const autojobsinvoicelines = service.getAutojobsinvoicelines(formGroup) as any;

        expect(autojobsinvoicelines).toMatchObject({});
      });

      it('should return IAutojobsinvoicelines', () => {
        const formGroup = service.createAutojobsinvoicelinesFormGroup(sampleWithRequiredData);

        const autojobsinvoicelines = service.getAutojobsinvoicelines(formGroup) as any;

        expect(autojobsinvoicelines).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAutojobsinvoicelines should not enable id FormControl', () => {
        const formGroup = service.createAutojobsinvoicelinesFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewAutojobsinvoicelines should disable id FormControl', () => {
        const formGroup = service.createAutojobsinvoicelinesFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
