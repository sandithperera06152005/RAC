import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { InvoiceServiceChargeDetailComponent } from './invoice-service-charge-detail.component';

describe('InvoiceServiceCharge Management Detail Component', () => {
  let comp: InvoiceServiceChargeDetailComponent;
  let fixture: ComponentFixture<InvoiceServiceChargeDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InvoiceServiceChargeDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./invoice-service-charge-detail.component').then(m => m.InvoiceServiceChargeDetailComponent),
              resolve: { invoiceServiceCharge: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(InvoiceServiceChargeDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(InvoiceServiceChargeDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load invoiceServiceCharge on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', InvoiceServiceChargeDetailComponent);

      // THEN
      expect(instance.invoiceServiceCharge()).toEqual(expect.objectContaining({ id: 123 }));
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
