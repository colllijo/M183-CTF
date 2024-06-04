import { HttpErrorResponse, HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { catchError, switchMap, throwError } from 'rxjs';
import { AuthenticationControllerService } from '../api/services';
import { AuthenticatedResponse } from '../api/models';
import { Store } from '@ngrx/store';
import { AuthenticationActions } from '@app/+store/authentication/authentication.actions';

export const accessTokenInterceptor: HttpInterceptorFn = (req, next) => {
  const store = inject(Store);
  const authenticationService = inject(AuthenticationControllerService);

  return next(req).pipe(
    catchError((response: HttpErrorResponse) => {
      if (
        response.status === 401 &&
        response.error.error === 'ExpiredJwtException'
      ) {
        const refreshToken = sessionStorage.getItem('refreshToken');
        sessionStorage.removeItem('refreshToken');

        if (!refreshToken) return throwError(() => response);

        return authenticationService
          .refresh({
            body: { refreshToken }
          })
          .pipe(
            switchMap((response: AuthenticatedResponse) => {
              if (response.accessToken) {
                sessionStorage.setItem('accessToken', response.accessToken);
                sessionStorage.setItem('refreshToken', refreshToken);
              }

              return next(req);
            }),
            catchError(() => {
              store.dispatch(AuthenticationActions.logout());

              return throwError(() => response);
            })
          );
      }
      return throwError(() => response);
    })
  );
};
