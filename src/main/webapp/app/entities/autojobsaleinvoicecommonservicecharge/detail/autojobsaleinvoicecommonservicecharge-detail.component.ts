import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { IAutojobsaleinvoicecommonservicecharge } from '../autojobsaleinvoicecommonservicecharge.model';

@Component({
  standalone: true,
  selector: 'jhi-autojobsaleinvoicecommonservicecharge-detail',
  templateUrl: './autojobsaleinvoicecommonservicecharge-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class AutojobsaleinvoicecommonservicechargeDetailComponent {
  autojobsaleinvoicecommonservicecharge = input<IAutojobsaleinvoicecommonservicecharge | null>(null);

  previousState(): void {
    window.history.back();
  }
}
