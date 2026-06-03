import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../autojobsalesinvoiceservicechargeline.test-samples';

import { AutojobsalesinvoiceservicechargelineFormService } from './autojobsalesinvoiceservicechargeline-form.service';

describe('Autojobsalesinvoiceservicechargeline Form Service', () => {
  let service: AutojobsalesinvoiceservicechargelineFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AutojobsalesinvoiceservicechargelineFormService);
  });

  describe('Service methods', () => {
    describe('createAutojobsalesinvoiceservicechargelineFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAutojobsalesinvoiceservicechargelineFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            invoiceid: expect.any(Object),
            lineid: expect.any(Object),
            optionid: expect.any(Object),
            servicename: expect.any(Object),
            servicediscription: expect.any(Object),
            value: expect.any(Object),
            addedbyid: expect.any(Object),
            iscustomersrvice: expect.any(Object),
            discount: expect.any(Object),
            serviceprice: expect.any(Object),
          }),
        );
      });

      it('passing IAutojobsalesinvoiceservicechargeline should create a new form with FormGroup', () => {
        const formGroup = service.createAutojobsalesinvoiceservicechargelineFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            invoiceid: expect.any(Object),
            lineid: expect.any(Object),
            optionid: expect.any(Object),
            servicename: expect.any(Object),
            servicediscription: expect.any(Object),
            value: expect.any(Object),
            addedbyid: expect.any(Object),
            iscustomersrvice: expect.any(Object),
            discount: expect.any(Object),
            serviceprice: expect.any(Object),
          }),
        );
      });
    });

    describe('getAutojobsalesinvoiceservicechargeline', () => {
      it('should return NewAutojobsalesinvoiceservicechargeline for default Autojobsalesinvoiceservicechargeline initial value', () => {
        const formGroup = service.createAutojobsalesinvoiceservicechargelineFormGroup(sampleWithNewData);

        const autojobsalesinvoiceservicechargeline = service.getAutojobsalesinvoiceservicechargeline(formGroup) as any;

        expect(autojobsalesinvoiceservicechargeline).toMatchObject(sampleWithNewData);
      });

      it('should return NewAutojobsalesinvoiceservicechargeline for empty Autojobsalesinvoiceservicechargeline initial value', () => {
        const formGroup = service.createAutojobsalesinvoiceservicechargelineFormGroup();

        const autojobsalesinvoiceservicechargeline = service.getAutojobsalesinvoiceservicechargeline(formGroup) as any;

        expect(autojobsalesinvoiceservicechargeline).toMatchObject({});
      });

      it('should return IAutojobsalesinvoiceservicechargeline', () => {
        const formGroup = service.createAutojobsalesinvoiceservicechargelineFormGroup(sampleWithRequiredData);

        const autojobsalesinvoiceservicechargeline = service.getAutojobsalesinvoiceservicechargeline(formGroup) as any;

        expect(autojobsalesinvoiceservicechargeline).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAutojobsalesinvoiceservicechargeline should not enable id FormControl', () => {
        const formGroup = service.createAutojobsalesinvoiceservicechargelineFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewAutojobsalesinvoiceservicechargeline should disable id FormControl', () => {
        const formGroup = service.createAutojobsalesinvoiceservicechargelineFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
