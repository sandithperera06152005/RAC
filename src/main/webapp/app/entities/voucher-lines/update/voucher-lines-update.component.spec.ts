import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { VoucherLinesService } from '../service/voucher-lines.service';
import { IVoucherLines } from '../voucher-lines.model';
import { VoucherLinesFormService } from './voucher-lines-form.service';

import { VoucherLinesUpdateComponent } from './voucher-lines-update.component';

describe('VoucherLines Management Update Component', () => {
  let comp: VoucherLinesUpdateComponent;
  let fixture: ComponentFixture<VoucherLinesUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let voucherLinesFormService: VoucherLinesFormService;
  let voucherLinesService: VoucherLinesService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [VoucherLinesUpdateComponent],
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
      .overrideTemplate(VoucherLinesUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(VoucherLinesUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    voucherLinesFormService = TestBed.inject(VoucherLinesFormService);
    voucherLinesService = TestBed.inject(VoucherLinesService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const voucherLines: IVoucherLines = { id: 456 };

      activatedRoute.data = of({ voucherLines });
      comp.ngOnInit();

      expect(comp.voucherLines).toEqual(voucherLines);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IVoucherLines>>();
      const voucherLines = { id: 123 };
      jest.spyOn(voucherLinesFormService, 'getVoucherLines').mockReturnValue(voucherLines);
      jest.spyOn(voucherLinesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ voucherLines });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: voucherLines }));
      saveSubject.complete();

      // THEN
      expect(voucherLinesFormService.getVoucherLines).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(voucherLinesService.update).toHaveBeenCalledWith(expect.objectContaining(voucherLines));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IVoucherLines>>();
      const voucherLines = { id: 123 };
      jest.spyOn(voucherLinesFormService, 'getVoucherLines').mockReturnValue({ id: null });
      jest.spyOn(voucherLinesService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ voucherLines: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: voucherLines }));
      saveSubject.complete();

      // THEN
      expect(voucherLinesFormService.getVoucherLines).toHaveBeenCalled();
      expect(voucherLinesService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IVoucherLines>>();
      const voucherLines = { id: 123 };
      jest.spyOn(voucherLinesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ voucherLines });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(voucherLinesService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
