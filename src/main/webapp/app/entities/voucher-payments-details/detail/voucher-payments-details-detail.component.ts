import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { IVoucherPaymentsDetails } from '../voucher-payments-details.model';

@Component({
  standalone: true,
  selector: 'jhi-voucher-payments-details-detail',
  templateUrl: './voucher-payments-details-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class VoucherPaymentsDetailsDetailComponent {
  voucherPaymentsDetails = input<IVoucherPaymentsDetails | null>(null);

  previousState(): void {
    window.history.back();
  }
}
