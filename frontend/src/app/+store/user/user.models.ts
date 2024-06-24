import {User} from "@core/api/models/user";

export interface UserState {
  users: User[];
  loading: boolean;
  error: string | null;
}
