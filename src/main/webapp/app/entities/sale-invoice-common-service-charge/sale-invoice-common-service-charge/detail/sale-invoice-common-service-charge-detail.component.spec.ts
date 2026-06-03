import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { SaleInvoiceCommonServiceChargeDetailComponent } from './sale-invoice-common-service-charge-detail.component';

describe('SaleInvoiceCommonServiceCharge Management Detail Component', () => {
  let comp: SaleInvoiceCommonServiceChargeDetailComponent;
  let fixture: ComponentFixture<SaleInvoiceCommonServiceChargeDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SaleInvoiceCommonServiceChargeDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () =>
                import('./sale-invoice-common-service-charge-detail.component').then(m => m.SaleInvoiceCommonServiceChargeDetailComponent),
              resolve: { saleInvoiceCommonServiceCharge: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(SaleInvoiceCommonServiceChargeDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SaleInvoiceCommonServiceChargeDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load saleInvoiceCommonServiceCharge on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', SaleInvoiceCommonServiceChargeDetailComponent);

      // THEN
      expect(instance.saleInvoiceCommonServiceCharge()).toEqual(expect.objectContaining({ id: 123 }));
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
