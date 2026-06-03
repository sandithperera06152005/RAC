import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { WorkshopVehicleWorkListDetailComponent } from './workshop-vehicle-work-list-detail.component';

describe('WorkshopVehicleWorkList Management Detail Component', () => {
  let comp: WorkshopVehicleWorkListDetailComponent;
  let fixture: ComponentFixture<WorkshopVehicleWorkListDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [WorkshopVehicleWorkListDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () =>
                import('./workshop-vehicle-work-list-detail.component').then(m => m.WorkshopVehicleWorkListDetailComponent),
              resolve: { workshopVehicleWorkList: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(WorkshopVehicleWorkListDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(WorkshopVehicleWorkListDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load workshopVehicleWorkList on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', WorkshopVehicleWorkListDetailComponent);

      // THEN
      expect(instance.workshopVehicleWorkList()).toEqual(expect.objectContaining({ id: 123 }));
    });
  });

  describe('PreviousState', () => {
    it('Should navigate to previous state', () => {
      jest.spyOn(window.history, 'back');
      comp.previousState();
      expect(window.history.back).toHaveBeenCalled();
    });
  });
});
