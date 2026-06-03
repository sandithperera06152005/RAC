import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { InvoiceServiceCommonService } from '../service/invoice-service-common.service';
import { IInvoiceServiceCommon } from '../invoice-service-common.model';
import { InvoiceServiceCommonFormService } from './invoice-service-common-form.service';

import { InvoiceServiceCommonUpdateComponent } from './invoice-service-common-update.component';

describe('InvoiceServiceCommon Management Update Component', () => {
  let comp: InvoiceServiceCommonUpdateComponent;
  let fixture: ComponentFixture<InvoiceServiceCommonUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let invoiceServiceCommonFormService: InvoiceServiceCommonFormService;
  let invoiceServiceCommonService: InvoiceServiceCommonService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [InvoiceServiceCommonUpdateComponent],
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
      .overrideTemplate(InvoiceServiceCommonUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(InvoiceServiceCommonUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    invoiceServiceCommonFormService = TestBed.inject(InvoiceServiceCommonFormService);
    invoiceServiceCommonService = TestBed.inject(InvoiceServiceCommonService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const invoiceServiceCommon: IInvoiceServiceCommon = { id: 456 };

      activatedRoute.data = of({ invoiceServiceCommon });
      comp.ngOnInit();

      expect(comp.invoiceServiceCommon).toEqual(invoiceServiceCommon);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IInvoiceServiceCommon>>();
      const invoiceServiceCommon = { id: 123 };
      jest.spyOn(invoiceServiceCommonFormService, 'getInvoiceServiceCommon').mockReturnValue(invoiceServiceCommon);
      jest.spyOn(invoiceServiceCommonService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ invoiceServiceCommon });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: invoiceServiceCommon }));
      saveSubject.complete();

      // THEN
      expect(invoiceServiceCommonFormService.getInvoiceServiceCommon).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(invoiceServiceCommonService.update).toHaveBeenCalledWith(expect.objectContaining(invoiceServiceCommon));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IInvoiceServiceCommon>>();
      const invoiceServiceCommon = { id: 123 };
      jest.spyOn(invoiceServiceCommonFormService, 'getInvoiceServiceCommon').mockReturnValue({ id: null });
      jest.spyOn(invoiceServiceCommonService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ invoiceServiceCommon: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: invoiceServiceCommon }));
      saveSubject.complete();

      // THEN
      expect(invoiceServiceCommonFormService.getInvoiceServiceCommon).toHaveBeenCalled();
      expect(invoiceServiceCommonService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IInvoiceServiceCommon>>();
      const invoiceServiceCommon = { id: 123 };
      jest.spyOn(invoiceServiceCommonService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ invoiceServiceCommon });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(invoiceServiceCommonService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
