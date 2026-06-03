jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, fakeAsync, inject, tick } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { AutojobsinvoicelinebatchesService } from '../service/autojobsinvoicelinebatches.service';

import { AutojobsinvoicelinebatchesDeleteDialogComponent } from './autojobsinvoicelinebatches-delete-dialog.component';

describe('Autojobsinvoicelinebatches Management Delete Component', () => {
  let comp: AutojobsinvoicelinebatchesDeleteDialogComponent;
  let fixture: ComponentFixture<AutojobsinvoicelinebatchesDeleteDialogComponent>;
  let service: AutojobsinvoicelinebatchesService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [AutojobsinvoicelinebatchesDeleteDialogComponent],
      providers: [provideHttpClient(), NgbActiveModal],
    })
      .overrideTemplate(AutojobsinvoicelinebatchesDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(AutojobsinvoicelinebatchesDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(AutojobsinvoicelinebatchesService);
    mockActiveModal = TestBed.inject(NgbActiveModal);
  });

  describe('confirmDelete', () => {
    it('Should call delete service on confirmDelete', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        jest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse({ body: {} })));

        // WHEN
        comp.confirmDelete(123);
        tick();

        // THEN
        expect(service.delete).toHaveBeenCalledWith(123);
        expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
      }),
    ));

    it('Should not call delete service on clear', () => {
      // GIVEN
      jest.spyOn(service, 'delete');

      // WHEN
      comp.cancel();

      // THEN
      expect(service.delete).not.toHaveBeenCalled();
      expect(mockActiveModal.close).not.toHaveBeenCalled();
      expect(mockActiveModal.dismiss).toHaveBeenCalled();
    });
  });
});
