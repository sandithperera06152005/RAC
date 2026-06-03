import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { AutojobsaleinvoicecommonservicechargeDetailComponent } from './autojobsaleinvoicecommonservicecharge-detail.component';

describe('Autojobsaleinvoicecommonservicecharge Management Detail Component', () => {
  let comp: AutojobsaleinvoicecommonservicechargeDetailComponent;
  let fixture: ComponentFixture<AutojobsaleinvoicecommonservicechargeDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AutojobsaleinvoicecommonservicechargeDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () =>
                import('./autojobsaleinvoicecommonservicecharge-detail.component').then(
                  m => m.AutojobsaleinvoicecommonservicechargeDetailComponent,
                ),
              resolve: { autojobsaleinvoicecommonservicecharge: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(AutojobsaleinvoicecommonservicechargeDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AutojobsaleinvoicecommonservicechargeDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load autojobsaleinvoicecommonservicecharge on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', AutojobsaleinvoicecommonservicechargeDetailComponent);

      // THEN
      expect(instance.autojobsaleinvoicecommonservicecharge()).toEqual(expect.objectContaining({ id: 123 }));
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
