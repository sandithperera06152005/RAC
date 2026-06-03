import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IAutojobsaleinvoicecommonservicecharge } from '../autojobsaleinvoicecommonservicecharge.model';
import { AutojobsaleinvoicecommonservicechargeService } from '../service/autojobsaleinvoicecommonservicecharge.service';

@Component({
  standalone: true,
  templateUrl: './autojobsaleinvoicecommonservicecharge-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class AutojobsaleinvoicecommonservicechargeDeleteDialogComponent {
  autojobsaleinvoicecommonservicecharge?: IAutojobsaleinvoicecommonservicecharge;

  protected autojobsaleinvoicecommonservicechargeService = inject(AutojobsaleinvoicecommonservicechargeService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.autojobsaleinvoicecommonservicechargeService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
