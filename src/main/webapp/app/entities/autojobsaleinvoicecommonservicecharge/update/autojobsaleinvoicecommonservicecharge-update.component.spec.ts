import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { AutojobsaleinvoicecommonservicechargeService } from '../service/autojobsaleinvoicecommonservicecharge.service';
import { IAutojobsaleinvoicecommonservicecharge } from '../autojobsaleinvoicecommonservicecharge.model';
import { AutojobsaleinvoicecommonservicechargeFormService } from './autojobsaleinvoicecommonservicecharge-form.service';

import { AutojobsaleinvoicecommonservicechargeUpdateComponent } from './autojobsaleinvoicecommonservicecharge-update.component';

describe('Autojobsaleinvoicecommonservicecharge Management Update Component', () => {
  let comp: AutojobsaleinvoicecommonservicechargeUpdateComponent;
  let fixture: ComponentFixture<AutojobsaleinvoicecommonservicechargeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let autojobsaleinvoicecommonservicechargeFormService: AutojobsaleinvoicecommonservicechargeFormService;
  let autojobsaleinvoicecommonservicechargeService: AutojobsaleinvoicecommonservicechargeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [AutojobsaleinvoicecommonservicechargeUpdateComponent],
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
      .overrideTemplate(AutojobsaleinvoicecommonservicechargeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AutojobsaleinvoicecommonservicechargeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    autojobsaleinvoicecommonservicechargeFormService = TestBed.inject(AutojobsaleinvoicecommonservicechargeFormService);
    autojobsaleinvoicecommonservicechargeService = TestBed.inject(AutojobsaleinvoicecommonservicechargeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const autojobsaleinvoicecommonservicecharge: IAutojobsaleinvoicecommonservicecharge = { id: 456 };

      activatedRoute.data = of({ autojobsaleinvoicecommonservicecharge });
      comp.ngOnInit();

      expect(comp.autojobsaleinvoicecommonservicecharge).toEqual(autojobsaleinvoicecommonservicecharge);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAutojobsaleinvoicecommonservicecharge>>();
      const autojobsaleinvoicecommonservicecharge = { id: 123 };
      jest
        .spyOn(autojobsaleinvoicecommonservicechargeFormService, 'getAutojobsaleinvoicecommonservicecharge')
        .mockReturnValue(autojobsaleinvoicecommonservicecharge);
      jest.spyOn(autojobsaleinvoicecommonservicechargeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ autojobsaleinvoicecommonservicecharge });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: autojobsaleinvoicecommonservicecharge }));
      saveSubject.complete();

      // THEN
      expect(autojobsaleinvoicecommonservicechargeFormService.getAutojobsaleinvoicecommonservicecharge).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(autojobsaleinvoicecommonservicechargeService.update).toHaveBeenCalledWith(
        expect.objectContaining(autojobsaleinvoicecommonservicecharge),
      );
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAutojobsaleinvoicecommonservicecharge>>();
      const autojobsaleinvoicecommonservicecharge = { id: 123 };
      jest
        .spyOn(autojobsaleinvoicecommonservicechargeFormService, 'getAutojobsaleinvoicecommonservicecharge')
        .mockReturnValue({ id: null });
      jest.spyOn(autojobsaleinvoicecommonservicechargeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ autojobsaleinvoicecommonservicecharge: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: autojobsaleinvoicecommonservicecharge }));
      saveSubject.complete();

      // THEN
      expect(autojobsaleinvoicecommonservicechargeFormService.getAutojobsaleinvoicecommonservicecharge).toHaveBeenCalled();
      expect(autojobsaleinvoicecommonservicechargeService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAutojobsaleinvoicecommonservicecharge>>();
      const autojobsaleinvoicecommonservicecharge = { id: 123 };
      jest.spyOn(autojobsaleinvoicecommonservicechargeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ autojobsaleinvoicecommonservicecharge });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(autojobsaleinvoicecommonservicechargeService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
