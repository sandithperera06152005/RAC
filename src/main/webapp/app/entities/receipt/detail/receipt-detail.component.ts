import { Component, effect, inject, input } from '@angular/core';
import { RouterModule } from '@angular/router';
import { of } from 'rxjs';
import { catchError } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IReceipt } from '../receipt.model';
import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/service/user.service';
import { IPaymentterm } from 'app/entities/paymentterm/paymentterm.model';
import { PaymenttermService } from 'app/entities/paymentterm/service/paymentterm.service';

@Component({
  standalone: true,
  selector: 'jhi-receipt-detail',
  templateUrl: './receipt-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class ReceiptDetailComponent {
  receipt = input<IReceipt | null>(null);
  employeeNames = new Map<number, string>();
  paymentTermNames = new Map<number, string>();

  protected userService = inject(UserService);
  protected paymenttermService = inject(PaymenttermService);

  constructor() {
    effect(() => {
      const receipt = this.receipt();
      if (receipt) {
        this.loadEmployeeNames([receipt.createdby, receipt.lmu]);
        this.loadPaymentTermName(receipt.termid);
      }
    });
  }

  getEmployeeName(employeeId: number | null | undefined): string | number | null | undefined {
    return employeeId == null ? employeeId : this.employeeNames.get(employeeId) ?? employeeId;
  }

  getPaymentTermName(termId: number | null | undefined): string | number | null | undefined {
    return termId == null ? termId : this.paymentTermNames.get(termId) ?? termId;
  }

  previousState(): void {
    window.history.back();
  }

  protected loadEmployeeNames(employeeIds: Array<number | null | undefined>): void {
    const ids = employeeIds
      .filter((employeeId): employeeId is number => employeeId != null && employeeId > 0 && !this.employeeNames.has(employeeId))
      .filter((employeeId, index, allIds) => allIds.indexOf(employeeId) === index);

    if (ids.length === 0) {
      return;
    }

    this.userService
      .queryEmployeeNamesByIds(ids)
      .pipe(catchError(() => of({ body: [] as IUser[] })))
      .subscribe(response => {
        const names = new Map(this.employeeNames);
        (response.body ?? []).forEach(employee => {
          names.set(employee.id, employee.login ?? String(employee.id));
        });
        this.employeeNames = names;
      });
  }

  protected loadPaymentTermName(termId: number | null | undefined): void {
    if (termId == null || termId <= 0 || this.paymentTermNames.has(termId)) {
      return;
    }

    this.paymenttermService
      .find(termId)
      .pipe(catchError(() => of({ body: null as IPaymentterm | null })))
      .subscribe(response => {
        if (response.body?.paymenttype) {
          this.paymentTermNames = new Map(this.paymentTermNames).set(termId, response.body.paymenttype);
        }
      });
  }
}
