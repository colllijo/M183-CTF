import { Ctf } from '@app/core/api/models';
import { Error } from '@core/model/error';

export interface ChallengeState {
  file: File | null;
  challenge: Ctf | null;
  challenges: Ctf[];
  challengeLoading: boolean;
  loading: boolean;
  fileLoading: boolean;
  errors: Error | null;
}
