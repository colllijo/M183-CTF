import { createFeature, createReducer, createSelector, on } from '@ngrx/store';

import { UserInfo } from '@app/core/api/models';
import { UserActions } from './user.actions';
import { UserState } from './user.models';

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
  ),
  extraSelectors: ({ selectUsers }) => ({
    selectRankedUsers: createSelector(selectUsers, (users: UserInfo[]) => users.sort((a, b) => (b.points ?? 0) - (a.points ?? 0)))
  })
});
