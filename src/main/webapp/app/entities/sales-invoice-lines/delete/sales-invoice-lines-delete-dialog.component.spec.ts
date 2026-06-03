jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, fakeAsync, inject, tick } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { SalesInvoiceLinesService } from '../service/sales-invoice-lines.service';

import { SalesInvoiceLinesDeleteDialogComponent } from './sales-invoice-lines-delete-dialog.component';

describe('SalesInvoiceLines Management Delete Component', () => {
  let comp: SalesInvoiceLinesDeleteDialogComponent;
  let fixture: ComponentFixture<SalesInvoiceLinesDeleteDialogComponent>;
  let service: SalesInvoiceLinesService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [SalesInvoiceLinesDeleteDialogComponent],
      providers: [provideHttpClient(), NgbActiveModal],
    })
      .overrideTemplate(SalesInvoiceLinesDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(SalesInvoiceLinesDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(SalesInvoiceLinesService);
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
