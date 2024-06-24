/* tslint:disable */
/* eslint-disable */
import { Links } from '../models/links';
import { User } from '../models/user';
export interface CtfResponse {
  '_links'?: Links;
  author?: User;
  description?: string;
  name?: string;
}
