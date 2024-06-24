import { CtfResponse } from '@app/core/api/models';
import { Error } from '@core/model/error';

export interface ChallengeState {
  file: File | null;
  challenge: CtfResponse | null;
  challenges: CtfResponse[];
  challengeLoading: boolean;
  loading: boolean;
  fileLoading: boolean;
  errors: Error | null;
}
