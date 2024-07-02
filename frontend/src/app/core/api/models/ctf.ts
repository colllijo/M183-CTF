/* tslint:disable */
/* eslint-disable */
import { Links } from '../models/links';
import { UserInfo } from '../models/user-info';
export interface Ctf {
  '_links'?: Links;
  author?: UserInfo;
  description?: string;
  filePath?: string;
  name?: string;
}
