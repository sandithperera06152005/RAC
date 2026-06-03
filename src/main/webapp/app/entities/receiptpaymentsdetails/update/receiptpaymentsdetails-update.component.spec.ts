import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { ReceiptpaymentsdetailsService } from '../service/receiptpaymentsdetails.service';
import { IReceiptpaymentsdetails } from '../receiptpaymentsdetails.model';
import { ReceiptpaymentsdetailsFormService } from './receiptpaymentsdetails-form.service';

import { ReceiptpaymentsdetailsUpdateComponent } from './receiptpaymentsdetails-update.component';

describe('Receiptpaymentsdetails Management Update Component', () => {
  let comp: ReceiptpaymentsdetailsUpdateComponent;
  let fixture: ComponentFixture<ReceiptpaymentsdetailsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let receiptpaymentsdetailsFormService: ReceiptpaymentsdetailsFormService;
  let receiptpaymentsdetailsService: ReceiptpaymentsdetailsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ReceiptpaymentsdetailsUpdateComponent],
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
      .overrideTemplate(ReceiptpaymentsdetailsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ReceiptpaymentsdetailsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    receiptpaymentsdetailsFormService = TestBed.inject(ReceiptpaymentsdetailsFormService);
    receiptpaymentsdetailsService = TestBed.inject(ReceiptpaymentsdetailsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const receiptpaymentsdetails: IReceiptpaymentsdetails = { id: 456 };

      activatedRoute.data = of({ receiptpaymentsdetails });
      comp.ngOnInit();

      expect(comp.receiptpaymentsdetails).toEqual(receiptpaymentsdetails);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IReceiptpaymentsdetails>>();
      const receiptpaymentsdetails = { id: 123 };
      jest.spyOn(receiptpaymentsdetailsFormService, 'getReceiptpaymentsdetails').mockReturnValue(receiptpaymentsdetails);
      jest.spyOn(receiptpaymentsdetailsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ receiptpaymentsdetails });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: receiptpaymentsdetails }));
      saveSubject.complete();

      // THEN
      expect(receiptpaymentsdetailsFormService.getReceiptpaymentsdetails).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(receiptpaymentsdetailsService.update).toHaveBeenCalledWith(expect.objectContaining(receiptpaymentsdetails));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IReceiptpaymentsdetails>>();
      const receiptpaymentsdetails = { id: 123 };
      jest.spyOn(receiptpaymentsdetailsFormService, 'getReceiptpaymentsdetails').mockReturnValue({ id: null });
      jest.spyOn(receiptpaymentsdetailsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ receiptpaymentsdetails: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: receiptpaymentsdetails }));
      saveSubject.complete();

      // THEN
      expect(receiptpaymentsdetailsFormService.getReceiptpaymentsdetails).toHaveBeenCalled();
      expect(receiptpaymentsdetailsService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IReceiptpaymentsdetails>>();
      const receiptpaymentsdetails = { id: 123 };
      jest.spyOn(receiptpaymentsdetailsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ receiptpaymentsdetails });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(receiptpaymentsdetailsService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
