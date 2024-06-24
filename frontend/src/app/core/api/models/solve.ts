/* tslint:disable */
/* eslint-disable */
import { Ctf } from '../models/ctf';
import { User } from '../models/user';
export interface Solve {
  ctf: Ctf;
  points: number;
  rank?: number;
  solver?: User;
  timestamp?: string;
}
