import { UserInfo } from '@app/core/api/models';
import { createActionGroup, emptyProps, props } from '@ngrx/store';

export const AdministrationActions = createActionGroup({
  source: 'Administration',
  events: {
    getUserInfos: emptyProps(),
    getUserInfosSuccess: props<{ users: UserInfo[] }>(),
    getUserInfosFailure: props<{
      error: string;
    }>(),
  }
});
