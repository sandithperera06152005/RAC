import { AfterViewInit, Directive, ElementRef, HostListener, Input, inject } from '@angular/core';
import { NgControl } from '@angular/forms';

/**
 * Directive that ensures numeric inputs always display exactly 2 decimal places.
 * Apply to any <input appDecimal> element (use type="text" inputmode="decimal").
 * Works with reactive forms (formControlName) by subscribing to valueChanges.
 */
@Directive({
  selector: 'input[appDecimal]',
  standalone: true,
})
export class DecimalInputDirective implements AfterViewInit {
  /** When true, empty/null values stay blank instead of showing 0.00. */
  @Input() appDecimalAllowEmpty = false;

  /** When true, only format on blur (and initial render), not while the user is typing. */
  @Input() appDecimalDeferFormat = false;

  private el = inject(ElementRef<HTMLInputElement>);
  private ngControl = inject(NgControl, { optional: true, self: true });

  ngAfterViewInit(): void {
    // Format on initial render (after Angular sets the value)
    setTimeout(() => this.formatDisplay(), 0);

    if (!this.appDecimalDeferFormat) {
      // Re-format whenever the reactive form control value changes programmatically
      this.ngControl?.valueChanges?.subscribe(() => {
        setTimeout(() => this.formatDisplay(), 0);
      });
    }
  }

  @HostListener('focus')
  onFocus(): void {
    if (!this.appDecimalDeferFormat) {
      return;
    }
    const controlVal = this.ngControl?.value;
    if (controlVal === null || controlVal === undefined || controlVal === '') {
      return;
    }
    const num = parseFloat(String(controlVal));
    if (!isNaN(num)) {
      this.el.nativeElement.value = String(num);
    }
  }

  @HostListener('blur')
  onBlur(): void {
    if (this.appDecimalDeferFormat) {
      // Run after component blur handlers update the form control
      setTimeout(() => this.formatDisplay(), 0);
    } else {
      this.formatDisplay();
    }
  }

  private formatDisplay(): void {
    const controlVal = this.ngControl?.value;
    const elementVal = this.el.nativeElement.value;
    const raw =
      controlVal === null || controlVal === undefined || controlVal === ''
        ? elementVal
        : String(controlVal !== undefined ? controlVal : elementVal);
    const num = parseFloat(raw);
    if (!isNaN(num) && raw !== '') {
      this.el.nativeElement.value = num.toFixed(2);
    } else if (raw === '' || raw == null) {
      this.el.nativeElement.value = this.appDecimalAllowEmpty ? '' : '0.00';
    }
  }
}
