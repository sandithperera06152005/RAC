import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IReceiptpaymentsdetails } from '../receiptpaymentsdetails.model';
import { ReceiptpaymentsdetailsService } from '../service/receiptpaymentsdetails.service';
import { ReceiptpaymentsdetailsFormGroup, ReceiptpaymentsdetailsFormService } from './receiptpaymentsdetails-form.service';
import { IBanks } from 'app/entities/banks/banks.model';
import { BanksService } from 'app/entities/banks/service/banks.service';
import { IBankbranch } from 'app/entities/bankbranch/bankbranch.model';
import { BankbranchService } from 'app/entities/bankbranch/service/bankbranch.service';

@Component({
  standalone: true,
  selector: 'jhi-receiptpaymentsdetails-update',
  templateUrl: './receiptpaymentsdetails-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ReceiptpaymentsdetailsUpdateComponent implements OnInit {
  isSaving = false;
  receiptpaymentsdetails: IReceiptpaymentsdetails | null = null;
  selectedOption: number | null = null;
  banks: IBanks[] = [];
  bankbranch: IBankbranch[] = [];

  protected receiptpaymentsdetailsService = inject(ReceiptpaymentsdetailsService);
  protected receiptpaymentsdetailsFormService = inject(ReceiptpaymentsdetailsFormService);
  protected activatedRoute = inject(ActivatedRoute);
  protected banksService = inject(BanksService);
  protected bankbranchService = inject(BankbranchService);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: ReceiptpaymentsdetailsFormGroup = this.receiptpaymentsdetailsFormService.createReceiptpaymentsdetailsFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ receiptpaymentsdetails }) => {
      this.receiptpaymentsdetails = receiptpaymentsdetails;
      if (receiptpaymentsdetails) {
        this.updateForm(receiptpaymentsdetails);
      }
      this.loadBanks();
      this.loadBankBranch();
    });
  }

  previousState(): void {
    window.history.back();
  }

  loadBanks(): void {
    this.banksService.query({ size: 1000 }).subscribe((res: HttpResponse<IBanks[]>) => {
      this.banks = res.body || [];
    });
  }

  loadBankBranch(): void {
    this.bankbranchService.query({ size: 1000 }).subscribe((res: HttpResponse<IBankbranch[]>) => {
      this.bankbranch = res.body || [];
    });
  }

  save(): void {
    this.isSaving = true;
    const receiptpaymentsdetails = this.receiptpaymentsdetailsFormService.getReceiptpaymentsdetails(this.editForm);
    if (receiptpaymentsdetails.id !== null) {
      this.subscribeToSaveResponse(this.receiptpaymentsdetailsService.update(receiptpaymentsdetails));
    } else {
      this.subscribeToSaveResponse(this.receiptpaymentsdetailsService.create(receiptpaymentsdetails));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IReceiptpaymentsdetails>>): void {
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

  protected updateForm(receiptpaymentsdetails: IReceiptpaymentsdetails): void {
    this.receiptpaymentsdetails = receiptpaymentsdetails;
    this.receiptpaymentsdetailsFormService.resetForm(this.editForm, receiptpaymentsdetails);
  }
}
