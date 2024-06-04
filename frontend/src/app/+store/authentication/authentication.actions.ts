import { createActionGroup, emptyProps, props } from '@ngrx/store';

import { Error } from '@core/model/error';

export const AuthenticationActions = createActionGroup({
  source: 'Authentication',
  events: {
    // Check authentication
    // Login
    login: props<{ username: string; password: string }>(),
    loginSuccess: emptyProps(),
    loginFailure: props<{
      errors: Error;
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
