import { Component, Input, ViewChild, forwardRef } from '@angular/core';
import { ControlValueAccessor, FormsModule, NG_VALUE_ACCESSOR, ReactiveFormsModule } from '@angular/forms';
import { MatButton, MatIconButton } from "@angular/material/button";
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIcon } from "@angular/material/icon";
import { MatTooltipModule } from '@angular/material/tooltip';
import { TranslateModule } from "@ngx-translate/core";

@Component({
  selector: 'ctf-file-input',
  standalone: true,
  imports: [
    FormsModule,
    MatButton,
    MatFormFieldModule,
    MatIconButton,
    MatIcon,
    MatTooltipModule,
    ReactiveFormsModule,
    TranslateModule
  ],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => FileInputComponent),
      multi: true
    }
  ],
  templateUrl: './file-input.component.html',
  styleUrl: './file-input.component.scss'
})
export class FileInputComponent implements ControlValueAccessor {
  public file: File | null;

  @ViewChild('fileInput') private input!: HTMLInputElement;
  @Input() public label?: string;

  public touched: boolean;
  public disabled: boolean;
  public onChange: (file: File | null) => void;
  public onTouched: () => void;

  constructor() {
    this.file = null;

    this.touched = false;
    this.disabled = false;
    this.onChange = (_: File | null) => {};
    this.onTouched = () => {};
  }

  public onSelect(event: Event): void {
    event.preventDefault();
    event.stopPropagation();

    const target = event.target as HTMLInputElement;
    if (target.files && !this.disabled) {
      this.file = target.files.item(0);

      this.markAsTouched();
      this.onChange(this.file);
    }
  }

  public deleteFile(): void {
    this.input.files = null;
    this.file = null;

    this.markAsTouched();
    this.onChange(this.file);
  }

  public writeValue(file: File | null): void {
    this.file = file;
  }

  public registerOnChange(fn: (file: File | null) => void): void {
    this.onChange = fn;
  }

  public registerOnTouched(fn: () => void): void {
    this.onTouched = fn;
  }

  public markAsTouched(): void {
    if (!this.touched) {
      this.touched = true;
      this.onTouched();
    }
  }

  public setDisabledState(isDisabled: boolean): void {
    this.disabled = isDisabled;
  }
}
