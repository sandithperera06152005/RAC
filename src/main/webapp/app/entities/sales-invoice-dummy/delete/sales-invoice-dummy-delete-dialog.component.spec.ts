jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, fakeAsync, inject, tick } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { SalesInvoiceDummyService } from '../service/sales-invoice-dummy.service';

import { SalesInvoiceDummyDeleteDialogComponent } from './sales-invoice-dummy-delete-dialog.component';

describe('SalesInvoiceDummy Management Delete Component', () => {
  let comp: SalesInvoiceDummyDeleteDialogComponent;
  let fixture: ComponentFixture<SalesInvoiceDummyDeleteDialogComponent>;
  let service: SalesInvoiceDummyService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [SalesInvoiceDummyDeleteDialogComponent],
      providers: [provideHttpClient(), NgbActiveModal],
    })
      .overrideTemplate(SalesInvoiceDummyDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(SalesInvoiceDummyDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(SalesInvoiceDummyService);
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
