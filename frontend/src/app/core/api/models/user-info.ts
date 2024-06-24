/* tslint:disable */
/* eslint-disable */
import { Links } from '../models/links';
import { Permission } from '../models/permission';
import { Role } from '../models/role';
import { Solve } from '../models/solve';
export interface UserInfo {
  '_links'?: Links;
  active: boolean;
  email: string;
  id?: number;
  password: string;
  permissions?: Array<Permission>;
  roles: Array<Role>;
  solves: Array<Solve>;
  username: string;
}
