import { Component, NgZone, inject, OnInit } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Data, ParamMap, Router, RouterModule } from '@angular/router';
import { combineLatest, filter, Observable, Subscription, tap } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { sortStateSignal, SortDirective, SortByDirective, type SortState, SortService } from 'app/shared/sort';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { ItemCountComponent } from 'app/shared/pagination';
import { FormsModule } from '@angular/forms';
import dayjs from 'dayjs/esm';

import { ITEMS_PER_PAGE, PAGE_HEADER, TOTAL_COUNT_RESPONSE_HEADER } from 'app/config/pagination.constants';
import { SORT, ITEM_DELETED_EVENT, DEFAULT_SORT_DATA } from 'app/config/navigation.constants';
import { IAutocarejob } from '../autocarejob.model';
import { EntityArrayResponseType, AutocarejobService } from '../service/autocarejob.service';
import { AutocarejobDeleteDialogComponent } from '../delete/autocarejob-delete-dialog.component';
import { AutojobsinvoiceService } from 'app/entities/autojobsinvoice/service/autojobsinvoice.service';
import { IAutojobsinvoice } from 'app/entities/autojobsinvoice/autojobsinvoice.model';

@Component({
  standalone: true,
  selector: 'jhi-autocareclosejob',
  templateUrl: './autocareclosejob.component.html',
  styles: [
    `
      .closed-job-table-wrap {
        overflow-x: visible;
        width: 100%;
      }

      .closed-job-table-wrap .table {
        margin-bottom: 0;
        table-layout: fixed;
        width: 100%;
      }

      .closed-job-table-wrap th,
      .closed-job-table-wrap td {
        overflow: hidden;
        padding-left: 0.35rem;
        padding-right: 0.35rem;
        text-overflow: ellipsis;
        vertical-align: middle;
        white-space: nowrap;
      }

      .closed-job-table-wrap th .d-flex {
        align-items: center;
        min-width: 0;
      }

      .closed-job-table-wrap th span {
        overflow: hidden;
        text-overflow: ellipsis;
      }

      .closed-job-table-wrap th:nth-child(1),
      .closed-job-table-wrap td:nth-child(1) {
        width: 9%;
      }

      .closed-job-table-wrap th:nth-child(2),
      .closed-job-table-wrap td:nth-child(2) {
        width: 11%;
      }

      .closed-job-table-wrap th:nth-child(3),
      .closed-job-table-wrap td:nth-child(3) {
        width: 8%;
      }

      .closed-job-table-wrap th:nth-child(4),
      .closed-job-table-wrap td:nth-child(4) {
        width: 9%;
      }

      .closed-job-table-wrap th:nth-child(5),
      .closed-job-table-wrap td:nth-child(5) {
        width: 20%;
      }

      .closed-job-table-wrap th:nth-child(6),
      .closed-job-table-wrap td:nth-child(6) {
        width: 20%;
      }

      .closed-job-table-wrap th:nth-child(7),
      .closed-job-table-wrap td:nth-child(7) {
        width: 18%;
      }

      .closed-job-table-wrap th:nth-child(8),
      .closed-job-table-wrap td:nth-child(8) {
        text-align: center !important;
        width: 5%;
      }

      .closed-job-table-wrap .btn-group {
        display: inline-flex;
        justify-content: center;
        width: 100%;
      }
    `,
  ],
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
export class AutocareclosejobComponent implements OnInit {
  subscription: Subscription | null = null;
  autocarejobs?: IAutocarejob[];
  isLoading = false;
  filteredAutocarejobs: IAutocarejob[] = [];
  searchText: string = '';

  sortState = sortStateSignal({});

  itemsPerPage = ITEMS_PER_PAGE;
  totalItems = 0;
  page = 1;

  public router = inject(Router);
  protected autocarejobService = inject(AutocarejobService);
  protected autojobsinvoiceService = inject(AutojobsinvoiceService);
  protected activatedRoute = inject(ActivatedRoute);
  protected sortService = inject(SortService);
  protected modalService = inject(NgbModal);
  protected ngZone = inject(NgZone);

  trackId = (_index: number, item: IAutocarejob): number => this.autocarejobService.getAutocarejobIdentifier(item);

  ngOnInit(): void {
    this.subscription = combineLatest([this.activatedRoute.queryParamMap, this.activatedRoute.data])
      .pipe(
        tap(([params, data]) => this.fillComponentAttributeFromRoute(params, data)),
        tap(() => this.load()),
      )
      .subscribe();
  }

  navigateToInvoice(job: IAutocarejob): void {
    if (job.id == null) return;
    this.autojobsinvoiceService.query({ 'jobid.equals': job.id, page: 0, size: 1 }).subscribe({
      next: (res: HttpResponse<IAutojobsinvoice[]>) => {
        const invoices = res.body || [];
        if (invoices.length > 0 && invoices[0].id != null) {
          this.router.navigate(['/salesinvoice', 'new'], { queryParams: { id: invoices[0].id } });
        } else {
          alert('No invoice found for this job.');
        }
      },
      error: () => {
        alert('Failed to load invoice. Please try again.');
      },
    });
  }

  delete(autocarejob: IAutocarejob): void {
    const modalRef = this.modalService.open(AutocarejobDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.autocarejob = autocarejob;
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
        const today = dayjs().format('YYYY-MM-DD');

        this.autocarejobs = this.autocarejobs?.filter(job => job.isjobclose && job.jobdate?.format('YYYY-MM-DD') === today);
        this.filterJobs(); // Apply filtering when loading data
      },
    });
  }
  filterJobs(): void {
    if (!this.autocarejobs) return;
    const today = dayjs().format('YYYY-MM-DD');

    this.filteredAutocarejobs = this.autocarejobs.filter(
      job =>
        job.vehiclenumber?.toLowerCase().includes(this.searchText.toLowerCase()) &&
        job.isjobclose &&
        job.jobdate?.format('YYYY-MM-DD') === today,
    );
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
    this.autocarejobs = dataFromBody;
  }

  protected fillComponentAttributesFromResponseBody(data: IAutocarejob[] | null): IAutocarejob[] {
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
    return this.autocarejobService.query(queryObject).pipe(tap(() => (this.isLoading = false)));
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
