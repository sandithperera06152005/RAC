import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ISalesInvoiceDummy } from '../sales-invoice-dummy.model';
import { SalesInvoiceDummyService } from '../service/sales-invoice-dummy.service';

@Component({
  standalone: true,
  templateUrl: './sales-invoice-dummy-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class SalesInvoiceDummyDeleteDialogComponent {
  salesInvoiceDummy?: ISalesInvoiceDummy;

  protected salesInvoiceDummyService = inject(SalesInvoiceDummyService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.salesInvoiceDummyService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
