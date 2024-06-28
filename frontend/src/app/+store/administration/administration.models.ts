import { Permission, Role, UserDetails } from "@app/core/api/models";

export interface AdministrationState {
  users: UserDetails[];
  roles: Role[];
  permissions: Permission[];
  loading: boolean;
  error: string | null;
}
