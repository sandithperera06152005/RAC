import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ISalesInvoiceDummy, NewSalesInvoiceDummy } from '../sales-invoice-dummy.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ISalesInvoiceDummy for edit and NewSalesInvoiceDummyFormGroupInput for create.
 */
type SalesInvoiceDummyFormGroupInput = ISalesInvoiceDummy | PartialWithRequiredKeyOf<NewSalesInvoiceDummy>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ISalesInvoiceDummy | NewSalesInvoiceDummy> = Omit<
  T,
  'invoiceDate' | 'createdDate' | 'deliveryDate' | 'lmd' | 'commissionPaidDate'
> & {
  invoiceDate?: string | null;
  createdDate?: string | null;
  deliveryDate?: string | null;
  lmd?: string | null;
  commissionPaidDate?: string | null;
};

type SalesInvoiceDummyFormRawValue = FormValueOf<ISalesInvoiceDummy>;

type NewSalesInvoiceDummyFormRawValue = FormValueOf<NewSalesInvoiceDummy>;

type SalesInvoiceDummyFormDefaults = Pick<
  NewSalesInvoiceDummy,
  'id' | 'invoiceDate' | 'createdDate' | 'deliveryDate' | 'lmd' | 'isActive' | 'commissionPaidDate'
>;

type SalesInvoiceDummyFormGroupContent = {
  id: FormControl<SalesInvoiceDummyFormRawValue['id'] | NewSalesInvoiceDummy['id']>;
  originalInvoiceId: FormControl<SalesInvoiceDummyFormRawValue['originalInvoiceId']>;
  code: FormControl<SalesInvoiceDummyFormRawValue['code']>;
  invoiceDate: FormControl<SalesInvoiceDummyFormRawValue['invoiceDate']>;
  createdDate: FormControl<SalesInvoiceDummyFormRawValue['createdDate']>;
  quoteID: FormControl<SalesInvoiceDummyFormRawValue['quoteID']>;
  orderID: FormControl<SalesInvoiceDummyFormRawValue['orderID']>;
  deliveryDate: FormControl<SalesInvoiceDummyFormRawValue['deliveryDate']>;
  salesRepID: FormControl<SalesInvoiceDummyFormRawValue['salesRepID']>;
  salesRepName: FormControl<SalesInvoiceDummyFormRawValue['salesRepName']>;
  deliverFrom: FormControl<SalesInvoiceDummyFormRawValue['deliverFrom']>;
  customerID: FormControl<SalesInvoiceDummyFormRawValue['customerID']>;
  customerName: FormControl<SalesInvoiceDummyFormRawValue['customerName']>;
  customerAddress: FormControl<SalesInvoiceDummyFormRawValue['customerAddress']>;
  deliveryAddress: FormControl<SalesInvoiceDummyFormRawValue['deliveryAddress']>;
  subTotal: FormControl<SalesInvoiceDummyFormRawValue['subTotal']>;
  totalTax: FormControl<SalesInvoiceDummyFormRawValue['totalTax']>;
  totalDiscount: FormControl<SalesInvoiceDummyFormRawValue['totalDiscount']>;
  netTotal: FormControl<SalesInvoiceDummyFormRawValue['netTotal']>;
  message: FormControl<SalesInvoiceDummyFormRawValue['message']>;
  lmu: FormControl<SalesInvoiceDummyFormRawValue['lmu']>;
  lmd: FormControl<SalesInvoiceDummyFormRawValue['lmd']>;
  paidAmount: FormControl<SalesInvoiceDummyFormRawValue['paidAmount']>;
  amountOwing: FormControl<SalesInvoiceDummyFormRawValue['amountOwing']>;
  isActive: FormControl<SalesInvoiceDummyFormRawValue['isActive']>;
  locationID: FormControl<SalesInvoiceDummyFormRawValue['locationID']>;
  locationCode: FormControl<SalesInvoiceDummyFormRawValue['locationCode']>;
  referenceCode: FormControl<SalesInvoiceDummyFormRawValue['referenceCode']>;
  createdById: FormControl<SalesInvoiceDummyFormRawValue['createdById']>;
  createdByName: FormControl<SalesInvoiceDummyFormRawValue['createdByName']>;
  autoCareCharges: FormControl<SalesInvoiceDummyFormRawValue['autoCareCharges']>;
  autoCareJobId: FormControl<SalesInvoiceDummyFormRawValue['autoCareJobId']>;
  vehicleNo: FormControl<SalesInvoiceDummyFormRawValue['vehicleNo']>;
  nbtAmount: FormControl<SalesInvoiceDummyFormRawValue['nbtAmount']>;
  vatAmount: FormControl<SalesInvoiceDummyFormRawValue['vatAmount']>;
  dummyCommission: FormControl<SalesInvoiceDummyFormRawValue['dummyCommission']>;
  commissionPaidDate: FormControl<SalesInvoiceDummyFormRawValue['commissionPaidDate']>;
  paidCommission: FormControl<SalesInvoiceDummyFormRawValue['paidCommission']>;
  paidBy: FormControl<SalesInvoiceDummyFormRawValue['paidBy']>;
  originalInvoiceCode: FormControl<SalesInvoiceDummyFormRawValue['originalInvoiceCode']>;
};

