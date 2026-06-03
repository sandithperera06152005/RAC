import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { VoucherPaymentsDetailsDetailComponent } from './voucher-payments-details-detail.component';

describe('VoucherPaymentsDetails Management Detail Component', () => {
  let comp: VoucherPaymentsDetailsDetailComponent;
  let fixture: ComponentFixture<VoucherPaymentsDetailsDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VoucherPaymentsDetailsDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./voucher-payments-details-detail.component').then(m => m.VoucherPaymentsDetailsDetailComponent),
              resolve: { voucherPaymentsDetails: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(VoucherPaymentsDetailsDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(VoucherPaymentsDetailsDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load voucherPaymentsDetails on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', VoucherPaymentsDetailsDetailComponent);

      // THEN
      expect(instance.voucherPaymentsDetails()).toEqual(expect.objectContaining({ id: 123 }));
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
