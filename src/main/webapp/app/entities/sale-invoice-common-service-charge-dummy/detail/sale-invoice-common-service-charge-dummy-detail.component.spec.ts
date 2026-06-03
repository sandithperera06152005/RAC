import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { SaleInvoiceCommonServiceChargeDummyDetailComponent } from './sale-invoice-common-service-charge-dummy-detail.component';

describe('SaleInvoiceCommonServiceChargeDummy Management Detail Component', () => {
  let comp: SaleInvoiceCommonServiceChargeDummyDetailComponent;
  let fixture: ComponentFixture<SaleInvoiceCommonServiceChargeDummyDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SaleInvoiceCommonServiceChargeDummyDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () =>
                import('./sale-invoice-common-service-charge-dummy-detail.component').then(
                  m => m.SaleInvoiceCommonServiceChargeDummyDetailComponent,
                ),
              resolve: { saleInvoiceCommonServiceChargeDummy: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(SaleInvoiceCommonServiceChargeDummyDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SaleInvoiceCommonServiceChargeDummyDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load saleInvoiceCommonServiceChargeDummy on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', SaleInvoiceCommonServiceChargeDummyDetailComponent);

      // THEN
      expect(instance.saleInvoiceCommonServiceChargeDummy()).toEqual(expect.objectContaining({ id: 123 }));
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
