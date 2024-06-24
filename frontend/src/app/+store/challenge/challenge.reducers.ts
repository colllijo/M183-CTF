import { createFeature, createReducer, on } from '@ngrx/store';
import { ChallengeState } from './challenge.models';
import { ChallengeActions } from './challenge.actions';

const initialState: ChallengeState = {
  file: null,
  challenge: null,
  challenges: [],
  challengeLoading: false,
  loading: false,
  fileLoading: false,
  errors: null
};

export const challengeFeature = createFeature({
  name: 'challenge',
  reducer: createReducer(
    initialState,
    on(
      ChallengeActions.downloadFile,
      (state): ChallengeState => ({
        ...state,
        file: null,
        errors: null,
        fileLoading: true
      })
    ),
    on(
      ChallengeActions.downloadFileSuccess,
      (state, { file}): ChallengeState => ({
        ...state,
        file: file ?? null,
        errors: null,
        fileLoading: false
      })
    ),
    on(
      ChallengeActions.downloadFileFailure,
      (state, { errors }): ChallengeState => ({
        ...state,
        errors,
        fileLoading: false
      })
    ),
    on(
      ChallengeActions.getChallenge,
      (state): ChallengeState => ({
        ...state,
        challenge: null,
        errors: null,
        loading: true
      })
    ),
    on(
      ChallengeActions.getChallengeSuccess,
      (state, { challenge }): ChallengeState => ({
        ...state,
        challenge: challenge ?? null,
        errors: null,
        loading: false
      })
    ),
    on(
      ChallengeActions.getChallengeFailure,
      (state, { errors }): ChallengeState => ({
        ...state,
        errors,
        loading: false
      })
    ),
    on(
      ChallengeActions.getAllChallenges,
      (state): ChallengeState => ({
        ...state,
        challenges: [],
        errors: null,
        loading: true
      })
    ),
    on(
      ChallengeActions.getAllChallengesSuccess,
      (state, { challenges }): ChallengeState => ({
        ...state,
        challenges: challenges ?? [],
        errors: null,
        loading: false
      })
    ),
    on(
      ChallengeActions.getAllChallengesFailure,
      (state, { errors }): ChallengeState => ({
        ...state,
        errors,
        loading: false
      })
    ),
  )
});
