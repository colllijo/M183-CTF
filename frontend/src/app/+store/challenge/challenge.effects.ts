import { HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';

import { catchError, exhaustMap, map, of } from 'rxjs';

import { AuthenticatedResponse } from '@core/api/models/authenticated-response';
import { Error as RestError } from '@core/api/models/error';
import { Error } from '@core/model/error';
import { ChallengeActions } from './challenge.actions';
import { Router } from '@angular/router';
import {FileResponse} from "@core/api/models/file-response";
import {ChallengeResponse} from "@core/api/models/challenge-response";
import {ChallengesResponse} from "@core/api/models/challenges-response";

@Injectable()
export class AuthenticationEffects {
  public create$ = createEffect(() => {
    return this.actions$.pipe(
      ofType(ChallengeActions.create),
      exhaustMap((action) =>
        this.ctfService.create({ body: action }).pipe(
          map(() => {
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
  public getChallenge$ = createEffect(() => {
    return this.actions$.pipe(
      ofType(ChallengeActions.getChallenge),
      exhaustMap((action) =>
        this.ctfService.get({ body: action }).pipe(
          map((response: ChallengeResponse) => {
            return of(
              ChallengeActions.getChallengeSuccess({
                challenge: response.challenge
              })
            )
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
  public getChallenges$ = createEffect(() => {
    return this.actions$.pipe(
      ofType(ChallengeActions.getAllChallenges),
      exhaustMap((action) =>
        this.ctfService.get({ body: action }).pipe(
          map((response: ChallengesResponse) => {
            return of(
              ChallengeActions.getAllChallengesSuccess({
                challenges: response.challenges
              })
            )
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
  public downloadFile$ = createEffect(() => {
    return this.actions$.pipe(
      ofType(ChallengeActions.downloadFile),
      exhaustMap((action) =>
        this.ctfService.getFile({ body: action }).pipe(
          map((response: FileResponse) => {
            return of(ChallengeActions.downloadFileSuccess({
              file: response.file
            }))
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
