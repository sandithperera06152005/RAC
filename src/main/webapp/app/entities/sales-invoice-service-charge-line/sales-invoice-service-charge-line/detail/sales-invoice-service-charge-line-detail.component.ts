import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { ISalesInvoiceServiceChargeLine } from '../sales-invoice-service-charge-line.model';

@Component({
  standalone: true,
  selector: 'jhi-sales-invoice-service-charge-line-detail',
  templateUrl: './sales-invoice-service-charge-line-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class SalesInvoiceServiceChargeLineDetailComponent {
  salesInvoiceServiceChargeLine = input<ISalesInvoiceServiceChargeLine | null>(null);

  previousState(): void {
    window.history.back();
  }
}
