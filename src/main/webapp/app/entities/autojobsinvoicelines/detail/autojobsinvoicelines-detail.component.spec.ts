import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { AutojobsinvoicelinesDetailComponent } from './autojobsinvoicelines-detail.component';

describe('Autojobsinvoicelines Management Detail Component', () => {
  let comp: AutojobsinvoicelinesDetailComponent;
  let fixture: ComponentFixture<AutojobsinvoicelinesDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AutojobsinvoicelinesDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./autojobsinvoicelines-detail.component').then(m => m.AutojobsinvoicelinesDetailComponent),
              resolve: { autojobsinvoicelines: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(AutojobsinvoicelinesDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AutojobsinvoicelinesDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load autojobsinvoicelines on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', AutojobsinvoicelinesDetailComponent);

      // THEN
      expect(instance.autojobsinvoicelines()).toEqual(expect.objectContaining({ id: 123 }));
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
