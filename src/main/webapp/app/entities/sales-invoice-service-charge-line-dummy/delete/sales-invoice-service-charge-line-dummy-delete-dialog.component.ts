import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ISalesInvoiceServiceChargeLineDummy } from '../sales-invoice-service-charge-line-dummy.model';
import { SalesInvoiceServiceChargeLineDummyService } from '../service/sales-invoice-service-charge-line-dummy.service';

@Component({
  standalone: true,
  templateUrl: './sales-invoice-service-charge-line-dummy-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class SalesInvoiceServiceChargeLineDummyDeleteDialogComponent {
  salesInvoiceServiceChargeLineDummy?: ISalesInvoiceServiceChargeLineDummy;

  protected salesInvoiceServiceChargeLineDummyService = inject(SalesInvoiceServiceChargeLineDummyService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.salesInvoiceServiceChargeLineDummyService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
