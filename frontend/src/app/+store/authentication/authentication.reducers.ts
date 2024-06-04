import { createFeature, createReducer, on } from '@ngrx/store';
import { AuthenticationState } from './authentication.models';
import { AuthenticationActions } from './authentication.actions';

const initialState: AuthenticationState = {
  authenticated: false,
  username: null,
  errors: null,
  loading: false
};

export const authenticationFeature = createFeature({
  name: 'authentication',
  reducer: createReducer(
    initialState,
    // Check authentication
    // Login
    on(
      AuthenticationActions.login,
      (state, { username }): AuthenticationState => ({
        ...state,
        username,
        errors: null,
        loading: true
      })
    ),
    on(
      AuthenticationActions.loginSuccess,
      (state): AuthenticationState => ({
        ...state,
        authenticated: true,
        errors: null,
        loading: false
      })
    ),
    on(
      AuthenticationActions.loginFailure,
      (state, { errors }): AuthenticationState => ({
        ...state,
        errors,
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
        errors: null,
        loading: true
      })
    ),
    on(
      AuthenticationActions.registrationSuccess,
      (state): AuthenticationState => ({
        ...state,
        authenticated: true,
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
