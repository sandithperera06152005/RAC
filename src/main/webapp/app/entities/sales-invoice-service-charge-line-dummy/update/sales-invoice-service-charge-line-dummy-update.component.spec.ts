import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { SalesInvoiceServiceChargeLineDummyService } from '../service/sales-invoice-service-charge-line-dummy.service';
import { ISalesInvoiceServiceChargeLineDummy } from '../sales-invoice-service-charge-line-dummy.model';
import { SalesInvoiceServiceChargeLineDummyFormService } from './sales-invoice-service-charge-line-dummy-form.service';

import { SalesInvoiceServiceChargeLineDummyUpdateComponent } from './sales-invoice-service-charge-line-dummy-update.component';

describe('SalesInvoiceServiceChargeLineDummy Management Update Component', () => {
  let comp: SalesInvoiceServiceChargeLineDummyUpdateComponent;
  let fixture: ComponentFixture<SalesInvoiceServiceChargeLineDummyUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let salesInvoiceServiceChargeLineDummyFormService: SalesInvoiceServiceChargeLineDummyFormService;
  let salesInvoiceServiceChargeLineDummyService: SalesInvoiceServiceChargeLineDummyService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [SalesInvoiceServiceChargeLineDummyUpdateComponent],
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
      .overrideTemplate(SalesInvoiceServiceChargeLineDummyUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SalesInvoiceServiceChargeLineDummyUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    salesInvoiceServiceChargeLineDummyFormService = TestBed.inject(SalesInvoiceServiceChargeLineDummyFormService);
    salesInvoiceServiceChargeLineDummyService = TestBed.inject(SalesInvoiceServiceChargeLineDummyService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const salesInvoiceServiceChargeLineDummy: ISalesInvoiceServiceChargeLineDummy = { id: 456 };

      activatedRoute.data = of({ salesInvoiceServiceChargeLineDummy });
      comp.ngOnInit();

      expect(comp.salesInvoiceServiceChargeLineDummy).toEqual(salesInvoiceServiceChargeLineDummy);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISalesInvoiceServiceChargeLineDummy>>();
      const salesInvoiceServiceChargeLineDummy = { id: 123 };
      jest
        .spyOn(salesInvoiceServiceChargeLineDummyFormService, 'getSalesInvoiceServiceChargeLineDummy')
        .mockReturnValue(salesInvoiceServiceChargeLineDummy);
      jest.spyOn(salesInvoiceServiceChargeLineDummyService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ salesInvoiceServiceChargeLineDummy });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: salesInvoiceServiceChargeLineDummy }));
      saveSubject.complete();

      // THEN
      expect(salesInvoiceServiceChargeLineDummyFormService.getSalesInvoiceServiceChargeLineDummy).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(salesInvoiceServiceChargeLineDummyService.update).toHaveBeenCalledWith(
        expect.objectContaining(salesInvoiceServiceChargeLineDummy),
      );
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISalesInvoiceServiceChargeLineDummy>>();
      const salesInvoiceServiceChargeLineDummy = { id: 123 };
      jest.spyOn(salesInvoiceServiceChargeLineDummyFormService, 'getSalesInvoiceServiceChargeLineDummy').mockReturnValue({ id: null });
      jest.spyOn(salesInvoiceServiceChargeLineDummyService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ salesInvoiceServiceChargeLineDummy: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: salesInvoiceServiceChargeLineDummy }));
      saveSubject.complete();

      // THEN
      expect(salesInvoiceServiceChargeLineDummyFormService.getSalesInvoiceServiceChargeLineDummy).toHaveBeenCalled();
      expect(salesInvoiceServiceChargeLineDummyService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISalesInvoiceServiceChargeLineDummy>>();
      const salesInvoiceServiceChargeLineDummy = { id: 123 };
      jest.spyOn(salesInvoiceServiceChargeLineDummyService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ salesInvoiceServiceChargeLineDummy });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(salesInvoiceServiceChargeLineDummyService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
