/* tslint:disable */
/* eslint-disable */
import { Links } from '../models/links';
import { RoleResponse } from '../models/role-response';
export interface UserInfo {
  '_links'?: Links;
  email?: string;
  roles?: Array<RoleResponse>;
  username?: string;
}
