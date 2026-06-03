import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { SalesInvoiceServiceChargeLineDetailComponent } from './sales-invoice-service-charge-line-detail.component';

describe('SalesInvoiceServiceChargeLine Management Detail Component', () => {
  let comp: SalesInvoiceServiceChargeLineDetailComponent;
  let fixture: ComponentFixture<SalesInvoiceServiceChargeLineDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SalesInvoiceServiceChargeLineDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () =>
                import('./sales-invoice-service-charge-line-detail.component').then(m => m.SalesInvoiceServiceChargeLineDetailComponent),
              resolve: { salesInvoiceServiceChargeLine: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(SalesInvoiceServiceChargeLineDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SalesInvoiceServiceChargeLineDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load salesInvoiceServiceChargeLine on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', SalesInvoiceServiceChargeLineDetailComponent);

      // THEN
      expect(instance.salesInvoiceServiceChargeLine()).toEqual(expect.objectContaining({ id: 123 }));
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
