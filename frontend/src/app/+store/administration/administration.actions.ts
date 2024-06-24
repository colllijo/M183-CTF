import { RoleResponse, UserInfo } from '@app/core/api/models';
import { createActionGroup, emptyProps, props } from '@ngrx/store';

export const AdministrationActions = createActionGroup({
  source: 'Administration',
  events: {
    // User Administration
    getUserInfos: emptyProps(),
    getUserInfosSuccess: props<{ users: UserInfo[] }>(),
    getUserInfosFailure: props<{ error: string }>(),
    addRole: props<{ user: UserInfo; role: string }>(),
    addRoleSuccess: props<{ user: UserInfo; }>(),
    addRoleFailure: props<{ error: string }>(),
    removeRole: props<{ user: UserInfo; role: RoleResponse }>(),
    removeRoleSuccess: props<{ user: UserInfo }>(),
    removeRoleFailure: props<{ error: string }>(),
    // Role Administration
    getRoles: emptyProps(),
    getRolesSuccess: props<{ roles: RoleResponse[] }>(),
    getRolesFailure: props<{ error: string }>()
    // Permission Administration
  }
});
