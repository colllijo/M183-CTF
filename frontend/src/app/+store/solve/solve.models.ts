import { Error } from '@core/model/error';

export interface SolveState {
  solve: Solve | null;
  loading: boolean;
  errors: Error | null;
}
