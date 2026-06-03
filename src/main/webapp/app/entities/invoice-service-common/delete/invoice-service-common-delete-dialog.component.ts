import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IInvoiceServiceCommon } from '../invoice-service-common.model';
import { InvoiceServiceCommonService } from '../service/invoice-service-common.service';

@Component({
  standalone: true,
  templateUrl: './invoice-service-common-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class InvoiceServiceCommonDeleteDialogComponent {
  invoiceServiceCommon?: IInvoiceServiceCommon;

  protected invoiceServiceCommonService = inject(InvoiceServiceCommonService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.invoiceServiceCommonService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
