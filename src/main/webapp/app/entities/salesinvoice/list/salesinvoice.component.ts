import { Component, NgZone, inject, OnInit } from '@angular/core';
import { HttpHeaders } from '@angular/common/http';
import { ActivatedRoute, Data, ParamMap, Router, RouterModule } from '@angular/router';
import { combineLatest, filter, Observable, Subscription, tap } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { sortStateSignal, SortDirective, SortByDirective, type SortState, SortService } from 'app/shared/sort';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { ItemCountComponent } from 'app/shared/pagination';
import { FormsModule } from '@angular/forms';

import { ITEMS_PER_PAGE, PAGE_HEADER, TOTAL_COUNT_RESPONSE_HEADER } from 'app/config/pagination.constants';
import { SORT, ITEM_DELETED_EVENT, DEFAULT_SORT_DATA } from 'app/config/navigation.constants';
import { ISalesinvoice } from '../salesinvoice.model';
import { EntityArrayResponseType, SalesinvoiceService } from '../service/salesinvoice.service';
import { SalesinvoiceDeleteDialogComponent } from '../delete/salesinvoice-delete-dialog.component';

@Component({
  standalone: true,
  selector: 'jhi-salesinvoice',
  templateUrl: './salesinvoice.component.html',
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
export class SalesinvoiceComponent implements OnInit {
  subscription: Subscription | null = null;
  salesinvoices?: ISalesinvoice[];
  isLoading = false;

  sortState = sortStateSignal({});

  itemsPerPage = ITEMS_PER_PAGE;
  totalItems = 0;
  page = 1;
  searchQuery = '';
  searchByCode = true;
  searchByCustomerName = false;
  searchByVehicleNo = false;
  searchByDateRange = false;
  startDate = '';
  endDate = '';
  originalSalesinvoices?: ISalesinvoice[];

  public router = inject(Router);
  protected salesinvoiceService = inject(SalesinvoiceService);
  protected activatedRoute = inject(ActivatedRoute);
  protected sortService = inject(SortService);
  protected modalService = inject(NgbModal);
  protected ngZone = inject(NgZone);

  trackId = (_index: number, item: ISalesinvoice): number => this.salesinvoiceService.getSalesinvoiceIdentifier(item);

  ngOnInit(): void {
    this.subscription = combineLatest([this.activatedRoute.queryParamMap, this.activatedRoute.data])
      .pipe(
        tap(([params, data]) => this.fillComponentAttributeFromRoute(params, data)),
        tap(() => this.load()),
      )
      .subscribe();
  }

  delete(salesinvoice: ISalesinvoice): void {
    const modalRef = this.modalService.open(SalesinvoiceDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.salesinvoice = salesinvoice;
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
    this.handleNavigation(this.page, event);
  }

  navigateToPage(page: number): void {
    this.handleNavigation(page, this.sortState());
  }

  protected fillComponentAttributeFromRoute(params: ParamMap, data: Data): void {
    const page = params.get(PAGE_HEADER);
    this.page = +(page ?? 1);
    this.sortState.set(this.sortService.parseSortParam(params.get(SORT) ?? data[DEFAULT_SORT_DATA]));

    // Read search params from route
    this.searchQuery = params.get('code.contains') ?? params.get('customername.contains') ?? params.get('vehicleno.contains') ?? '';

    if (params.get('code.contains')) {
      this.toggleFilterState('code');
    } else if (params.get('customername.contains')) {
      this.toggleFilterState('name');
    } else if (params.get('vehicleno.contains')) {
      this.toggleFilterState('vehicle');
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
    this.searchByVehicleNo = filter === 'vehicle';
    this.searchByDateRange = filter === 'date';
  }

  protected onResponseSuccess(response: EntityArrayResponseType): void {
    this.fillComponentAttributesFromResponseHeader(response.headers);
    const dataFromBody = this.fillComponentAttributesFromResponseBody(response.body);
    this.salesinvoices = dataFromBody;
  }

  protected fillComponentAttributesFromResponseBody(data: ISalesinvoice[] | null): ISalesinvoice[] {
    return data ?? [];
  }

  protected fillComponentAttributesFromResponseHeader(headers: HttpHeaders): void {
    this.totalItems = Number(headers.get(TOTAL_COUNT_RESPONSE_HEADER));
  }

  protected queryBackend(): Observable<EntityArrayResponseType> {
    const { page } = this;

    this.isLoading = true;
    const queryObject: any = {
      page: page - 1,
      size: this.itemsPerPage,
      sort: this.sortService.buildSortParam(this.sortState()),
    };

    if (this.searchQuery) {
      if (this.searchByCode) {
        queryObject['code.contains'] = this.searchQuery;
      } else if (this.searchByCustomerName) {
        queryObject['customername.contains'] = this.searchQuery;
      } else if (this.searchByVehicleNo) {
        queryObject['vehicleno.contains'] = this.searchQuery;
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

    return this.salesinvoiceService.query(queryObject).pipe(tap(() => (this.isLoading = false)));
  }

  protected handleNavigation(page: number, sortState: SortState): void {
    const queryParamsObj: any = {
      page,
      size: this.itemsPerPage,
      sort: this.sortService.buildSortParam(sortState),
    };

    if (this.searchQuery) {
      if (this.searchByCode) {
        queryParamsObj['code.contains'] = this.searchQuery;
      } else if (this.searchByCustomerName) {
        queryParamsObj['customername.contains'] = this.searchQuery;
      } else if (this.searchByVehicleNo) {
        queryParamsObj['vehicleno.contains'] = this.searchQuery;
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
  printInvoice(id: number | null | undefined): void {
    if (!id) {
      return;
    }

    window.open('/printinvoice?id=' + id, '_blank');
  }

  toggleFilter(filter: string): void {
    this.toggleFilterState(filter);
    this.filterData();
  }

  filterData(): void {
    this.page = 1;
    this.handleNavigation(this.page, this.sortState());
  }
}
