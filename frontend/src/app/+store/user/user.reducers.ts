import { createFeature, createReducer, on } from '@ngrx/store';
import { UserState } from './user.models';
import { UserActions } from './user.actions';

const initialState: UserState = {
  users: [],
  error: null,
  loading: false
};

export const userFeature = createFeature({
  name: 'user',
  reducer: createReducer(
    initialState,
    // get Users
    on(
      UserActions.getUsers,
      (state): UserState => ({
        ...state,
        error: null,
        loading: true
      })
    ),
    on(
      UserActions.getUsersSuccess,
      (state, { users}): UserState => ({
        ...state,
        users: users,
        error: null,
        loading: false
      })
    ),
    on(
      UserActions.getUsersFailure,
      (state, { error }): UserState => ({
        ...state,
        error,
        loading: false
      })
    ),
  )
});
