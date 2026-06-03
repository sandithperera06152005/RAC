import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { ISaleInvoiceCommonServiceChargeDummy } from '../sale-invoice-common-service-charge-dummy.model';

@Component({
  standalone: true,
  selector: 'jhi-sale-invoice-common-service-charge-dummy-detail',
  templateUrl: './sale-invoice-common-service-charge-dummy-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class SaleInvoiceCommonServiceChargeDummyDetailComponent {
  saleInvoiceCommonServiceChargeDummy = input<ISaleInvoiceCommonServiceChargeDummy | null>(null);

  previousState(): void {
    window.history.back();
  }
}
