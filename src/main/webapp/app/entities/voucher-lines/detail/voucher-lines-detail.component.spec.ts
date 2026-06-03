import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { VoucherLinesDetailComponent } from './voucher-lines-detail.component';

describe('VoucherLines Management Detail Component', () => {
  let comp: VoucherLinesDetailComponent;
  let fixture: ComponentFixture<VoucherLinesDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VoucherLinesDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./voucher-lines-detail.component').then(m => m.VoucherLinesDetailComponent),
              resolve: { voucherLines: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(VoucherLinesDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(VoucherLinesDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load voucherLines on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', VoucherLinesDetailComponent);

      // THEN
      expect(instance.voucherLines()).toEqual(expect.objectContaining({ id: 123 }));
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
