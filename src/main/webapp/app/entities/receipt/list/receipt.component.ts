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
import { IReceipt } from '../receipt.model';
import { EntityArrayResponseType, ReceiptService } from '../service/receipt.service';
import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/service/user.service';

@Component({
  standalone: true,
  selector: 'jhi-receipt',
  templateUrl: './receipt.component.html',
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
export class ReceiptComponent implements OnInit {
  subscription: Subscription | null = null;
  receipts?: IReceipt[];
  isLoading = false;
  createdByNames = new Map<number, string>();

  sortState = sortStateSignal({});

  itemsPerPage = ITEMS_PER_PAGE;
  totalItems = 0;
  page = 1;

  public router = inject(Router);
  protected receiptService = inject(ReceiptService);
  protected userService = inject(UserService);
  protected activatedRoute = inject(ActivatedRoute);
  protected sortService = inject(SortService);
  protected ngZone = inject(NgZone);

  trackId = (_index: number, item: IReceipt): number => this.receiptService.getReceiptIdentifier(item);

  formatAmount(value: number | null | undefined): string | number | null | undefined {
    return value == null
      ? value
      : new Intl.NumberFormat('en-US', {
          minimumFractionDigits: 2,
          maximumFractionDigits: 2,
        }).format(value);
  }

  ngOnInit(): void {
    this.subscription = combineLatest([this.activatedRoute.queryParamMap, this.activatedRoute.data])
      .pipe(
        tap(([params, data]) => this.fillComponentAttributeFromRoute(params, data)),
        tap(() => this.load()),
      )
      .subscribe();
  }

  getCreatedByName(createdById: number | null | undefined): string | number | null | undefined {
    return createdById == null ? createdById : this.createdByNames.get(createdById) ?? createdById;
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
    this.receipts = dataFromBody;
    this.loadCreatedByNames(dataFromBody);
  }

  protected fillComponentAttributesFromResponseBody(data: IReceipt[] | null): IReceipt[] {
    return data ?? [];
  }

  protected fillComponentAttributesFromResponseHeader(headers: HttpHeaders): void {
    this.totalItems = Number(headers.get(TOTAL_COUNT_RESPONSE_HEADER));
  }

  protected loadCreatedByNames(receipts: IReceipt[]): void {
    const createdByIds = receipts
      .map(receipt => receipt.createdby)
      .filter((createdById): createdById is number => createdById != null && createdById > 0)
      .filter((createdById, index, ids) => ids.indexOf(createdById) === index);

    if (createdByIds.length === 0) {
      this.createdByNames = new Map<number, string>();
      return;
    }

    this.userService
      .queryEmployeeNamesByIds(createdByIds)
      .pipe(catchError(() => of({ body: [] as IUser[] })))
      .subscribe(response => {
        this.createdByNames = this.createUserNameMap(response.body ?? []);
      });
  }

  protected createUserNameMap(users: IUser[]): Map<number, string> {
    const names = new Map<number, string>();
    users.forEach(user => {
      names.set(user.id, user.login ?? String(user.id));
    });
    return names;
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
    return this.receiptService.query(queryObject).pipe(tap(() => (this.isLoading = false)));
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
