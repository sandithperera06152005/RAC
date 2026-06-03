import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { SalesInvoiceServiceChargeLineDummyDetailComponent } from './sales-invoice-service-charge-line-dummy-detail.component';

describe('SalesInvoiceServiceChargeLineDummy Management Detail Component', () => {
  let comp: SalesInvoiceServiceChargeLineDummyDetailComponent;
  let fixture: ComponentFixture<SalesInvoiceServiceChargeLineDummyDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SalesInvoiceServiceChargeLineDummyDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () =>
                import('./sales-invoice-service-charge-line-dummy-detail.component').then(
                  m => m.SalesInvoiceServiceChargeLineDummyDetailComponent,
                ),
              resolve: { salesInvoiceServiceChargeLineDummy: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(SalesInvoiceServiceChargeLineDummyDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SalesInvoiceServiceChargeLineDummyDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load salesInvoiceServiceChargeLineDummy on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', SalesInvoiceServiceChargeLineDummyDetailComponent);

      // THEN
      expect(instance.salesInvoiceServiceChargeLineDummy()).toEqual(expect.objectContaining({ id: 123 }));
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
