import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ISaleInvoiceCommonServiceChargeDummy } from '../sale-invoice-common-service-charge-dummy.model';
import { SaleInvoiceCommonServiceChargeDummyService } from '../service/sale-invoice-common-service-charge-dummy.service';

@Component({
  standalone: true,
  templateUrl: './sale-invoice-common-service-charge-dummy-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class SaleInvoiceCommonServiceChargeDummyDeleteDialogComponent {
  saleInvoiceCommonServiceChargeDummy?: ISaleInvoiceCommonServiceChargeDummy;

  protected saleInvoiceCommonServiceChargeDummyService = inject(SaleInvoiceCommonServiceChargeDummyService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.saleInvoiceCommonServiceChargeDummyService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
