import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IInvoiceServiceCharge } from '../invoice-service-charge.model';
import { InvoiceServiceChargeService } from '../service/invoice-service-charge.service';

@Component({
  standalone: true,
  templateUrl: './invoice-service-charge-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class InvoiceServiceChargeDeleteDialogComponent {
  invoiceServiceCharge?: IInvoiceServiceCharge;

  protected invoiceServiceChargeService = inject(InvoiceServiceChargeService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.invoiceServiceChargeService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
