import { Injectable } from '@angular/core';
import { FormArray, FormControl, FormGroup, Validators } from '@angular/forms';
import dayjs from 'dayjs/esm';
import { IInvoiceServiceCharge, NewInvoiceServiceCharge } from '../invoice-service-charge.model';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IInvoiceServiceCharge for edit and NewInvoiceServiceChargeFormGroupInput for create.
 */
type InvoiceServiceChargeFormGroupInput = IInvoiceServiceCharge | PartialWithRequiredKeyOf<NewInvoiceServiceCharge>;

type InvoiceServiceChargeFormDefaults = Pick<NewInvoiceServiceCharge, 'id' | 'isCustomerSrvice'>;

type InvoiceServiceChargeFormGroupContent = {
  id: FormControl<IInvoiceServiceCharge['id'] | NewInvoiceServiceCharge['id']>;
  invoiceId: FormControl<IInvoiceServiceCharge['invoiceId']>;
  lineId: FormControl<IInvoiceServiceCharge['lineId']>;
  optionId: FormControl<IInvoiceServiceCharge['optionId']>;
  serviceName: FormControl<IInvoiceServiceCharge['serviceName']>;
  serviceDiscription: FormControl<IInvoiceServiceCharge['serviceDiscription']>;
  value: FormControl<IInvoiceServiceCharge['value']>;
  addedById: FormControl<IInvoiceServiceCharge['addedById']>;
  isCustomerSrvice: FormControl<IInvoiceServiceCharge['isCustomerSrvice']>;
  discount: FormControl<IInvoiceServiceCharge['discount']>;
  servicePrice: FormControl<IInvoiceServiceCharge['servicePrice']>;
};

export type InvoiceServiceChargeFormGroup = FormGroup<InvoiceServiceChargeFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class InvoiceServiceChargeFormService {
  createInvoiceServiceChargeFormArray(invoiceServiceChargeArray: InvoiceServiceChargeFormGroupInput[] = [{ id: null }]): FormArray {
    const formArray = new FormArray(
      invoiceServiceChargeArray.map(serviceCharge => this.createInvoiceServiceChargeFormGroup(serviceCharge)),
    );
    return formArray;
  }

  createInvoiceServiceChargeFormGroup(
    invoiceServiceCharge: InvoiceServiceChargeFormGroupInput = { id: null },
  ): InvoiceServiceChargeFormGroup {
    const serviceChargeRawValue = {
      ...this.getFormDefaults(),
      ...invoiceServiceCharge,
    };
    return new FormGroup<InvoiceServiceChargeFormGroupContent>({
      id: new FormControl(
        { value: serviceChargeRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      invoiceId: new FormControl(serviceChargeRawValue.invoiceId),
      lineId: new FormControl(serviceChargeRawValue.lineId),
      optionId: new FormControl(serviceChargeRawValue.optionId),
      serviceName: new FormControl(serviceChargeRawValue.serviceName),
      serviceDiscription: new FormControl(serviceChargeRawValue.serviceDiscription),
      value: new FormControl(serviceChargeRawValue.value),
      addedById: new FormControl(serviceChargeRawValue.addedById),
      isCustomerSrvice: new FormControl(serviceChargeRawValue.isCustomerSrvice),
      discount: new FormControl(serviceChargeRawValue.discount),
      servicePrice: new FormControl(serviceChargeRawValue.servicePrice),
    });
  }

  getInvoiceServiceCharges(form: FormArray): (IInvoiceServiceCharge | NewInvoiceServiceCharge)[] {
    return form.controls.map(control => control.getRawValue() as IInvoiceServiceCharge | NewInvoiceServiceCharge);
  }

  resetForm(form: FormArray, invoiceServiceCharges: InvoiceServiceChargeFormGroupInput[]): void {
    const serviceChargeRawValues = invoiceServiceCharges.map(serviceCharge => ({
      ...this.getFormDefaults(),
      ...serviceCharge,
    }));
    form.clear();
    serviceChargeRawValues.forEach(rawValue => {
      form.push(this.createInvoiceServiceChargeFormGroup(rawValue));
    });
  }

  private getFormDefaults(): InvoiceServiceChargeFormDefaults {
    return {
      id: null,
      isCustomerSrvice: false,
    };
  }
}
