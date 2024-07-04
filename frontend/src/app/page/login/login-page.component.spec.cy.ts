import { HttpClient, provideHttpClient } from '@angular/common/http';
import { importProvidersFrom } from '@angular/core';
import { provideMockStore } from '@ngrx/store/testing';
import { MissingTranslationHandler, TranslateLoader, TranslateModule } from '@ngx-translate/core';
import { provideRouter } from '@angular/router';
import { provideNoopAnimations } from '@angular/platform-browser/animations';

import { HttpLoaderFactory } from '@app/app.config';
import { DefaultTranslationHandler } from '@app/core/translate/default-translation.handler';
import { LoginPageComponent } from './login-page.component';

const loginPageComponentProviders = [
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
  provideHttpClient(),
  provideMockStore({}),
  provideNoopAnimations(),
  provideRouter([])
];

describe('LoginPageComponent', () => {
  beforeEach(() => {
    cy.mount(LoginPageComponent, { providers: loginPageComponentProviders });
  });

  it('mounts', () => {});

  it('should display the login form', () => {
    cy.get('h1').should('exist').and('have.text', 'Login');
    cy.testId('username-input').should('exist').find('mat-label').should('have.text', 'Username');
    cy.testId('password-input').should('exist').find('mat-label').should('have.text', 'Password');
  });

  it('should display the username required message', () => {
     cy.testId('username-input').find('mat-error').should('not.exist');
     cy.testId('username-input').find('input').click().blur();
     cy.testId('username-input').find('mat-error').should('exist').and('contain.text', 'Username is required');
  });

  it('should display the password required message', () => {
    cy.testId('password-input').find('mat-error').should('not.exist');
    cy.testId('password-input').find('input').click().blur();
    cy.testId('password-input').find('mat-error').should('exist').and('contain.text', 'Password is required');
  });

  it('should enable the login button when the form is valid', () => {
    cy.testId('login-button').should('be.disabled');

    cy.testId('username-input').find('input').type('username');
    cy.testId('password-input').find('input').type('password');
    cy.testId('login-button').should('be.enabled');
  });

  it('should show the password when the eye icon is clicked', () => {
    cy.testId('password-input').find('input').should('have.attr', 'type', 'password');
    cy.testId('password-input').find('mat-icon').click();
    cy.testId('password-input').find('input').should('have.attr', 'type', 'text');
  });
});
