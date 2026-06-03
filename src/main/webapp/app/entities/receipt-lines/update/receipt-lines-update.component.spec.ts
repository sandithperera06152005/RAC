import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { ReceiptLinesService } from '../service/receipt-lines.service';
import { IReceiptLines } from '../receipt-lines.model';
import { ReceiptLinesFormService } from './receipt-lines-form.service';

import { ReceiptLinesUpdateComponent } from './receipt-lines-update.component';

describe('ReceiptLines Management Update Component', () => {
  let comp: ReceiptLinesUpdateComponent;
  let fixture: ComponentFixture<ReceiptLinesUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let receiptLinesFormService: ReceiptLinesFormService;
  let receiptLinesService: ReceiptLinesService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ReceiptLinesUpdateComponent],
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
      .overrideTemplate(ReceiptLinesUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ReceiptLinesUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    receiptLinesFormService = TestBed.inject(ReceiptLinesFormService);
    receiptLinesService = TestBed.inject(ReceiptLinesService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const receiptLines: IReceiptLines = { id: 456 };

      activatedRoute.data = of({ receiptLines });
      comp.ngOnInit();

      expect(comp.receiptLines).toEqual(receiptLines);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IReceiptLines>>();
      const receiptLines = { id: 123 };
      jest.spyOn(receiptLinesFormService, 'getReceiptLines').mockReturnValue(receiptLines);
      jest.spyOn(receiptLinesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ receiptLines });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: receiptLines }));
      saveSubject.complete();

      // THEN
      expect(receiptLinesFormService.getReceiptLines).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(receiptLinesService.update).toHaveBeenCalledWith(expect.objectContaining(receiptLines));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IReceiptLines>>();
      const receiptLines = { id: 123 };
      jest.spyOn(receiptLinesFormService, 'getReceiptLines').mockReturnValue({ id: null });
      jest.spyOn(receiptLinesService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ receiptLines: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: receiptLines }));
      saveSubject.complete();

      // THEN
      expect(receiptLinesFormService.getReceiptLines).toHaveBeenCalled();
      expect(receiptLinesService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IReceiptLines>>();
      const receiptLines = { id: 123 };
      jest.spyOn(receiptLinesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ receiptLines });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(receiptLinesService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
