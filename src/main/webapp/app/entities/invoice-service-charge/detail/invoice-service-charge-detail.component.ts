import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { IInvoiceServiceCharge } from '../invoice-service-charge.model';

@Component({
  standalone: true,
  selector: 'jhi-invoice-service-charge-detail',
  templateUrl: './invoice-service-charge-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class InvoiceServiceChargeDetailComponent {
  invoiceServiceCharge = input<IInvoiceServiceCharge | null>(null);

  previousState(): void {
    window.history.back();
  }
}
