import { createFeature, createReducer, on } from '@ngrx/store';
import { AuthenticationState } from './authentication.models';
import { AuthenticationActions } from './authentication.actions';

const initialState: AuthenticationState = {
  authenticated: false,
  username: null,
  roles: [],
  error: null,
  errors: null,
  loading: false
};

export const authenticationFeature = createFeature({
  name: 'authentication',
  reducer: createReducer(
    initialState,
    // Check authentication
    on(
      AuthenticationActions.setAuthentication,
      (state, { username, roles }): AuthenticationState => ({
        ...state,
        authenticated: true,
        username,
        roles
      })
    ),
    // Login
    on(
      AuthenticationActions.login,
      (state): AuthenticationState => ({
        ...state,
        error: null,
        errors: null,
        loading: true
      })
    ),
    on(
      AuthenticationActions.loginSuccess,
      (state, { username, roles }): AuthenticationState => ({
        ...state,
        authenticated: true,
        username,
        roles,
        error: null,
        errors: null,
        loading: false
      })
    ),
    on(
      AuthenticationActions.loginFailure,
      (state, { error }): AuthenticationState => ({
        ...state,
        error,
        loading: false
      })
    ),
    // Logout
    on(
      AuthenticationActions.logout,
      (state): AuthenticationState => ({
        ...state,
        username: null,
        loading: true
      })
    ),
    on(
      AuthenticationActions.logoutSuccess,
      (state): AuthenticationState => ({
        ...state,
        authenticated: false,
        loading: false
      })
    ),
    // Register
    on(
      AuthenticationActions.register,
      (state, { username }): AuthenticationState => ({
        ...state,
        username,
        error: null,
        errors: null,
        loading: true
      })
    ),
    on(
      AuthenticationActions.registrationSuccess,
      (state): AuthenticationState => ({
        ...state,
        authenticated: true,
        error: null,
        errors: null,
        loading: false
      })
    ),
    on(
      AuthenticationActions.registrationFailure,
      (state, { errors }): AuthenticationState => ({
        ...state,
        errors,
        loading: false
      })
    )
  )
});
