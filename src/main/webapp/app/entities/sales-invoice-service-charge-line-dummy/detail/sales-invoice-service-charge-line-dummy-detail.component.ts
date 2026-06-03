import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { ISalesInvoiceServiceChargeLineDummy } from '../sales-invoice-service-charge-line-dummy.model';

@Component({
  standalone: true,
  selector: 'jhi-sales-invoice-service-charge-line-dummy-detail',
  templateUrl: './sales-invoice-service-charge-line-dummy-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class SalesInvoiceServiceChargeLineDummyDetailComponent {
  salesInvoiceServiceChargeLineDummy = input<ISalesInvoiceServiceChargeLineDummy | null>(null);

  previousState(): void {
    window.history.back();
  }
}
