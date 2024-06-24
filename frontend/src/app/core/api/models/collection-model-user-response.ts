/* tslint:disable */
/* eslint-disable */
import { Links } from '../models/links';
import { User } from '../models/user';
export interface CollectionModelUserResponse {
  '_embedded'?: {
'users'?: Array<User>;
};
  '_links'?: Links;
}
