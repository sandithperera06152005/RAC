jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, fakeAsync, inject, tick } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { SalesInvoiceServiceChargeLineDummyService } from '../service/sales-invoice-service-charge-line-dummy.service';

import { SalesInvoiceServiceChargeLineDummyDeleteDialogComponent } from './sales-invoice-service-charge-line-dummy-delete-dialog.component';

describe('SalesInvoiceServiceChargeLineDummy Management Delete Component', () => {
  let comp: SalesInvoiceServiceChargeLineDummyDeleteDialogComponent;
  let fixture: ComponentFixture<SalesInvoiceServiceChargeLineDummyDeleteDialogComponent>;
  let service: SalesInvoiceServiceChargeLineDummyService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [SalesInvoiceServiceChargeLineDummyDeleteDialogComponent],
      providers: [provideHttpClient(), NgbActiveModal],
    })
      .overrideTemplate(SalesInvoiceServiceChargeLineDummyDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(SalesInvoiceServiceChargeLineDummyDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(SalesInvoiceServiceChargeLineDummyService);
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
