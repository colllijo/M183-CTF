/* tslint:disable */
/* eslint-disable */
import { Links } from '../models/links';
import { Tokens } from '../models/tokens';
export interface AuthenticatedResponse {
  '_links'?: Links;
  tokens?: Tokens;
  username?: string;
}
