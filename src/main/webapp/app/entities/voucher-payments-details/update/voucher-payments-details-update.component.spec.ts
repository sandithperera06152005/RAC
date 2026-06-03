import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { VoucherPaymentsDetailsService } from '../service/voucher-payments-details.service';
import { IVoucherPaymentsDetails } from '../voucher-payments-details.model';
import { VoucherPaymentsDetailsFormService } from './voucher-payments-details-form.service';

import { VoucherPaymentsDetailsUpdateComponent } from './voucher-payments-details-update.component';

describe('VoucherPaymentsDetails Management Update Component', () => {
  let comp: VoucherPaymentsDetailsUpdateComponent;
  let fixture: ComponentFixture<VoucherPaymentsDetailsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let voucherPaymentsDetailsFormService: VoucherPaymentsDetailsFormService;
  let voucherPaymentsDetailsService: VoucherPaymentsDetailsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [VoucherPaymentsDetailsUpdateComponent],
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
      .overrideTemplate(VoucherPaymentsDetailsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(VoucherPaymentsDetailsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    voucherPaymentsDetailsFormService = TestBed.inject(VoucherPaymentsDetailsFormService);
    voucherPaymentsDetailsService = TestBed.inject(VoucherPaymentsDetailsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const voucherPaymentsDetails: IVoucherPaymentsDetails = { id: 456 };

      activatedRoute.data = of({ voucherPaymentsDetails });
      comp.ngOnInit();

      expect(comp.voucherPaymentsDetails).toEqual(voucherPaymentsDetails);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IVoucherPaymentsDetails>>();
      const voucherPaymentsDetails = { id: 123 };
      jest.spyOn(voucherPaymentsDetailsFormService, 'getVoucherPaymentsDetails').mockReturnValue(voucherPaymentsDetails);
      jest.spyOn(voucherPaymentsDetailsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ voucherPaymentsDetails });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: voucherPaymentsDetails }));
      saveSubject.complete();

      // THEN
      expect(voucherPaymentsDetailsFormService.getVoucherPaymentsDetails).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(voucherPaymentsDetailsService.update).toHaveBeenCalledWith(expect.objectContaining(voucherPaymentsDetails));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IVoucherPaymentsDetails>>();
      const voucherPaymentsDetails = { id: 123 };
      jest.spyOn(voucherPaymentsDetailsFormService, 'getVoucherPaymentsDetails').mockReturnValue({ id: null });
      jest.spyOn(voucherPaymentsDetailsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ voucherPaymentsDetails: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: voucherPaymentsDetails }));
      saveSubject.complete();

      // THEN
      expect(voucherPaymentsDetailsFormService.getVoucherPaymentsDetails).toHaveBeenCalled();
      expect(voucherPaymentsDetailsService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IVoucherPaymentsDetails>>();
      const voucherPaymentsDetails = { id: 123 };
      jest.spyOn(voucherPaymentsDetailsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ voucherPaymentsDetails });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(voucherPaymentsDetailsService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
