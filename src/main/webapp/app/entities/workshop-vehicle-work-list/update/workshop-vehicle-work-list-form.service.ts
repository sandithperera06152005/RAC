import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IWorkshopVehicleWorkList, NewWorkshopVehicleWorkList } from '../workshop-vehicle-work-list.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IWorkshopVehicleWorkList for edit and NewWorkshopVehicleWorkListFormGroupInput for create.
 */
type WorkshopVehicleWorkListFormGroupInput = IWorkshopVehicleWorkList | PartialWithRequiredKeyOf<NewWorkshopVehicleWorkList>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IWorkshopVehicleWorkList | NewWorkshopVehicleWorkList> = Omit<T, 'jobdonedate'> & {
  jobdonedate?: string | null;
};

type WorkshopVehicleWorkListFormRawValue = FormValueOf<IWorkshopVehicleWorkList>;

type NewWorkshopVehicleWorkListFormRawValue = FormValueOf<NewWorkshopVehicleWorkList>;

type WorkshopVehicleWorkListFormDefaults = Pick<NewWorkshopVehicleWorkList, 'id' | 'isjobdone' | 'jobdonedate'>;

type WorkshopVehicleWorkListFormGroupContent = {
  id: FormControl<WorkshopVehicleWorkListFormRawValue['id'] | NewWorkshopVehicleWorkList['id']>;
  vehicleworkid: FormControl<WorkshopVehicleWorkListFormRawValue['vehicleworkid']>;
  lineid: FormControl<WorkshopVehicleWorkListFormRawValue['lineid']>;
  workid: FormControl<WorkshopVehicleWorkListFormRawValue['workid']>;
  workshopwork: FormControl<WorkshopVehicleWorkListFormRawValue['workshopwork']>;
  isjobdone: FormControl<WorkshopVehicleWorkListFormRawValue['isjobdone']>;
  jobdonedate: FormControl<WorkshopVehicleWorkListFormRawValue['jobdonedate']>;
  jobnumber: FormControl<WorkshopVehicleWorkListFormRawValue['jobnumber']>;
  jobvalue: FormControl<WorkshopVehicleWorkListFormRawValue['jobvalue']>;
  estimatevalue: FormControl<WorkshopVehicleWorkListFormRawValue['estimatevalue']>;
};

export type WorkshopVehicleWorkListFormGroup = FormGroup<WorkshopVehicleWorkListFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class WorkshopVehicleWorkListFormService {
  createWorkshopVehicleWorkListFormGroup(
    workshopVehicleWorkList: WorkshopVehicleWorkListFormGroupInput = { id: null },
  ): WorkshopVehicleWorkListFormGroup {
    const workshopVehicleWorkListRawValue = this.convertWorkshopVehicleWorkListToWorkshopVehicleWorkListRawValue({
      ...this.getFormDefaults(),
      ...workshopVehicleWorkList,
    });
    return new FormGroup<WorkshopVehicleWorkListFormGroupContent>({
      id: new FormControl(
        { value: workshopVehicleWorkListRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      vehicleworkid: new FormControl(workshopVehicleWorkListRawValue.vehicleworkid),
      lineid: new FormControl(workshopVehicleWorkListRawValue.lineid),
      workid: new FormControl(workshopVehicleWorkListRawValue.workid),
      workshopwork: new FormControl(workshopVehicleWorkListRawValue.workshopwork),
      isjobdone: new FormControl(workshopVehicleWorkListRawValue.isjobdone),
      jobdonedate: new FormControl(workshopVehicleWorkListRawValue.jobdonedate),
      jobnumber: new FormControl(workshopVehicleWorkListRawValue.jobnumber),
      jobvalue: new FormControl(workshopVehicleWorkListRawValue.jobvalue),
      estimatevalue: new FormControl(workshopVehicleWorkListRawValue.estimatevalue),
    });
  }

  getWorkshopVehicleWorkList(form: WorkshopVehicleWorkListFormGroup): IWorkshopVehicleWorkList | NewWorkshopVehicleWorkList {
    return this.convertWorkshopVehicleWorkListRawValueToWorkshopVehicleWorkList(
      form.getRawValue() as WorkshopVehicleWorkListFormRawValue | NewWorkshopVehicleWorkListFormRawValue,
    );
  }

  resetForm(form: WorkshopVehicleWorkListFormGroup, workshopVehicleWorkList: WorkshopVehicleWorkListFormGroupInput): void {
    const workshopVehicleWorkListRawValue = this.convertWorkshopVehicleWorkListToWorkshopVehicleWorkListRawValue({
      ...this.getFormDefaults(),
      ...workshopVehicleWorkList,
    });
    form.reset(
      {
        ...workshopVehicleWorkListRawValue,
        id: { value: workshopVehicleWorkListRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): WorkshopVehicleWorkListFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      isjobdone: false,
      jobdonedate: currentTime,
    };
  }

  private convertWorkshopVehicleWorkListRawValueToWorkshopVehicleWorkList(
    rawWorkshopVehicleWorkList: WorkshopVehicleWorkListFormRawValue | NewWorkshopVehicleWorkListFormRawValue,
  ): IWorkshopVehicleWorkList | NewWorkshopVehicleWorkList {
    return {
      ...rawWorkshopVehicleWorkList,
      jobdonedate: dayjs(rawWorkshopVehicleWorkList.jobdonedate, DATE_TIME_FORMAT),
    };
  }

  private convertWorkshopVehicleWorkListToWorkshopVehicleWorkListRawValue(
    workshopVehicleWorkList: IWorkshopVehicleWorkList | (Partial<NewWorkshopVehicleWorkList> & WorkshopVehicleWorkListFormDefaults),
  ): WorkshopVehicleWorkListFormRawValue | PartialWithRequiredKeyOf<NewWorkshopVehicleWorkListFormRawValue> {
    return {
      ...workshopVehicleWorkList,
      jobdonedate: workshopVehicleWorkList.jobdonedate ? workshopVehicleWorkList.jobdonedate.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
