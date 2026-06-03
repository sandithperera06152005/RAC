import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ISalesInvoiceLinesDummy } from '../sales-invoice-lines-dummy.model';
import { SalesInvoiceLinesDummyService } from '../service/sales-invoice-lines-dummy.service';

@Component({
  standalone: true,
  templateUrl: './sales-invoice-lines-dummy-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class SalesInvoiceLinesDummyDeleteDialogComponent {
  salesInvoiceLinesDummy?: ISalesInvoiceLinesDummy;

  protected salesInvoiceLinesDummyService = inject(SalesInvoiceLinesDummyService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.salesInvoiceLinesDummyService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
