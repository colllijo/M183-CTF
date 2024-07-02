import { Role, UserDetails } from '@app/core/api/models';
import { createActionGroup, emptyProps, props } from '@ngrx/store';

export const AdministrationActions = createActionGroup({
  source: 'Administration',
  events: {
    // User Administration
    getUserDetails: emptyProps(),
    getUserDetailsSuccess: props<{ users: UserDetails[] }>(),
    getUserDetailsFailure: props<{ error: string }>(),
    selectUser: props<{ user: UserDetails }>(),
    addRole: props<{ user: UserDetails; role: string }>(),
    addRoleSuccess: props<{ user: UserDetails; }>(),
    addRoleFailure: props<{ error: string }>(),
    removeRole: props<{ user: UserDetails; role: Role }>(),
    removeRoleSuccess: props<{ user: UserDetails }>(),
    removeRoleFailure: props<{ error: string }>(),
    // Role Administration
    getRoles: emptyProps(),
    getRolesSuccess: props<{ roles: Role[] }>(),
    getRolesFailure: props<{ error: string }>()
  }
});
