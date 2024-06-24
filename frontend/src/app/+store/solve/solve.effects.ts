import { HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';

import { catchError, exhaustMap, map, of } from 'rxjs';

import { Error as RestError } from '@core/api/models/error';
import { Error } from '@core/model/error';
import { Router } from '@angular/router';
import {SolveActions} from "@+store/solve/solve.actions";
import {SolveResponse} from "@core/api/models/solve-response";

@Injectable()
export class AuthenticationEffects {
  public getSolve$ = createEffect(() => {
    return this.actions$.pipe(
      ofType(SolveActions.getSolve),
      exhaustMap((action) =>
        this.solveService.get({ body: action }).pipe(
          map((response: SolveResponse) => {
            return of(
              SolveActions.getSolveSuccess({
                solve: response.solve
              })
            )
          }),
          catchError((response: HttpErrorResponse) => {
            return of(
              SolveActions.getSolveFailure({
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
    private solveService: SolveControllerService
  ) {}

  private getDetailedErrors(response: HttpErrorResponse): Error {
    let errors = {};
    if ((response.error as RestError).details) {
      errors = (response.error as RestError).details!;
    }

    return errors;
  }
}
