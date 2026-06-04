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

  private el = inject(ElementRef<HTMLInputElement>);
  private ngControl = inject(NgControl, { optional: true, self: true });

  ngAfterViewInit(): void {
    // Format on initial render (after Angular sets the value)
    setTimeout(() => this.formatDisplay(), 0);

    // Re-format whenever the reactive form control value changes programmatically
    this.ngControl?.valueChanges?.subscribe(() => {
      setTimeout(() => this.formatDisplay(), 0);
    });
  }

  @HostListener('blur')
  onBlur(): void {
    this.formatDisplay();
  }

  private formatDisplay(): void {
    const controlVal = this.ngControl?.value;
    const raw =
      controlVal === null || controlVal === undefined || controlVal === ''
        ? ''
        : String(controlVal !== undefined ? controlVal : this.el.nativeElement.value);
    const num = parseFloat(raw);
    if (!isNaN(num) && raw !== '') {
      this.el.nativeElement.value = num.toFixed(2);
    } else if (raw === '' || raw == null) {
      this.el.nativeElement.value = this.appDecimalAllowEmpty ? '' : '0.00';
    }
  }
}
