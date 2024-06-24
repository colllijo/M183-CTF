import { Component } from '@angular/core';
import {MatButton} from "@angular/material/button";
import {MatCardModule} from "@angular/material/card";
import {MatError, MatFormFieldModule, MatLabel, MatSuffix} from "@angular/material/form-field";
import {MatIcon} from "@angular/material/icon";
import {MatInput} from "@angular/material/input";
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {RouterLink} from "@angular/router";
import {TranslateModule} from "@ngx-translate/core";
import {Store} from "@ngrx/store";
import {ChallengeActions} from "@+store/challenge/challenge.actions";
import {FileInputComponent} from "@component/file-input/file-input.component";

@Component({
  selector: 'ctf-challenge-creation',
  standalone: true,
  imports: [
    MatButton,
    MatCardModule,
    MatError,
    MatFormFieldModule,
    MatIcon,
    MatInput,
    MatLabel,
    MatSuffix,
    ReactiveFormsModule,
    RouterLink,
    TranslateModule,
    FileInputComponent
  ],
  templateUrl: './challenge-creation.component.html',
  styleUrl: './challenge-creation.component.scss'
})
export class ChallengeCreationComponent {
  public ctfCreationForm: FormGroup;

  constructor(private store: Store) {
    this.ctfCreationForm = new FormGroup({
        name: new FormControl('', Validators.required),
        description: new FormControl('', Validators.required),
        flag: new FormControl('CCTF{}', [
          Validators.required,
          Validators.pattern("^CCTF\{[A-Za-z0-9]+\}$")
        ]),
        file: new FormControl('')
    });
  }

  public createCTF(): void {
    this.store.dispatch(
      ChallengeActions.create(this.ctfCreationForm.value)
    );
  }

  public selectFile(file: File | null) {
    if(file) {
      this.ctfCreationForm.patchValue({ file: file});
    }
  }
}
