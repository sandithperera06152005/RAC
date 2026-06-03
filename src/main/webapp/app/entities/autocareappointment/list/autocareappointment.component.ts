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
import { SORT, ITEM_DELETED_EVENT, DEFAULT_SORT_DATA, ITEM_UPDATED_EVENT } from 'app/config/navigation.constants';
import { IAutocareappointment } from '../autocareappointment.model';
import { EntityArrayResponseType, AutocareappointmentService } from '../service/autocareappointment.service';
import { AutocareappointmentDeleteDialogComponent } from '../delete/autocareappointment-delete-dialog.component';
import { AutocareappointmentUpdateDialogComponent } from '../delete/autocareappointment-update-dialog.component';
@Component({
  standalone: true,
  selector: 'jhi-autocareappointment',
  templateUrl: './autocareappointment.component.html',
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
export class AutocareappointmentComponent implements OnInit {
  subscription: Subscription | null = null;
  autocareappointments?: IAutocareappointment[];
  isLoading = false;

  sortState = sortStateSignal({});
  showConfirmButton = false;
  itemsPerPage = ITEMS_PER_PAGE;
  totalItems = 0;
  page = 1;

  public router = inject(Router);
  protected autocareappointmentService = inject(AutocareappointmentService);
  protected activatedRoute = inject(ActivatedRoute);
  protected sortService = inject(SortService);
  protected modalService = inject(NgbModal);
  protected ngZone = inject(NgZone);
  showUpdateMissedButton = false;
  trackId = (_index: number, item: IAutocareappointment): number => this.autocareappointmentService.getAutocareappointmentIdentifier(item);

  ngOnInit(): void {
    this.subscription = combineLatest([this.activatedRoute.queryParamMap, this.activatedRoute.data])
      .pipe(
        tap(([params, data]) => this.fillComponentAttributeFromRoute(params, data)),
        tap(() => this.load()),
      )
      .subscribe();

    this.subscription = this.activatedRoute.queryParamMap.subscribe(params => {
      const sort = params.get('sort'); // Get the 'sort' query parameter
      // Check if the sort parameter is 'isconformed,asc'
      if (sort === 'isconformed,asc') {
        this.showConfirmButton = true;
      } else {
        this.showConfirmButton = false;
      }
    });

    this.subscription = this.activatedRoute.queryParamMap.subscribe(params => {
      const page = params.get('page');
      const size = params.get('size');
      const sort = params.get('sort');

      // Check if the URL contains the expected parameters
      this.showUpdateMissedButton = page === '1' && size === '20' && sort === 'missedappointmentcall,desc';
    });
  }
  checkCurrentUrl(): void {
    // Get the current URL
    const currentUrl = this.router.url;

    // Define the target URL path
    const targetUrl = '/autocareappointment?page=1&size=20&sort=missedappointmentcall,desc';

    // Check if the current URL matches the target URL
    this.showUpdateMissedButton = currentUrl === targetUrl;
  }

  delete(autocareappointment: IAutocareappointment): void {
    const modalRef = this.modalService.open(AutocareappointmentDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.autocareappointment = autocareappointment;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed
      .pipe(
        filter(reason => reason === ITEM_DELETED_EVENT),
        tap(() => this.load()),
      )
      .subscribe();
  }

  tick(autocareappointment: IAutocareappointment): void {
    // Open the modal to confirm the 'tick' action (confirmation for 'isconformed' update)
    const modalRef = this.modalService.open(AutocareappointmentUpdateDialogComponent, { size: 'lg', backdrop: 'static' });

    // Pass the autocareappointment object (it can have the 'isconformed' value set to true in the modal)
    autocareappointment.isconformed = true;
    modalRef.componentInstance.autocareappointment = autocareappointment;

    // Handle modal close and reload the data if updated (event for the 'tick' action)
    modalRef.closed
      .pipe(
        filter(reason => reason === ITEM_UPDATED_EVENT), // Listen for the update event
        tap(() => this.load()), // Reload or refresh the data after updating
      )
      .subscribe();
  }

  updateMissedAppointment(autocareappointment: IAutocareappointment): void {
    // Open modal for updating missed appointment call
    const modalRef = this.modalService.open(AutocareappointmentUpdateDialogComponent, { size: 'lg', backdrop: 'static' });

    // Pass the autocareappointment object to the modal
    modalRef.componentInstance.autocareappointment = { ...autocareappointment };

    // Handle modal close and update the UI with the new value
    modalRef.closed
      .pipe(
        filter(reason => reason === ITEM_UPDATED_EVENT), // Listen for update event
        tap(() => this.load()), // Reload the updated data
      )
      .subscribe();
  }

  updateUrl(order: string): void {
    console.log('Current URL:', window.location.href); // ✅ Logs the current URL

    let newUrl = '';

    if (order === 'change') {
      newUrl = '/autocareappointment?page=1&size=20&sort=missedappointmentcall,desc';
    } else {
      const params = new URLSearchParams({
        page: '1',
        size: '20',
        sort: `isconformed,${order}`,
      });

      newUrl = `/autocareappointment?${params.toString()}`;
    }

    this.router.navigateByUrl(newUrl); // ✅ Navigate to the new URL
    console.log('Updated URL:', newUrl); // ✅ Logs the updated URL
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
    this.autocareappointments = dataFromBody;
  }

  protected fillComponentAttributesFromResponseBody(data: IAutocareappointment[] | null): IAutocareappointment[] {
    return data ?? [];
  }

  protected fillComponentAttributesFromResponseHeader(headers: HttpHeaders): void {
    this.totalItems = Number(headers.get(TOTAL_COUNT_RESPONSE_HEADER));
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
    return this.autocareappointmentService.query(queryObject).pipe(tap(() => (this.isLoading = false)));
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
