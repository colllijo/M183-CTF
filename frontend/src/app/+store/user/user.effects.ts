import { HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';

import { catchError, exhaustMap, map, of } from 'rxjs';

import { UserControllerService } from '@core/api/services/user-controller.service';
import { UserActions } from './user.actions';
import { Router } from '@angular/router';
import {CollectionModelUserResponse} from "@core/api/models/collection-model-user-response";

@Injectable()
export class UserEffects {
  public getUsers$ = createEffect(() => {
    return this.actions$.pipe(
      ofType(UserActions.getUsers),
      exhaustMap((action) =>
        this.userService.getUsers({ body: action }).pipe(
          map((response: CollectionModelUserResponse) => {
            return UserActions.getUsersSuccess({ users: response._embedded?.users ?? [] });
          }),
          catchError((response: HttpErrorResponse) => {
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
    private router: Router,
    private userService: UserControllerService
  ) {}
}
