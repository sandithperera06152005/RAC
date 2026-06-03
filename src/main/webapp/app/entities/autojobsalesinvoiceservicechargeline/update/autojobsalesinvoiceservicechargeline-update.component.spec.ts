import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { AutojobsalesinvoiceservicechargelineService } from '../service/autojobsalesinvoiceservicechargeline.service';
import { IAutojobsalesinvoiceservicechargeline } from '../autojobsalesinvoiceservicechargeline.model';
import { AutojobsalesinvoiceservicechargelineFormService } from './autojobsalesinvoiceservicechargeline-form.service';

import { AutojobsalesinvoiceservicechargelineUpdateComponent } from './autojobsalesinvoiceservicechargeline-update.component';

describe('Autojobsalesinvoiceservicechargeline Management Update Component', () => {
  let comp: AutojobsalesinvoiceservicechargelineUpdateComponent;
  let fixture: ComponentFixture<AutojobsalesinvoiceservicechargelineUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let autojobsalesinvoiceservicechargelineFormService: AutojobsalesinvoiceservicechargelineFormService;
  let autojobsalesinvoiceservicechargelineService: AutojobsalesinvoiceservicechargelineService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [AutojobsalesinvoiceservicechargelineUpdateComponent],
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
      .overrideTemplate(AutojobsalesinvoiceservicechargelineUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AutojobsalesinvoiceservicechargelineUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    autojobsalesinvoiceservicechargelineFormService = TestBed.inject(AutojobsalesinvoiceservicechargelineFormService);
    autojobsalesinvoiceservicechargelineService = TestBed.inject(AutojobsalesinvoiceservicechargelineService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const autojobsalesinvoiceservicechargeline: IAutojobsalesinvoiceservicechargeline = { id: 456 };

      activatedRoute.data = of({ autojobsalesinvoiceservicechargeline });
      comp.ngOnInit();

      expect(comp.autojobsalesinvoiceservicechargeline).toEqual(autojobsalesinvoiceservicechargeline);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAutojobsalesinvoiceservicechargeline>>();
      const autojobsalesinvoiceservicechargeline = { id: 123 };
      jest
        .spyOn(autojobsalesinvoiceservicechargelineFormService, 'getAutojobsalesinvoiceservicechargeline')
        .mockReturnValue(autojobsalesinvoiceservicechargeline);
      jest.spyOn(autojobsalesinvoiceservicechargelineService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ autojobsalesinvoiceservicechargeline });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: autojobsalesinvoiceservicechargeline }));
      saveSubject.complete();

      // THEN
      expect(autojobsalesinvoiceservicechargelineFormService.getAutojobsalesinvoiceservicechargeline).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(autojobsalesinvoiceservicechargelineService.update).toHaveBeenCalledWith(
        expect.objectContaining(autojobsalesinvoiceservicechargeline),
      );
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAutojobsalesinvoiceservicechargeline>>();
      const autojobsalesinvoiceservicechargeline = { id: 123 };
      jest.spyOn(autojobsalesinvoiceservicechargelineFormService, 'getAutojobsalesinvoiceservicechargeline').mockReturnValue({ id: null });
      jest.spyOn(autojobsalesinvoiceservicechargelineService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ autojobsalesinvoiceservicechargeline: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: autojobsalesinvoiceservicechargeline }));
      saveSubject.complete();

      // THEN
      expect(autojobsalesinvoiceservicechargelineFormService.getAutojobsalesinvoiceservicechargeline).toHaveBeenCalled();
      expect(autojobsalesinvoiceservicechargelineService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAutojobsalesinvoiceservicechargeline>>();
      const autojobsalesinvoiceservicechargeline = { id: 123 };
      jest.spyOn(autojobsalesinvoiceservicechargelineService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ autojobsalesinvoiceservicechargeline });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(autojobsalesinvoiceservicechargelineService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
