import { Component, effect, inject, input } from '@angular/core';
import { RouterModule } from '@angular/router';
import { of } from 'rxjs';
import { catchError } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { ICustomer } from '../customer.model';
import { CustomerService, ICustomerTypeName } from '../service/customer.service';
import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/service/user.service';

@Component({
  standalone: true,
  selector: 'jhi-customer-detail',
  templateUrl: './customer-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class CustomerDetailComponent {
  customer = input<ICustomer | null>(null);
  customerTypeNames = new Map<number, string>();
  employeeNames = new Map<number, string>();

  protected customerService = inject(CustomerService);
  protected userService = inject(UserService);

  constructor() {
    effect(() => {
      const customer = this.customer();
      if (customer) {
        this.loadCustomerTypeName(customer.customertype);
        this.loadEmployeeName(customer.lmu);
      }
    });
  }

  getCustomerTypeName(customerTypeId: number | null | undefined): string | number | null | undefined {
    return customerTypeId == null ? customerTypeId : this.customerTypeNames.get(customerTypeId) ?? customerTypeId;
  }

  getEmployeeName(employeeId: number | null | undefined): string | number | null | undefined {
    return employeeId == null ? employeeId : this.employeeNames.get(employeeId) ?? employeeId;
  }

  previousState(): void {
    window.history.back();
  }

  protected loadCustomerTypeName(customerTypeId: number | null | undefined): void {
    if (customerTypeId == null || customerTypeId <= 0 || this.customerTypeNames.has(customerTypeId)) {
      return;
    }

    this.customerService
      .queryCustomerTypeNamesByIds([customerTypeId])
      .pipe(catchError(() => of({ body: [] as ICustomerTypeName[] })))
      .subscribe(response => {
        const customerType = response.body?.find(type => type.id === customerTypeId);
        if (customerType?.customerTypeName) {
          this.customerTypeNames = new Map(this.customerTypeNames).set(customerTypeId, customerType.customerTypeName);
        }
      });
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
