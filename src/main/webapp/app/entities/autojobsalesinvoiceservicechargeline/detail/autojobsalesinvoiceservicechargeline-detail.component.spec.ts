import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { AutojobsalesinvoiceservicechargelineDetailComponent } from './autojobsalesinvoiceservicechargeline-detail.component';

describe('Autojobsalesinvoiceservicechargeline Management Detail Component', () => {
  let comp: AutojobsalesinvoiceservicechargelineDetailComponent;
  let fixture: ComponentFixture<AutojobsalesinvoiceservicechargelineDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AutojobsalesinvoiceservicechargelineDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () =>
                import('./autojobsalesinvoiceservicechargeline-detail.component').then(
                  m => m.AutojobsalesinvoiceservicechargelineDetailComponent,
                ),
              resolve: { autojobsalesinvoiceservicechargeline: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(AutojobsalesinvoiceservicechargelineDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AutojobsalesinvoiceservicechargelineDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load autojobsalesinvoiceservicechargeline on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', AutojobsalesinvoiceservicechargelineDetailComponent);

      // THEN
      expect(instance.autojobsalesinvoiceservicechargeline()).toEqual(expect.objectContaining({ id: 123 }));
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
