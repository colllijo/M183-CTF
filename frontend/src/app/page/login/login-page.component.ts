import { AsyncPipe } from '@angular/common';
import { Component, OnDestroy, OnInit } from '@angular/core';
import {
    FormControl,
    FormGroup,
    ReactiveFormsModule,
    Validators
} from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatDialog } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { RouterLink } from '@angular/router';
import { Store } from '@ngrx/store';
import { TranslateModule } from '@ngx-translate/core';
import { Observable, Subscription } from 'rxjs';

import { AuthenticationActions } from '@app/+store/authentication/authentication.actions';
import { authenticationFeature } from '@app/+store/authentication/authentication.reducers';
import { TestIdDirective } from '@app/core/directive/test-id.directive';
import { ErrorDialogComponent } from './component/error-dialog/error-dialog.component';

@Component({
  selector: 'ctf-login',
  standalone: true,
  imports: [
    AsyncPipe,
    MatButtonModule,
    MatCardModule,
    MatFormFieldModule,
    MatIconModule,
    MatInputModule,
    ReactiveFormsModule,
    RouterLink,
    TestIdDirective,
    TranslateModule
  ],
  templateUrl: './login-page.component.html',
  styleUrl: './login-page.component.scss'
})
export class LoginPageComponent implements OnInit, OnDestroy {
  public loginForm: FormGroup;
  public passwordVisible: boolean;

  private error$: Observable<string | null>;
  private subscription: Subscription;

  constructor(
    private dialog: MatDialog,
    private store: Store
  ) {
    this.loginForm = new FormGroup({
      username: new FormControl('', Validators.required),
      password: new FormControl('', Validators.required)
    });
    this.passwordVisible = false;

    this.error$ = this.store.select(authenticationFeature.selectError);
    this.subscription = new Subscription();
  }

  public ngOnInit(): void {
    this.subscription.add(
      this.error$.subscribe((error) => {
        if (error) {
          this.dialog.open(ErrorDialogComponent, {
            data: { error }
          });
        }
      })
    );
  }

  public ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  public login(): void {
    this.store.dispatch(AuthenticationActions.login(this.loginForm.value));
  }
}
