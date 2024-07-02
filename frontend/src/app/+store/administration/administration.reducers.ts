import { createFeature, createReducer, on } from '@ngrx/store';
import { AdministrationState } from './administration.models';
import { AdministrationActions } from './administration.actions';

const initialState: AdministrationState = {
  users: [],
  selectedUser: null,
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
      AdministrationActions.getUserDetails,
      (state): AdministrationState => ({
        ...state,
        users: [],
        error: null,
        loading: true
      })
    ),
    on(
      AdministrationActions.getUserDetailsSuccess,
      (state, { users }): AdministrationState => ({
        ...state,
        users,
        error: null,
        loading: false
      })
    ),
    on(
      AdministrationActions.getUserDetailsFailure,
      (state, { error }): AdministrationState => ({
        ...state,
        error,
        loading: false
      })
    ),
    on(
      AdministrationActions.selectUser,
      (state, { user }): AdministrationState => ({
        ...state,
        selectedUser: state.selectedUser === user ? null : user
      })
    ),
    on(
      AdministrationActions.addRole,
      (state): AdministrationState => ({
        ...state,
        error: null,
        loading: true
      })
    ),
    on(
      AdministrationActions.addRoleSuccess,
      (state, { user }): AdministrationState => ({
        ...state,
        users: state.users.map((u) => (u.username === user.username ? user : u)),
        selectedUser: user,
        error: null,
        loading: false
      })
    ),
    on(
      AdministrationActions.removeRole,
      (state): AdministrationState => ({
        ...state,
        error: null,
        loading: true
      })
    ),
    on(
      AdministrationActions.removeRoleSuccess,
      (state, { user }): AdministrationState => ({
        ...state,
        users: state.users.map((u) => (u.username === user.username ? user : u)),
        selectedUser: user,
        error: null,
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
