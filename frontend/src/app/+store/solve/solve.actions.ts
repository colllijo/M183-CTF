import { createActionGroup, props } from '@ngrx/store';

import { Error } from '@core/model/error';

export const SolveActions = createActionGroup({
  source: 'Solve',
  events: {
    // Get Solve
    getSolve: props<{ name: string; description: string, flag: string, file: File }>(),
    getSolveFailure: props<{
      errors: Error;
    }>(),
    getSolveSuccess: props<{
      solve?: Solve
    }>(),
  }
});
