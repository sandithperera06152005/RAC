import { Component, inject, OnInit, Input, OnChanges, SimpleChanges } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable, forkJoin } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IWorkshopvehiclework } from '../workshopvehiclework.model';
import { WorkshopvehicleworkService } from '../service/workshopvehiclework.service';
import { WorkshopvehicleworkFormService, WorkshopvehicleworkFormGroup } from './workshopvehiclework-form.service';
import { IWorkshopworklist } from 'app/entities/workshopworklist/workshopworklist.model';
import { WorkshopworklistService } from 'app/entities/workshopworklist/service/workshopworklist.service';
import { IWorkshopVehicleWorkList } from 'app/entities/workshop-vehicle-work-list/workshop-vehicle-work-list.model';
import { WorkshopVehicleWorkListService } from 'app/entities/workshop-vehicle-work-list/service/workshop-vehicle-work-list.service';
import { AccountService } from 'app/core/auth/account.service';
import dayjs from 'dayjs/esm';

@Component({
  standalone: true,
  selector: 'jhi-workshopvehiclework-update',
  templateUrl: './workshopvehiclework-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class WorkshopvehicleworkUpdateComponent implements OnInit, OnChanges {
  isSaving = false;
  workshopvehiclework: IWorkshopvehiclework | null = null;
  workshopworklist: IWorkshopworklist[] = [];
  selectedworkItems: IWorkshopworklist[] = [];
  savedWorkshopWorkIds = new Set<number>();
  savedWorkshopWorkNames = new Set<string>();
  @Input() formData: any;

  /**
   * Tracks the last jobId for which we loaded saved selections.
   * Prevents redundant HTTP calls because [formData]="mapFormTowork(editForm.value)"
   * produces a new object reference on every change-detection cycle even when
   * the actual jobId has not changed.
   */
  private lastLoadedJobId = 0;

  protected workshopvehicleworkService = inject(WorkshopvehicleworkService);
  protected workshopvehicleworkFormService = inject(WorkshopvehicleworkFormService);
  protected activatedRoute = inject(ActivatedRoute);
  protected workshopworklistService = inject(WorkshopworklistService);
  protected workshopVehicleWorkListService = inject(WorkshopVehicleWorkListService);
  protected accountService = inject(AccountService);

  currentUserId: number = 0;
  private localNow(): dayjs.Dayjs {
    return dayjs().add(-new Date().getTimezoneOffset(), 'minute');
  }

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: WorkshopvehicleworkFormGroup = this.workshopvehicleworkFormService.createWorkshopvehicleworkFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ workshopvehiclework }) => {
      this.workshopvehiclework = workshopvehiclework;
      if (workshopvehiclework) {
        this.updateForm(workshopvehiclework);
      }
      this.loadDataFromWorkshopWorklistEntities();
    });

    this.accountService.identity().subscribe(account => {
      if (account) {
        if ((account as any).id) {
          this.currentUserId = (account as any).id;
        } else {
          const storedUserId = localStorage.getItem('userId');
          if (storedUserId) {
            this.currentUserId = parseInt(storedUserId, 10);
          }
        }
      }
    });
  }

  ngOnChanges(changes: SimpleChanges): void {
    const jobId = Number(changes['formData']?.currentValue?.jobid ?? 0);

    if (jobId > 0) {
      // Only fire the HTTP call when the jobId has actually changed.
      // Without this guard, every Angular change-detection cycle that
      // re-evaluates [formData]="mapFormTowork(editForm.value)" would
      // create a new object reference and trigger a fresh (immediately
      // cancelled) HTTP request, producing the status-0 error loop.
      if (jobId !== this.lastLoadedJobId) {
        this.lastLoadedJobId = jobId;
        this.loadSavedWorkshopSelections(jobId);
      }
    } else {
      this.lastLoadedJobId = 0;
      this.savedWorkshopWorkIds.clear();
      this.savedWorkshopWorkNames.clear();
      this.selectedworkItems = [];
    }
  }

  loadDataFromWorkshopWorklistEntities() {
    this.workshopworklistService.query({ size: 1000 }).subscribe((res: any) => {
      this.workshopworklist = res.body || [];
      this.syncSelectedWorkshopItemsFromSaved();
    });
  }

  onworkServiceSelectionChange(item: any, event: any): void {
    if (event.target.checked) {
      if (!this.selectedworkItems.some(selectedworkItem => Number(selectedworkItem.id ?? 0) === Number(item.id ?? 0))) {
        this.selectedworkItems.push(item);
      }
    } else {
      this.selectedworkItems = this.selectedworkItems.filter(selectedworkItem => selectedworkItem !== item);
    }
  }

  isWorkshopWorkSelected(item: IWorkshopworklist): boolean {
    const workId = Number(item.id ?? 0);
    const workName = (item.workshopwork ?? '').trim().toLowerCase();
    return (
      this.savedWorkshopWorkIds.has(workId) ||
      this.savedWorkshopWorkNames.has(workName) ||
      this.selectedworkItems.some(selectedworkItem => Number(selectedworkItem.id ?? 0) === workId)
    );
  }

  private loadSavedWorkshopSelections(jobId: number): void {
    this.workshopvehicleworkService.queryByJobId(jobId).subscribe({
      next: (vehicleWorkResponse: HttpResponse<IWorkshopvehiclework[]>) => {
        const vehicleWorkIds = (vehicleWorkResponse.body || []).map(work => Number(work.id ?? 0)).filter(id => id > 0);

        if (vehicleWorkIds.length === 0) {
          this.savedWorkshopWorkIds.clear();
          this.savedWorkshopWorkNames.clear();
          this.selectedworkItems = [];
          return;
        }

        this.workshopVehicleWorkListService.queryByVehicleWorkIds(vehicleWorkIds).subscribe({
          next: (workListResponse: HttpResponse<IWorkshopVehicleWorkList[]>) => {
            const savedWorkRows = workListResponse.body || [];

            this.savedWorkshopWorkIds = new Set(savedWorkRows.map(row => Number(row.workid ?? 0)).filter(workId => workId > 0));
            this.savedWorkshopWorkNames = new Set(
              savedWorkRows
                .map(row =>
                  String(row.workshopwork ?? '')
                    .trim()
                    .toLowerCase(),
                )
                .filter(name => name.length > 0),
            );

            this.syncSelectedWorkshopItemsFromSaved();
          },
          error: (error: unknown) => {
            console.error('Failed to load saved workshop work rows:', error);
            this.savedWorkshopWorkIds.clear();
            this.savedWorkshopWorkNames.clear();
            this.selectedworkItems = [];
          },
        });
      },
      error: (error: unknown) => {
        console.error('Failed to load workshop work headers:', error);
        this.savedWorkshopWorkIds.clear();
        this.savedWorkshopWorkNames.clear();
        this.selectedworkItems = [];
      },
    });
  }

  private syncSelectedWorkshopItemsFromSaved(): void {
    this.selectedworkItems = this.workshopworklist.filter(item => {
      const workId = Number(item.id ?? 0);
      const workName = (item.workshopwork ?? '').trim().toLowerCase();
      return this.savedWorkshopWorkIds.has(workId) || this.savedWorkshopWorkNames.has(workName);
    });
  }

  previousState(): void {
    window.history.back();
  }

  /**
   * Save is invoked standalone (from the child form) or can be called
   * programmatically.  When called from the parent's "Save All" flow the
   * jobId comes from formData.jobid (= Autocarejob.id).
   */
  save(): void {
    const jobId = Number(this.formData?.jobid ?? 0);
    if (jobId > 0) {
      this.isSaving = true;
      this.saveWorkshopWorkListDetails(jobId);
    }
  }

  /**
   * Ensures a WorkshopVehicleWork header row exists for the given jobId
   * (from Autocarejob.id), then replaces all WorkshopVehicleWorkList detail
   * rows with the currently-selected items from WorkshopWorkList.
   *
   * Data flow:
   *   Source list   : WorkshopWorkList          (workshopworklist)
   *   Header table  : WorkshopVehicleWork        (jobid = Autocarejob.id)
   *   Detail table  : WorkshopVehicleWorkList    (vehicleworkid, workid, workshopwork)
   */
  saveWorkshopWorkListDetails(jobId: number): void {
    // Step 1 – find or create the WorkshopVehicleWork header for this job
    this.workshopvehicleworkService.queryByJobId(jobId).subscribe({
      next: (headerResponse: HttpResponse<IWorkshopvehiclework[]>) => {
        const existingHeader = (headerResponse.body || []).find(h => Number(h.id ?? 0) > 0);
        if (existingHeader?.id) {
          this.replaceDetailRows(existingHeader.id);
        } else {
          // Create header with jobid = Autocarejob.id
          const headerPayload = {
            id: null,
            jobid: jobId,
            vehicleid: this.formData?.vehicleid ?? null,
            customerid: this.formData?.customerid ?? null,
            customername: this.formData?.customername ?? '',
            contactno: this.formData?.contactno ?? '',
            vehicleno: this.formData?.vehicleno ?? '',
            vehiclebrand: this.formData?.vehiclebrand ?? '',
            vehiclemodel: this.formData?.vehiclemodel ?? '',
            mileage: this.formData?.mileage ?? '',
            addeddate: this.localNow(),
            iscalltocustomer: false,
            remarks: '',
            calldate: null,
            lmu: this.currentUserId,
            lmd: this.localNow(),
          };
          this.workshopvehicleworkService.create(headerPayload).subscribe({
            next: createResp => {
              const vehicleWorkId = Number(createResp.body?.id ?? 0);
              if (vehicleWorkId > 0) {
                this.replaceDetailRows(vehicleWorkId);
              }
            },
            error: (err: unknown) => {
              console.error('Failed to create WorkshopVehicleWork header:', err);
              this.onSaveFinalize();
            },
          });
        }
      },
      error: (err: unknown) => {
        console.error('Failed to query WorkshopVehicleWork headers:', err);
        this.onSaveFinalize();
      },
    });
  }

  /**
   * Delete all existing WorkshopVehicleWorkList rows for this vehicleworkid,
   * then create fresh rows for every currently-selected WorkshopWorkList item.
   * This ensures unchecked items are removed (no stale rows).
   */
  private replaceDetailRows(vehicleWorkId: number): void {
    this.workshopVehicleWorkListService.queryByVehicleWorkIds([vehicleWorkId]).subscribe({
      next: (detailResponse: HttpResponse<IWorkshopVehicleWorkList[]>) => {
        const existing = detailResponse.body || [];

        // Delete all existing detail rows, then create new ones.
        // Filter out rows with a falsy/undefined id — calling delete(undefined)
        // produces a 400 "error.idinvalid" from the backend.
        const validExisting = existing.filter(row => row.id != null && String(row.id).trim() !== '' && String(row.id) !== 'undefined');
        const deleteOps = validExisting.map(row => this.workshopVehicleWorkListService.delete(row.id));

        const doCreate = () => {
          let lineId = 0;
          this.selectedworkItems.forEach(item => {
            lineId += 1;
            // workid comes from WorkshopWorkList.id
            // workshopwork comes from WorkshopWorkList.workshopwork
            const detailPayload = {
              id: null,
              vehicleworkid: vehicleWorkId,
              lineid: lineId,
              workid: Number(item.id ?? 0) || null,
              workshopwork: String(item.workshopwork ?? '').trim(),
              isjobdone: false,
              jobdonedate: null,
              jobnumber: '',
              jobvalue: 0,
              estimatevalue: 0,
            };
            this.workshopVehicleWorkListService.create(detailPayload).subscribe({
              next: () => {},
              error: (err: unknown) => console.error('Failed to create WorkshopVehicleWorkList detail row:', err),
            });
          });
          this.onSaveFinalize();
        };

        if (deleteOps.length === 0) {
          doCreate();
        } else {
          forkJoin(deleteOps).subscribe({
            next: () => doCreate(),
            error: (err: unknown) => {
              console.error('Failed to delete stale WorkshopVehicleWorkList rows:', err);
              // Proceed with creation even if some deletes fail
              doCreate();
            },
          });
        }
      },
      error: (err: unknown) => {
        console.error('Failed to load existing WorkshopVehicleWorkList rows:', err);
        this.onSaveFinalize();
      },
    });
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IWorkshopvehiclework>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    // Intentionally not calling previousState() — this component is embedded.
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(workshopvehiclework: IWorkshopvehiclework): void {
    this.workshopvehiclework = workshopvehiclework;
    this.workshopvehicleworkFormService.resetForm(this.editForm, workshopvehiclework);
  }
}
