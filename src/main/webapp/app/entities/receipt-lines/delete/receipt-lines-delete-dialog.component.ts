import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IReceiptLines } from '../receipt-lines.model';
import { ReceiptLinesService } from '../service/receipt-lines.service';

@Component({
  standalone: true,
  templateUrl: './receipt-lines-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ReceiptLinesDeleteDialogComponent {
  receiptLines?: IReceiptLines;

  protected receiptLinesService = inject(ReceiptLinesService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.receiptLinesService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
