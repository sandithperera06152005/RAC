import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { SaleInvoiceCommonServiceChargeDummyService } from '../service/sale-invoice-common-service-charge-dummy.service';
import { ISaleInvoiceCommonServiceChargeDummy } from '../sale-invoice-common-service-charge-dummy.model';
import { SaleInvoiceCommonServiceChargeDummyFormService } from './sale-invoice-common-service-charge-dummy-form.service';

import { SaleInvoiceCommonServiceChargeDummyUpdateComponent } from './sale-invoice-common-service-charge-dummy-update.component';

describe('SaleInvoiceCommonServiceChargeDummy Management Update Component', () => {
  let comp: SaleInvoiceCommonServiceChargeDummyUpdateComponent;
  let fixture: ComponentFixture<SaleInvoiceCommonServiceChargeDummyUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let saleInvoiceCommonServiceChargeDummyFormService: SaleInvoiceCommonServiceChargeDummyFormService;
  let saleInvoiceCommonServiceChargeDummyService: SaleInvoiceCommonServiceChargeDummyService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [SaleInvoiceCommonServiceChargeDummyUpdateComponent],
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
      .overrideTemplate(SaleInvoiceCommonServiceChargeDummyUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SaleInvoiceCommonServiceChargeDummyUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    saleInvoiceCommonServiceChargeDummyFormService = TestBed.inject(SaleInvoiceCommonServiceChargeDummyFormService);
    saleInvoiceCommonServiceChargeDummyService = TestBed.inject(SaleInvoiceCommonServiceChargeDummyService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const saleInvoiceCommonServiceChargeDummy: ISaleInvoiceCommonServiceChargeDummy = { id: 456 };

      activatedRoute.data = of({ saleInvoiceCommonServiceChargeDummy });
      comp.ngOnInit();

      expect(comp.saleInvoiceCommonServiceChargeDummy).toEqual(saleInvoiceCommonServiceChargeDummy);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISaleInvoiceCommonServiceChargeDummy>>();
      const saleInvoiceCommonServiceChargeDummy = { id: 123 };
      jest
        .spyOn(saleInvoiceCommonServiceChargeDummyFormService, 'getSaleInvoiceCommonServiceChargeDummy')
        .mockReturnValue(saleInvoiceCommonServiceChargeDummy);
      jest.spyOn(saleInvoiceCommonServiceChargeDummyService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ saleInvoiceCommonServiceChargeDummy });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: saleInvoiceCommonServiceChargeDummy }));
      saveSubject.complete();

      // THEN
      expect(saleInvoiceCommonServiceChargeDummyFormService.getSaleInvoiceCommonServiceChargeDummy).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(saleInvoiceCommonServiceChargeDummyService.update).toHaveBeenCalledWith(
        expect.objectContaining(saleInvoiceCommonServiceChargeDummy),
      );
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISaleInvoiceCommonServiceChargeDummy>>();
      const saleInvoiceCommonServiceChargeDummy = { id: 123 };
      jest.spyOn(saleInvoiceCommonServiceChargeDummyFormService, 'getSaleInvoiceCommonServiceChargeDummy').mockReturnValue({ id: null });
      jest.spyOn(saleInvoiceCommonServiceChargeDummyService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ saleInvoiceCommonServiceChargeDummy: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: saleInvoiceCommonServiceChargeDummy }));
      saveSubject.complete();

      // THEN
      expect(saleInvoiceCommonServiceChargeDummyFormService.getSaleInvoiceCommonServiceChargeDummy).toHaveBeenCalled();
      expect(saleInvoiceCommonServiceChargeDummyService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISaleInvoiceCommonServiceChargeDummy>>();
      const saleInvoiceCommonServiceChargeDummy = { id: 123 };
      jest.spyOn(saleInvoiceCommonServiceChargeDummyService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ saleInvoiceCommonServiceChargeDummy });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(saleInvoiceCommonServiceChargeDummyService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
