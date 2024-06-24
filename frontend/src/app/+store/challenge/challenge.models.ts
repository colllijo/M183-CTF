import { Error } from '@core/model/error';

export interface ChallengeState {
  file: File | null;
  challenge: Challenge | null;
  challenges: Challenge[];
  challengeLoading: boolean;
  loading: boolean;
  fileLoading: boolean;
  errors: Error | null;
}