export type SalesInvoiceDummyFormGroup = FormGroup<SalesInvoiceDummyFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class SalesInvoiceDummyFormService {
  createSalesInvoiceDummyFormGroup(salesInvoiceDummy: SalesInvoiceDummyFormGroupInput = { id: null }): SalesInvoiceDummyFormGroup {
    const salesInvoiceDummyRawValue = this.convertSalesInvoiceDummyToSalesInvoiceDummyRawValue({
      ...this.getFormDefaults(),
      ...salesInvoiceDummy,
    });
    return new FormGroup<SalesInvoiceDummyFormGroupContent>({
      id: new FormControl(
        { value: salesInvoiceDummyRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      originalInvoiceId: new FormControl(salesInvoiceDummyRawValue.originalInvoiceId),
      code: new FormControl(salesInvoiceDummyRawValue.code),
      invoiceDate: new FormControl(salesInvoiceDummyRawValue.invoiceDate),
      createdDate: new FormControl(salesInvoiceDummyRawValue.createdDate),
      quoteID: new FormControl(salesInvoiceDummyRawValue.quoteID),
      orderID: new FormControl(salesInvoiceDummyRawValue.orderID),
      deliveryDate: new FormControl(salesInvoiceDummyRawValue.deliveryDate),
      salesRepID: new FormControl(salesInvoiceDummyRawValue.salesRepID),
      salesRepName: new FormControl(salesInvoiceDummyRawValue.salesRepName),
      deliverFrom: new FormControl(salesInvoiceDummyRawValue.deliverFrom),
      customerID: new FormControl(salesInvoiceDummyRawValue.customerID),
      customerName: new FormControl(salesInvoiceDummyRawValue.customerName),
      customerAddress: new FormControl(salesInvoiceDummyRawValue.customerAddress),
      deliveryAddress: new FormControl(salesInvoiceDummyRawValue.deliveryAddress),
      subTotal: new FormControl(salesInvoiceDummyRawValue.subTotal),
      totalTax: new FormControl(salesInvoiceDummyRawValue.totalTax),
      totalDiscount: new FormControl(salesInvoiceDummyRawValue.totalDiscount),
      netTotal: new FormControl(salesInvoiceDummyRawValue.netTotal),
      message: new FormControl(salesInvoiceDummyRawValue.message),
      lmu: new FormControl(salesInvoiceDummyRawValue.lmu),
      lmd: new FormControl(salesInvoiceDummyRawValue.lmd),
      paidAmount: new FormControl(salesInvoiceDummyRawValue.paidAmount),
      amountOwing: new FormControl(salesInvoiceDummyRawValue.amountOwing),
      isActive: new FormControl(salesInvoiceDummyRawValue.isActive),
      locationID: new FormControl(salesInvoiceDummyRawValue.locationID),
      locationCode: new FormControl(salesInvoiceDummyRawValue.locationCode),
      referenceCode: new FormControl(salesInvoiceDummyRawValue.referenceCode),
      createdById: new FormControl(salesInvoiceDummyRawValue.createdById),
      createdByName: new FormControl(salesInvoiceDummyRawValue.createdByName),
      autoCareCharges: new FormControl(salesInvoiceDummyRawValue.autoCareCharges),
      autoCareJobId: new FormControl(salesInvoiceDummyRawValue.autoCareJobId),
      vehicleNo: new FormControl(salesInvoiceDummyRawValue.vehicleNo),
      nbtAmount: new FormControl(salesInvoiceDummyRawValue.nbtAmount),
      vatAmount: new FormControl(salesInvoiceDummyRawValue.vatAmount),
      dummyCommission: new FormControl(salesInvoiceDummyRawValue.dummyCommission),
      commissionPaidDate: new FormControl(salesInvoiceDummyRawValue.commissionPaidDate),
      paidCommission: new FormControl(salesInvoiceDummyRawValue.paidCommission),
      paidBy: new FormControl(salesInvoiceDummyRawValue.paidBy),
      originalInvoiceCode: new FormControl(salesInvoiceDummyRawValue.originalInvoiceCode),
    });
  }

  getSalesInvoiceDummy(form: SalesInvoiceDummyFormGroup): ISalesInvoiceDummy | NewSalesInvoiceDummy {
    return this.convertSalesInvoiceDummyRawValueToSalesInvoiceDummy(
      form.getRawValue() as SalesInvoiceDummyFormRawValue | NewSalesInvoiceDummyFormRawValue,
    );
  }

  resetForm(form: SalesInvoiceDummyFormGroup, salesInvoiceDummy: SalesInvoiceDummyFormGroupInput): void {
    const salesInvoiceDummyRawValue = this.convertSalesInvoiceDummyToSalesInvoiceDummyRawValue({
      ...this.getFormDefaults(),
      ...salesInvoiceDummy,
    });
    form.reset(
      {
        ...salesInvoiceDummyRawValue,
        id: { value: salesInvoiceDummyRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): SalesInvoiceDummyFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      invoiceDate: currentTime,
      createdDate: currentTime,
      deliveryDate: currentTime,
      lmd: currentTime,
      isActive: false,
      commissionPaidDate: currentTime,
    };
  }

  private convertSalesInvoiceDummyRawValueToSalesInvoiceDummy(
    rawSalesInvoiceDummy: SalesInvoiceDummyFormRawValue | NewSalesInvoiceDummyFormRawValue,
  ): ISalesInvoiceDummy | NewSalesInvoiceDummy {
    return {
      ...rawSalesInvoiceDummy,
      invoiceDate: dayjs(rawSalesInvoiceDummy.invoiceDate, DATE_TIME_FORMAT),
      createdDate: dayjs(rawSalesInvoiceDummy.createdDate, DATE_TIME_FORMAT),
      deliveryDate: dayjs(rawSalesInvoiceDummy.deliveryDate, DATE_TIME_FORMAT),
      lmd: dayjs(rawSalesInvoiceDummy.lmd, DATE_TIME_FORMAT),
      commissionPaidDate: dayjs(rawSalesInvoiceDummy.commissionPaidDate, DATE_TIME_FORMAT),
    };
  }

  private convertSalesInvoiceDummyToSalesInvoiceDummyRawValue(
    salesInvoiceDummy: ISalesInvoiceDummy | (Partial<NewSalesInvoiceDummy> & SalesInvoiceDummyFormDefaults),
  ): SalesInvoiceDummyFormRawValue | PartialWithRequiredKeyOf<NewSalesInvoiceDummyFormRawValue> {
    return {
      ...salesInvoiceDummy,
      invoiceDate: salesInvoiceDummy.invoiceDate ? salesInvoiceDummy.invoiceDate.format(DATE_TIME_FORMAT) : undefined,
      createdDate: salesInvoiceDummy.createdDate ? salesInvoiceDummy.createdDate.format(DATE_TIME_FORMAT) : undefined,
      deliveryDate: salesInvoiceDummy.deliveryDate ? salesInvoiceDummy.deliveryDate.format(DATE_TIME_FORMAT) : undefined,
      lmd: salesInvoiceDummy.lmd ? salesInvoiceDummy.lmd.format(DATE_TIME_FORMAT) : undefined,
      commissionPaidDate: salesInvoiceDummy.commissionPaidDate ? salesInvoiceDummy.commissionPaidDate.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
