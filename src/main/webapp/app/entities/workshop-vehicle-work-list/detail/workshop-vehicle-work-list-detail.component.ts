import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { IWorkshopVehicleWorkList } from '../workshop-vehicle-work-list.model';

@Component({
  standalone: true,
  selector: 'jhi-workshop-vehicle-work-list-detail',
  templateUrl: './workshop-vehicle-work-list-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class WorkshopVehicleWorkListDetailComponent {
  workshopVehicleWorkList = input<IWorkshopVehicleWorkList | null>(null);

  previousState(): void {
    window.history.back();
  }
}
