/* tslint:disable */
/* eslint-disable */
import { Links } from '../models/links';
import { UserDetails } from '../models/user-details';
export interface CollectionModelUserDetailsResponse {
  '_embedded'?: {
'UserDetailsCollection'?: Array<UserDetails>;
};
  '_links'?: Links;
}
