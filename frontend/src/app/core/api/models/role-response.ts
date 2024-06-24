/* tslint:disable */
/* eslint-disable */
import { Links } from '../models/links';
import { PermissionResponse } from '../models/permission-response';
export interface RoleResponse {
  '_links'?: Links;
  description?: string;
  name?: string;
  permissions?: Array<PermissionResponse>;
}
