import { createActionGroup, emptyProps, props } from '@ngrx/store';

import { Error } from '@core/model/error';

export const AuthenticationActions = createActionGroup({
  source: 'Authentication',
  events: {
    // Check authentication
    setAuthentication: props<{ username: string; roles: string[]; }>(),
    // Login
    login: props<{ username: string; password: string }>(),
    loginSuccess: props<{ username: string; roles: string[]; }>(),
    loginFailure: props<{
      error: string;
    }>(),
    // Logout
    logout: emptyProps(),
    logoutSuccess: emptyProps(),
    // Register
    register: props<{
      username: string;
      password: string;
      passwordConfirmation: string;
      email: string;
    }>(),
    registrationSuccess: emptyProps(),
    registrationFailure: props<{
      errors: Error;
    }>()
  }
});
