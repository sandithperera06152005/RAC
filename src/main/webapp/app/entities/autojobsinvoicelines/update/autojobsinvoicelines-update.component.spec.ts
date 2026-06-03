import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { AutojobsinvoicelinesService } from '../service/autojobsinvoicelines.service';
import { IAutojobsinvoicelines } from '../autojobsinvoicelines.model';
import { AutojobsinvoicelinesFormService } from './autojobsinvoicelines-form.service';

import { AutojobsinvoicelinesUpdateComponent } from './autojobsinvoicelines-update.component';

describe('Autojobsinvoicelines Management Update Component', () => {
  let comp: AutojobsinvoicelinesUpdateComponent;
  let fixture: ComponentFixture<AutojobsinvoicelinesUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let autojobsinvoicelinesFormService: AutojobsinvoicelinesFormService;
  let autojobsinvoicelinesService: AutojobsinvoicelinesService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [AutojobsinvoicelinesUpdateComponent],
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
      .overrideTemplate(AutojobsinvoicelinesUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AutojobsinvoicelinesUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    autojobsinvoicelinesFormService = TestBed.inject(AutojobsinvoicelinesFormService);
    autojobsinvoicelinesService = TestBed.inject(AutojobsinvoicelinesService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const autojobsinvoicelines: IAutojobsinvoicelines = { id: 456 };

      activatedRoute.data = of({ autojobsinvoicelines });
      comp.ngOnInit();

      expect(comp.autojobsinvoicelines).toEqual(autojobsinvoicelines);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAutojobsinvoicelines>>();
      const autojobsinvoicelines = { id: 123 };
      jest.spyOn(autojobsinvoicelinesFormService, 'getAutojobsinvoicelines').mockReturnValue(autojobsinvoicelines);
      jest.spyOn(autojobsinvoicelinesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ autojobsinvoicelines });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: autojobsinvoicelines }));
      saveSubject.complete();

      // THEN
      expect(autojobsinvoicelinesFormService.getAutojobsinvoicelines).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(autojobsinvoicelinesService.update).toHaveBeenCalledWith(expect.objectContaining(autojobsinvoicelines));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAutojobsinvoicelines>>();
      const autojobsinvoicelines = { id: 123 };
      jest.spyOn(autojobsinvoicelinesFormService, 'getAutojobsinvoicelines').mockReturnValue({ id: null });
      jest.spyOn(autojobsinvoicelinesService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ autojobsinvoicelines: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: autojobsinvoicelines }));
      saveSubject.complete();

      // THEN
      expect(autojobsinvoicelinesFormService.getAutojobsinvoicelines).toHaveBeenCalled();
      expect(autojobsinvoicelinesService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAutojobsinvoicelines>>();
      const autojobsinvoicelines = { id: 123 };
      jest.spyOn(autojobsinvoicelinesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ autojobsinvoicelines });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(autojobsinvoicelinesService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
