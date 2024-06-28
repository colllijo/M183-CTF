import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { catchError, exhaustMap, map, of } from 'rxjs';

import { CollectionModelUserInfoResponse } from "@core/api/models";
import { UserControllerService } from '@core/api/services/user-controller.service';
import { UserActions } from './user.actions';

@Injectable()
export class UserEffects {
  public getUsers$ = createEffect(() => {
    return this.actions$.pipe(
      ofType(UserActions.getUsers),
      exhaustMap((action) =>
        this.userService.getUsers({ body: action }).pipe(
          map((response: CollectionModelUserInfoResponse) => {
            return UserActions.getUsersSuccess({ users: response._embedded?.UserInfoCollection ?? [] });
          }),
          catchError(() => {
            return of(
              UserActions.getUsersFailure({
                error: 'Get Users failed'
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
