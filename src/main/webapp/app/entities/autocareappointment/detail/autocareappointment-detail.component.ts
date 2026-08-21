import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IAutocareappointment } from '../autocareappointment.model';

@Component({
  standalone: true,
  selector: 'jhi-autocareappointment-detail',
  templateUrl: './autocareappointment-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class AutocareappointmentDetailComponent {
  autocareappointment = input<IAutocareappointment | null>(null);

  appointmentTypeLabel(appointmenttype: number | null | undefined): string {
    const appointmentTypeMap: Record<number, string> = {
      1: 'Full Service and Other Services',
      2: 'Detailing Services',
      3: 'Performance Care',
      4: 'Other',
    };

    return appointmenttype ? appointmentTypeMap[appointmenttype] ?? `Type ${appointmenttype}` : 'Not Set';
  }

  statusLabel(): string {
    const appointment = this.autocareappointment();
    if (!appointment) {
      return 'Not Set';
    }
    if (appointment.iscancel === true) {
      return 'Cancelled';
    }
    if (appointment.isarrived === true) {
      return 'Arrived';
    }
    if (appointment.isconformed === true) {
      return 'Confirmed';
    }
    if (appointment.isnoanswer === true) {
      return 'No Answer';
    }
    return 'Pending';
  }

  statusBadgeClass(): string {
    const appointment = this.autocareappointment();
    if (appointment?.iscancel === true) {
      return 'bg-danger';
    }
    if (appointment?.isarrived === true) {
      return 'bg-success';
    }
    if (appointment?.isconformed === true) {
      return 'bg-primary';
    }
    if (appointment?.isnoanswer === true) {
      return 'bg-warning text-dark';
    }
    return 'bg-secondary';
  }

  previousState(): void {
    window.history.back();
  }
}
