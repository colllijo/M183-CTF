import { HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';

import { catchError, exhaustMap, map, of } from 'rxjs';

import { AuthenticatedResponse } from '@core/api/models/authenticated-response';
import { Error as RestError } from '@core/api/models/error';
import { Error } from '@core/model/error';
import { ChallengeActions } from './challenge.actions';
import { Router } from '@angular/router';

@Injectable()
export class AuthenticationEffects {
  public create$ = createEffect(() => {
    return this.actions$.pipe(
      ofType(ChallengeActions.create),
      exhaustMap((action) =>
        this.ctfService.create({ body: action }).pipe(
          map((response: AuthenticatedResponse) => {
            sessionStorage.setItem('accessToken', response.accessToken || '');
            this.router.navigate(['/challenges']);
          }),
          catchError((response: HttpErrorResponse) => {
            return of(
              ChallengeActions.createFailure({
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
    private ctfService: CtfControllerService
  ) {}

  private getDetailedErrors(response: HttpErrorResponse): Error {
    let errors = {};
    if ((response.error as RestError).details) {
      errors = (response.error as RestError).details!;
    }

    return errors;
  }
}
