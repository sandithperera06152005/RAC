import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../autojobsaleinvoicecommonservicecharge.test-samples';

import { AutojobsaleinvoicecommonservicechargeFormService } from './autojobsaleinvoicecommonservicecharge-form.service';

describe('Autojobsaleinvoicecommonservicecharge Form Service', () => {
  let service: AutojobsaleinvoicecommonservicechargeFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AutojobsaleinvoicecommonservicechargeFormService);
  });

  describe('Service methods', () => {
    describe('createAutojobsaleinvoicecommonservicechargeFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAutojobsaleinvoicecommonservicechargeFormGroup();

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
            addedbyid: expect.any(Object),
            discount: expect.any(Object),
            serviceprice: expect.any(Object),
          }),
        );
      });

      it('passing IAutojobsaleinvoicecommonservicecharge should create a new form with FormGroup', () => {
        const formGroup = service.createAutojobsaleinvoicecommonservicechargeFormGroup(sampleWithRequiredData);

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
            addedbyid: expect.any(Object),
            discount: expect.any(Object),
            serviceprice: expect.any(Object),
          }),
        );
      });
    });

    describe('getAutojobsaleinvoicecommonservicecharge', () => {
      it('should return NewAutojobsaleinvoicecommonservicecharge for default Autojobsaleinvoicecommonservicecharge initial value', () => {
        const formGroup = service.createAutojobsaleinvoicecommonservicechargeFormGroup(sampleWithNewData);

        const autojobsaleinvoicecommonservicecharge = service.getAutojobsaleinvoicecommonservicecharge(formGroup) as any;

        expect(autojobsaleinvoicecommonservicecharge).toMatchObject(sampleWithNewData);
      });

      it('should return NewAutojobsaleinvoicecommonservicecharge for empty Autojobsaleinvoicecommonservicecharge initial value', () => {
        const formGroup = service.createAutojobsaleinvoicecommonservicechargeFormGroup();

        const autojobsaleinvoicecommonservicecharge = service.getAutojobsaleinvoicecommonservicecharge(formGroup) as any;

        expect(autojobsaleinvoicecommonservicecharge).toMatchObject({});
      });

      it('should return IAutojobsaleinvoicecommonservicecharge', () => {
        const formGroup = service.createAutojobsaleinvoicecommonservicechargeFormGroup(sampleWithRequiredData);

        const autojobsaleinvoicecommonservicecharge = service.getAutojobsaleinvoicecommonservicecharge(formGroup) as any;

        expect(autojobsaleinvoicecommonservicecharge).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAutojobsaleinvoicecommonservicecharge should not enable id FormControl', () => {
        const formGroup = service.createAutojobsaleinvoicecommonservicechargeFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewAutojobsaleinvoicecommonservicecharge should disable id FormControl', () => {
        const formGroup = service.createAutojobsaleinvoicecommonservicechargeFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
