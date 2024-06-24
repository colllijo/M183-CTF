/* tslint:disable */
/* eslint-disable */
import { CtfResponse } from '../models/ctf-response';
import { Links } from '../models/links';
export interface CollectionModelCtfResponse {
  '_embedded'?: {
'ctfResponseList'?: Array<CtfResponse>;
};
  '_links'?: Links;
}
