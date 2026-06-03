import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { SaleInvoiceCommonServiceChargeService } from '../service/sale-invoice-common-service-charge.service';
import { ISaleInvoiceCommonServiceCharge } from '../sale-invoice-common-service-charge.model';
import { SaleInvoiceCommonServiceChargeFormService } from './sale-invoice-common-service-charge-form.service';

import { SaleInvoiceCommonServiceChargeUpdateComponent } from './sale-invoice-common-service-charge-update.component';

describe('SaleInvoiceCommonServiceCharge Management Update Component', () => {
  let comp: SaleInvoiceCommonServiceChargeUpdateComponent;
  let fixture: ComponentFixture<SaleInvoiceCommonServiceChargeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let saleInvoiceCommonServiceChargeFormService: SaleInvoiceCommonServiceChargeFormService;
  let saleInvoiceCommonServiceChargeService: SaleInvoiceCommonServiceChargeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [SaleInvoiceCommonServiceChargeUpdateComponent],
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
      .overrideTemplate(SaleInvoiceCommonServiceChargeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SaleInvoiceCommonServiceChargeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    saleInvoiceCommonServiceChargeFormService = TestBed.inject(SaleInvoiceCommonServiceChargeFormService);
    saleInvoiceCommonServiceChargeService = TestBed.inject(SaleInvoiceCommonServiceChargeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const saleInvoiceCommonServiceCharge: ISaleInvoiceCommonServiceCharge = { id: 456 };

      activatedRoute.data = of({ saleInvoiceCommonServiceCharge });
      comp.ngOnInit();

      expect(comp.saleInvoiceCommonServiceCharge).toEqual(saleInvoiceCommonServiceCharge);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISaleInvoiceCommonServiceCharge>>();
      const saleInvoiceCommonServiceCharge = { id: 123 };
      jest
        .spyOn(saleInvoiceCommonServiceChargeFormService, 'getSaleInvoiceCommonServiceCharge')
        .mockReturnValue(saleInvoiceCommonServiceCharge);
      jest.spyOn(saleInvoiceCommonServiceChargeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ saleInvoiceCommonServiceCharge });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: saleInvoiceCommonServiceCharge }));
      saveSubject.complete();

      // THEN
      expect(saleInvoiceCommonServiceChargeFormService.getSaleInvoiceCommonServiceCharge).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(saleInvoiceCommonServiceChargeService.update).toHaveBeenCalledWith(expect.objectContaining(saleInvoiceCommonServiceCharge));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISaleInvoiceCommonServiceCharge>>();
      const saleInvoiceCommonServiceCharge = { id: 123 };
      jest.spyOn(saleInvoiceCommonServiceChargeFormService, 'getSaleInvoiceCommonServiceCharge').mockReturnValue({ id: null });
      jest.spyOn(saleInvoiceCommonServiceChargeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ saleInvoiceCommonServiceCharge: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: saleInvoiceCommonServiceCharge }));
      saveSubject.complete();

      // THEN
      expect(saleInvoiceCommonServiceChargeFormService.getSaleInvoiceCommonServiceCharge).toHaveBeenCalled();
      expect(saleInvoiceCommonServiceChargeService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISaleInvoiceCommonServiceCharge>>();
      const saleInvoiceCommonServiceCharge = { id: 123 };
      jest.spyOn(saleInvoiceCommonServiceChargeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ saleInvoiceCommonServiceCharge });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(saleInvoiceCommonServiceChargeService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
