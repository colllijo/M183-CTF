/* tslint:disable */
/* eslint-disable */
import { Solve } from '../models/solve';
import { User } from '../models/user';
export interface Ctf {
  author?: User;
  description?: string;
  flag: string;
  name: string;
  solves: Array<Solve>;
}
