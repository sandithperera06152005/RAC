import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IAutojobsinvoicelinebatches } from '../autojobsinvoicelinebatches.model';
import { AutojobsinvoicelinebatchesService } from '../service/autojobsinvoicelinebatches.service';
import { AutojobsinvoicelinebatchesFormGroup, AutojobsinvoicelinebatchesFormService } from './autojobsinvoicelinebatches-form.service';

@Component({
  standalone: true,
  selector: 'jhi-autojobsinvoicelinebatches-update',
  templateUrl: './autojobsinvoicelinebatches-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class AutojobsinvoicelinebatchesUpdateComponent implements OnInit {
  isSaving = false;
  autojobsinvoicelinebatches: IAutojobsinvoicelinebatches | null = null;

  protected autojobsinvoicelinebatchesService = inject(AutojobsinvoicelinebatchesService);
  protected autojobsinvoicelinebatchesFormService = inject(AutojobsinvoicelinebatchesFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: AutojobsinvoicelinebatchesFormGroup = this.autojobsinvoicelinebatchesFormService.createAutojobsinvoicelinebatchesFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ autojobsinvoicelinebatches }) => {
      this.autojobsinvoicelinebatches = autojobsinvoicelinebatches;
      if (autojobsinvoicelinebatches) {
        this.updateForm(autojobsinvoicelinebatches);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const autojobsinvoicelinebatches = this.autojobsinvoicelinebatchesFormService.getAutojobsinvoicelinebatches(this.editForm);
    if (autojobsinvoicelinebatches.id !== null) {
      this.subscribeToSaveResponse(this.autojobsinvoicelinebatchesService.update(autojobsinvoicelinebatches));
    } else {
      this.subscribeToSaveResponse(this.autojobsinvoicelinebatchesService.create(autojobsinvoicelinebatches));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAutojobsinvoicelinebatches>>): void {
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

  protected updateForm(autojobsinvoicelinebatches: IAutojobsinvoicelinebatches): void {
    this.autojobsinvoicelinebatches = autojobsinvoicelinebatches;
    this.autojobsinvoicelinebatchesFormService.resetForm(this.editForm, autojobsinvoicelinebatches);
  }
}
