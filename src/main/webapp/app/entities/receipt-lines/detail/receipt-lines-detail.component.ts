import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { IReceiptLines } from '../receipt-lines.model';

@Component({
  standalone: true,
  selector: 'jhi-receipt-lines-detail',
  templateUrl: './receipt-lines-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class ReceiptLinesDetailComponent {
  receiptLines = input<IReceiptLines | null>(null);

  previousState(): void {
    window.history.back();
  }
}
