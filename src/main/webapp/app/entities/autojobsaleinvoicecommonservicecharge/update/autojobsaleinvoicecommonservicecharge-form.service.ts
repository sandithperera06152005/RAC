import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import {
  IAutojobsaleinvoicecommonservicecharge,
  NewAutojobsaleinvoicecommonservicecharge,
} from '../autojobsaleinvoicecommonservicecharge.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAutojobsaleinvoicecommonservicecharge for edit and NewAutojobsaleinvoicecommonservicechargeFormGroupInput for create.
 */
type AutojobsaleinvoicecommonservicechargeFormGroupInput =
  | IAutojobsaleinvoicecommonservicecharge
  | PartialWithRequiredKeyOf<NewAutojobsaleinvoicecommonservicecharge>;

type AutojobsaleinvoicecommonservicechargeFormDefaults = Pick<NewAutojobsaleinvoicecommonservicecharge, 'id'>;

type AutojobsaleinvoicecommonservicechargeFormGroupContent = {
  id: FormControl<IAutojobsaleinvoicecommonservicecharge['id'] | NewAutojobsaleinvoicecommonservicecharge['id']>;
  invoiceid: FormControl<IAutojobsaleinvoicecommonservicecharge['invoiceid']>;
  lineid: FormControl<IAutojobsaleinvoicecommonservicecharge['lineid']>;
  optionid: FormControl<IAutojobsaleinvoicecommonservicecharge['optionid']>;
  mainid: FormControl<IAutojobsaleinvoicecommonservicecharge['mainid']>;
  code: FormControl<IAutojobsaleinvoicecommonservicecharge['code']>;
  name: FormControl<IAutojobsaleinvoicecommonservicecharge['name']>;
  description: FormControl<IAutojobsaleinvoicecommonservicecharge['description']>;
  value: FormControl<IAutojobsaleinvoicecommonservicecharge['value']>;
  addedbyid: FormControl<IAutojobsaleinvoicecommonservicecharge['addedbyid']>;
  discount: FormControl<IAutojobsaleinvoicecommonservicecharge['discount']>;
  serviceprice: FormControl<IAutojobsaleinvoicecommonservicecharge['serviceprice']>;
};

export type AutojobsaleinvoicecommonservicechargeFormGroup = FormGroup<AutojobsaleinvoicecommonservicechargeFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AutojobsaleinvoicecommonservicechargeFormService {
  createAutojobsaleinvoicecommonservicechargeFormGroup(
    autojobsaleinvoicecommonservicecharge: AutojobsaleinvoicecommonservicechargeFormGroupInput = { id: null },
  ): AutojobsaleinvoicecommonservicechargeFormGroup {
    const autojobsaleinvoicecommonservicechargeRawValue = {
      ...this.getFormDefaults(),
      ...autojobsaleinvoicecommonservicecharge,
    };
    return new FormGroup<AutojobsaleinvoicecommonservicechargeFormGroupContent>({
      id: new FormControl(
        { value: autojobsaleinvoicecommonservicechargeRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      invoiceid: new FormControl(autojobsaleinvoicecommonservicechargeRawValue.invoiceid),
      lineid: new FormControl(autojobsaleinvoicecommonservicechargeRawValue.lineid),
      optionid: new FormControl(autojobsaleinvoicecommonservicechargeRawValue.optionid),
      mainid: new FormControl(autojobsaleinvoicecommonservicechargeRawValue.mainid),
      code: new FormControl(autojobsaleinvoicecommonservicechargeRawValue.code),
      name: new FormControl(autojobsaleinvoicecommonservicechargeRawValue.name),
      description: new FormControl(autojobsaleinvoicecommonservicechargeRawValue.description),
      value: new FormControl(autojobsaleinvoicecommonservicechargeRawValue.value),
      addedbyid: new FormControl(autojobsaleinvoicecommonservicechargeRawValue.addedbyid),
      discount: new FormControl(autojobsaleinvoicecommonservicechargeRawValue.discount),
      serviceprice: new FormControl(autojobsaleinvoicecommonservicechargeRawValue.serviceprice),
    });
  }

  getAutojobsaleinvoicecommonservicecharge(
    form: AutojobsaleinvoicecommonservicechargeFormGroup,
  ): IAutojobsaleinvoicecommonservicecharge | NewAutojobsaleinvoicecommonservicecharge {
    return form.getRawValue() as IAutojobsaleinvoicecommonservicecharge | NewAutojobsaleinvoicecommonservicecharge;
  }

  resetForm(
    form: AutojobsaleinvoicecommonservicechargeFormGroup,
    autojobsaleinvoicecommonservicecharge: AutojobsaleinvoicecommonservicechargeFormGroupInput,
  ): void {
    const autojobsaleinvoicecommonservicechargeRawValue = { ...this.getFormDefaults(), ...autojobsaleinvoicecommonservicecharge };
    form.reset(
      {
        ...autojobsaleinvoicecommonservicechargeRawValue,
        id: { value: autojobsaleinvoicecommonservicechargeRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): AutojobsaleinvoicecommonservicechargeFormDefaults {
    return {
      id: null,
    };
  }
}
