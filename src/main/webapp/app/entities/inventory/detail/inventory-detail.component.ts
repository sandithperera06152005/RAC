import { Component, effect, inject, input } from '@angular/core';
import { RouterModule } from '@angular/router';
import { of } from 'rxjs';
import { catchError } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { DataUtils } from 'app/core/util/data-util.service';
import { IInventory } from '../inventory.model';
import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/service/user.service';

@Component({
  standalone: true,
  selector: 'jhi-inventory-detail',
  templateUrl: './inventory-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class InventoryDetailComponent {
  inventory = input<IInventory | null>(null);
  employeeNames = new Map<number, string>();

  protected dataUtils = inject(DataUtils);
  protected userService = inject(UserService);

  constructor() {
    effect(() => {
      const inventory = this.inventory();
      if (inventory) {
        this.loadEmployeeName(inventory.lmu);
      }
    });
  }

  getEmployeeName(employeeId: number | null | undefined): string | number | null | undefined {
    return employeeId == null ? employeeId : this.employeeNames.get(employeeId) ?? employeeId;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  previousState(): void {
    window.history.back();
  }

  protected loadEmployeeName(employeeId: number | null | undefined): void {
    if (employeeId == null || employeeId <= 0 || this.employeeNames.has(employeeId)) {
      return;
    }

    this.userService
      .queryEmployeeNamesByIds([employeeId])
      .pipe(catchError(() => of({ body: [] as IUser[] })))
      .subscribe(response => {
        const employee = response.body?.find(user => user.id === employeeId);
        if (employee) {
          this.employeeNames = new Map(this.employeeNames).set(employeeId, employee.login ?? String(employeeId));
        }
      });
  }
}
