import { UserInfo } from "@core/api/models";

export interface UserState {
  users: UserInfo[];
  loading: boolean;
  error: string | null;
}
