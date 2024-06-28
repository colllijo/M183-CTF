/* tslint:disable */
/* eslint-disable */
import { Links } from '../models/links';
import { Permission } from '../models/permission';
export interface Role {
  '_links'?: Links;
  description?: string;
  name?: string;
  permissions?: Array<Permission>;
}
