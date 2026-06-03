import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { SalesInvoiceLinesDummyDetailComponent } from './sales-invoice-lines-dummy-detail.component';

describe('SalesInvoiceLinesDummy Management Detail Component', () => {
  let comp: SalesInvoiceLinesDummyDetailComponent;
  let fixture: ComponentFixture<SalesInvoiceLinesDummyDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SalesInvoiceLinesDummyDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () =>
                import('./sales-invoice-lines-dummy-detail.component').then(m => m.SalesInvoiceLinesDummyDetailComponent),
              resolve: { salesInvoiceLinesDummy: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(SalesInvoiceLinesDummyDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SalesInvoiceLinesDummyDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load salesInvoiceLinesDummy on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', SalesInvoiceLinesDummyDetailComponent);

      // THEN
      expect(instance.salesInvoiceLinesDummy()).toEqual(expect.objectContaining({ id: 123 }));
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
