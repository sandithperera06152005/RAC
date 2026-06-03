import { Component, inject, OnInit, Input, OnChanges, SimpleChanges } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IAutojobsinvoice } from '../autojobsinvoice.model';
import { AutojobsinvoiceService } from '../service/autojobsinvoice.service';
import { AutojobsinvoiceFormService, AutojobsinvoiceFormGroup } from './autojobsinvoice-form.service';

import { Output, EventEmitter } from '@angular/core';

@Component({
  standalone: true,
  selector: 'jhi-autojobsinvoice-update',
  templateUrl: './autojobsinvoice-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class AutojobsinvoiceUpdateComponent implements OnInit, OnChanges {
  isSaving = false;
  autojobsinvoice: IAutojobsinvoice | null = null;

  // Add an @Input() property to receive data from the parent component
  @Input() formData: IAutojobsinvoice | null = null;

  protected autojobsinvoiceService = inject(AutojobsinvoiceService);
  protected autojobsinvoiceFormService = inject(AutojobsinvoiceFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: AutojobsinvoiceFormGroup = this.autojobsinvoiceFormService.createAutojobsinvoiceFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ autojobsinvoice }) => {
      this.autojobsinvoice = autojobsinvoice;
      if (autojobsinvoice) {
        this.updateForm(autojobsinvoice);
      }
    });

    // If formData is provided via @Input(), update the form
    if (this.formData) {
      this.updateForm(this.formData);
    }
  }

  ngOnChanges(changes: SimpleChanges): void {
    // If formData changes, update the form
    if (changes.formData && this.formData) {
      this.updateForm(this.formData);
    }
  }

  previousState(): void {
    window.history.back();
  }

  @Output() invoiceSaved = new EventEmitter<IAutojobsinvoice>();

  save(): void {
    this.isSaving = true;
    const autojobsinvoice = this.autojobsinvoiceFormService.getAutojobsinvoice(this.editForm);

    if (autojobsinvoice.id !== null) {
      this.subscribeToSaveResponse(this.autojobsinvoiceService.update(autojobsinvoice));
    } else {
      this.subscribeToSaveResponse(this.autojobsinvoiceService.create(autojobsinvoice));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAutojobsinvoice>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: response => {
        if (response.body != null) {
          this.invoiceSaved.emit(response.body); // 🔥 EMIT HERE
        }
        this.onSaveSuccess();
      },
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    if (!this.formData) {
      this.previousState();
    }
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(autojobsinvoice: IAutojobsinvoice): void {
    this.autojobsinvoice = autojobsinvoice;
    this.autojobsinvoiceFormService.resetForm(this.editForm, autojobsinvoice);
  }
}
