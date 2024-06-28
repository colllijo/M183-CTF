import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthenticationControllerService } from '../api/services';
import { catchError, map, of } from 'rxjs';

export const featureGuard: CanActivateFn = (route) => {
  const authenticationService = inject(AuthenticationControllerService);
  const router = inject(Router);

  return authenticationService
    .checkFeatureAccess({ feature: route.data['feature'] })
    .pipe(
      map(() => true),
      catchError(() => of(router.parseUrl('/')))
    );
};
