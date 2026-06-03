import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { CommonserviceoptionService } from '../service/commonserviceoption.service';
import { ICommonserviceoption } from '../commonserviceoption.model';
import { CommonserviceoptionFormService } from './commonserviceoption-form.service';

import { CommonserviceoptionUpdateComponent } from './commonserviceoption-update.component';

describe('Commonserviceoption Management Update Component', () => {
  let comp: CommonserviceoptionUpdateComponent;
  let fixture: ComponentFixture<CommonserviceoptionUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let commonserviceoptionFormService: CommonserviceoptionFormService;
  let commonserviceoptionService: CommonserviceoptionService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [CommonserviceoptionUpdateComponent],
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
      .overrideTemplate(CommonserviceoptionUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CommonserviceoptionUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    commonserviceoptionFormService = TestBed.inject(CommonserviceoptionFormService);
    commonserviceoptionService = TestBed.inject(CommonserviceoptionService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const commonserviceoption: ICommonserviceoption = { id: 456 };

      activatedRoute.data = of({ commonserviceoption });
      comp.ngOnInit();

      expect(comp.commonserviceoption).toEqual(commonserviceoption);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICommonserviceoption>>();
      const commonserviceoption = { id: 123 };
      jest.spyOn(commonserviceoptionFormService, 'getCommonserviceoption').mockReturnValue(commonserviceoption);
      jest.spyOn(commonserviceoptionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ commonserviceoption });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: commonserviceoption }));
      saveSubject.complete();

      // THEN
      expect(commonserviceoptionFormService.getCommonserviceoption).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(commonserviceoptionService.update).toHaveBeenCalledWith(expect.objectContaining(commonserviceoption));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICommonserviceoption>>();
      const commonserviceoption = { id: 123 };
      jest.spyOn(commonserviceoptionFormService, 'getCommonserviceoption').mockReturnValue({ id: null });
      jest.spyOn(commonserviceoptionService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ commonserviceoption: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: commonserviceoption }));
      saveSubject.complete();

      // THEN
      expect(commonserviceoptionFormService.getCommonserviceoption).toHaveBeenCalled();
      expect(commonserviceoptionService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICommonserviceoption>>();
      const commonserviceoption = { id: 123 };
      jest.spyOn(commonserviceoptionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ commonserviceoption });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(commonserviceoptionService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
