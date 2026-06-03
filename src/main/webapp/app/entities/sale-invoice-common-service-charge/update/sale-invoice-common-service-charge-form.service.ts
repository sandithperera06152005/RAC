import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators, FormArray } from '@angular/forms';

import { ISaleInvoiceCommonServiceCharge, NewSaleInvoiceCommonServiceCharge } from '../sale-invoice-common-service-charge.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ISaleInvoiceCommonServiceCharge for edit and NewSaleInvoiceCommonServiceChargeFormGroupInput for create.
 */
type SaleInvoiceCommonServiceChargeFormGroupInput =
  | ISaleInvoiceCommonServiceCharge
  | PartialWithRequiredKeyOf<NewSaleInvoiceCommonServiceCharge>;

type SaleInvoiceCommonServiceChargeFormDefaults = Pick<NewSaleInvoiceCommonServiceCharge, 'id'>;

type SaleInvoiceCommonServiceChargeFormGroupContent = {
  id: FormControl<ISaleInvoiceCommonServiceCharge['id'] | NewSaleInvoiceCommonServiceCharge['id']>;
  invoiceId: FormControl<ISaleInvoiceCommonServiceCharge['invoiceId']>;
  lineId: FormControl<ISaleInvoiceCommonServiceCharge['lineId']>;
  optionId: FormControl<ISaleInvoiceCommonServiceCharge['optionId']>;
  mainId: FormControl<ISaleInvoiceCommonServiceCharge['mainId']>;
  code: FormControl<ISaleInvoiceCommonServiceCharge['code']>;
  name: FormControl<ISaleInvoiceCommonServiceCharge['name']>;
  description: FormControl<ISaleInvoiceCommonServiceCharge['description']>;
  value: FormControl<ISaleInvoiceCommonServiceCharge['value']>;
  discount: FormControl<ISaleInvoiceCommonServiceCharge['discount']>;
  servicePrice: FormControl<ISaleInvoiceCommonServiceCharge['servicePrice']>;
};

export type SaleInvoiceCommonServiceChargeFormGroup = FormGroup<SaleInvoiceCommonServiceChargeFormGroupContent>;
@Injectable({ providedIn: 'root' })
export class SaleInvoiceCommonServiceChargeFormService {
  // Creates a FormArray of SaleInvoiceCommonServiceChargeFormGroups
  createSaleInvoiceCommonServiceChargeFormArray(
    saleInvoiceCommonServiceChargeArray: SaleInvoiceCommonServiceChargeFormGroupInput[] = [{ id: null }],
  ): FormArray {
    const formArray = new FormArray(
      saleInvoiceCommonServiceChargeArray.map(saleInvoiceCommonServiceCharge =>
        this.createSaleInvoiceCommonServiceChargeFormGroup(saleInvoiceCommonServiceCharge),
      ),
    );
    return formArray;
  }

  // Creates a FormGroup for SaleInvoiceCommonServiceChargeFormGroup
  createSaleInvoiceCommonServiceChargeFormGroup(
    saleInvoiceCommonServiceCharge: SaleInvoiceCommonServiceChargeFormGroupInput = { id: null },
  ): SaleInvoiceCommonServiceChargeFormGroup {
    const saleInvoiceCommonServiceChargeRawValue = {
      ...this.getFormDefaults(),
      ...saleInvoiceCommonServiceCharge,
    };
    return new FormGroup<SaleInvoiceCommonServiceChargeFormGroupContent>({
      id: new FormControl(
        { value: saleInvoiceCommonServiceChargeRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      invoiceId: new FormControl(saleInvoiceCommonServiceChargeRawValue.invoiceId),
      lineId: new FormControl(saleInvoiceCommonServiceChargeRawValue.lineId),
      optionId: new FormControl(saleInvoiceCommonServiceChargeRawValue.optionId),
      mainId: new FormControl(saleInvoiceCommonServiceChargeRawValue.mainId),
      code: new FormControl(saleInvoiceCommonServiceChargeRawValue.code),
      name: new FormControl(saleInvoiceCommonServiceChargeRawValue.name),
      description: new FormControl(saleInvoiceCommonServiceChargeRawValue.description),
      value: new FormControl(saleInvoiceCommonServiceChargeRawValue.value),
      discount: new FormControl(saleInvoiceCommonServiceChargeRawValue.discount),
      servicePrice: new FormControl(saleInvoiceCommonServiceChargeRawValue.servicePrice),
    });
  }

  // Gets the SaleInvoiceCommonServiceCharge data from the FormArray
  getSaleInvoiceCommonServiceCharge(form: FormArray): (ISaleInvoiceCommonServiceCharge | NewSaleInvoiceCommonServiceCharge)[] {
    return form.controls.map(control => control.getRawValue() as ISaleInvoiceCommonServiceCharge | NewSaleInvoiceCommonServiceCharge);
  }

  // Resets the FormArray based on a new SaleInvoiceCommonServiceCharge data array
  resetForm(form: FormArray, saleInvoiceCommonServiceChargeArray: SaleInvoiceCommonServiceChargeFormGroupInput[]): void {
    const saleInvoiceCommonServiceChargeRawValue = saleInvoiceCommonServiceChargeArray.map(saleInvoiceCommonServiceCharge => ({
      ...this.getFormDefaults(),
      ...saleInvoiceCommonServiceCharge,
    }));
    form.clear();
    saleInvoiceCommonServiceChargeRawValue.forEach(saleInvoiceCommonServiceCharge => {
      form.push(this.createSaleInvoiceCommonServiceChargeFormGroup(saleInvoiceCommonServiceCharge));
    });
  }

  // Helper function for default values
  private getFormDefaults(): SaleInvoiceCommonServiceChargeFormDefaults {
    return {
      id: null,
    };
  }
}
