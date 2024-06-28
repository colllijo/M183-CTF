import { createActionGroup, emptyProps, props } from '@ngrx/store';

import {User} from "@core/api/models/user";

export const UserActions = createActionGroup({
  source: 'User',
  events: {
    // Login
    getUsers: emptyProps(),
    getUsersSuccess: props<{ users: User[] }>(),
    getUsersFailure: props<{
      error: string;
    }>(),
  }
});
