jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, fakeAsync, inject, tick } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { SalesInvoiceServiceChargeLineService } from '../service/sales-invoice-service-charge-line.service';

import { SalesInvoiceServiceChargeLineDeleteDialogComponent } from './sales-invoice-service-charge-line-delete-dialog.component';

describe('SalesInvoiceServiceChargeLine Management Delete Component', () => {
  let comp: SalesInvoiceServiceChargeLineDeleteDialogComponent;
  let fixture: ComponentFixture<SalesInvoiceServiceChargeLineDeleteDialogComponent>;
  let service: SalesInvoiceServiceChargeLineService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [SalesInvoiceServiceChargeLineDeleteDialogComponent],
      providers: [provideHttpClient(), NgbActiveModal],
    })
      .overrideTemplate(SalesInvoiceServiceChargeLineDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(SalesInvoiceServiceChargeLineDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(SalesInvoiceServiceChargeLineService);
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
