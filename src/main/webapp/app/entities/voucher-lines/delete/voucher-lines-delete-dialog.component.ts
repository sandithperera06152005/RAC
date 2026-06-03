import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IVoucherLines } from '../voucher-lines.model';
import { VoucherLinesService } from '../service/voucher-lines.service';

@Component({
  standalone: true,
  templateUrl: './voucher-lines-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class VoucherLinesDeleteDialogComponent {
  voucherLines?: IVoucherLines;

  protected voucherLinesService = inject(VoucherLinesService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.voucherLinesService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
