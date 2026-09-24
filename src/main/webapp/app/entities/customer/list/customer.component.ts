import { Component, NgZone, inject, OnInit } from '@angular/core';
import { HttpHeaders } from '@angular/common/http';
import { ActivatedRoute, Data, ParamMap, Router, RouterModule } from '@angular/router';
import { combineLatest, Observable, of, Subscription, tap } from 'rxjs';
import { catchError } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { sortStateSignal, SortDirective, SortByDirective, type SortState, SortService } from 'app/shared/sort';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { ItemCountComponent } from 'app/shared/pagination';
import { FormsModule } from '@angular/forms';

import { ITEMS_PER_PAGE, PAGE_HEADER, TOTAL_COUNT_RESPONSE_HEADER } from 'app/config/pagination.constants';
import { SORT, DEFAULT_SORT_DATA } from 'app/config/navigation.constants';
import { ICustomer } from '../customer.model';
import { EntityArrayResponseType, CustomerService, ICustomerTypeName } from '../service/customer.service';
import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/service/user.service';

@Component({
  standalone: true,
  selector: 'jhi-customer',
  templateUrl: './customer.component.html',
  imports: [
    RouterModule,
    FormsModule,
    SharedModule,
    SortDirective,
    SortByDirective,
    DurationPipe,
    FormatMediumDatetimePipe,
    FormatMediumDatePipe,
    ItemCountComponent,
  ],
})
export class CustomerComponent implements OnInit {
  subscription: Subscription | null = null;
  customers?: ICustomer[];
  isLoading = false;
  customerTypeNames = new Map<number, string>();
  lmuNames = new Map<number, string>();

  sortState = sortStateSignal({});

  itemsPerPage = ITEMS_PER_PAGE;
  totalItems = 0;
  page = 1;

  public router = inject(Router);
  protected customerService = inject(CustomerService);
  protected userService = inject(UserService);
  protected activatedRoute = inject(ActivatedRoute);
  protected sortService = inject(SortService);
  protected ngZone = inject(NgZone);

  trackId = (_index: number, item: ICustomer): number => this.customerService.getCustomerIdentifier(item);

  ngOnInit(): void {
    this.subscription = combineLatest([this.activatedRoute.queryParamMap, this.activatedRoute.data])
      .pipe(
        tap(([params, data]) => this.fillComponentAttributeFromRoute(params, data)),
        tap(() => this.load()),
      )
      .subscribe();
  }

  getCustomerTypeName(customerTypeId: number | null | undefined): string | number | null | undefined {
    return customerTypeId == null ? customerTypeId : this.customerTypeNames.get(customerTypeId) ?? customerTypeId;
  }

  getEmployeeName(employeeId: number | null | undefined): string | number | null | undefined {
    return employeeId == null ? employeeId : this.lmuNames.get(employeeId) ?? employeeId;
  }

  load(): void {
    this.queryBackend().subscribe({
      next: (res: EntityArrayResponseType) => {
        this.onResponseSuccess(res);
      },
    });
  }

  navigateToWithComponentValues(event: SortState): void {
    this.handleNavigation(this.page, event);
  }

  navigateToPage(page: number): void {
    this.handleNavigation(page, this.sortState());
  }

  protected fillComponentAttributeFromRoute(params: ParamMap, data: Data): void {
    const page = params.get(PAGE_HEADER);
    this.page = +(page ?? 1);
    this.sortState.set(this.sortService.parseSortParam(params.get(SORT) ?? data[DEFAULT_SORT_DATA]));
  }

  protected onResponseSuccess(response: EntityArrayResponseType): void {
    this.fillComponentAttributesFromResponseHeader(response.headers);
    const dataFromBody = this.fillComponentAttributesFromResponseBody(response.body);
    this.customers = dataFromBody;
    this.loadCustomerLookups(dataFromBody);
  }

  protected fillComponentAttributesFromResponseBody(data: ICustomer[] | null): ICustomer[] {
    return data ?? [];
  }

  protected fillComponentAttributesFromResponseHeader(headers: HttpHeaders): void {
    this.totalItems = Number(headers.get(TOTAL_COUNT_RESPONSE_HEADER));
  }

  protected loadCustomerLookups(customers: ICustomer[]): void {
    this.loadCustomerTypeNames(customers);
    this.loadEmployeeNames(customers);
  }

  protected loadCustomerTypeNames(customers: ICustomer[]): void {
    const customerTypeIds = this.uniquePositiveNumbers(customers.map(customer => customer.customertype));

    if (customerTypeIds.length === 0) {
      this.customerTypeNames = new Map<number, string>();
      return;
    }

    this.customerService
      .queryCustomerTypeNamesByIds(customerTypeIds)
      .pipe(catchError(() => of({ body: [] as ICustomerTypeName[] })))
      .subscribe(response => {
        this.customerTypeNames = this.createCustomerTypeNameMap(response.body ?? []);
      });
  }

  protected loadEmployeeNames(customers: ICustomer[]): void {
    const employeeIds = this.uniquePositiveNumbers(customers.map(customer => customer.lmu));

    if (employeeIds.length === 0) {
      this.lmuNames = new Map<number, string>();
      return;
    }

    this.userService
      .queryEmployeeNamesByIds(employeeIds)
      .pipe(catchError(() => of({ body: [] as IUser[] })))
      .subscribe(response => {
        this.lmuNames = this.createUserNameMap(response.body ?? []);
      });
  }

  protected createCustomerTypeNameMap(customerTypes: ICustomerTypeName[]): Map<number, string> {
    const names = new Map<number, string>();
    customerTypes.forEach(customerType => {
      if (customerType.customerTypeName) {
        names.set(customerType.id, customerType.customerTypeName);
      }
    });
    return names;
  }

  protected createUserNameMap(users: IUser[]): Map<number, string> {
    const names = new Map<number, string>();
    users.forEach(user => {
      names.set(user.id, user.login ?? String(user.id));
    });
    return names;
  }

  protected uniquePositiveNumbers(values: Array<number | null | undefined>): number[] {
    return values
      .filter((value): value is number => value != null && value > 0)
      .filter((value, index, ids) => ids.indexOf(value) === index);
  }

  protected queryBackend(): Observable<EntityArrayResponseType> {
    const { page } = this;

    this.isLoading = true;
    const pageToLoad: number = page;
    const queryObject: any = {
      page: pageToLoad - 1,
      size: this.itemsPerPage,
      sort: this.sortService.buildSortParam(this.sortState()),
    };
    return this.customerService.query(queryObject).pipe(tap(() => (this.isLoading = false)));
  }

  protected handleNavigation(page: number, sortState: SortState): void {
    const queryParamsObj = {
      page,
      size: this.itemsPerPage,
      sort: this.sortService.buildSortParam(sortState),
    };

    this.ngZone.run(() => {
      this.router.navigate(['./'], {
        relativeTo: this.activatedRoute,
        queryParams: queryParamsObj,
      });
    });
  }
}
