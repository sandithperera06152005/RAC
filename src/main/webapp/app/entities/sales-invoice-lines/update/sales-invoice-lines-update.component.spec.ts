import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { SalesInvoiceLinesService } from '../service/sales-invoice-lines.service';
import { ISalesInvoiceLines } from '../sales-invoice-lines.model';
import { SalesInvoiceLinesFormService } from './sales-invoice-lines-form.service';

import { SalesInvoiceLinesUpdateComponent } from './sales-invoice-lines-update.component';

describe('SalesInvoiceLines Management Update Component', () => {
  let comp: SalesInvoiceLinesUpdateComponent;
  let fixture: ComponentFixture<SalesInvoiceLinesUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let salesInvoiceLinesFormService: SalesInvoiceLinesFormService;
  let salesInvoiceLinesService: SalesInvoiceLinesService;
 
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [SalesInvoiceLinesUpdateComponent],
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
      .overrideTemplate(SalesInvoiceLinesUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SalesInvoiceLinesUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    salesInvoiceLinesFormService = TestBed.inject(SalesInvoiceLinesFormService);
    salesInvoiceLinesService = TestBed.inject(SalesInvoiceLinesService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const salesInvoiceLines: ISalesInvoiceLines = { id: 456 };

      activatedRoute.data = of({ salesInvoiceLines });
      comp.ngOnInit();

      expect(comp.salesInvoiceLines).toEqual(salesInvoiceLines);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISalesInvoiceLines>>();
      const salesInvoiceLines = { id: 123 };
      jest.spyOn(salesInvoiceLinesFormService, 'getSalesInvoiceLines').mockReturnValue(salesInvoiceLines);
      jest.spyOn(salesInvoiceLinesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ salesInvoiceLines });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: salesInvoiceLines }));
      saveSubject.complete();

      // THEN
      expect(salesInvoiceLinesFormService.getSalesInvoiceLines).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(salesInvoiceLinesService.update).toHaveBeenCalledWith(expect.objectContaining(salesInvoiceLines));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISalesInvoiceLines>>();
      const salesInvoiceLines = { id: 123 };
      jest.spyOn(salesInvoiceLinesFormService, 'getSalesInvoiceLines').mockReturnValue({ id: null });
      jest.spyOn(salesInvoiceLinesService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ salesInvoiceLines: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: salesInvoiceLines }));
      saveSubject.complete();

      // THEN
      expect(salesInvoiceLinesFormService.getSalesInvoiceLines).toHaveBeenCalled();
      expect(salesInvoiceLinesService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISalesInvoiceLines>>();
      const salesInvoiceLines = { id: 123 };
      jest.spyOn(salesInvoiceLinesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ salesInvoiceLines });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(salesInvoiceLinesService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
