import { Permission, Role, UserDetails } from "@app/core/api/models";

export interface AdministrationState {
  users: UserDetails[];
  selectedUser: UserDetails | null;
  roles: Role[];
  permissions: Permission[];
  loading: boolean;
  error: string | null;
}
