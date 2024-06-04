import {
  HttpClient,
  provideHttpClient,
  withInterceptors
} from '@angular/common/http';
import { ApplicationConfig, importProvidersFrom } from '@angular/core';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { provideRouter } from '@angular/router';

import {
  MissingTranslationHandler,
  TranslateLoader,
  TranslateModule
} from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';

import { routes } from './app.routes';
import { provideState, provideStore } from '@ngrx/store';
import { authenticationFeature } from './+store/authentication/authentication.reducers';
import { AuthenticationEffects } from './+store/authentication/authentication.effects';
import { provideEffects } from '@ngrx/effects';
import { DefaultTranslationHandler } from './core/translate/default-translation.handler';
import { authInterceptor } from './core/interceptor/auth.interceptor';

export function HttpLoaderFactory(httpClient: HttpClient) {
  return new TranslateHttpLoader(httpClient, './assets/i18n/', '.json');
}

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes),
    provideAnimationsAsync(),
    provideHttpClient(withInterceptors([authInterceptor])),
    importProvidersFrom(
      TranslateModule.forRoot({
        defaultLanguage: 'en',
        loader: {
          provide: TranslateLoader,
          useFactory: HttpLoaderFactory,
          deps: [HttpClient]
        },
        missingTranslationHandler: {
          provide: MissingTranslationHandler,
          useClass: DefaultTranslationHandler
        }
      })
    ),
    provideStore(),
    provideState(authenticationFeature),
    provideEffects(AuthenticationEffects)
  ]
};
