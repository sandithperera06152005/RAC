import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IAutojobsalesinvoiceservicechargeline } from '../autojobsalesinvoiceservicechargeline.model';
import { AutojobsalesinvoiceservicechargelineService } from '../service/autojobsalesinvoiceservicechargeline.service';
import {
  AutojobsalesinvoiceservicechargelineFormGroup,
  AutojobsalesinvoiceservicechargelineFormService,
} from './autojobsalesinvoiceservicechargeline-form.service';

@Component({
  standalone: true,
  selector: 'jhi-autojobsalesinvoiceservicechargeline-update',
  templateUrl: './autojobsalesinvoiceservicechargeline-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class AutojobsalesinvoiceservicechargelineUpdateComponent implements OnInit {
  isSaving = false;
  autojobsalesinvoiceservicechargeline: IAutojobsalesinvoiceservicechargeline | null = null;

  protected autojobsalesinvoiceservicechargelineService = inject(AutojobsalesinvoiceservicechargelineService);
  protected autojobsalesinvoiceservicechargelineFormService = inject(AutojobsalesinvoiceservicechargelineFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: AutojobsalesinvoiceservicechargelineFormGroup =
    this.autojobsalesinvoiceservicechargelineFormService.createAutojobsalesinvoiceservicechargelineFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ autojobsalesinvoiceservicechargeline }) => {
      this.autojobsalesinvoiceservicechargeline = autojobsalesinvoiceservicechargeline;
      if (autojobsalesinvoiceservicechargeline) {
        this.updateForm(autojobsalesinvoiceservicechargeline);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const autojobsalesinvoiceservicechargeline =
      this.autojobsalesinvoiceservicechargelineFormService.getAutojobsalesinvoiceservicechargeline(this.editForm);
    if (autojobsalesinvoiceservicechargeline.id !== null) {
      this.subscribeToSaveResponse(this.autojobsalesinvoiceservicechargelineService.update(autojobsalesinvoiceservicechargeline));
    } else {
      this.subscribeToSaveResponse(this.autojobsalesinvoiceservicechargelineService.create(autojobsalesinvoiceservicechargeline));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAutojobsalesinvoiceservicechargeline>>): void {
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

  protected updateForm(autojobsalesinvoiceservicechargeline: IAutojobsalesinvoiceservicechargeline): void {
    this.autojobsalesinvoiceservicechargeline = autojobsalesinvoiceservicechargeline;
    this.autojobsalesinvoiceservicechargelineFormService.resetForm(this.editForm, autojobsalesinvoiceservicechargeline);
  }
}
