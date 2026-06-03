import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IAutojobsinvoicelines } from '../autojobsinvoicelines.model';
import { AutojobsinvoicelinesService } from '../service/autojobsinvoicelines.service';

@Component({
  standalone: true,
  templateUrl: './autojobsinvoicelines-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class AutojobsinvoicelinesDeleteDialogComponent {
  autojobsinvoicelines?: IAutojobsinvoicelines;

  protected autojobsinvoicelinesService = inject(AutojobsinvoicelinesService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.autojobsinvoicelinesService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
