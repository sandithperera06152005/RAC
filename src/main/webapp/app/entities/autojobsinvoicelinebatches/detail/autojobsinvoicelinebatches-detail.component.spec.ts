import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { AutojobsinvoicelinebatchesDetailComponent } from './autojobsinvoicelinebatches-detail.component';

describe('Autojobsinvoicelinebatches Management Detail Component', () => {
  let comp: AutojobsinvoicelinebatchesDetailComponent;
  let fixture: ComponentFixture<AutojobsinvoicelinebatchesDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AutojobsinvoicelinebatchesDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () =>
                import('./autojobsinvoicelinebatches-detail.component').then(m => m.AutojobsinvoicelinebatchesDetailComponent),
              resolve: { autojobsinvoicelinebatches: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(AutojobsinvoicelinebatchesDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AutojobsinvoicelinebatchesDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load autojobsinvoicelinebatches on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', AutojobsinvoicelinebatchesDetailComponent);

      // THEN
      expect(instance.autojobsinvoicelinebatches()).toEqual(expect.objectContaining({ id: 123 }));
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
