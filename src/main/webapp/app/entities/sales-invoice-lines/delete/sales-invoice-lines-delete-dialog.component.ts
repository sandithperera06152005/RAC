import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ISalesInvoiceLines } from '../sales-invoice-lines.model';
import { SalesInvoiceLinesService } from '../service/sales-invoice-lines.service';

@Component({
  standalone: true,
  templateUrl: './sales-invoice-lines-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class SalesInvoiceLinesDeleteDialogComponent {
  salesInvoiceLines?: ISalesInvoiceLines;

  protected salesInvoiceLinesService = inject(SalesInvoiceLinesService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.salesInvoiceLinesService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
