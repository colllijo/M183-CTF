/* tslint:disable */
/* eslint-disable */
import { Links } from '../models/links';
export interface RequestError {
  '_links'?: Links;
  details?: {
[key: string]: {
[key: string]: string;
};
};
  error?: string;
  message?: string;
  status?: number;
  timestamp?: string;
}
