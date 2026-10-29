import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { SalesinvoiceService } from '../service/salesinvoice.service';
import { ISalesinvoice } from '../salesinvoice.model';
import { SalesinvoiceFormService } from './salesinvoice-form.service';

import { SalesinvoiceUpdateComponent } from './salesinvoice-update.component';

describe('Salesinvoice Management Update Component', () => {
  let comp: SalesinvoiceUpdateComponent;
  let fixture: ComponentFixture<SalesinvoiceUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let salesinvoiceFormService: SalesinvoiceFormService;
  let salesinvoiceService: SalesinvoiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, SalesinvoiceUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(SalesinvoiceUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SalesinvoiceUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    salesinvoiceFormService = TestBed.inject(SalesinvoiceFormService);
    salesinvoiceService = TestBed.inject(SalesinvoiceService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const salesinvoice: ISalesinvoice = { id: 456 };

      activatedRoute.data = of({ salesinvoice });
      comp.ngOnInit();

      expect(comp.salesinvoice).toEqual(salesinvoice);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISalesinvoice>>();
      const salesinvoice = { id: 123 };
      jest.spyOn(salesinvoiceFormService, 'getSalesinvoice').mockReturnValue(salesinvoice);
      jest.spyOn(salesinvoiceService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ salesinvoice });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: salesinvoice }));
      saveSubject.complete();

      // THEN
      expect(salesinvoiceFormService.getSalesinvoice).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(salesinvoiceService.update).toHaveBeenCalledWith(expect.objectContaining(salesinvoice));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISalesinvoice>>();
      const salesinvoice = { id: 123 };
      jest.spyOn(salesinvoiceFormService, 'getSalesinvoice').mockReturnValue({ id: null });
      jest.spyOn(salesinvoiceService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ salesinvoice: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: salesinvoice }));
      saveSubject.complete();

      // THEN
      expect(salesinvoiceFormService.getSalesinvoice).toHaveBeenCalled();
      expect(salesinvoiceService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISalesinvoice>>();
      const salesinvoice = { id: 123 };
      jest.spyOn(salesinvoiceService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ salesinvoice });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(salesinvoiceService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('item selection', () => {
    it('Should load item details immediately when a datalist option emits input', () => {
      comp.filteredItems[0] = [
        {
          id: 101,
          code: 'ITEM-101',
          name: 'Test item',
          availablequantity: 8,
          lastsellingprice: 1250,
        },
      ];
      const detectChanges = jest.spyOn((comp as any).cdr, 'detectChanges');
      const event = { target: { value: 'ITEM-101' } } as unknown as Event;

      comp.onItemCodeInput(event, 0);

      expect(comp.itemname).toBe('Test item');
      expect(comp.availablequantity).toBe(8);
      expect(comp.lastsellingprice).toBe(1250);
      expect(comp.code).toBe('ITEM-101');
      expect(comp.buyquantity).toBe(1);
      expect(detectChanges).toHaveBeenCalled();
    });
  });
});
