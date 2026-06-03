import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { InvoiceServiceChargeService } from '../service/invoice-service-charge.service';
import { IInvoiceServiceCharge } from '../invoice-service-charge.model';
import { InvoiceServiceChargeFormService } from './invoice-service-charge-form.service';

import { InvoiceServiceChargeUpdateComponent } from './invoice-service-charge-update.component';

describe('InvoiceServiceCharge Management Update Component', () => {
  let comp: InvoiceServiceChargeUpdateComponent;
  let fixture: ComponentFixture<InvoiceServiceChargeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let invoiceServiceChargeFormService: InvoiceServiceChargeFormService;
  let invoiceServiceChargeService: InvoiceServiceChargeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [InvoiceServiceChargeUpdateComponent],
      providers: [
        provideHttpClient(),
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(InvoiceServiceChargeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(InvoiceServiceChargeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    invoiceServiceChargeFormService = TestBed.inject(InvoiceServiceChargeFormService);
    invoiceServiceChargeService = TestBed.inject(InvoiceServiceChargeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const invoiceServiceCharge: IInvoiceServiceCharge = { id: 456 };

      activatedRoute.data = of({ invoiceServiceCharge });
      comp.ngOnInit();

      expect(comp.invoiceServiceCharge).toEqual(invoiceServiceCharge);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IInvoiceServiceCharge>>();
      const invoiceServiceCharge = { id: 123 };
      jest.spyOn(invoiceServiceChargeFormService, 'getInvoiceServiceCharge').mockReturnValue(invoiceServiceCharge);
      jest.spyOn(invoiceServiceChargeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ invoiceServiceCharge });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: invoiceServiceCharge }));
      saveSubject.complete();

      // THEN
      expect(invoiceServiceChargeFormService.getInvoiceServiceCharge).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(invoiceServiceChargeService.update).toHaveBeenCalledWith(expect.objectContaining(invoiceServiceCharge));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IInvoiceServiceCharge>>();
      const invoiceServiceCharge = { id: 123 };
      jest.spyOn(invoiceServiceChargeFormService, 'getInvoiceServiceCharge').mockReturnValue({ id: null });
      jest.spyOn(invoiceServiceChargeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ invoiceServiceCharge: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: invoiceServiceCharge }));
      saveSubject.complete();

      // THEN
      expect(invoiceServiceChargeFormService.getInvoiceServiceCharge).toHaveBeenCalled();
      expect(invoiceServiceChargeService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IInvoiceServiceCharge>>();
      const invoiceServiceCharge = { id: 123 };
      jest.spyOn(invoiceServiceChargeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ invoiceServiceCharge });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(invoiceServiceChargeService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
