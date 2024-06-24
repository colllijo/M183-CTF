import { UserInfo } from "@app/core/api/models";

export interface AdministrationState {
  users: UserInfo[];
  loading: boolean;
  error: string | null;
}
