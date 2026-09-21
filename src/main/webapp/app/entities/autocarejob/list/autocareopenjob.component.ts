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
import { AutocareclosejobComponent } from '../list/autocareclosejob.component';
import { AutojobsinvoiceService } from 'app/entities/autojobsinvoice/service/autojobsinvoice.service';
import { IAutojobsinvoice } from 'app/entities/autojobsinvoice/autojobsinvoice.model';
import { AccountService } from 'app/core/auth/account.service';

@Component({
  standalone: true,
  selector: 'jhi-autocareopenjob',
  templateUrl: './autocareopenjob.componenet.html',
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
    AutocareclosejobComponent,
  ],
  styles: [
    `
      :host {
        display: block;
        min-width: 0;
        max-width: 100%;
      }

      .autocare-open-job-page {
        max-width: 100%;
        overflow-x: hidden;
      }

      .open-job-search {
        width: min(100%, 270px);
      }

      .open-job-table-wrap {
        max-width: 100%;
        overflow-x: auto;
        overflow-y: visible;
        scrollbar-gutter: stable;
      }

      .open-job-table {
        width: 100%;
        min-width: 1150px;
        table-layout: auto;
      }

      .open-job-table th,
      .open-job-table td {
        vertical-align: middle;
      }

      .open-job-table tbody tr,
      .open-job-table tbody td {
        background-color: #fff !important;
      }

      .open-job-table th:not(:nth-child(6)):not(:nth-child(7)),
      .open-job-table td:not(:nth-child(6)):not(:nth-child(7)) {
        white-space: nowrap;
      }

      .open-job-table th:nth-child(6),
      .open-job-table td:nth-child(6),
      .open-job-table th:nth-child(7),
      .open-job-table td:nth-child(7) {
        min-width: 130px;
        white-space: normal;
      }

      .open-job-actions-col {
        position: sticky;
        right: 0;
        z-index: 2;
        max-width: 372px;
        min-width: 372px;
        width: 372px;
        padding-left: 8px !important;
        padding-right: 8px !important;
        background: #fff !important;
        box-shadow: -8px 0 10px -10px rgba(0, 0, 0, 0.45);
      }

      .open-job-table thead .open-job-actions-col {
        z-index: 3;
        background: #fff !important;
      }

      .open-job-actions {
        display: flex;
        flex-wrap: nowrap;
        justify-content: flex-start;
        gap: 8px;
        white-space: nowrap;
        overflow: visible;
        width: 100%;
        margin-left: 0;
      }

      .open-job-actions .btn {
        flex: 0 0 auto;
      }

      .job-by-date-panel {
        font-size: 0.9rem;
      }

      .job-by-date-panel h3 {
        font-size: 1.25rem;
      }

      .job-by-date-panel .nav-link {
        font-size: 0.9rem;
      }

      .job-by-date-panel .table {
        font-size: 0.875rem;
      }

      .job-by-date-input::-webkit-calendar-picker-indicator {
        display: none;
        -webkit-appearance: none;
      }

      .job-by-date-input {
        appearance: textfield;
      }

      .job-by-date-actions-col {
        min-width: 100px;
        width: 100px;
        white-space: nowrap;
      }

      .job-by-date-actions {
        display: inline-flex;
        flex-wrap: nowrap;
        gap: 6px;
        justify-content: flex-end;
        margin-left: auto;
        white-space: nowrap;
        width: max-content;
      }

      .job-by-date-actions .btn {
        flex: 0 0 auto;
      }

      .job-by-date-status {
        color: #495057;
        font-size: 0.8rem;
        font-weight: 600;
      }

      @media (max-width: 767.98px) {
        .open-job-table {
          min-width: 1150px;
        }

        .open-job-actions-col {
          max-width: 372px;
          min-width: 372px;
          width: 372px;
        }
      }
    `,
  ],
})
export class AutocareopenjobComponent implements OnInit {
  subscription: Subscription | null = null;
  autocarejobs?: IAutocarejob[];
  isLoading = false;
  filteredAutocarejobs: IAutocarejob[] = [];
  searchText: string = '';
  activeMainTab: 'ongoing' | 'closed' | 'jobByDate' = 'ongoing';
  activeJobByDateTab: 'ongoing' | 'closed' = 'ongoing';
  selectedJobByDate = '';
  jobByDateJobs: IAutocarejob[] = [];
  jobByDateOngoingJobs: IAutocarejob[] = [];
  jobByDateClosedJobs: IAutocarejob[] = [];
  jobByDateOngoingPage = 1;
  jobByDateClosedPage = 1;
  isLoadingJobByDate = false;
  canUpdateAdvisorInstructionItems = false;
  advisorInstructionJobIds = new Set<number>();

