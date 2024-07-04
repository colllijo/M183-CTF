import { Directive, ElementRef, Input, OnChanges } from '@angular/core';

@Directive({
  selector: '[ctfTestId]',
  standalone: true
})
export class TestIdDirective implements OnChanges {
  @Input() ctfTestId?: string;

  constructor(private element: ElementRef) {}

  public ngOnChanges(): void {
    if (Object.hasOwn(window, 'Cypress')) {
      this.element.nativeElement.setAttribute('cypress-test-id', this.ctfTestId);
    }
  }
}
