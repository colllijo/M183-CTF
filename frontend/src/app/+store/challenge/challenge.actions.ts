import { createActionGroup, props } from '@ngrx/store';

import { Error } from '@core/model/error';

export const ChallengeActions = createActionGroup({
  source: 'Challenge',
  events: {
    // Create challenge
    create: props<{ name: string; description: string, flag: string, file: File }>(),
    createFailure: props<{
      errors: Error;
    }>(),
  }
});
