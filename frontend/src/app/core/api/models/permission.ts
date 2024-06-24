/* tslint:disable */
/* eslint-disable */
import { Role } from '../models/role';
export interface Permission {
  description?: string;
  name?: string;
  roles?: Array<Role>;
}
