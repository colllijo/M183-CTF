import { createActionGroup, emptyProps, props } from '@ngrx/store';

import { UserInfo } from '@core/api/models';

export const UserActions = createActionGroup({
  source: 'User',
  events: {
    // Login
    getUsers: emptyProps(),
    getUsersSuccess: props<{ users: UserInfo[] }>(),
    getUsersFailure: props<{
      error: string;
    }>(),
  }
});
