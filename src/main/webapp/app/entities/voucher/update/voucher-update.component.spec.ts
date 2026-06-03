import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { VoucherService } from '../service/voucher.service';
import { IVoucher } from '../voucher.model';
import { VoucherFormService } from './voucher-form.service';

import { VoucherUpdateComponent } from './voucher-update.component';

describe('Voucher Management Update Component', () => {
  let comp: VoucherUpdateComponent;
  let fixture: ComponentFixture<VoucherUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let voucherFormService: VoucherFormService;
  let voucherService: VoucherService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [VoucherUpdateComponent],
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
      .overrideTemplate(VoucherUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(VoucherUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    voucherFormService = TestBed.inject(VoucherFormService);
    voucherService = TestBed.inject(VoucherService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const voucher: IVoucher = { id: 456 };

      activatedRoute.data = of({ voucher });
      comp.ngOnInit();

      expect(comp.voucher).toEqual(voucher);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IVoucher>>();
      const voucher = { id: 123 };
      jest.spyOn(voucherFormService, 'getVoucher').mockReturnValue(voucher);
      jest.spyOn(voucherService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ voucher });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: voucher }));
      saveSubject.complete();

      // THEN
      expect(voucherFormService.getVoucher).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(voucherService.update).toHaveBeenCalledWith(expect.objectContaining(voucher));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IVoucher>>();
      const voucher = { id: 123 };
      jest.spyOn(voucherFormService, 'getVoucher').mockReturnValue({ id: null });
      jest.spyOn(voucherService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ voucher: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: voucher }));
      saveSubject.complete();

      // THEN
      expect(voucherFormService.getVoucher).toHaveBeenCalled();
      expect(voucherService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IVoucher>>();
      const voucher = { id: 123 };
      jest.spyOn(voucherService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ voucher });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(voucherService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
