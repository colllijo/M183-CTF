import { JsonPipe } from '@angular/common';
import { Component } from '@angular/core';
import {
  AbstractControl,
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  ValidationErrors,
  Validators
} from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { RouterLink } from '@angular/router';

import { TranslateModule } from '@ngx-translate/core';

@Component({
  selector: 'ctf-registration',
  standalone: true,
  imports: [
    JsonPipe,
    MatButtonModule,
    MatCardModule,
    MatFormFieldModule,
    MatIconModule,
    MatInputModule,
    ReactiveFormsModule,
    RouterLink,
    TranslateModule
  ],
  templateUrl: './registration.component.html',
  styleUrl: './registration.component.scss'
})
export class RegistrationComponent {
  public registrationForm: FormGroup;
  public passwordVisible: boolean;

  constructor() {
    this.registrationForm = new FormGroup(
      {
        username: new FormControl('', Validators.required),
        password: new FormControl('', Validators.required),
        passwordConfirmation: new FormControl('', Validators.required),
        email: new FormControl('', [Validators.required, Validators.email])
      },
      passwordConfirmationValidator
    );
    this.passwordVisible = false;
  }

  public register(): void {
    console.log(this.registrationForm.value);
  }
}

function passwordConfirmationValidator(
  control: AbstractControl
): ValidationErrors | null {
  const password = control.get('password');
  const passwordConfirmation = control.get('passwordConfirmation');
  return password?.dirty &&
    passwordConfirmation?.dirty &&
    password.value !== passwordConfirmation.value
    ? { passwordConfirmation: true }
    : null;
}
