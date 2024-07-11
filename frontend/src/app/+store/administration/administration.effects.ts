import { HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';

import { catchError, exhaustMap, map, of } from 'rxjs';

import { CollectionModelRoleResponse, CollectionModelUserDetailsResponse, RoleForm } from '@app/core/api/models';
import { AdministrationControllerService } from '@app/core/api/services';
import { AdministrationActions } from './administration.actions';

@Injectable()
export class AdministrationEffects {
  public getUserInfos$ = createEffect(() => {
    return this.actions$.pipe(
      ofType(AdministrationActions.getUserDetails),
      exhaustMap(() =>
        this.administrationService.getUsers1().pipe(
          map((response: CollectionModelUserDetailsResponse) => {
            return AdministrationActions.getUserDetailsSuccess({ users: response._embedded?.UserDetailsCollection ?? [] });
          }),
          catchError((response: HttpErrorResponse) => {
            return of(
              AdministrationActions.getUserDetailsFailure({
                error: response.error.error
              })
            );
          })
        )
      )
    );
  });

  public getRoles$ = createEffect(() => {
    return this.actions$.pipe(
      ofType(AdministrationActions.getRoles),
      exhaustMap(() =>
        this.administrationService.getRoles().pipe(
          map((response: CollectionModelRoleResponse) => {
            return AdministrationActions.getRolesSuccess({ roles: response._embedded?.RoleCollection ?? [] });
          }),
          catchError((response: HttpErrorResponse) => {
            return of(
              AdministrationActions.getRolesFailure({
                error: response.error.error
              })
            );
          })
        )
      )
    );
  });

  public addRole$ = createEffect(() => {
    return this.actions$.pipe(
      ofType(AdministrationActions.addRole),
      exhaustMap((action) =>
        this.administrationService.addRoleToUser({
          username: action.user.username!,
          body: { name: action.role }
        }).pipe(
          map((user) => AdministrationActions.addRoleSuccess({user})),
          catchError((response: HttpErrorResponse) => {
            return of(
              AdministrationActions.addRoleFailure({
                error: response.error.error
              })
            );
          })
        )
      )
    )
  });

  public removeRole$ = createEffect(() => {
    return this.actions$.pipe(
      ofType(AdministrationActions.removeRole),
      exhaustMap((action) =>
        this.administrationService.removeRoleFromUser({
          username: action.user.username!,
          body: action.role as RoleForm
        }).pipe(
          map((user) => AdministrationActions.removeRoleSuccess({user})),
          catchError((response: HttpErrorResponse) => {
            return of(
              AdministrationActions.removeRoleFailure({
                error: response.error.error
              })
            );
          })
        )
      )
    )
  });

  constructor(
    private actions$: Actions,
    private administrationService: AdministrationControllerService
  ) {}
}
