import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { SalesInvoiceLinesDetailComponent } from './sales-invoice-lines-detail.component';

describe('SalesInvoiceLines Management Detail Component', () => {
  let comp: SalesInvoiceLinesDetailComponent;
  let fixture: ComponentFixture<SalesInvoiceLinesDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SalesInvoiceLinesDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./sales-invoice-lines-detail.component').then(m => m.SalesInvoiceLinesDetailComponent),
              resolve: { salesInvoiceLines: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(SalesInvoiceLinesDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SalesInvoiceLinesDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load salesInvoiceLines on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', SalesInvoiceLinesDetailComponent);

      // THEN
      expect(instance.salesInvoiceLines()).toEqual(expect.objectContaining({ id: 123 }));
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
