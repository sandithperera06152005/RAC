import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule, FormArray, FormBuilder } from '@angular/forms';

import { IInvoiceServiceCommon } from '../invoice-service-common.model';
import { InvoiceServiceCommonService } from '../service/invoice-service-common.service';
import { InvoiceServiceCommonFormGroup, InvoiceServiceCommonFormService } from './invoice-service-common-form.service';

@Component({
  standalone: true,
  selector: 'jhi-invoice-service-common-update',
  templateUrl: './invoice-service-common-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class InvoiceServiceCommonUpdateComponent implements OnInit {
  isSaving = false;
  invoiceServiceCommons: IInvoiceServiceCommon[] = [];

  protected invoiceServiceCommonService = inject(InvoiceServiceCommonService);
  protected invoiceServiceCommonFormService = inject(InvoiceServiceCommonFormService);
  protected activatedRoute = inject(ActivatedRoute);
  protected fb = inject(FormBuilder);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm = this.fb.group({
    invoiceServiceCommons: this.fb.array([]),
  });

  get invoiceServiceCommonsArray(): FormArray {
    return this.editForm.get('invoiceServiceCommons') as FormArray;
  }

  ngOnInit(): void {
    // Load data and populate the form array
  }

  addInvoiceServiceCommon(invoiceServiceCommon?: IInvoiceServiceCommon): void {
    const newInvoiceServiceCommon = invoiceServiceCommon ?? ({} as IInvoiceServiceCommon);
    this.invoiceServiceCommonsArray.push(this.invoiceServiceCommonFormService.createInvoiceServiceCommonFormGroup(newInvoiceServiceCommon));
  }

  removeInvoiceServiceCommon(index: number): void {
    this.invoiceServiceCommonsArray.removeAt(index);
  }
  previousState(): void {
    window.history.back();
  }
}
