import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { SalesInvoiceDummyDetailComponent } from './sales-invoice-dummy-detail.component';

describe('SalesInvoiceDummy Management Detail Component', () => {
  let comp: SalesInvoiceDummyDetailComponent;
  let fixture: ComponentFixture<SalesInvoiceDummyDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SalesInvoiceDummyDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./sales-invoice-dummy-detail.component').then(m => m.SalesInvoiceDummyDetailComponent),
              resolve: { salesInvoiceDummy: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(SalesInvoiceDummyDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SalesInvoiceDummyDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load salesInvoiceDummy on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', SalesInvoiceDummyDetailComponent);

      // THEN
      expect(instance.salesInvoiceDummy()).toEqual(expect.objectContaining({ id: 123 }));
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
