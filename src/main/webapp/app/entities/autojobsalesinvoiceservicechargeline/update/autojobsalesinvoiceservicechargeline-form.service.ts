import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import {
  IAutojobsalesinvoiceservicechargeline,
  NewAutojobsalesinvoiceservicechargeline,
} from '../autojobsalesinvoiceservicechargeline.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAutojobsalesinvoiceservicechargeline for edit and NewAutojobsalesinvoiceservicechargelineFormGroupInput for create.
 */
type AutojobsalesinvoiceservicechargelineFormGroupInput =
  | IAutojobsalesinvoiceservicechargeline
  | PartialWithRequiredKeyOf<NewAutojobsalesinvoiceservicechargeline>;

type AutojobsalesinvoiceservicechargelineFormDefaults = Pick<NewAutojobsalesinvoiceservicechargeline, 'id' | 'iscustomersrvice'>;

type AutojobsalesinvoiceservicechargelineFormGroupContent = {
  id: FormControl<IAutojobsalesinvoiceservicechargeline['id'] | NewAutojobsalesinvoiceservicechargeline['id']>;
  invoiceid: FormControl<IAutojobsalesinvoiceservicechargeline['invoiceid']>;
  lineid: FormControl<IAutojobsalesinvoiceservicechargeline['lineid']>;
  optionid: FormControl<IAutojobsalesinvoiceservicechargeline['optionid']>;
  servicename: FormControl<IAutojobsalesinvoiceservicechargeline['servicename']>;
  servicediscription: FormControl<IAutojobsalesinvoiceservicechargeline['servicediscription']>;
  value: FormControl<IAutojobsalesinvoiceservicechargeline['value']>;
  addedbyid: FormControl<IAutojobsalesinvoiceservicechargeline['addedbyid']>;
  iscustomersrvice: FormControl<IAutojobsalesinvoiceservicechargeline['iscustomersrvice']>;
  discount: FormControl<IAutojobsalesinvoiceservicechargeline['discount']>;
  serviceprice: FormControl<IAutojobsalesinvoiceservicechargeline['serviceprice']>;
};

export type AutojobsalesinvoiceservicechargelineFormGroup = FormGroup<AutojobsalesinvoiceservicechargelineFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AutojobsalesinvoiceservicechargelineFormService {
  createAutojobsalesinvoiceservicechargelineFormGroup(
    autojobsalesinvoiceservicechargeline: AutojobsalesinvoiceservicechargelineFormGroupInput = { id: null },
  ): AutojobsalesinvoiceservicechargelineFormGroup {
    const autojobsalesinvoiceservicechargelineRawValue = {
      ...this.getFormDefaults(),
      ...autojobsalesinvoiceservicechargeline,
    };
    return new FormGroup<AutojobsalesinvoiceservicechargelineFormGroupContent>({
      id: new FormControl(
        { value: autojobsalesinvoiceservicechargelineRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      invoiceid: new FormControl(autojobsalesinvoiceservicechargelineRawValue.invoiceid),
      lineid: new FormControl(autojobsalesinvoiceservicechargelineRawValue.lineid),
      optionid: new FormControl(autojobsalesinvoiceservicechargelineRawValue.optionid),
      servicename: new FormControl(autojobsalesinvoiceservicechargelineRawValue.servicename),
      servicediscription: new FormControl(autojobsalesinvoiceservicechargelineRawValue.servicediscription),
      value: new FormControl(autojobsalesinvoiceservicechargelineRawValue.value),
      addedbyid: new FormControl(autojobsalesinvoiceservicechargelineRawValue.addedbyid),
      iscustomersrvice: new FormControl(autojobsalesinvoiceservicechargelineRawValue.iscustomersrvice),
      discount: new FormControl(autojobsalesinvoiceservicechargelineRawValue.discount),
      serviceprice: new FormControl(autojobsalesinvoiceservicechargelineRawValue.serviceprice),
    });
  }

  getAutojobsalesinvoiceservicechargeline(
    form: AutojobsalesinvoiceservicechargelineFormGroup,
  ): IAutojobsalesinvoiceservicechargeline | NewAutojobsalesinvoiceservicechargeline {
    return form.getRawValue() as IAutojobsalesinvoiceservicechargeline | NewAutojobsalesinvoiceservicechargeline;
  }

  resetForm(
    form: AutojobsalesinvoiceservicechargelineFormGroup,
    autojobsalesinvoiceservicechargeline: AutojobsalesinvoiceservicechargelineFormGroupInput,
  ): void {
    const autojobsalesinvoiceservicechargelineRawValue = { ...this.getFormDefaults(), ...autojobsalesinvoiceservicechargeline };
    form.reset(
      {
        ...autojobsalesinvoiceservicechargelineRawValue,
        id: { value: autojobsalesinvoiceservicechargelineRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): AutojobsalesinvoiceservicechargelineFormDefaults {
    return {
      id: null,
      iscustomersrvice: false,
    };
  }
}
