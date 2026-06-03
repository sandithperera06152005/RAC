import { Component, NgZone, OnInit, inject } from '@angular/core';
import { HttpHeaders } from '@angular/common/http';
import { ActivatedRoute, Data, ParamMap, Router, RouterModule } from '@angular/router';
import { Observable, Subscription, combineLatest, filter, tap } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { SortByDirective, SortDirective, SortService, type SortState, sortStateSignal } from 'app/shared/sort';
import { DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { ItemCountComponent } from 'app/shared/pagination';
import { FormsModule } from '@angular/forms';
import { ITEMS_PER_PAGE, PAGE_HEADER, TOTAL_COUNT_RESPONSE_HEADER } from 'app/config/pagination.constants';
import { DEFAULT_SORT_DATA, ITEM_DELETED_EVENT, SORT } from 'app/config/navigation.constants';
import { FilterComponent, FilterOptions, IFilterOption, IFilterOptions } from 'app/shared/filter';
import { IAutojobsinvoice } from '../autojobsinvoice.model';

import { AutojobsinvoiceService, EntityArrayResponseType } from '../service/autojobsinvoice.service';
import { AutojobsinvoiceDeleteDialogComponent } from '../delete/autojobsinvoice-delete-dialog.component';

@Component({
  standalone: true,
  selector: 'jhi-autojobsinvoice',
  templateUrl: './autojobsinvoice.component.html',
  imports: [
    RouterModule,
    FormsModule,
    SharedModule,
    SortDirective,
    SortByDirective,
    DurationPipe,
    FormatMediumDatetimePipe,
    FormatMediumDatePipe,
    FilterComponent,
    ItemCountComponent,
  ],
})
export class AutojobsinvoiceComponent implements OnInit {
  subscription: Subscription | null = null;
  autojobsinvoices?: IAutojobsinvoice[];
  isLoading = false;

  sortState = sortStateSignal({});
  filters: IFilterOptions = new FilterOptions();

  itemsPerPage = ITEMS_PER_PAGE;
  totalItems = 0;
  page = 1;
  searchQuery = '';
  searchByCode = true;
  searchByCustomerName = false;
  searchByJobId = false;
  searchByDateRange = false;
  startDate = '';
  endDate = '';

  public router = inject(Router);
  protected autojobsinvoiceService = inject(AutojobsinvoiceService);
  protected activatedRoute = inject(ActivatedRoute);
  protected sortService = inject(SortService);
  protected modalService = inject(NgbModal);
  protected ngZone = inject(NgZone);

  trackId = (item: IAutojobsinvoice): number => this.autojobsinvoiceService.getAutojobsinvoiceIdentifier(item);

  ngOnInit(): void {
    this.subscription = combineLatest([this.activatedRoute.queryParamMap, this.activatedRoute.data])
      .pipe(
        tap(([params, data]) => this.fillComponentAttributeFromRoute(params, data)),
        tap(() => this.load()),
      )
      .subscribe();

    this.filters.filterChanges.subscribe(filterOptions => this.handleNavigation(1, this.sortState(), filterOptions));
  }

  delete(autojobsinvoice: IAutojobsinvoice): void {
    const modalRef = this.modalService.open(AutojobsinvoiceDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.autojobsinvoice = autojobsinvoice;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed
      .pipe(
        filter(reason => reason === ITEM_DELETED_EVENT),
        tap(() => this.load()),
      )
      .subscribe();
  }

  load(): void {
    this.queryBackend().subscribe({
      next: (res: EntityArrayResponseType) => {
        this.onResponseSuccess(res);
      },
    });
  }

  navigateToWithComponentValues(event: SortState): void {
    this.handleNavigation(this.page, event, this.filters.filterOptions);
  }

  navigateToPage(page: number): void {
    this.handleNavigation(page, this.sortState(), this.filters.filterOptions);
  }

  protected fillComponentAttributeFromRoute(params: ParamMap, data: Data): void {
    const page = params.get(PAGE_HEADER);
    this.page = +(page ?? 1);
    this.sortState.set(this.sortService.parseSortParam(params.get(SORT) ?? data[DEFAULT_SORT_DATA]));
    this.filters.initializeFromParams(params);

    // Read search params from route
    this.searchQuery = params.get('code.contains') ?? params.get('customername.contains') ?? params.get('jobid.equals') ?? '';

    if (params.get('code.contains')) {
      this.toggleFilterState('code');
    } else if (params.get('customername.contains')) {
      this.toggleFilterState('name');
    } else if (params.get('jobid.equals')) {
      this.toggleFilterState('job');
    }

    if (params.get('invoicedate.greaterThanOrEqual') || params.get('invoicedate.lessThanOrEqual')) {
      this.toggleFilterState('date');
      this.startDate = params.get('invoicedate.greaterThanOrEqual')?.split('T')[0] ?? '';
      this.endDate = params.get('invoicedate.lessThanOrEqual')?.split('T')[0] ?? '';
    }
  }

  private toggleFilterState(filter: string): void {
    this.searchByCode = filter === 'code';
    this.searchByCustomerName = filter === 'name';
    this.searchByJobId = filter === 'job';
    this.searchByDateRange = filter === 'date';
  }

  protected onResponseSuccess(response: EntityArrayResponseType): void {
    this.fillComponentAttributesFromResponseHeader(response.headers);
    const dataFromBody = this.fillComponentAttributesFromResponseBody(response.body);
    this.autojobsinvoices = dataFromBody;
  }

  protected fillComponentAttributesFromResponseBody(data: IAutojobsinvoice[] | null): IAutojobsinvoice[] {
    return data ?? [];
  }

  protected fillComponentAttributesFromResponseHeader(headers: HttpHeaders): void {
    this.totalItems = Number(headers.get(TOTAL_COUNT_RESPONSE_HEADER));
  }

  protected queryBackend(): Observable<EntityArrayResponseType> {
    const { page, filters } = this;

    this.isLoading = true;
    const queryObject: any = {
      page: page - 1,
      size: this.itemsPerPage,
      sort: this.sortService.buildSortParam(this.sortState()),
    };
    filters.filterOptions.forEach(filterOption => {
      queryObject[filterOption.name] = filterOption.values;
    });

    if (this.searchQuery) {
      if (this.searchByCode) {
        queryObject['code.contains'] = this.searchQuery;
      } else if (this.searchByCustomerName) {
        queryObject['customername.contains'] = this.searchQuery;
      } else if (this.searchByJobId) {
        queryObject['jobid.equals'] = this.searchQuery;
      }
    }

    if (this.searchByDateRange) {
      if (this.startDate) {
        queryObject['invoicedate.greaterThanOrEqual'] = this.startDate + 'T00:00:00Z';
      }
      if (this.endDate) {
        queryObject['invoicedate.lessThanOrEqual'] = this.endDate + 'T23:59:59Z';
      }
    }

    return this.autojobsinvoiceService.query(queryObject).pipe(tap(() => (this.isLoading = false)));
  }

  protected handleNavigation(page: number, sortState: SortState, filterOptions?: IFilterOption[]): void {
    const queryParamsObj: any = {
      page,
      size: this.itemsPerPage,
      sort: this.sortService.buildSortParam(sortState),
    };

    filterOptions?.forEach(filterOption => {
      queryParamsObj[filterOption.nameAsQueryParam()] = filterOption.values;
    });

    if (this.searchQuery) {
      if (this.searchByCode) {
        queryParamsObj['code.contains'] = this.searchQuery;
      } else if (this.searchByCustomerName) {
        queryParamsObj['customername.contains'] = this.searchQuery;
      } else if (this.searchByJobId) {
        queryParamsObj['jobid.equals'] = this.searchQuery;
      }
    }

    if (this.searchByDateRange) {
      if (this.startDate) {
        queryParamsObj['invoicedate.greaterThanOrEqual'] = this.startDate + 'T00:00:00Z';
      }
      if (this.endDate) {
        queryParamsObj['invoicedate.lessThanOrEqual'] = this.endDate + 'T23:59:59Z';
      }
    }

    this.ngZone.run(() => {
      this.router.navigate(['./'], {
        relativeTo: this.activatedRoute,
        queryParams: queryParamsObj,
      });
    });
  }

  toggleFilter(filter: string): void {
    this.toggleFilterState(filter);
    this.filterData();
  }

  filterData(): void {
    this.page = 1;
    this.handleNavigation(this.page, this.sortState(), this.filters.filterOptions);
  }
}
