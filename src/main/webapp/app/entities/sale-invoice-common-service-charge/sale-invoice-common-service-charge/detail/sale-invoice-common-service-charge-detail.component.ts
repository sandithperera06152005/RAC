import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { ISaleInvoiceCommonServiceCharge } from '../sale-invoice-common-service-charge.model';

@Component({
  standalone: true,
  selector: 'jhi-sale-invoice-common-service-charge-detail',
  templateUrl: './sale-invoice-common-service-charge-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class SaleInvoiceCommonServiceChargeDetailComponent {
  saleInvoiceCommonServiceCharge = input<ISaleInvoiceCommonServiceCharge | null>(null);

  previousState(): void {
    window.history.back();
  }
}
