import { Component, OnDestroy, OnInit } from '@angular/core';
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
import { Store } from '@ngrx/store';

import { TranslateModule } from '@ngx-translate/core';

import { AuthenticationActions } from '@+store/authentication/authentication.actions';
import { MatDialog } from '@angular/material/dialog';
import { Observable, Subscription } from 'rxjs';
import { Error } from '@app/core/model/error';
import { authenticationFeature } from '@app/+store/authentication/authentication.reducers';
import { ErrorDialogComponent } from './component/error-dialog/error-dialog.component';

@Component({
  selector: 'ctf-registration',
  standalone: true,
  imports: [
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
export class RegistrationComponent implements OnInit, OnDestroy {
  public registrationForm: FormGroup;
  public passwordVisible: boolean;

  private errors$: Observable<Error | null>;
  private subscription: Subscription;

  constructor(
    private dialog: MatDialog,
    private store: Store
  ) {
    this.registrationForm = new FormGroup(
      {
        username: new FormControl('', Validators.required),
        password: new FormControl('', [
          Validators.required,
          Validators.minLength(12)
        ]),
        passwordConfirmation: new FormControl('', Validators.required),
        email: new FormControl('', [Validators.required, Validators.email])
      },
      passwordConfirmationValidator
    );
    this.passwordVisible = false;

    this.errors$ = this.store.select(authenticationFeature.selectErrors);
    this.subscription = new Subscription();
  }

  public ngOnInit(): void {
    this.subscription.add(
      this.errors$.subscribe((errors) => {
        if (errors) {
          this.dialog.open(ErrorDialogComponent, {
            data: { errors: Object.keys(errors) }
          });
        }
      })
    );
  }

  public ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  public register(): void {
    this.store.dispatch(
      AuthenticationActions.register(this.registrationForm.value)
    );
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
