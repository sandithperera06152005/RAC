import { Injectable } from '@angular/core';
import { FormArray, FormControl, FormGroup, Validators } from '@angular/forms';

import {
  ISaleInvoiceCommonServiceChargeDummy,
  NewSaleInvoiceCommonServiceChargeDummy,
} from '../sale-invoice-common-service-charge-dummy.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ISaleInvoiceCommonServiceChargeDummy for edit and NewSaleInvoiceCommonServiceChargeDummyFormGroupInput for create.
 */
type SaleInvoiceCommonServiceChargeDummyFormGroupInput =
  | ISaleInvoiceCommonServiceChargeDummy
  | PartialWithRequiredKeyOf<NewSaleInvoiceCommonServiceChargeDummy>;

type SaleInvoiceCommonServiceChargeDummyFormDefaults = Pick<NewSaleInvoiceCommonServiceChargeDummy, 'id'>;

type SaleInvoiceCommonServiceChargeDummyFormGroupContent = {
  id: FormControl<ISaleInvoiceCommonServiceChargeDummy['id'] | NewSaleInvoiceCommonServiceChargeDummy['id']>;
  invoiceid: FormControl<ISaleInvoiceCommonServiceChargeDummy['invoiceid']>;
  lineid: FormControl<ISaleInvoiceCommonServiceChargeDummy['lineid']>;
  optionid: FormControl<ISaleInvoiceCommonServiceChargeDummy['optionid']>;
  mainid: FormControl<ISaleInvoiceCommonServiceChargeDummy['mainid']>;
  code: FormControl<ISaleInvoiceCommonServiceChargeDummy['code']>;
  name: FormControl<ISaleInvoiceCommonServiceChargeDummy['name']>;
  description: FormControl<ISaleInvoiceCommonServiceChargeDummy['description']>;
  value: FormControl<ISaleInvoiceCommonServiceChargeDummy['value']>;
};

export type SaleInvoiceCommonServiceChargeDummyFormGroup = FormGroup<SaleInvoiceCommonServiceChargeDummyFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class SaleInvoiceCommonServiceChargeDummyFormService {
  createSaleInvoiceCommonServiceChargeDummyFormArray(
    saleInvoiceCommonServiceChargeDummyArray: SaleInvoiceCommonServiceChargeDummyFormGroupInput[] = [{ id: null }],
  ): FormArray {
    const formArray = new FormArray(
      saleInvoiceCommonServiceChargeDummyArray.map(saleInvoiceCommonServiceChargeDummy =>
        this.createSaleInvoiceCommonServiceChargeDummyFormGroup(saleInvoiceCommonServiceChargeDummy),
      ),
    );
    return formArray;
  }

  createSaleInvoiceCommonServiceChargeDummyFormGroup(
    saleInvoiceCommonServiceChargeDummy: SaleInvoiceCommonServiceChargeDummyFormGroupInput = { id: null },
  ): SaleInvoiceCommonServiceChargeDummyFormGroup {
    const saleInvoiceCommonServiceChargeDummyRawValue = {
      ...this.getFormDefaults(),
      ...saleInvoiceCommonServiceChargeDummy,
    };
    return new FormGroup<SaleInvoiceCommonServiceChargeDummyFormGroupContent>({
      id: new FormControl(
        { value: saleInvoiceCommonServiceChargeDummyRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      invoiceid: new FormControl(saleInvoiceCommonServiceChargeDummyRawValue.invoiceid),
      lineid: new FormControl(saleInvoiceCommonServiceChargeDummyRawValue.lineid),
      optionid: new FormControl(saleInvoiceCommonServiceChargeDummyRawValue.optionid),
      mainid: new FormControl(saleInvoiceCommonServiceChargeDummyRawValue.mainid),
      code: new FormControl(saleInvoiceCommonServiceChargeDummyRawValue.code),
      name: new FormControl(saleInvoiceCommonServiceChargeDummyRawValue.name),
      description: new FormControl(saleInvoiceCommonServiceChargeDummyRawValue.description),
      value: new FormControl(saleInvoiceCommonServiceChargeDummyRawValue.value),
    });
  }

  getSaleInvoiceCommonServiceChargeDummy(
    form: FormArray,
  ): (ISaleInvoiceCommonServiceChargeDummy | NewSaleInvoiceCommonServiceChargeDummy)[] {
    return form.controls.map(
      control => control.getRawValue() as ISaleInvoiceCommonServiceChargeDummy | NewSaleInvoiceCommonServiceChargeDummy,
    );
  }

  resetForm(form: FormArray, saleInvoiceCommonServiceChargeDummyArray: SaleInvoiceCommonServiceChargeDummyFormGroupInput[]): void {
    const saleInvoiceCommonServiceChargeDummyRawValue = saleInvoiceCommonServiceChargeDummyArray.map(
      saleInvoiceCommonServiceChargeDummy => ({
        ...this.getFormDefaults(),
        ...saleInvoiceCommonServiceChargeDummy,
      }),
    );
    form.clear();
    saleInvoiceCommonServiceChargeDummyRawValue.forEach(saleInvoiceCommonServiceChargeDummy => {
      form.push(this.createSaleInvoiceCommonServiceChargeDummyFormGroup(saleInvoiceCommonServiceChargeDummy));
    });
  }

  private getFormDefaults(): SaleInvoiceCommonServiceChargeDummyFormDefaults {
    return {
      id: null,
    };
  }
}
