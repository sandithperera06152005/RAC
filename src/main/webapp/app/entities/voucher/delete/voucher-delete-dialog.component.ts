import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IVoucher } from '../voucher.model';
import { VoucherService } from '../service/voucher.service';

@Component({
  standalone: true,
  templateUrl: './voucher-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class VoucherDeleteDialogComponent {
  voucher?: IVoucher;

  protected voucherService = inject(VoucherService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.voucherService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
