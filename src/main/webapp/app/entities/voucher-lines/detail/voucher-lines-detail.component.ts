import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { IVoucherLines } from '../voucher-lines.model';

@Component({
  standalone: true,
  selector: 'jhi-voucher-lines-detail',
  templateUrl: './voucher-lines-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class VoucherLinesDetailComponent {
  voucherLines = input<IVoucherLines | null>(null);

  previousState(): void {
    window.history.back();
  }
}
