import {createActionGroup, emptyProps, props} from '@ngrx/store';

import { Error } from '@core/model/error';

export const ChallengeActions = createActionGroup({
  source: 'Challenge',
  events: {
    // Create challenge
    create: props<{ name: string, description: string, flag: string, file: File }>(),
    createFailure: props<{
      errors: Error;
    }>(),
    getChallenge: props<{ name: string }>(),
    getChallengeSuccess: props<{ challenge?: Challenge}>(),
    getChallengeFailure: props<{
      errors: Error;
    }>(),
    getAllChallenges: emptyProps(),
    getAllChallengesSuccess: props<{challenges?: Challenge[]}>(),
    getAllChallengesFailure: props<{
      errors: Error;
    }>(),
    downloadFile: props<{name: string}>(),
    downloadFileFailure: props<{
      errors: Error;
    }>(),
    downloadFileSuccess: props<{
      file?: File
    }>()
  }
});
