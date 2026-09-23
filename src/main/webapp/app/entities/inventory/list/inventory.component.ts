import { Component, NgZone, OnInit, inject } from '@angular/core';
import { HttpHeaders } from '@angular/common/http';
import { ActivatedRoute, Data, ParamMap, Router, RouterModule } from '@angular/router';
import { Observable, Subscription, combineLatest, filter, of, tap } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { SortByDirective, SortDirective, SortService, type SortState, sortStateSignal } from 'app/shared/sort';
import { DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { ItemCountComponent } from 'app/shared/pagination';
import { FormsModule } from '@angular/forms';

import { ITEMS_PER_PAGE, PAGE_HEADER, TOTAL_COUNT_RESPONSE_HEADER } from 'app/config/pagination.constants';
import { DEFAULT_SORT_DATA, ITEM_DELETED_EVENT, SORT } from 'app/config/navigation.constants';
import { DataUtils } from 'app/core/util/data-util.service';
import { FilterComponent, FilterOptions, IFilterOption, IFilterOptions } from 'app/shared/filter';
import { EntityArrayResponseType, InventoryService } from '../service/inventory.service';
import { InventoryDeleteDialogComponent } from '../delete/inventory-delete-dialog.component';
import { IInventory } from '../inventory.model';
import { CommonserviceoptionService } from 'app/entities/commonserviceoption/service/commonserviceoption.service';
import { ICommonserviceoption } from 'app/entities/commonserviceoption/commonserviceoption.model';
import { ServicecategoryService } from 'app/entities/servicecategory/service/servicecategory.service';
import { IServicecategory } from 'app/entities/servicecategory/servicecategory.model';

@Component({
  standalone: true,
  selector: 'jhi-inventory',
  templateUrl: './inventory.component.html',
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
export class InventoryComponent implements OnInit {
  subscription: Subscription | null = null;
  inventories?: IInventory[];
  isLoading = false;
  inventoryTypeNames = new Map<number, string>();
  categoryNames = new Map<number, string>();

  sortState = sortStateSignal({});
  filters: IFilterOptions = new FilterOptions();

  itemsPerPage = ITEMS_PER_PAGE;
  totalItems = 0;
  page = 1;

  public router = inject(Router);
  protected inventoryService = inject(InventoryService);
  protected commonserviceoptionService = inject(CommonserviceoptionService);
  protected servicecategoryService = inject(ServicecategoryService);
  protected activatedRoute = inject(ActivatedRoute);
  protected sortService = inject(SortService);
  protected dataUtils = inject(DataUtils);
  protected modalService = inject(NgbModal);
  protected ngZone = inject(NgZone);

  trackId = (item: IInventory): number => this.inventoryService.getInventoryIdentifier(item);

  ngOnInit(): void {
    this.loadInventoryLookups();

    this.subscription = combineLatest([this.activatedRoute.queryParamMap, this.activatedRoute.data])
      .pipe(
        tap(([params, data]) => this.fillComponentAttributeFromRoute(params, data)),
        tap(() => this.load()),
      )
      .subscribe();

    this.filters.filterChanges.subscribe(filterOptions => this.handleNavigation(1, this.sortState(), filterOptions));
  }

  getInventoryTypeName(typeId: number | null | undefined): string | number | null | undefined {
    return typeId == null ? typeId : this.inventoryTypeNames.get(typeId) ?? typeId;
  }

  getClassification2Name(classification2: string | null | undefined): string | null | undefined {
    const classification2Id = this.toLookupId(classification2);
    return classification2Id == null ? classification2 : this.categoryNames.get(classification2Id) ?? classification2;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    return this.dataUtils.openFile(base64String, contentType);
  }

  delete(inventory: IInventory): void {
    const modalRef = this.modalService.open(InventoryDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.inventory = inventory;
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
  }

  protected onResponseSuccess(response: EntityArrayResponseType): void {
    this.fillComponentAttributesFromResponseHeader(response.headers);
    const dataFromBody = this.fillComponentAttributesFromResponseBody(response.body);
    this.inventories = dataFromBody;
  }

  protected fillComponentAttributesFromResponseBody(data: IInventory[] | null): IInventory[] {
    return data ?? [];
  }

  protected fillComponentAttributesFromResponseHeader(headers: HttpHeaders): void {
    this.totalItems = Number(headers.get(TOTAL_COUNT_RESPONSE_HEADER));
  }

  protected loadInventoryLookups(): void {
    this.commonserviceoptionService
      .query({ size: 1000 })
      .pipe(catchError(() => of({ body: [] as ICommonserviceoption[] })))
      .subscribe(response => {
        this.inventoryTypeNames = this.createCommonOptionNameMap(response.body ?? []);
      });

    this.servicecategoryService
      .query({ size: 1000 })
      .pipe(catchError(() => of({ body: [] as IServicecategory[] })))
      .subscribe(response => {
        this.categoryNames = this.createCategoryNameMap(response.body ?? []);
      });
  }

  protected createCommonOptionNameMap(options: ICommonserviceoption[]): Map<number, string> {
    const names = new Map<number, string>();
    options.forEach(option => {
      if (option.name) {
        names.set(option.id, option.name);

        if (option.value != null) {
          names.set(option.value, option.name);
        }
      }
    });
    return names;
  }

  protected createCategoryNameMap(categories: IServicecategory[]): Map<number, string> {
    const names = new Map<number, string>();
    categories.forEach(category => {
      if (category.name) {
        names.set(category.id, category.name);
      }
    });
    return names;
  }

  protected toLookupId(value: string | null | undefined): number | null {
    if (value == null || value.trim() === '') {
      return null;
    }

    const id = Number(value);
    return Number.isInteger(id) ? id : null;
  }

  protected queryBackend(): Observable<EntityArrayResponseType> {
    const { page, filters } = this;

    this.isLoading = true;
    const pageToLoad: number = page;
    const queryObject: any = {
      page: pageToLoad - 1,
      size: this.itemsPerPage,
      sort: this.sortService.buildSortParam(this.sortState()),
    };
    filters.filterOptions.forEach(filterOption => {
      queryObject[filterOption.name] = filterOption.values;
    });
    return this.inventoryService.query(queryObject).pipe(tap(() => (this.isLoading = false)));
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

    this.ngZone.run(() => {
      this.router.navigate(['./'], {
        relativeTo: this.activatedRoute,
        queryParams: queryParamsObj,
      });
    });
  }
}
