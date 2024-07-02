import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from "@angular/forms";
import { MatButton } from "@angular/material/button";
import { MatCardModule } from "@angular/material/card";
import { MatError, MatFormFieldModule, MatLabel, MatSuffix } from "@angular/material/form-field";
import { MatIcon } from "@angular/material/icon";
import { MatInput } from "@angular/material/input";
import { RouterLink } from "@angular/router";
import { Store } from "@ngrx/store";
import { TranslateModule } from "@ngx-translate/core";

import { ChallengeActions } from "@+store/challenge/challenge.actions";
import { FileInputComponent } from "@component/file-input/file-input.component";

@Component({
  selector: 'ctf-challenge-create',
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
  templateUrl: './create.component.html',
  styleUrl: './create.component.scss'
})
export class ChallengeCreateComponent {
  public ctfCreationForm: FormGroup;

  constructor(private store: Store) {
    this.ctfCreationForm = new FormGroup({
        name: new FormControl('', Validators.required),
        description: new FormControl('', Validators.required),
        flag: new FormControl('CCTF{}', [
          Validators.required,
          Validators.pattern("^CCTF{[A-Za-z0-9_-]+}$")
        ]),
        file: new FormControl('')
    });
  }

  public createCTF(): void {
    this.store.dispatch(
      ChallengeActions.create(this.ctfCreationForm.value)
    );
  }
}
