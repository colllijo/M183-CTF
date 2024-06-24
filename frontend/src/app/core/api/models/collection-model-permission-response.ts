/* tslint:disable */
/* eslint-disable */
import { Links } from '../models/links';
import { PermissionResponse } from '../models/permission-response';
export interface CollectionModelPermissionResponse {
  '_embedded'?: {
'permissionResponseList'?: Array<PermissionResponse>;
};
  '_links'?: Links;
}
