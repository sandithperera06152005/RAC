import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { SalesInvoiceDummyService } from '../service/sales-invoice-dummy.service';
import { ISalesInvoiceDummy } from '../sales-invoice-dummy.model';
import { SalesInvoiceDummyFormService } from './sales-invoice-dummy-form.service';

import { SalesInvoiceDummyUpdateComponent } from './sales-invoice-dummy-update.component';

describe('SalesInvoiceDummy Management Update Component', () => {
  let comp: SalesInvoiceDummyUpdateComponent;
  let fixture: ComponentFixture<SalesInvoiceDummyUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let salesInvoiceDummyFormService: SalesInvoiceDummyFormService;
  let salesInvoiceDummyService: SalesInvoiceDummyService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [SalesInvoiceDummyUpdateComponent],
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
      .overrideTemplate(SalesInvoiceDummyUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SalesInvoiceDummyUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    salesInvoiceDummyFormService = TestBed.inject(SalesInvoiceDummyFormService);
    salesInvoiceDummyService = TestBed.inject(SalesInvoiceDummyService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const salesInvoiceDummy: ISalesInvoiceDummy = { id: 456 };

      activatedRoute.data = of({ salesInvoiceDummy });
      comp.ngOnInit();

      expect(comp.salesInvoiceDummy).toEqual(salesInvoiceDummy);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISalesInvoiceDummy>>();
      const salesInvoiceDummy = { id: 123 };
      jest.spyOn(salesInvoiceDummyFormService, 'getSalesInvoiceDummy').mockReturnValue(salesInvoiceDummy);
      jest.spyOn(salesInvoiceDummyService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ salesInvoiceDummy });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: salesInvoiceDummy }));
      saveSubject.complete();

      // THEN
      expect(salesInvoiceDummyFormService.getSalesInvoiceDummy).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(salesInvoiceDummyService.update).toHaveBeenCalledWith(expect.objectContaining(salesInvoiceDummy));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISalesInvoiceDummy>>();
      const salesInvoiceDummy = { id: 123 };
      jest.spyOn(salesInvoiceDummyFormService, 'getSalesInvoiceDummy').mockReturnValue({ id: null });
      jest.spyOn(salesInvoiceDummyService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ salesInvoiceDummy: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: salesInvoiceDummy }));
      saveSubject.complete();

      // THEN
      expect(salesInvoiceDummyFormService.getSalesInvoiceDummy).toHaveBeenCalled();
      expect(salesInvoiceDummyService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISalesInvoiceDummy>>();
      const salesInvoiceDummy = { id: 123 };
      jest.spyOn(salesInvoiceDummyService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ salesInvoiceDummy });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(salesInvoiceDummyService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
