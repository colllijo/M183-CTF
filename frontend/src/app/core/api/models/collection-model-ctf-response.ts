/* tslint:disable */
/* eslint-disable */
import { Ctf } from '../models/ctf';
import { Links } from '../models/links';
export interface CollectionModelCtfResponse {
  '_embedded'?: {
'ctfCollection'?: Array<Ctf>;
};
  '_links'?: Links;
}
