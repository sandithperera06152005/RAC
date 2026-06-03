import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { ReceiptpaymentsdetailsDetailComponent } from './receiptpaymentsdetails-detail.component';

describe('Receiptpaymentsdetails Management Detail Component', () => {
  let comp: ReceiptpaymentsdetailsDetailComponent;
  let fixture: ComponentFixture<ReceiptpaymentsdetailsDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReceiptpaymentsdetailsDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./receiptpaymentsdetails-detail.component').then(m => m.ReceiptpaymentsdetailsDetailComponent),
              resolve: { receiptpaymentsdetails: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(ReceiptpaymentsdetailsDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ReceiptpaymentsdetailsDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load receiptpaymentsdetails on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', ReceiptpaymentsdetailsDetailComponent);

      // THEN
      expect(instance.receiptpaymentsdetails()).toEqual(expect.objectContaining({ id: 123 }));
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
