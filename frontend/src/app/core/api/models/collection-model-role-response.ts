/* tslint:disable */
/* eslint-disable */
import { Links } from '../models/links';
import { Role } from '../models/role';
export interface CollectionModelRoleResponse {
  '_embedded'?: {
'roleCollection'?: Array<Role>;
};
  '_links'?: Links;
}
