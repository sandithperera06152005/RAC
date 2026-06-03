import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IWorkshopVehicleWorkList } from '../workshop-vehicle-work-list.model';
import { WorkshopVehicleWorkListService } from '../service/workshop-vehicle-work-list.service';

@Component({
  standalone: true,
  templateUrl: './workshop-vehicle-work-list-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class WorkshopVehicleWorkListDeleteDialogComponent {
  workshopVehicleWorkList?: IWorkshopVehicleWorkList;

  protected workshopVehicleWorkListService = inject(WorkshopVehicleWorkListService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.workshopVehicleWorkListService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
