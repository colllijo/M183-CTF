import { createFeature, createReducer, on } from '@ngrx/store';
import { AdministrationState } from './administration.models';
import { AdministrationActions } from './administration.actions';

const initialState: AdministrationState = {
  users: [],
  error: null,
  loading: false
};

export const administrationFeature = createFeature({
  name: 'Administration',
  reducer: createReducer(
    initialState,
    // get UserInfos
    on(
      AdministrationActions.getUserInfos,
      (state): AdministrationState => ({
        ...state,
        error: null,
        loading: true
      })
    ),
    on(
      AdministrationActions.getUserInfosSuccess,
      (state, { users }): AdministrationState => ({
        ...state,
        users: users,
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
    )
  )
});
