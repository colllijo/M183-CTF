/* tslint:disable */
/* eslint-disable */
import { Links } from '../models/links';
import { Permission } from '../models/permission';
export interface CollectionModelPermissionResponse {
  '_embedded'?: {
'PermissionCollection'?: Array<Permission>;
};
  '_links'?: Links;
}
