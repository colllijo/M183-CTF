/* tslint:disable */
/* eslint-disable */
import { Links } from '../models/links';
import { UserInfo } from '../models/user-info';
export interface CollectionModelUserInfoResponse {
  '_embedded'?: {
'UserInfoCollection'?: Array<UserInfo>;
};
  '_links'?: Links;
}
