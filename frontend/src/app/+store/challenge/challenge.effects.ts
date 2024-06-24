import { HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';

import { catchError, exhaustMap, map, of } from 'rxjs';

import { Error as RestError } from '@core/api/models/error';
import { Error } from '@core/model/error';
import { ChallengeActions } from './challenge.actions';
import { Router } from '@angular/router';
import { CtfControllerService } from '@app/core/api/services';
import { CollectionModelCtfResponse, Ctf, CtfResponse } from '@app/core/api/models';

@Injectable()
export class ChallengeEffects {
  public create$ = createEffect(() => {
    return this.actions$.pipe(
      ofType(ChallengeActions.create),
      exhaustMap((action) =>
        this.ctfService.createCtf({ body: action as unknown as Ctf }).pipe(
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
        )
      )
    );
  });

  public getChallenge$ = createEffect(() => {
    return this.actions$.pipe(
      ofType(ChallengeActions.getChallenge),
      exhaustMap((action) =>
        this.ctfService.getCtf({ name: action.name }).pipe(
          map((response: CtfResponse) => {
            return ChallengeActions.getChallengeSuccess({ challenge: response })
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
        this.ctfService.getAllCtfs().pipe(
          map((response: CollectionModelCtfResponse) => {
            console.log(response);
            return ChallengeActions.getAllChallengesSuccess({ challenges: response._embedded?.ctfResponseList ?? [] })
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

  // public downloadFile$ = createEffect(() => {
  //   return this.actions$.pipe(
  //     ofType(ChallengeActions.downloadFile),
  //     exhaustMap((action) =>
  //       this.ctfService.getFile({ body: action }).pipe(
  //         map((response: FileResponse) => {
  //           return of(ChallengeActions.downloadFileSuccess({
  //             file: response.file
  //           }))
  //         }),
  //         catchError((response: HttpErrorResponse) => {
  //           return of(
  //             ChallengeActions.createFailure({
  //               errors: this.getDetailedErrors(response)
  //             })
  //           );
  //         })
  //       )
  //     )
  //   );
  // });

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
