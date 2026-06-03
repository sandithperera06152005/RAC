import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IWorkshopVehicleWorkList } from '../workshop-vehicle-work-list.model';
import { WorkshopVehicleWorkListService } from '../service/workshop-vehicle-work-list.service';
import { WorkshopVehicleWorkListFormGroup, WorkshopVehicleWorkListFormService } from './workshop-vehicle-work-list-form.service';

@Component({
  standalone: true,
  selector: 'jhi-workshop-vehicle-work-list-update',
  templateUrl: './workshop-vehicle-work-list-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class WorkshopVehicleWorkListUpdateComponent implements OnInit {
  isSaving = false;
  workshopVehicleWorkList: IWorkshopVehicleWorkList | null = null;

  protected workshopVehicleWorkListService = inject(WorkshopVehicleWorkListService);
  protected workshopVehicleWorkListFormService = inject(WorkshopVehicleWorkListFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: WorkshopVehicleWorkListFormGroup = this.workshopVehicleWorkListFormService.createWorkshopVehicleWorkListFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ workshopVehicleWorkList }) => {
      this.workshopVehicleWorkList = workshopVehicleWorkList;
      if (workshopVehicleWorkList) {
        this.updateForm(workshopVehicleWorkList);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    // Ensure this method is public
    this.isSaving = true;
    const workshopVehicleWorkList = this.workshopVehicleWorkListFormService.getWorkshopVehicleWorkList(this.editForm);
    if (workshopVehicleWorkList.id !== null) {
      this.subscribeToSaveResponse(this.workshopVehicleWorkListService.update(workshopVehicleWorkList));
    } else {
      this.subscribeToSaveResponse(this.workshopVehicleWorkListService.create(workshopVehicleWorkList));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IWorkshopVehicleWorkList>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(workshopVehicleWorkList: IWorkshopVehicleWorkList): void {
    this.workshopVehicleWorkList = workshopVehicleWorkList;
    this.workshopVehicleWorkListFormService.resetForm(this.editForm, workshopVehicleWorkList);
  }
}
