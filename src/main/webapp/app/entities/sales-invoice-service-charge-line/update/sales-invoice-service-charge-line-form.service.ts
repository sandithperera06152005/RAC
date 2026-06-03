import { Injectable } from '@angular/core';
import { FormArray, FormControl, FormGroup, Validators } from '@angular/forms';

import { ISalesInvoiceServiceChargeLine, NewSalesInvoiceServiceChargeLine } from '../sales-invoice-service-charge-line.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ISalesInvoiceServiceChargeLine for edit and NewSalesInvoiceServiceChargeLineFormGroupInput for create.
 */
type SalesInvoiceServiceChargeLineFormGroupInput =
  | ISalesInvoiceServiceChargeLine
  | PartialWithRequiredKeyOf<NewSalesInvoiceServiceChargeLine>;

type SalesInvoiceServiceChargeLineFormDefaults = Pick<NewSalesInvoiceServiceChargeLine, 'id' | 'isCustomerService'>;

type SalesInvoiceServiceChargeLineFormGroupContent = {
  id: FormControl<ISalesInvoiceServiceChargeLine['id'] | NewSalesInvoiceServiceChargeLine['id']>;
  invoiceId: FormControl<ISalesInvoiceServiceChargeLine['invoiceId']>;
  lineId: FormControl<ISalesInvoiceServiceChargeLine['lineId']>;
  optionId: FormControl<ISalesInvoiceServiceChargeLine['optionId']>;
  serviceName: FormControl<ISalesInvoiceServiceChargeLine['serviceName']>;
  serviceDescription: FormControl<ISalesInvoiceServiceChargeLine['serviceDescription']>;
  value: FormControl<ISalesInvoiceServiceChargeLine['value']>;
  isCustomerService: FormControl<ISalesInvoiceServiceChargeLine['isCustomerService']>;
  discount: FormControl<ISalesInvoiceServiceChargeLine['discount']>;
  servicePrice: FormControl<ISalesInvoiceServiceChargeLine['servicePrice']>;
};

export type SalesInvoiceServiceChargeLineFormGroup = FormGroup<SalesInvoiceServiceChargeLineFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class SalesInvoiceServiceChargeLineFormService {
  /**
   * Creates a new FormGroup for Sales Invoice Service Charge Line
   * @param salesInvoiceServiceChargeLine - Sales Invoice Service Charge Line input (can be either ISalesInvoiceServiceChargeLine or NewSalesInvoiceServiceChargeLine)
   */
  createSalesInvoiceServiceChargeLineFormGroup(
    salesInvoiceServiceChargeLine: SalesInvoiceServiceChargeLineFormGroupInput = { id: null },
  ): SalesInvoiceServiceChargeLineFormGroup {
    const salesInvoiceServiceChargeLineRawValue = {
      ...this.getFormDefaults(),
      ...salesInvoiceServiceChargeLine,
    };

    return new FormGroup<SalesInvoiceServiceChargeLineFormGroupContent>({
      id: new FormControl(
        { value: salesInvoiceServiceChargeLineRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      invoiceId: new FormControl(salesInvoiceServiceChargeLineRawValue.invoiceId),
      lineId: new FormControl(salesInvoiceServiceChargeLineRawValue.lineId),
      optionId: new FormControl(salesInvoiceServiceChargeLineRawValue.optionId),
      serviceName: new FormControl(salesInvoiceServiceChargeLineRawValue.serviceName),
      serviceDescription: new FormControl(salesInvoiceServiceChargeLineRawValue.serviceDescription),
      value: new FormControl(salesInvoiceServiceChargeLineRawValue.value),
      isCustomerService: new FormControl(salesInvoiceServiceChargeLineRawValue.isCustomerService),
      discount: new FormControl(salesInvoiceServiceChargeLineRawValue.discount),
      servicePrice: new FormControl(salesInvoiceServiceChargeLineRawValue.servicePrice),
    });
  }

  /**
   * Retrieves the raw value of the form as an object
   * @param form - The FormGroup to retrieve values from
   */
  getSalesInvoiceServiceChargeLine(
    form: SalesInvoiceServiceChargeLineFormGroup,
  ): ISalesInvoiceServiceChargeLine | NewSalesInvoiceServiceChargeLine {
    return form.getRawValue() as ISalesInvoiceServiceChargeLine | NewSalesInvoiceServiceChargeLine;
  }

  /**
   * Resets the form with new values for editing or creating
   * @param form - The FormGroup to reset
   * @param salesInvoiceServiceChargeLine - New or edited sales invoice service charge line data
   */
  resetForm(
    form: SalesInvoiceServiceChargeLineFormGroup,
    salesInvoiceServiceChargeLine: SalesInvoiceServiceChargeLineFormGroupInput,
  ): void {
    const salesInvoiceServiceChargeLineRawValue = { ...this.getFormDefaults(), ...salesInvoiceServiceChargeLine };
    form.reset({
      ...salesInvoiceServiceChargeLineRawValue,
      id: { value: salesInvoiceServiceChargeLineRawValue.id, disabled: true },
    } as any);
  }

  /**
   * Returns the default values for the form
   */
  private getFormDefaults(): SalesInvoiceServiceChargeLineFormDefaults {
    return {
      id: null,
      isCustomerService: false,
    };
  }

  /**
   * Creates a FormArray for multiple Sales Invoice Service Charge Line FormGroups
   * @param salesInvoiceServiceChargeLines - An array of sales invoice service charge lines to initialize the form
   */
  createSalesInvoiceServiceChargeLineFormArray(
    salesInvoiceServiceChargeLines: (ISalesInvoiceServiceChargeLine | Partial<NewSalesInvoiceServiceChargeLine>)[] = [],
  ): FormArray {
    return new FormArray(
      salesInvoiceServiceChargeLines.map(salesInvoiceServiceChargeLine =>
        this.createSalesInvoiceServiceChargeLineFormGroup({ id: null, ...salesInvoiceServiceChargeLine }),
      ),
    );
  }

  /**
   * Retrieves an array of form values (raw values) from a FormArray
   * @param formArray - FormArray to extract the raw values from
   */
  getSalesInvoiceServiceChargeLines(formArray: FormArray): (ISalesInvoiceServiceChargeLine | NewSalesInvoiceServiceChargeLine)[] {
    return formArray.controls.map(form => form.getRawValue() as ISalesInvoiceServiceChargeLine | NewSalesInvoiceServiceChargeLine);
  }

  /**
   * Resets the entire FormArray with new data
   * @param formArray - The FormArray to reset
   * @param salesInvoiceServiceChargeLines - Array of sales invoice service charge lines
   */
  resetFormArray(
    formArray: FormArray,
    salesInvoiceServiceChargeLines: (ISalesInvoiceServiceChargeLine | Partial<NewSalesInvoiceServiceChargeLine>)[],
  ): void {
    formArray.clear();
    salesInvoiceServiceChargeLines.forEach(salesInvoiceServiceChargeLine => {
      formArray.push(this.createSalesInvoiceServiceChargeLineFormGroup({ id: null, ...salesInvoiceServiceChargeLine }));
    });
  }
}
