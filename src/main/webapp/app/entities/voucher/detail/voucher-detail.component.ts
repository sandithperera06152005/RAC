import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { IVoucher } from '../voucher.model';

@Component({
  standalone: true,
  selector: 'jhi-voucher-detail',
  templateUrl: './voucher-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class VoucherDetailComponent {
  voucher = input<IVoucher | null>(null);

  previousState(): void {
    window.history.back();
  }
}
