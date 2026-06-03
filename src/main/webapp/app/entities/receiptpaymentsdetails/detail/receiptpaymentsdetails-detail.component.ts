import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { IReceiptpaymentsdetails } from '../receiptpaymentsdetails.model';

@Component({
  standalone: true,
  selector: 'jhi-receiptpaymentsdetails-detail',
  templateUrl: './receiptpaymentsdetails-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class ReceiptpaymentsdetailsDetailComponent {
  receiptpaymentsdetails = input<IReceiptpaymentsdetails | null>(null);

  previousState(): void {
    window.history.back();
  }
}
