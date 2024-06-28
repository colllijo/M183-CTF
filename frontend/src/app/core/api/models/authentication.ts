/* tslint:disable */
/* eslint-disable */
import { Links } from '../models/links';
import { Tokens } from '../models/tokens';
export interface Authentication {
  '_links'?: Links;
  tokens?: Tokens;
  username?: string;
}
