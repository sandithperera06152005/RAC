import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { ISalesInvoiceLines } from '../sales-invoice-lines.model';

@Component({
  standalone: true,
  selector: 'jhi-sales-invoice-lines-detail',
  templateUrl: './sales-invoice-lines-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class SalesInvoiceLinesDetailComponent {
  salesInvoiceLines = input<ISalesInvoiceLines | null>(null);

  previousState(): void {
    window.history.back();
  }
}
