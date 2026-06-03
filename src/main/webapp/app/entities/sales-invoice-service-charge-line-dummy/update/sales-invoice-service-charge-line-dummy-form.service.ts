import { Injectable } from '@angular/core';
import { FormArray, FormControl, FormGroup, Validators } from '@angular/forms';

import {
  ISalesInvoiceServiceChargeLineDummy,
  NewSalesInvoiceServiceChargeLineDummy,
} from '../sales-invoice-service-charge-line-dummy.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ISalesInvoiceServiceChargeLineDummy for edit and NewSalesInvoiceServiceChargeLineDummyFormGroupInput for create.
 */
type SalesInvoiceServiceChargeLineDummyFormGroupInput =
  | ISalesInvoiceServiceChargeLineDummy
  | PartialWithRequiredKeyOf<NewSalesInvoiceServiceChargeLineDummy>;

type SalesInvoiceServiceChargeLineDummyFormDefaults = Pick<NewSalesInvoiceServiceChargeLineDummy, 'id' | 'isCustomerService'>;

type SalesInvoiceServiceChargeLineDummyFormGroupContent = {
  id: FormControl<ISalesInvoiceServiceChargeLineDummy['id'] | NewSalesInvoiceServiceChargeLineDummy['id']>;
  invoiceId: FormControl<ISalesInvoiceServiceChargeLineDummy['invoiceId']>;
  lineId: FormControl<ISalesInvoiceServiceChargeLineDummy['lineId']>;
  optionId: FormControl<ISalesInvoiceServiceChargeLineDummy['optionId']>;
  serviceName: FormControl<ISalesInvoiceServiceChargeLineDummy['serviceName']>;
  serviceDescription: FormControl<ISalesInvoiceServiceChargeLineDummy['serviceDescription']>;
  value: FormControl<ISalesInvoiceServiceChargeLineDummy['value']>;
  isCustomerService: FormControl<ISalesInvoiceServiceChargeLineDummy['isCustomerService']>;
};

export type SalesInvoiceServiceChargeLineDummyFormGroup = FormGroup<SalesInvoiceServiceChargeLineDummyFormGroupContent>;
@Injectable({ providedIn: 'root' })
export class SalesInvoiceServiceChargeLineDummyFormService {
  createSalesInvoiceServiceChargeLineDummyFormArray(
    salesInvoiceServiceChargeLineDummies: (ISalesInvoiceServiceChargeLineDummy | Partial<NewSalesInvoiceServiceChargeLineDummy>)[] = [],
  ): FormArray {
    return new FormArray(
      salesInvoiceServiceChargeLineDummies.map(salesInvoiceServiceChargeLineDummy =>
        this.createSalesInvoiceServiceChargeLineDummyFormGroup(salesInvoiceServiceChargeLineDummy),
      ),
    );
  }

  createSalesInvoiceServiceChargeLineDummyFormGroup(
    salesInvoiceServiceChargeLineDummy: ISalesInvoiceServiceChargeLineDummy | Partial<NewSalesInvoiceServiceChargeLineDummy> = { id: null },
  ): FormGroup {
    const salesInvoiceServiceChargeLineDummyRawValue = {
      ...this.getFormDefaults(),
      ...salesInvoiceServiceChargeLineDummy,
    };

    return new FormGroup({
      id: new FormControl(
        { value: salesInvoiceServiceChargeLineDummyRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      invoiceId: new FormControl(salesInvoiceServiceChargeLineDummyRawValue.invoiceId),
      lineId: new FormControl(salesInvoiceServiceChargeLineDummyRawValue.lineId),
      optionId: new FormControl(salesInvoiceServiceChargeLineDummyRawValue.optionId),
      serviceName: new FormControl(salesInvoiceServiceChargeLineDummyRawValue.serviceName),
      serviceDescription: new FormControl(salesInvoiceServiceChargeLineDummyRawValue.serviceDescription),
      value: new FormControl(salesInvoiceServiceChargeLineDummyRawValue.value),
      isCustomerService: new FormControl(salesInvoiceServiceChargeLineDummyRawValue.isCustomerService),
    });
  }

  getSalesInvoiceServiceChargeLineDummies(
    formArray: FormArray,
  ): (ISalesInvoiceServiceChargeLineDummy | NewSalesInvoiceServiceChargeLineDummy)[] {
    return formArray.controls.map(
      form => form.getRawValue() as ISalesInvoiceServiceChargeLineDummy | NewSalesInvoiceServiceChargeLineDummy,
    );
  }

  resetForm(
    formArray: FormArray,
    salesInvoiceServiceChargeLineDummies: (ISalesInvoiceServiceChargeLineDummy | Partial<NewSalesInvoiceServiceChargeLineDummy>)[],
  ): void {
    formArray.clear();
    salesInvoiceServiceChargeLineDummies.forEach(salesInvoiceServiceChargeLineDummy => {
      formArray.push(this.createSalesInvoiceServiceChargeLineDummyFormGroup(salesInvoiceServiceChargeLineDummy));
    });
  }

  private getFormDefaults(): SalesInvoiceServiceChargeLineDummyFormDefaults {
    return {
      id: null,
      isCustomerService: false,
    };
  }
}
