import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { WorkshopVehicleWorkListService } from '../service/workshop-vehicle-work-list.service';
import { IWorkshopVehicleWorkList } from '../workshop-vehicle-work-list.model';
import { WorkshopVehicleWorkListFormService } from './workshop-vehicle-work-list-form.service';

import { WorkshopVehicleWorkListUpdateComponent } from './workshop-vehicle-work-list-update.component';

describe('WorkshopVehicleWorkList Management Update Component', () => {
  let comp: WorkshopVehicleWorkListUpdateComponent;
  let fixture: ComponentFixture<WorkshopVehicleWorkListUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let workshopVehicleWorkListFormService: WorkshopVehicleWorkListFormService;
  let workshopVehicleWorkListService: WorkshopVehicleWorkListService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [WorkshopVehicleWorkListUpdateComponent],
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
      .overrideTemplate(WorkshopVehicleWorkListUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(WorkshopVehicleWorkListUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    workshopVehicleWorkListFormService = TestBed.inject(WorkshopVehicleWorkListFormService);
    workshopVehicleWorkListService = TestBed.inject(WorkshopVehicleWorkListService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const workshopVehicleWorkList: IWorkshopVehicleWorkList = { id: 456 };

      activatedRoute.data = of({ workshopVehicleWorkList });
      comp.ngOnInit();

      expect(comp.workshopVehicleWorkList).toEqual(workshopVehicleWorkList);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IWorkshopVehicleWorkList>>();
      const workshopVehicleWorkList = { id: 123 };
      jest.spyOn(workshopVehicleWorkListFormService, 'getWorkshopVehicleWorkList').mockReturnValue(workshopVehicleWorkList);
      jest.spyOn(workshopVehicleWorkListService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ workshopVehicleWorkList });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: workshopVehicleWorkList }));
      saveSubject.complete();

      // THEN
      expect(workshopVehicleWorkListFormService.getWorkshopVehicleWorkList).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(workshopVehicleWorkListService.update).toHaveBeenCalledWith(expect.objectContaining(workshopVehicleWorkList));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IWorkshopVehicleWorkList>>();
      const workshopVehicleWorkList = { id: 123 };
      jest.spyOn(workshopVehicleWorkListFormService, 'getWorkshopVehicleWorkList').mockReturnValue({ id: null });
      jest.spyOn(workshopVehicleWorkListService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ workshopVehicleWorkList: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: workshopVehicleWorkList }));
      saveSubject.complete();

      // THEN
      expect(workshopVehicleWorkListFormService.getWorkshopVehicleWorkList).toHaveBeenCalled();
      expect(workshopVehicleWorkListService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IWorkshopVehicleWorkList>>();
      const workshopVehicleWorkList = { id: 123 };
      jest.spyOn(workshopVehicleWorkListService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ workshopVehicleWorkList });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(workshopVehicleWorkListService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
