import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IAutojobsinvoicelinebatches } from '../autojobsinvoicelinebatches.model';
import { AutojobsinvoicelinebatchesService } from '../service/autojobsinvoicelinebatches.service';

@Component({
  standalone: true,
  templateUrl: './autojobsinvoicelinebatches-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class AutojobsinvoicelinebatchesDeleteDialogComponent {
  autojobsinvoicelinebatches?: IAutojobsinvoicelinebatches;

  protected autojobsinvoicelinebatchesService = inject(AutojobsinvoicelinebatchesService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.autojobsinvoicelinebatchesService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
