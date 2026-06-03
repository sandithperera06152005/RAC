import { Component, OnInit, inject, Input } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormArray, FormBuilder, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IVoucherLines } from '../voucher-lines.model';
import { VoucherLinesService } from '../service/voucher-lines.service';
import { VoucherLinesFormGroup, VoucherLinesFormService } from './voucher-lines-form.service';

@Component({
  standalone: true,
  selector: 'jhi-voucher-lines-update',
  templateUrl: './voucher-lines-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class VoucherLinesUpdateComponent implements OnInit {
  isSaving = false;
  voucherLines: IVoucherLines[] = [];
  @Input() accountId: number = 0;
  @Input() amount: number = 0;
  @Input() originalamount: number = 0;
  @Input() code: String = '';
  @Input() name: String = '';
  protected voucherLinesService = inject(VoucherLinesService);
  protected voucherLinesFormService = inject(VoucherLinesFormService);
  protected activatedRoute = inject(ActivatedRoute);
  protected fb = inject(FormBuilder);
  // eslint-disable-next-line @typescript-eslint/member-ordering
  get voucherlinesArray(): FormArray {
    return this.editForm.get('voucherlines') as FormArray;
  }
  editForm: FormGroup = new FormGroup({
    voucherlines: new FormArray([]),
  });
  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ voucherLines }) => {
      this.voucherLines = voucherLines;
      if (voucherLines) {
        this.updateForm(voucherLines);
      } else {
        this.addVoucherLine(); // Add default row when no data is available
      }
    });
  }
  // In VoucherLinesUpdateComponent

  // Function to calculate and update amountOwing
  calculateAmountOwing(i: number, line: FormGroup): void {
    const originalAmount = line.get('originalAmount')?.value || 0;
    const amountReceived = line.get('amountReceived')?.value || 0;

    const amountOwing = originalAmount - amountReceived;
    line.get('amountOwing')?.setValue(amountOwing); // Update the amountOwing form control
  }

  // Function to get the current amountOwing value
  getAmountOwing(i: number, line: FormGroup): number {
    if (i === 0) {
      return this.originalamount - this.amount; // For the first row
    }
    return line.get('amountOwing')?.value || 0;
  }

  previousState(): void {
    window.history.back();
  }

  // Method to display account ID
  displayAccountId(accountId: number): void {
    if (this.voucherLines) {
      this.voucherLines.forEach(voucherLine => (voucherLine.accountId = accountId)); // Set the accountId on each VoucherLine model
    }
    if (this.voucherLines) {
      this.voucherLines.forEach(voucherLine => {
        console.log('Account ID in VoucherLinesComponent:', voucherLine.accountId); // Log the accountId from each VoucherLine model
      });
    }
  }

  addVoucherLine(): void {
    // Push a new form group into the voucherlinesArray
    const newDummy = this.voucherLinesFormService.createVoucherLinesFormGroup();
    this.voucherlinesArray.push(newDummy);

    // Log the updated voucherlinesArray
    console.log('Voucher lines after adding a new one:', this.voucherlinesArray.value);
  }

  removeServiceChargeDummy(index: number): void {
    this.voucherlinesArray.removeAt(index);
  }

  save(): void {
    this.isSaving = true;
    const voucherLines = this.voucherLinesFormService.getVoucherLines(this.voucherlinesArray);
    voucherLines.forEach(voucherLine => {
      if (voucherLine.id !== null) {
        this.subscribeToSaveResponse(this.voucherLinesService.update(voucherLine));
      } else {
        this.subscribeToSaveResponse(this.voucherLinesService.create(voucherLine));
      }
    });
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVoucherLines>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }
  removeVoucherLine(index: number): void {
    this.voucherlinesArray.removeAt(index);
  }
  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(voucherLines: IVoucherLines[]): void {
    // Clear existing form data
    this.voucherlinesArray.clear(); // Clear previous form array contents

    if (voucherLines.length > 0) {
      // Populate form with existing data
      voucherLines.forEach(dummy => {
        this.voucherlinesArray.push(this.voucherLinesFormService.createVoucherLinesFormGroup(dummy));
      });
      voucherLines.forEach(line => {
        // Ensure each line is converted to a form group
        this.voucherlinesArray.push(this.voucherLinesFormService.createVoucherLinesFormGroup(line));
      });
    } else {
      // If no data is provided, add a default voucher line
      this.addVoucherLine();
    }
  }
}
