import { AfterViewInit, Directive, ElementRef, HostListener, inject } from '@angular/core';
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
    const raw = this.el.nativeElement.value;
    const num = parseFloat(raw);
    if (!isNaN(num)) {
      this.el.nativeElement.value = num.toFixed(2);
    } else if (raw === '' || raw == null) {
      this.el.nativeElement.value = '0.00';
    }
  }
}
