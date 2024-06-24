import { HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';

import { catchError, exhaustMap, map, of } from 'rxjs';

import { UserControllerService } from '@core/api/services/user-controller.service';
import { AdministrationActions } from './administration.actions';
import { CollectionModelUserInfoResponse } from '@app/core/api/models';

@Injectable()
export class AdministrationEffects {
  public getUserInfos$ = createEffect(() => {
    return this.actions$.pipe(
      ofType(AdministrationActions.getUserInfos),
      exhaustMap((action) =>
        this.userService.getUsersInfo({ body: action }).pipe(
          map((response: CollectionModelUserInfoResponse) => {
            return AdministrationActions.getUserInfosSuccess({ users: response._embedded?.userInfos ?? [] });
          }),
          catchError((response: HttpErrorResponse) => {
            return of(
              AdministrationActions.getUserInfosFailure({
                error: response.error.error
              })
            );
          })
        )
      )
    );
  });

  constructor(
    private actions$: Actions,
    private userService: UserControllerService
  ) {}
}
