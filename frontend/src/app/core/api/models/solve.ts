/* tslint:disable */
/* eslint-disable */
import { CaptureTheFlag } from '../models/capture-the-flag';
import { User } from '../models/user';
export interface Solve {
  ctf?: CaptureTheFlag;
  points?: number;
  rank?: number;
  solver?: User;
  timestamp?: string;
}
