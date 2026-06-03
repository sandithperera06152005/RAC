import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { IInvoiceServiceCommon } from '../invoice-service-common.model';

@Component({
  standalone: true,
  selector: 'jhi-invoice-service-common-detail',
  templateUrl: './invoice-service-common-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class InvoiceServiceCommonDetailComponent {
  invoiceServiceCommon = input<IInvoiceServiceCommon | null>(null);

  previousState(): void {
    window.history.back();
  }
}
