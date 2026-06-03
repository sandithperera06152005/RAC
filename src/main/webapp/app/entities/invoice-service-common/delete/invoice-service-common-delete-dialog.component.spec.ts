jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, fakeAsync, inject, tick } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { InvoiceServiceCommonService } from '../service/invoice-service-common.service';

import { InvoiceServiceCommonDeleteDialogComponent } from './invoice-service-common-delete-dialog.component';

describe('InvoiceServiceCommon Management Delete Component', () => {
  let comp: InvoiceServiceCommonDeleteDialogComponent;
  let fixture: ComponentFixture<InvoiceServiceCommonDeleteDialogComponent>;
  let service: InvoiceServiceCommonService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [InvoiceServiceCommonDeleteDialogComponent],
      providers: [provideHttpClient(), NgbActiveModal],
    })
      .overrideTemplate(InvoiceServiceCommonDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(InvoiceServiceCommonDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(InvoiceServiceCommonService);
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
