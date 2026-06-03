import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_UPDATED_EVENT } from 'app/config/navigation.constants';
import { IAutocareappointment } from '../autocareappointment.model';
import { AutocareappointmentService } from '../service/autocareappointment.service';

@Component({
  standalone: true,
  templateUrl: './autocareappointment-update-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class AutocareappointmentUpdateDialogComponent {
  autocareappointment?: IAutocareappointment;

  protected autocareappointmentService = inject(AutocareappointmentService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmUpdate(autocareappointment: IAutocareappointment): void {
    this.autocareappointmentService.update(autocareappointment).subscribe(() => {
      this.activeModal.close(ITEM_UPDATED_EVENT);
    });
  }
}
