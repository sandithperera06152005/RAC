jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, fakeAsync, inject, tick } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { InvoiceServiceChargeService } from '../service/invoice-service-charge.service';

import { InvoiceServiceChargeDeleteDialogComponent } from './invoice-service-charge-delete-dialog.component';

describe('InvoiceServiceCharge Management Delete Component', () => {
  let comp: InvoiceServiceChargeDeleteDialogComponent;
  let fixture: ComponentFixture<InvoiceServiceChargeDeleteDialogComponent>;
  let service: InvoiceServiceChargeService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [InvoiceServiceChargeDeleteDialogComponent],
      providers: [provideHttpClient(), NgbActiveModal],
    })
      .overrideTemplate(InvoiceServiceChargeDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(InvoiceServiceChargeDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(InvoiceServiceChargeService);
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
