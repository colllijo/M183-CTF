import { HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { catchError, exhaustMap, map, of, tap } from 'rxjs';
import { saveAs } from 'file-saver';

import { CollectionModelCtfResponse, Ctf, CtfForm } from '@app/core/api/models';
import { CtfControllerService } from '@app/core/api/services';
import { RequestError } from '@core/api/models';
import { Error } from '@core/model/error';
import { ChallengeActions } from './challenge.actions';

@Injectable()
export class ChallengeEffects {
  public create$ = createEffect(() => {
    return this.actions$.pipe(
      ofType(ChallengeActions.create),
      exhaustMap((action) => {
        const ctf: CtfForm = {
          name: action.name,
          description: action.description,
          flag: action.flag
        };
        const file = action.file ? action.file : undefined;

        return this.ctfService.createCtf({ body: { ctf, file } }).pipe(
          map(() => {
            this.router.navigate(['/challenges']);
            return ChallengeActions.createSuccess();
          }),
          catchError((response: HttpErrorResponse) => {
            return of(
              ChallengeActions.createFailure({
                errors: this.getDetailedErrors(response)
              })
            );
          })
        );
      })
    );
  });

  public getChallenge$ = createEffect(() => {
    return this.actions$.pipe(
      ofType(ChallengeActions.getChallenge),
      exhaustMap((action) =>
        this.ctfService.getCtf({ name: action.name }).pipe(
          map((response: Ctf) => {
            return ChallengeActions.getChallengeSuccess({
              challenge: response
            });
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
      exhaustMap(() =>
        this.ctfService.getCtfs().pipe(
          map((response: CollectionModelCtfResponse) => {
            return ChallengeActions.getAllChallengesSuccess({
              challenges: response._embedded?.ctfCollection ?? []
            });
          }),
          catchError((response: HttpErrorResponse) => {
            return of(
              ChallengeActions.getAllChallengesFailure({
                errors: this.getDetailedErrors(response)
              })
            );
          })
        )
      )
    );
  });

  public downloadFile$ = createEffect(
    () => {
      return this.actions$.pipe(
        ofType(ChallengeActions.downloadFile),
        exhaustMap((action) => {
          const parts = action.name.split('/');

          return this.ctfService
            .downloadFile({ name: parts[0], file: parts[1] })
            .pipe(
              tap((file) => {
                if (file) {
                  saveAs(file, parts[1]);
                }
              })
            );
        })
      );
    },
    { dispatch: false }
  );

  public submitFlag$ = createEffect(() => {
    return this.actions$.pipe(
      ofType(ChallengeActions.submitFlag),
      exhaustMap((action) =>
        this.ctfService.solveCtf({ name: action.name, body: { flag: action.flag } }).pipe(
          map(() => {
            return ChallengeActions.submitFlagSuccess();
          }),
          catchError((response: HttpErrorResponse) => {
            return of(
              ChallengeActions.submitFlagFailure({
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
    if ((response.error as RequestError).details) {
      errors = (response.error as RequestError).details!;
    }

    return errors;
  }
}
