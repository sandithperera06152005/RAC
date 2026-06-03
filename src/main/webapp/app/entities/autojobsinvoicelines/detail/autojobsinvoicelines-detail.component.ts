import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { IAutojobsinvoicelines } from '../autojobsinvoicelines.model';

@Component({
  standalone: true,
  selector: 'jhi-autojobsinvoicelines-detail',
  templateUrl: './autojobsinvoicelines-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class AutojobsinvoicelinesDetailComponent {
  autojobsinvoicelines = input<IAutojobsinvoicelines | null>(null);

  previousState(): void {
    window.history.back();
  }
}
