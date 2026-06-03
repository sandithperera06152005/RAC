import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IAutojobsaleinvoicecommonservicecharge } from '../autojobsaleinvoicecommonservicecharge.model';
import { AutojobsaleinvoicecommonservicechargeService } from '../service/autojobsaleinvoicecommonservicecharge.service';
import {
  AutojobsaleinvoicecommonservicechargeFormGroup,
  AutojobsaleinvoicecommonservicechargeFormService,
} from './autojobsaleinvoicecommonservicecharge-form.service';

@Component({
  standalone: true,
  selector: 'jhi-autojobsaleinvoicecommonservicecharge-update',
  templateUrl: './autojobsaleinvoicecommonservicecharge-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class AutojobsaleinvoicecommonservicechargeUpdateComponent implements OnInit {
  isSaving = false;
  autojobsaleinvoicecommonservicecharge: IAutojobsaleinvoicecommonservicecharge | null = null;

  protected autojobsaleinvoicecommonservicechargeService = inject(AutojobsaleinvoicecommonservicechargeService);
  protected autojobsaleinvoicecommonservicechargeFormService = inject(AutojobsaleinvoicecommonservicechargeFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: AutojobsaleinvoicecommonservicechargeFormGroup =
    this.autojobsaleinvoicecommonservicechargeFormService.createAutojobsaleinvoicecommonservicechargeFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ autojobsaleinvoicecommonservicecharge }) => {
      this.autojobsaleinvoicecommonservicecharge = autojobsaleinvoicecommonservicecharge;
      if (autojobsaleinvoicecommonservicecharge) {
        this.updateForm(autojobsaleinvoicecommonservicecharge);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const autojobsaleinvoicecommonservicecharge =
      this.autojobsaleinvoicecommonservicechargeFormService.getAutojobsaleinvoicecommonservicecharge(this.editForm);
    if (autojobsaleinvoicecommonservicecharge.id !== null) {
      this.subscribeToSaveResponse(this.autojobsaleinvoicecommonservicechargeService.update(autojobsaleinvoicecommonservicecharge));
    } else {
      this.subscribeToSaveResponse(this.autojobsaleinvoicecommonservicechargeService.create(autojobsaleinvoicecommonservicecharge));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAutojobsaleinvoicecommonservicecharge>>): void {
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

  protected updateForm(autojobsaleinvoicecommonservicecharge: IAutojobsaleinvoicecommonservicecharge): void {
    this.autojobsaleinvoicecommonservicecharge = autojobsaleinvoicecommonservicecharge;
    this.autojobsaleinvoicecommonservicechargeFormService.resetForm(this.editForm, autojobsaleinvoicecommonservicecharge);
  }
}
