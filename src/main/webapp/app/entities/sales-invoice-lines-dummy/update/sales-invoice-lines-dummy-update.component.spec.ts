import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { SalesInvoiceLinesDummyService } from '../service/sales-invoice-lines-dummy.service';
import { ISalesInvoiceLinesDummy } from '../sales-invoice-lines-dummy.model';
import { SalesInvoiceLinesDummyFormService } from './sales-invoice-lines-dummy-form.service';

import { SalesInvoiceLinesDummyUpdateComponent } from './sales-invoice-lines-dummy-update.component';

describe('SalesInvoiceLinesDummy Management Update Component', () => {
  let comp: SalesInvoiceLinesDummyUpdateComponent;
  let fixture: ComponentFixture<SalesInvoiceLinesDummyUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let salesInvoiceLinesDummyFormService: SalesInvoiceLinesDummyFormService;
  let salesInvoiceLinesDummyService: SalesInvoiceLinesDummyService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [SalesInvoiceLinesDummyUpdateComponent],
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
      .overrideTemplate(SalesInvoiceLinesDummyUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SalesInvoiceLinesDummyUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    salesInvoiceLinesDummyFormService = TestBed.inject(SalesInvoiceLinesDummyFormService);
    salesInvoiceLinesDummyService = TestBed.inject(SalesInvoiceLinesDummyService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const salesInvoiceLinesDummy: ISalesInvoiceLinesDummy = { id: 456 };

      activatedRoute.data = of({ salesInvoiceLinesDummy });
      comp.ngOnInit();

      expect(comp.salesInvoiceLinesDummy).toEqual(salesInvoiceLinesDummy);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISalesInvoiceLinesDummy>>();
      const salesInvoiceLinesDummy = { id: 123 };
      jest.spyOn(salesInvoiceLinesDummyFormService, 'getSalesInvoiceLinesDummy').mockReturnValue(salesInvoiceLinesDummy);
      jest.spyOn(salesInvoiceLinesDummyService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ salesInvoiceLinesDummy });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: salesInvoiceLinesDummy }));
      saveSubject.complete();

      // THEN
      expect(salesInvoiceLinesDummyFormService.getSalesInvoiceLinesDummy).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(salesInvoiceLinesDummyService.update).toHaveBeenCalledWith(expect.objectContaining(salesInvoiceLinesDummy));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISalesInvoiceLinesDummy>>();
      const salesInvoiceLinesDummy = { id: 123 };
      jest.spyOn(salesInvoiceLinesDummyFormService, 'getSalesInvoiceLinesDummy').mockReturnValue({ id: null });
      jest.spyOn(salesInvoiceLinesDummyService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ salesInvoiceLinesDummy: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: salesInvoiceLinesDummy }));
      saveSubject.complete();

      // THEN
      expect(salesInvoiceLinesDummyFormService.getSalesInvoiceLinesDummy).toHaveBeenCalled();
      expect(salesInvoiceLinesDummyService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISalesInvoiceLinesDummy>>();
      const salesInvoiceLinesDummy = { id: 123 };
      jest.spyOn(salesInvoiceLinesDummyService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ salesInvoiceLinesDummy });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(salesInvoiceLinesDummyService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
