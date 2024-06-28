/* tslint:disable */
/* eslint-disable */
import { Links } from '../models/links';
import { Role } from '../models/role';
export interface UserDetails {
  '_links'?: Links;
  email?: string;
  roles?: Array<Role>;
  username?: string;
}
