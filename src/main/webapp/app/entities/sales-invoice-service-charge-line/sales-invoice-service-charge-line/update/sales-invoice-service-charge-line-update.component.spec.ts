import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { SalesInvoiceServiceChargeLineService } from '../service/sales-invoice-service-charge-line.service';
import { ISalesInvoiceServiceChargeLine } from '../sales-invoice-service-charge-line.model';
import { SalesInvoiceServiceChargeLineFormService } from './sales-invoice-service-charge-line-form.service';

import { SalesInvoiceServiceChargeLineUpdateComponent } from './sales-invoice-service-charge-line-update.component';

describe('SalesInvoiceServiceChargeLine Management Update Component', () => {
  let comp: SalesInvoiceServiceChargeLineUpdateComponent;
  let fixture: ComponentFixture<SalesInvoiceServiceChargeLineUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let salesInvoiceServiceChargeLineFormService: SalesInvoiceServiceChargeLineFormService;
  let salesInvoiceServiceChargeLineService: SalesInvoiceServiceChargeLineService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [SalesInvoiceServiceChargeLineUpdateComponent],
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
      .overrideTemplate(SalesInvoiceServiceChargeLineUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SalesInvoiceServiceChargeLineUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    salesInvoiceServiceChargeLineFormService = TestBed.inject(SalesInvoiceServiceChargeLineFormService);
    salesInvoiceServiceChargeLineService = TestBed.inject(SalesInvoiceServiceChargeLineService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const salesInvoiceServiceChargeLine: ISalesInvoiceServiceChargeLine = { id: 456 };

      activatedRoute.data = of({ salesInvoiceServiceChargeLine });
      comp.ngOnInit();

      expect(comp.salesInvoiceServiceChargeLine).toEqual(salesInvoiceServiceChargeLine);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISalesInvoiceServiceChargeLine>>();
      const salesInvoiceServiceChargeLine = { id: 123 };
      jest
        .spyOn(salesInvoiceServiceChargeLineFormService, 'getSalesInvoiceServiceChargeLine')
        .mockReturnValue(salesInvoiceServiceChargeLine);
      jest.spyOn(salesInvoiceServiceChargeLineService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ salesInvoiceServiceChargeLine });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: salesInvoiceServiceChargeLine }));
      saveSubject.complete();

      // THEN
      expect(salesInvoiceServiceChargeLineFormService.getSalesInvoiceServiceChargeLine).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(salesInvoiceServiceChargeLineService.update).toHaveBeenCalledWith(expect.objectContaining(salesInvoiceServiceChargeLine));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISalesInvoiceServiceChargeLine>>();
      const salesInvoiceServiceChargeLine = { id: 123 };
      jest.spyOn(salesInvoiceServiceChargeLineFormService, 'getSalesInvoiceServiceChargeLine').mockReturnValue({ id: null });
      jest.spyOn(salesInvoiceServiceChargeLineService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ salesInvoiceServiceChargeLine: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: salesInvoiceServiceChargeLine }));
      saveSubject.complete();

      // THEN
      expect(salesInvoiceServiceChargeLineFormService.getSalesInvoiceServiceChargeLine).toHaveBeenCalled();
      expect(salesInvoiceServiceChargeLineService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISalesInvoiceServiceChargeLine>>();
      const salesInvoiceServiceChargeLine = { id: 123 };
      jest.spyOn(salesInvoiceServiceChargeLineService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ salesInvoiceServiceChargeLine });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(salesInvoiceServiceChargeLineService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
