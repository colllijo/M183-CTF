import {Component, EventEmitter, Output} from '@angular/core';
import {MatButton, MatIconButton} from "@angular/material/button";
import{ TranslateModule } from "@ngx-translate/core";
import {MatIcon} from "@angular/material/icon";

@Component({
  selector: 'ctf-file-input',
  standalone: true,
  imports: [
    MatButton,
    TranslateModule,
    MatIconButton,
    MatIcon
  ],
  templateUrl: './file-input.component.html',
  styleUrl: './file-input.component.scss'
})
export class FileInputComponent {
  @Output() fileSelected = new EventEmitter();
  public file?: File;

  unselectFile() {
    this.fileSelected.emit();
    this.file = undefined;
  }

  selectFile(event: Event) {
    this.file = (event.target as HTMLInputElement).files![0];
    if (this.file) {
      this.fileSelected.emit(this.file);
    }
  }
}
