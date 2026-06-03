import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ISalesInvoiceServiceChargeLine } from '../sales-invoice-service-charge-line.model';
import { SalesInvoiceServiceChargeLineService } from '../service/sales-invoice-service-charge-line.service';

@Component({
  standalone: true,
  templateUrl: './sales-invoice-service-charge-line-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class SalesInvoiceServiceChargeLineDeleteDialogComponent {
  salesInvoiceServiceChargeLine?: ISalesInvoiceServiceChargeLine;

  protected salesInvoiceServiceChargeLineService = inject(SalesInvoiceServiceChargeLineService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.salesInvoiceServiceChargeLineService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
