import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IAutojobsalesinvoiceservicechargeline } from '../autojobsalesinvoiceservicechargeline.model';
import { AutojobsalesinvoiceservicechargelineService } from '../service/autojobsalesinvoiceservicechargeline.service';

@Component({
  standalone: true,
  templateUrl: './autojobsalesinvoiceservicechargeline-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class AutojobsalesinvoiceservicechargelineDeleteDialogComponent {
  autojobsalesinvoiceservicechargeline?: IAutojobsalesinvoiceservicechargeline;

  protected autojobsalesinvoiceservicechargelineService = inject(AutojobsalesinvoiceservicechargelineService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.autojobsalesinvoiceservicechargelineService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
