import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IVoucherPaymentsDetails } from '../voucher-payments-details.model';
import { VoucherPaymentsDetailsService } from '../service/voucher-payments-details.service';
import { VoucherPaymentsDetailsFormGroup, VoucherPaymentsDetailsFormService } from './voucher-payments-details-form.service';

@Component({
  standalone: true,
  selector: 'jhi-voucher-payments-details-update',
  templateUrl: './voucher-payments-details-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class VoucherPaymentsDetailsUpdateComponent implements OnInit {
  isSaving = false;
  voucherPaymentsDetails: IVoucherPaymentsDetails | null = null;

  protected voucherPaymentsDetailsService = inject(VoucherPaymentsDetailsService);
  protected voucherPaymentsDetailsFormService = inject(VoucherPaymentsDetailsFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: VoucherPaymentsDetailsFormGroup = this.voucherPaymentsDetailsFormService.createVoucherPaymentsDetailsFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ voucherPaymentsDetails }) => {
      this.voucherPaymentsDetails = voucherPaymentsDetails;
      if (voucherPaymentsDetails) {
        this.updateForm(voucherPaymentsDetails);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const voucherPaymentsDetails = this.voucherPaymentsDetailsFormService.getVoucherPaymentsDetails(this.editForm);
    if (voucherPaymentsDetails.id !== null) {
      this.subscribeToSaveResponse(this.voucherPaymentsDetailsService.update(voucherPaymentsDetails));
    } else {
      this.subscribeToSaveResponse(this.voucherPaymentsDetailsService.create(voucherPaymentsDetails));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVoucherPaymentsDetails>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(voucherPaymentsDetails: IVoucherPaymentsDetails): void {
    this.voucherPaymentsDetails = voucherPaymentsDetails;
    this.voucherPaymentsDetailsFormService.resetForm(this.editForm, voucherPaymentsDetails);
  }
}
