import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { ISalesInvoiceDummy } from '../sales-invoice-dummy.model';

@Component({
  standalone: true,
  selector: 'jhi-sales-invoice-dummy-detail',
  templateUrl: './sales-invoice-dummy-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class SalesInvoiceDummyDetailComponent {
  salesInvoiceDummy = input<ISalesInvoiceDummy | null>(null);

  previousState(): void {
    window.history.back();
  }
}
