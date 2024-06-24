import { PermissionResponse, RoleResponse, UserInfo } from "@app/core/api/models";

export interface AdministrationState {
  users: UserInfo[];
  roles: RoleResponse[];
  permissions: PermissionResponse[];
  loading: boolean;
  error: string | null;
}
