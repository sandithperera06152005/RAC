import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../workshop-vehicle-work-list.test-samples';

import { WorkshopVehicleWorkListFormService } from './workshop-vehicle-work-list-form.service';

describe('WorkshopVehicleWorkList Form Service', () => {
  let service: WorkshopVehicleWorkListFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(WorkshopVehicleWorkListFormService);
  });

  describe('Service methods', () => {
    describe('createWorkshopVehicleWorkListFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createWorkshopVehicleWorkListFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            vehicleworkid: expect.any(Object),
            lineid: expect.any(Object),
            workid: expect.any(Object),
            workshopwork: expect.any(Object),
            isjobdone: expect.any(Object),
            jobdonedate: expect.any(Object),
            jobnumber: expect.any(Object),
            jobvalue: expect.any(Object),
            estimatevalue: expect.any(Object),
          }),
        );
      });

      it('passing IWorkshopVehicleWorkList should create a new form with FormGroup', () => {
        const formGroup = service.createWorkshopVehicleWorkListFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            vehicleworkid: expect.any(Object),
            lineid: expect.any(Object),
            workid: expect.any(Object),
            workshopwork: expect.any(Object),
            isjobdone: expect.any(Object),
            jobdonedate: expect.any(Object),
            jobnumber: expect.any(Object),
            jobvalue: expect.any(Object),
            estimatevalue: expect.any(Object),
          }),
        );
      });
    });

    describe('getWorkshopVehicleWorkList', () => {
      it('should return NewWorkshopVehicleWorkList for default WorkshopVehicleWorkList initial value', () => {
        const formGroup = service.createWorkshopVehicleWorkListFormGroup(sampleWithNewData);

        const workshopVehicleWorkList = service.getWorkshopVehicleWorkList(formGroup) as any;

        expect(workshopVehicleWorkList).toMatchObject(sampleWithNewData);
      });

      it('should return NewWorkshopVehicleWorkList for empty WorkshopVehicleWorkList initial value', () => {
        const formGroup = service.createWorkshopVehicleWorkListFormGroup();

        const workshopVehicleWorkList = service.getWorkshopVehicleWorkList(formGroup) as any;

        expect(workshopVehicleWorkList).toMatchObject({});
      });

      it('should return IWorkshopVehicleWorkList', () => {
        const formGroup = service.createWorkshopVehicleWorkListFormGroup(sampleWithRequiredData);

        const workshopVehicleWorkList = service.getWorkshopVehicleWorkList(formGroup) as any;

        expect(workshopVehicleWorkList).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IWorkshopVehicleWorkList should not enable id FormControl', () => {
        const formGroup = service.createWorkshopVehicleWorkListFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewWorkshopVehicleWorkList should disable id FormControl', () => {
        const formGroup = service.createWorkshopVehicleWorkListFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
