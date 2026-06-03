import { Component, OnInit, ViewChild, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';
import { toWords } from 'number-to-words';
import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule, FormBuilder } from '@angular/forms';
import { ISalesInvoiceDummy } from 'app/entities/sales-invoice-dummy/sales-invoice-dummy.model';
import { IVoucher } from '../voucher.model';
import { VoucherService } from '../service/voucher.service';
import { VoucherFormGroup, VoucherFormService } from './voucher-form.service';
import { VoucherLinesComponent } from 'app/entities/voucher-lines/list/voucher-lines.component';
import { VoucherLinesUpdateComponent } from '../../voucher-lines/update/voucher-lines-update.component';
import { firstValueFrom } from 'rxjs';
@Component({
  standalone: true,
  selector: 'jhi-voucher-update',
  templateUrl: './voucher-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule, VoucherLinesUpdateComponent],
  // Make sure this is included
})
export class VoucherUpdateComponent implements OnInit {
  isSaving = false;
  @ViewChild(VoucherLinesUpdateComponent) VoucherLinesUpdateComponent!: VoucherLinesUpdateComponent; // Add the ViewChild decorator
  voucher: IVoucher | null = null;
  accountId: number = 0;
  name: string = '';
  originalamount: number = 0;
  amount: number = 0;
  code: String = '';
  salesinvoiceDummy: ISalesInvoiceDummy | null = null;
  dummyCommissionAmount: number | undefined; // Local variable to store the value
  totalAmount: number | undefined; // Add the totalAmount property
  voucherCode: string = '';
  protected voucherService = inject(VoucherService);
  protected formBuilder = inject(FormBuilder);
  protected voucherFormService = inject(VoucherFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: VoucherFormGroup = this.voucherFormService.createVoucherFormGroup();

  ngOnInit(): void {
    this.editForm.patchValue({
      lmu: 1,
      createdBy: 1,
      termId: 1,
      term: 'Cash',
    });
    this.activatedRoute.data.subscribe(({ voucher }) => {
      this.voucher = voucher;

      this.activatedRoute.queryParams.subscribe(params => {
        const id = params['id'];
        if (id) {
          this.loadSalesInvoiceDummy(id);
        }
        console.log('Query id:', id);
      });

      if (voucher) {
        this.updateForm(voucher);
      }
    });

    this.editForm.addControl('amountPaid', this.formBuilder.control(null));
    this.editForm.addControl('totalAmountInWord', this.formBuilder.control(''));
    this.amount = this.editForm.get('amountPaid')?.value ?? 0;
    console.log('amount:', this.amount);
    this.originalamount = this.editForm.get('totalAmount')?.value ?? 0;
    console.log('originalamount:', this.originalamount);
    // Listen for changes in the 'amountPaid' input field
    this.editForm.get('amountPaid')?.valueChanges.subscribe(value => {
      if (value !== null && value !== undefined) {
        this.convertToWords(value);
        setTimeout(() => {
          this.amount = value; // Ensure amount is updated
        });
      }
    });
    this.editForm.get('totalAmount')?.valueChanges.subscribe(value => {
      if (value !== null && value !== undefined) {
        this.originalamount = value; // Ensure amount is updated
      }
    });
    this.editForm.get('amountPaid')?.valueChanges.subscribe(value => {
      if (value !== null && value !== undefined) {
        this.convertToWords(value);
      }
    });
    // Fetch voucher lines and log the response
    this.fetchVoucherLines();
  }
  async fetchVoucherLines(): Promise<void> {
    try {
      const response = await lastValueFrom(this.voucherService.fetchVoucherLines());

      if (response.body && response.body.length > 0) {
        let lastCode = response.body[0].code; // Assuming the latest voucher is at index 0
        console.log('Last Voucher Code:', lastCode);

        // Extract numeric part and increment
        const match = lastCode.match(/(\D+)(\d+)/); // Splits into prefix and number
        if (match) {
          const prefix = match[1]; // Non-numeric prefix (e.g., "VOUCHER")
          const number = parseInt(match[2], 10) + 1; // Increment numeric part

          this.voucherCode = `${prefix}${number}`; // Set the new code to voucherCode
          this.editForm.patchValue({ code: this.voucherCode });
          console.log('New Voucher Code:', this.voucherCode);
        } else {
          console.warn('Invalid voucher code format:', lastCode);
        }
      } else {
        console.warn('No voucher codes found.');
      }
    } catch (error) {
      console.error('Error fetching voucher lines:', error);
    }
  }

  private convertToWords(value: number): void {
    if (value !== null && !isNaN(value)) {
      const words = toWords(value).toUpperCase() + ' RUPEES ONLY'; // Convert to words and uppercase
      this.editForm.patchValue({ totalAmountInWord: words }, { emitEvent: false });
    } else {
      this.editForm.patchValue({ totalAmountInWord: '' }, { emitEvent: false });
    }
  }

  private loadSalesInvoiceDummy(id: number): void {
    this.voucherService.find(id).subscribe(response => {
      const salesInvoiceDummy = response.body;
      console.log(response.body);

      if (salesInvoiceDummy) {
        console.log('Retrieved data:', (salesInvoiceDummy as ISalesInvoiceDummy).dummyCommission);
        console.log('Retrieved dataa:', (salesInvoiceDummy as ISalesInvoiceDummy).customerName);

        const customerName = (salesInvoiceDummy as ISalesInvoiceDummy).customerName;
        if (customerName) {
          this.voucherService.searchaccount(customerName).subscribe(accountResponse => {
            console.log('Account search response:', accountResponse.body);

            // Extracting and displaying the ID from the response
            if (accountResponse.body && accountResponse.body[0].id) {
              console.log('Account ID:', accountResponse.body[0].id);
              this.accountId = accountResponse.body[0].id;
              console.log('Account ID:', this.accountId);
              this.name = accountResponse.body[0].name;
              console.log('name:', accountResponse.body[0].name);
              // Pass account ID to the displayAccountId method
              this.VoucherLinesUpdateComponent.displayAccountId(accountResponse.body[0].id);

              // Show success message
              //  this.VoucherLinesUpdateComponent.displaySuccessMessage('Account ID retrieved successfully!');
            } else {
              console.log('No account ID found in response.');
            }
          });
        }
        // Update the form control instead of a separate variable
        this.editForm.patchValue({
          totalAmount: (salesInvoiceDummy as ISalesInvoiceDummy).dummyCommission ?? null,
          comments: 'Dummy commision payment - ' + (salesInvoiceDummy as ISalesInvoiceDummy).code,
          lmu: (salesInvoiceDummy as ISalesInvoiceDummy).lmu ?? 1,
          createdBy: (salesInvoiceDummy as ISalesInvoiceDummy).createdById ?? 1,
          termId: 1,
          term: 'Cash',
        });

        this.code = (salesInvoiceDummy as ISalesInvoiceDummy).code ?? '';
        console.log('Account IDddd:', this.code);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const voucher = this.voucherFormService.getVoucher(this.editForm);
    if (voucher.id !== null) {
      this.subscribeToSaveResponse(this.voucherService.update(voucher));
    } else {
      this.subscribeToSaveResponse(this.voucherService.create(voucher));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVoucher>>): void {
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

  protected updateForm(voucher: IVoucher): void {
    this.voucher = voucher;
    this.voucherFormService.resetForm(this.editForm, voucher);
  }
}
function lastValueFrom<T>(observable: Observable<T>): Promise<T> {
  return firstValueFrom(observable);
}
