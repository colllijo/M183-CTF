import { HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';

import { catchError, exhaustMap, map, of } from 'rxjs';

import { AuthenticationControllerService } from '@core/api/services/authentication-controller.service';
import { AuthenticatedResponse } from '@core/api/models/authenticated-response';
import { Error as RestError } from '@core/api/models/error';
import { Error } from '@core/model/error';
import { AuthenticationActions } from './authentication.actions';
import { Router } from '@angular/router';

@Injectable()
export class AuthenticationEffects {
  public login$ = createEffect(() => {
    return this.actions$.pipe(
      ofType(AuthenticationActions.login),
      exhaustMap((action) =>
        this.authenticationService.login({ body: action }).pipe(
          map((response: AuthenticatedResponse) => {
            sessionStorage.setItem('accessToken', response.accessToken || '');
            this.router.navigate(['/']);
            return AuthenticationActions.loginSuccess();
          }),
          catchError((response: HttpErrorResponse) => {
            return of(
              AuthenticationActions.loginFailure({
                errors: this.getDetailedErrors(response)
              })
            );
          })
        )
      )
    );
  });

  public logout$ = createEffect(() => {
    return this.actions$.pipe(
      ofType(AuthenticationActions.logout),
      exhaustMap(() =>
        this.authenticationService.logout().pipe(
          map(() => {
            sessionStorage.removeItem('accessToken');
            this.router.navigate(['/']);
            return AuthenticationActions.logoutSuccess();
          }),
          catchError(() => {
            this.router.navigate(['/']);
            return of(AuthenticationActions.logoutSuccess());
          })
        )
      )
    );
  });

  public register$ = createEffect(() => {
    return this.actions$.pipe(
      ofType(AuthenticationActions.register),
      exhaustMap((action) =>
        this.authenticationService.register({ body: action }).pipe(
          map((response: AuthenticatedResponse) => {
            sessionStorage.setItem('accessToken', response.accessToken || '');
            this.router.navigate(['/']);
            return AuthenticationActions.registrationSuccess();
          }),
          catchError((response: HttpErrorResponse) => {
            return of(
              AuthenticationActions.registrationFailure({
                errors: this.getDetailedErrors(response)
              })
            );
          })
        )
      )
    );
  });

  constructor(
    private actions$: Actions,
    private router: Router,
    private authenticationService: AuthenticationControllerService
  ) {}

  private getDetailedErrors(response: HttpErrorResponse): Error {
    let errors = {};
    if ((response.error as RestError).details) {
      errors = (response.error as RestError).details!;
    }

    return errors;
  }
}
