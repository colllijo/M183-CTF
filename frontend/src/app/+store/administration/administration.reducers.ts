import { createFeature, createReducer, on } from '@ngrx/store';
import { AdministrationState } from './administration.models';
import { AdministrationActions } from './administration.actions';

const initialState: AdministrationState = {
  users: [],
  roles: [],
  permissions: [],
  error: null,
  loading: false
};

export const administrationFeature = createFeature({
  name: 'Administration',
  reducer: createReducer(
    initialState,
    // User Administration
    on(
      AdministrationActions.getUserInfos,
      (state): AdministrationState => ({
        ...state,
        users: [],
        error: null,
        loading: true
      })
    ),
    on(
      AdministrationActions.getUserInfosSuccess,
      (state, { users }): AdministrationState => ({
        ...state,
        users,
        error: null,
        loading: false
      })
    ),
    on(
      AdministrationActions.getUserInfosFailure,
      (state, { error }): AdministrationState => ({
        ...state,
        error,
        loading: false
      })
    ),
    // Role Administration
    on(
      AdministrationActions.getRoles,
      (state): AdministrationState => ({
        ...state,
        roles: [],
        error: null,
        loading: true
      })
    ),
    on(
      AdministrationActions.getRolesSuccess,
      (state, { roles }): AdministrationState => ({
        ...state,
        roles,
        error: null,
        loading: false
      })
    ),
    on(
      AdministrationActions.getRolesFailure,
      (state, { error }): AdministrationState => ({
        ...state,
        error,
        loading: false
      })
    )
  )
});
