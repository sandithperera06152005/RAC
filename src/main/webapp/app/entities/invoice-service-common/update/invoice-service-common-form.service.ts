import { Injectable } from '@angular/core';
import { FormArray, FormControl, FormGroup, Validators } from '@angular/forms';

import { IInvoiceServiceCommon, NewInvoiceServiceCommon } from '../invoice-service-common.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IInvoiceServiceCommon for edit and NewInvoiceServiceCommonFormGroupInput for create.
 */
type InvoiceServiceCommonFormGroupInput = IInvoiceServiceCommon | PartialWithRequiredKeyOf<NewInvoiceServiceCommon>;

type InvoiceServiceCommonFormDefaults = Pick<NewInvoiceServiceCommon, 'id'>;

type InvoiceServiceCommonFormGroupContent = {
  id: FormControl<IInvoiceServiceCommon['id'] | NewInvoiceServiceCommon['id']>;
  invoiceId: FormControl<IInvoiceServiceCommon['invoiceId']>;
  lineId: FormControl<IInvoiceServiceCommon['lineId']>;
  optionId: FormControl<IInvoiceServiceCommon['optionId']>;
  mainId: FormControl<IInvoiceServiceCommon['mainId']>;
  code: FormControl<IInvoiceServiceCommon['code']>;
  name: FormControl<IInvoiceServiceCommon['name']>;
  description: FormControl<IInvoiceServiceCommon['description']>;
  value: FormControl<IInvoiceServiceCommon['value']>;
  addedById: FormControl<IInvoiceServiceCommon['addedById']>;
  discount: FormControl<IInvoiceServiceCommon['discount']>;
  servicePrice: FormControl<IInvoiceServiceCommon['servicePrice']>;
};

export type InvoiceServiceCommonFormGroup = FormGroup<InvoiceServiceCommonFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class InvoiceServiceCommonFormService {
  createInvoiceServiceCommonFormArray(invoiceServiceCommonArray: InvoiceServiceCommonFormGroupInput[] = [{ id: null }]): FormArray {
    const formArray = new FormArray(
      invoiceServiceCommonArray.map(serviceCommon => this.createInvoiceServiceCommonFormGroup(serviceCommon)),
    );
    return formArray;
  }

  createInvoiceServiceCommonFormGroup(
    invoiceServiceCommon: InvoiceServiceCommonFormGroupInput = { id: null },
  ): InvoiceServiceCommonFormGroup {
    const serviceCommonRawValue = {
      ...this.getFormDefaults(),
      ...invoiceServiceCommon,
    };
    return new FormGroup<InvoiceServiceCommonFormGroupContent>({
      id: new FormControl(
        { value: serviceCommonRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      invoiceId: new FormControl(serviceCommonRawValue.invoiceId),
      lineId: new FormControl(serviceCommonRawValue.lineId),
      optionId: new FormControl(serviceCommonRawValue.optionId),
      mainId: new FormControl(serviceCommonRawValue.mainId),
      code: new FormControl(serviceCommonRawValue.code),
      name: new FormControl(serviceCommonRawValue.name),
      description: new FormControl(serviceCommonRawValue.description),
      value: new FormControl(serviceCommonRawValue.value),
      addedById: new FormControl(serviceCommonRawValue.addedById),
      discount: new FormControl(serviceCommonRawValue.discount),
      servicePrice: new FormControl(serviceCommonRawValue.servicePrice),
    });
  }

  getInvoiceServiceCommons(form: FormArray): (IInvoiceServiceCommon | NewInvoiceServiceCommon)[] {
    return form.controls.map(control => control.getRawValue() as IInvoiceServiceCommon | NewInvoiceServiceCommon);
  }

  resetForm(form: FormArray, invoiceServiceCommons: InvoiceServiceCommonFormGroupInput[]): void {
    const serviceCommonRawValues = invoiceServiceCommons.map(serviceCommon => ({
      ...this.getFormDefaults(),
      ...serviceCommon,
    }));
    form.clear();
    serviceCommonRawValues.forEach(rawValue => {
      form.push(this.createInvoiceServiceCommonFormGroup(rawValue));
    });
  }

  private getFormDefaults(): InvoiceServiceCommonFormDefaults {
    return {
      id: null,
    };
  }
}
