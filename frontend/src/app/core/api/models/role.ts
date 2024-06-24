/* tslint:disable */
/* eslint-disable */
import { Permission } from '../models/permission';
import { User } from '../models/user';
export interface Role {
  description?: string;
  name?: string;
  permissions?: Array<Permission>;
  users?: Array<User>;
}