  sortState = sortStateSignal({});

  itemsPerPage = ITEMS_PER_PAGE;
  totalItems = 0;
  page = 1;
  private lastLoadedJobByDate = '';

  public router = inject(Router);
  protected autocarejobService = inject(AutocarejobService);
  protected autojobsinvoiceService = inject(AutojobsinvoiceService);
  protected activatedRoute = inject(ActivatedRoute);
  protected sortService = inject(SortService);
  protected modalService = inject(NgbModal);
  protected ngZone = inject(NgZone);
  protected accountService = inject(AccountService);

  trackId = (_index: number, item: IAutocarejob): number => this.autocarejobService.getAutocarejobIdentifier(item);

  ngOnInit(): void {
    this.accountService.identity().subscribe(account => {
      this.canUpdateAdvisorInstructionItems = this.accountService.canUpdateAdvisorInstructionItems();
    });

    this.subscription = combineLatest([this.activatedRoute.queryParamMap, this.activatedRoute.data])
      .pipe(
        tap(([params, data]) => this.fillComponentAttributeFromRoute(params, data)),
        tap(() => {
          this.load();
          if (this.activeMainTab === 'jobByDate' && this.selectedJobByDate && this.selectedJobByDate !== this.lastLoadedJobByDate) {
            this.loadJobsByDate(false);
          }
        }),
      )
      .subscribe();
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
        const today = dayjs().format('YYYY-MM-DD'); // Get today's date in local YYYY-MM-DD format

        this.autocarejobs = this.autocarejobs?.filter(
          job => !job.isjobclose && job.jobdate?.format('YYYY-MM-DD') === today, // Only show open jobs for today
        );

        this.loadAdvisorInstructionPrintStates(this.autocarejobs ?? []);
        this.filterJobs(); // Apply filtering when loading data
      },
    });
  }

  filterJobs(): void {
    if (!this.autocarejobs) return;

    const today = dayjs().format('YYYY-MM-DD'); // Get today's date in local YYYY-MM-DD format

    this.filteredAutocarejobs = this.autocarejobs.filter(
      job =>
        job.vehiclenumber?.toLowerCase().includes(this.searchText.toLowerCase()) &&
        !job.isjobclose &&
        job.jobdate?.format('YYYY-MM-DD') === today, // Ensure job date matches today
    );
  }

  loadJobsByDate(updateRoute = true): void {
    const selectedDate = this.selectedJobByDateAsDayjs();
    if (!selectedDate) {
      this.jobByDateJobs = [];
      this.jobByDateOngoingJobs = [];
      this.jobByDateClosedJobs = [];
      this.lastLoadedJobByDate = '';
      if (updateRoute) {
        this.updateJobByDateRouteState();
      }
      return;
    }

    if (updateRoute) {
      this.updateJobByDateRouteState();
    }

    this.lastLoadedJobByDate = this.selectedJobByDate;
    this.isLoadingJobByDate = true;
    this.autocarejobService.findByJobDate(selectedDate).subscribe({
      next: (res: EntityArrayResponseType) => {
        this.jobByDateJobs = res.body ?? [];
        this.jobByDateOngoingJobs = this.jobByDateJobs.filter(job => !job.isjobclose);
        this.jobByDateClosedJobs = this.jobByDateJobs.filter(job => job.isjobclose);
        this.jobByDateOngoingPage = 1;
        this.jobByDateClosedPage = 1;
        this.loadAdvisorInstructionPrintStates(this.jobByDateJobs);
        this.isLoadingJobByDate = false;
      },
      error: () => {
        this.jobByDateJobs = [];
        this.jobByDateOngoingJobs = [];
        this.jobByDateClosedJobs = [];
        this.isLoadingJobByDate = false;
      },
    });
  }

  setJobByDateToday(): void {
    this.selectedJobByDate = dayjs().format('YYYY-MM-DD');
    this.loadJobsByDate();
  }

  clearJobByDate(): void {
    this.selectedJobByDate = '';
    this.jobByDateJobs = [];
    this.jobByDateOngoingJobs = [];
    this.jobByDateClosedJobs = [];
    this.jobByDateOngoingPage = 1;
    this.jobByDateClosedPage = 1;
    this.lastLoadedJobByDate = '';
    this.updateJobByDateRouteState();
  }

  selectMainTab(tab: 'ongoing' | 'closed' | 'jobByDate'): void {
    this.activeMainTab = tab;
    this.updateJobByDateRouteState();
  }

  selectJobByDateTab(tab: 'ongoing' | 'closed'): void {
    this.activeJobByDateTab = tab;
    this.updateJobByDateRouteState();
  }

  jobByDateReturnQueryParams(): Record<string, string | number> {
    const params: Record<string, string | number> = {
      autocareTab: 'jobByDate',
      jobByDateTab: this.activeJobByDateTab,
    };

    if (this.selectedJobByDate) {
      params.jobDate = this.selectedJobByDate;
    }

    if (this.jobByDateOngoingPage > 1) {
      params.jobByDateOngoingPage = this.jobByDateOngoingPage;
    }

    if (this.jobByDateClosedPage > 1) {
      params.jobByDateClosedPage = this.jobByDateClosedPage;
    }

    return params;
  }

  jobByDatePrintQueryParams(): Record<string, string | number> {
    return {
      ...this.jobByDateReturnQueryParams(),
      print: 'true',
    };
  }

  jobByDateUpdateItemsQueryParams(): Record<string, string | number> {
    return {
      ...this.jobByDateReturnQueryParams(),
      tab: 'items',
      itemsOnly: 'true',
    };
  }

  openJobByDatePicker(input: HTMLInputElement): void {
    if (typeof input.showPicker === 'function') {
      input.showPicker();
      return;
    }

    input.focus();
  }

  get selectedJobByDateLabel(): string {
    const selectedDate = this.selectedJobByDateAsDayjs();
    return selectedDate ? selectedDate.format('DD/MM/YYYY') : '';
  }

  get paginatedJobByDateOngoingJobs(): IAutocarejob[] {
    return this.paginateJobByDateJobs(this.jobByDateOngoingJobs, this.jobByDateOngoingPage);
  }

  get paginatedJobByDateClosedJobs(): IAutocarejob[] {
    return this.paginateJobByDateJobs(this.jobByDateClosedJobs, this.jobByDateClosedPage);
  }

  navigateJobByDateOngoingPage(page: number): void {
    this.jobByDateOngoingPage = page;
    this.updateJobByDateRouteState();
  }

  navigateJobByDateClosedPage(page: number): void {
    this.jobByDateClosedPage = page;
    this.updateJobByDateRouteState();
  }

  private selectedJobByDateAsDayjs(): dayjs.Dayjs | null {
    if (!this.selectedJobByDate) {
      return null;
    }

    const selectedDate = dayjs(this.selectedJobByDate);
    return selectedDate.isValid() ? selectedDate.startOf('day') : null;
  }

  navigateToWithComponentValues(event: SortState): void {
    this.handleNavigation(this.page, event);
  }

  navigateToPage(page: number): void {
    this.handleNavigation(page, this.sortState());
  }

  needsCustomerRegistration(job: IAutocarejob): boolean {
    return !job.customerid;
  }

  needsVehicleRegistration(job: IAutocarejob): boolean {
    return !job.vehicleid;
  }

  canPrintAdvisorInstructions(job: IAutocarejob): boolean {
    return job.id != null && (job.isadvisorchecked === true || this.advisorInstructionJobIds.has(job.id));
  }

  loadAdvisorInstructionPrintStates(jobs: IAutocarejob[]): void {
    this.advisorInstructionJobIds.clear();

    const jobsWithIds = jobs.filter(job => job.id != null);
    if (jobsWithIds.length === 0) {
      return;
    }

    const jobIds = [...new Set(jobsWithIds.map(job => job.id))];
    this.autojobsinvoiceService.query({ 'jobid.in': jobIds.join(','), page: 0, size: jobIds.length }).subscribe({
      next: (response: HttpResponse<IAutojobsinvoice[]>) => {
        (response.body ?? []).forEach(invoice => {
          if (invoice.jobid != null) {
            this.advisorInstructionJobIds.add(invoice.jobid);
          }
        });
      },
      error: () => {
        this.advisorInstructionJobIds.clear();
      },
    });
  }

  private paginateJobByDateJobs(jobs: IAutocarejob[], page: number): IAutocarejob[] {
    const start = (page - 1) * this.itemsPerPage;
    return jobs.slice(start, start + this.itemsPerPage);
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

  private buildEncodedQuery(params: Record<string, string>): string {
    return Object.entries(params)
      .map(([key, value]) => `${encodeURIComponent(key)}=${encodeURIComponent(value)}`)
      .join('&');
  }

  getCustomerRegistrationUrl(job: IAutocarejob): string {
    const params = this.buildEncodedQuery({
      NewCustomer: job.customername ?? '',
      Tel: job.customertel ?? '',
      JobId: String(job.id ?? ''),
    });

    return `http://192.168.1.150:91/Sales/customer_profile.aspx?${params}`;
  }

  getVehicleRegistrationUrl(job: IAutocarejob): string {
    const params = this.buildEncodedQuery({
      NewVehicle: job.vehiclenumber ?? '',
      cusId: job.customerid != null ? String(job.customerid) : '',
      jobId: String(job.id ?? ''),
    });

    return `http://192.168.1.150:91/AutoCare/AutoCareVehicle.aspx?${params}`;
  }

  protected fillComponentAttributeFromRoute(params: ParamMap, data: Data): void {
    const page = params.get(PAGE_HEADER);
    this.page = +(page ?? 1);
    this.sortState.set(this.sortService.parseSortParam(params.get(SORT) ?? data[DEFAULT_SORT_DATA]));

    const activeTab = params.get('autocareTab');
    this.activeMainTab = activeTab === 'closed' || activeTab === 'jobByDate' ? activeTab : 'ongoing';

    const jobByDateTab = params.get('jobByDateTab');
    this.activeJobByDateTab = jobByDateTab === 'closed' ? 'closed' : 'ongoing';

    const jobDate = params.get('jobDate');
    this.selectedJobByDate = jobDate && dayjs(jobDate).isValid() ? jobDate : '';
    this.jobByDateOngoingPage = +(params.get('jobByDateOngoingPage') ?? 1);
    this.jobByDateClosedPage = +(params.get('jobByDateClosedPage') ?? 1);
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

  private updateJobByDateRouteState(): void {
    this.ngZone.run(() => {
      this.router.navigate(['./'], {
        relativeTo: this.activatedRoute,
        queryParams: {
          autocareTab: this.activeMainTab === 'ongoing' ? null : this.activeMainTab,
          jobDate: this.selectedJobByDate || null,
          jobByDateTab: this.activeMainTab === 'jobByDate' ? this.activeJobByDateTab : null,
          jobByDateOngoingPage: this.activeMainTab === 'jobByDate' && this.jobByDateOngoingPage > 1 ? this.jobByDateOngoingPage : null,
          jobByDateClosedPage: this.activeMainTab === 'jobByDate' && this.jobByDateClosedPage > 1 ? this.jobByDateClosedPage : null,
        },
        queryParamsHandling: 'merge',
        replaceUrl: true,
      });
    });
  }
}
