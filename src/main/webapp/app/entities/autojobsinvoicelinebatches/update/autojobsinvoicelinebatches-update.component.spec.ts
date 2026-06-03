import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { AutojobsinvoicelinebatchesService } from '../service/autojobsinvoicelinebatches.service';
import { IAutojobsinvoicelinebatches } from '../autojobsinvoicelinebatches.model';
import { AutojobsinvoicelinebatchesFormService } from './autojobsinvoicelinebatches-form.service';

import { AutojobsinvoicelinebatchesUpdateComponent } from './autojobsinvoicelinebatches-update.component';

describe('Autojobsinvoicelinebatches Management Update Component', () => {
  let comp: AutojobsinvoicelinebatchesUpdateComponent;
  let fixture: ComponentFixture<AutojobsinvoicelinebatchesUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let autojobsinvoicelinebatchesFormService: AutojobsinvoicelinebatchesFormService;
  let autojobsinvoicelinebatchesService: AutojobsinvoicelinebatchesService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [AutojobsinvoicelinebatchesUpdateComponent],
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
      .overrideTemplate(AutojobsinvoicelinebatchesUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AutojobsinvoicelinebatchesUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    autojobsinvoicelinebatchesFormService = TestBed.inject(AutojobsinvoicelinebatchesFormService);
    autojobsinvoicelinebatchesService = TestBed.inject(AutojobsinvoicelinebatchesService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const autojobsinvoicelinebatches: IAutojobsinvoicelinebatches = { id: 456 };

      activatedRoute.data = of({ autojobsinvoicelinebatches });
      comp.ngOnInit();

      expect(comp.autojobsinvoicelinebatches).toEqual(autojobsinvoicelinebatches);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAutojobsinvoicelinebatches>>();
      const autojobsinvoicelinebatches = { id: 123 };
      jest.spyOn(autojobsinvoicelinebatchesFormService, 'getAutojobsinvoicelinebatches').mockReturnValue(autojobsinvoicelinebatches);
      jest.spyOn(autojobsinvoicelinebatchesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ autojobsinvoicelinebatches });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: autojobsinvoicelinebatches }));
      saveSubject.complete();

      // THEN
      expect(autojobsinvoicelinebatchesFormService.getAutojobsinvoicelinebatches).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(autojobsinvoicelinebatchesService.update).toHaveBeenCalledWith(expect.objectContaining(autojobsinvoicelinebatches));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAutojobsinvoicelinebatches>>();
      const autojobsinvoicelinebatches = { id: 123 };
      jest.spyOn(autojobsinvoicelinebatchesFormService, 'getAutojobsinvoicelinebatches').mockReturnValue({ id: null });
      jest.spyOn(autojobsinvoicelinebatchesService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ autojobsinvoicelinebatches: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: autojobsinvoicelinebatches }));
      saveSubject.complete();

      // THEN
      expect(autojobsinvoicelinebatchesFormService.getAutojobsinvoicelinebatches).toHaveBeenCalled();
      expect(autojobsinvoicelinebatchesService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAutojobsinvoicelinebatches>>();
      const autojobsinvoicelinebatches = { id: 123 };
      jest.spyOn(autojobsinvoicelinebatchesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ autojobsinvoicelinebatches });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(autojobsinvoicelinebatchesService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
