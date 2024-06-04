import { Error } from '@core/model/error';

export interface AuthenticationState {
  authenticated: boolean;
  username: string | null;
  errors: Error | null;
  loading: boolean;
}
