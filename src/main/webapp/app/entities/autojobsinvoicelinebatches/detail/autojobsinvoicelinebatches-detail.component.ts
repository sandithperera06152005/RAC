import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { IAutojobsinvoicelinebatches } from '../autojobsinvoicelinebatches.model';

@Component({
  standalone: true,
  selector: 'jhi-autojobsinvoicelinebatches-detail',
  templateUrl: './autojobsinvoicelinebatches-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class AutojobsinvoicelinebatchesDetailComponent {
  autojobsinvoicelinebatches = input<IAutojobsinvoicelinebatches | null>(null);

  previousState(): void {
    window.history.back();
  }
}
