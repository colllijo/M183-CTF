import { Component, ElementRef, Inject, ModelSignal, ViewChild, model } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatButtonModule } from '@angular/material/button';
import { MAT_DIALOG_DATA, MatDialogModule } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';

import { TranslateModule } from '@ngx-translate/core';

@Component({
  selector: 'ctf-add-role-dialog',
  standalone: true,
  imports: [
    FormsModule,
    MatAutocompleteModule,
    MatButtonModule,
    MatDialogModule,
    MatFormFieldModule,
    MatInputModule,
    TranslateModule
  ],
  templateUrl: './add-role-dialog.component.html',
  styleUrl: './add-role-dialog.component.scss'
})
export class AddRoleDialogComponent {
  public role: ModelSignal<string>;
  public filteredOptions: string[];

  @ViewChild('input') input: ElementRef<HTMLInputElement> | undefined;

  constructor(@Inject(MAT_DIALOG_DATA) public data: { roles: string[] }) {
    this.role = model('');
    this.filteredOptions = data.roles.slice();
  }

  public filter(): void {
    const filterValue = this.input!.nativeElement.value.toLowerCase();
    this.filteredOptions = this.data.roles.filter(option => option.toLowerCase().includes(filterValue));
  }
}
