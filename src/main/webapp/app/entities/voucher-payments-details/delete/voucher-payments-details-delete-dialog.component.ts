import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IVoucherPaymentsDetails } from '../voucher-payments-details.model';
import { VoucherPaymentsDetailsService } from '../service/voucher-payments-details.service';

@Component({
  standalone: true,
  templateUrl: './voucher-payments-details-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class VoucherPaymentsDetailsDeleteDialogComponent {
  voucherPaymentsDetails?: IVoucherPaymentsDetails;

  protected voucherPaymentsDetailsService = inject(VoucherPaymentsDetailsService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.voucherPaymentsDetailsService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
