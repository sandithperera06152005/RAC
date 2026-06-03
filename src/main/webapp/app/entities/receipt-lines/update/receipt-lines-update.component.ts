import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IReceiptLines } from '../receipt-lines.model';
import { ReceiptLinesService } from '../service/receipt-lines.service';
import { ReceiptLinesFormGroup, ReceiptLinesFormService } from './receipt-lines-form.service';

@Component({
  standalone: true,
  selector: 'jhi-receipt-lines-update',
  templateUrl: './receipt-lines-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ReceiptLinesUpdateComponent implements OnInit {
  isSaving = false;
  receiptLines: IReceiptLines | null = null;

  protected receiptLinesService = inject(ReceiptLinesService);
  protected receiptLinesFormService = inject(ReceiptLinesFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: ReceiptLinesFormGroup = this.receiptLinesFormService.createReceiptLinesFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ receiptLines }) => {
      this.receiptLines = receiptLines;
      if (receiptLines) {
        this.updateForm(receiptLines);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const receiptLines = this.receiptLinesFormService.getReceiptLines(this.editForm);
    if (receiptLines.id !== null) {
      this.subscribeToSaveResponse(this.receiptLinesService.update(receiptLines));
    } else {
      this.subscribeToSaveResponse(this.receiptLinesService.create(receiptLines));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IReceiptLines>>): void {
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

  protected updateForm(receiptLines: IReceiptLines): void {
    this.receiptLines = receiptLines;
    this.receiptLinesFormService.resetForm(this.editForm, receiptLines);
  }
}
