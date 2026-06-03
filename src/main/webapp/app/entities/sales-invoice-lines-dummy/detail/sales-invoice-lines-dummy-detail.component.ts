import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { ISalesInvoiceLinesDummy } from '../sales-invoice-lines-dummy.model';

@Component({
  standalone: true,
  selector: 'jhi-sales-invoice-lines-dummy-detail',
  templateUrl: './sales-invoice-lines-dummy-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class SalesInvoiceLinesDummyDetailComponent {
  salesInvoiceLinesDummy = input<ISalesInvoiceLinesDummy | null>(null);

  previousState(): void {
    window.history.back();
  }
}
