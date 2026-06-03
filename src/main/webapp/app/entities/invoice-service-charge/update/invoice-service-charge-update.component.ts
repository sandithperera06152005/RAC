import { Component, OnInit, inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { IInvoiceServiceCharge } from '../invoice-service-charge.model';
import { InvoiceServiceChargeService } from '../service/invoice-service-charge.service';
import { InvoiceServiceChargeFormGroup, InvoiceServiceChargeFormService } from './invoice-service-charge-form.service';
import { FormArray, FormBuilder, FormsModule, ReactiveFormsModule } from '@angular/forms';
import SharedModule from 'app/shared/shared.module';

@Component({
  standalone: true,
  selector: 'jhi-invoice-service-charge-update',
  templateUrl: './invoice-service-charge-update.component.html',
  //styles:[``],
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class InvoiceServiceChargeUpdateComponent implements OnInit {
  isSaving = false;
  invoiceServiceCharges: IInvoiceServiceCharge[] = [];

  protected invoiceServiceChargeService = inject(InvoiceServiceChargeService);
  protected invoiceServiceChargeFormService = inject(InvoiceServiceChargeFormService);
  protected activatedRoute = inject(ActivatedRoute);
  protected fb = inject(FormBuilder);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm = this.fb.group({
    invoiceServiceCharges: this.fb.array([]),
  });

  get invoiceServiceChargesArray(): FormArray {
    return this.editForm.get('invoiceServiceCharges') as FormArray;
  }

  ngOnInit(): void {
    // Load data and populate the form array
  }

  addInvoiceServiceCharge(invoiceServiceCharge?: IInvoiceServiceCharge): void {
    const newInvoiceServiceCharge = invoiceServiceCharge ?? ({} as IInvoiceServiceCharge);
    this.invoiceServiceChargesArray.push(this.invoiceServiceChargeFormService.createInvoiceServiceChargeFormGroup(newInvoiceServiceCharge));
  }

  removeInvoiceServiceCharge(index: number): void {
    this.invoiceServiceChargesArray.removeAt(index);
  }
}
