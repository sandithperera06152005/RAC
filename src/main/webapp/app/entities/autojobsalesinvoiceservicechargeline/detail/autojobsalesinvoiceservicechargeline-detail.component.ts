import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { IAutojobsalesinvoiceservicechargeline } from '../autojobsalesinvoiceservicechargeline.model';

@Component({
  standalone: true,
  selector: 'jhi-autojobsalesinvoiceservicechargeline-detail',
  templateUrl: './autojobsalesinvoiceservicechargeline-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class AutojobsalesinvoiceservicechargelineDetailComponent {
  autojobsalesinvoiceservicechargeline = input<IAutojobsalesinvoiceservicechargeline | null>(null);

  previousState(): void {
    window.history.back();
  }
}
