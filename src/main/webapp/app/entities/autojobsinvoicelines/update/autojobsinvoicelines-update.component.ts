import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IAutojobsinvoicelines } from '../autojobsinvoicelines.model';
import { AutojobsinvoicelinesService } from '../service/autojobsinvoicelines.service';
import { AutojobsinvoicelinesFormGroup, AutojobsinvoicelinesFormService } from './autojobsinvoicelines-form.service';

@Component({
  standalone: true,
  selector: 'jhi-autojobsinvoicelines-update',
  templateUrl: './autojobsinvoicelines-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class AutojobsinvoicelinesUpdateComponent implements OnInit {
  isSaving = false;
  autojobsinvoicelines: IAutojobsinvoicelines | null = null;

  protected autojobsinvoicelinesService = inject(AutojobsinvoicelinesService);
  protected autojobsinvoicelinesFormService = inject(AutojobsinvoicelinesFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: AutojobsinvoicelinesFormGroup = this.autojobsinvoicelinesFormService.createAutojobsinvoicelinesFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ autojobsinvoicelines }) => {
      this.autojobsinvoicelines = autojobsinvoicelines;
      if (autojobsinvoicelines) {
        this.updateForm(autojobsinvoicelines);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const autojobsinvoicelines = this.autojobsinvoicelinesFormService.getAutojobsinvoicelines(this.editForm);
    if (autojobsinvoicelines.id !== null) {
      this.subscribeToSaveResponse(this.autojobsinvoicelinesService.update(autojobsinvoicelines));
    } else {
      this.subscribeToSaveResponse(this.autojobsinvoicelinesService.create(autojobsinvoicelines));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAutojobsinvoicelines>>): void {
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

  protected updateForm(autojobsinvoicelines: IAutojobsinvoicelines): void {
    this.autojobsinvoicelines = autojobsinvoicelines;
    this.autojobsinvoicelinesFormService.resetForm(this.editForm, autojobsinvoicelines);
  }
}
