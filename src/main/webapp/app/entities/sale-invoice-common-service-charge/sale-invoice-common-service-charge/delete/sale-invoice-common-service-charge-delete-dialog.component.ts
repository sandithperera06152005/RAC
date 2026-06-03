import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ISaleInvoiceCommonServiceCharge } from '../sale-invoice-common-service-charge.model';
import { SaleInvoiceCommonServiceChargeService } from '../service/sale-invoice-common-service-charge.service';

@Component({
  standalone: true,
  templateUrl: './sale-invoice-common-service-charge-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class SaleInvoiceCommonServiceChargeDeleteDialogComponent {
  saleInvoiceCommonServiceCharge?: ISaleInvoiceCommonServiceCharge;

  protected saleInvoiceCommonServiceChargeService = inject(SaleInvoiceCommonServiceChargeService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.saleInvoiceCommonServiceChargeService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
