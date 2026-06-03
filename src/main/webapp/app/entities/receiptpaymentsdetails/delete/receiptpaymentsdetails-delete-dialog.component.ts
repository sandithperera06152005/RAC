import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IReceiptpaymentsdetails } from '../receiptpaymentsdetails.model';
import { ReceiptpaymentsdetailsService } from '../service/receiptpaymentsdetails.service';

@Component({
  standalone: true,
  templateUrl: './receiptpaymentsdetails-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ReceiptpaymentsdetailsDeleteDialogComponent {
  receiptpaymentsdetails?: IReceiptpaymentsdetails;

  protected receiptpaymentsdetailsService = inject(ReceiptpaymentsdetailsService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.receiptpaymentsdetailsService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
