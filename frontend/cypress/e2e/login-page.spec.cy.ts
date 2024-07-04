import loginPage from "cypress/pages/login-page";

describe('Login Page', () => {
  beforeEach(() => {
    cy.visit('/login');
  });

  it('loads', () => {});

  it('should display the login form', () => {
    loginPage.elements.title().should('be.visible').and('contain', 'Login');
    loginPage.elements.subtitle().should('be.visible');
    loginPage.elements.usernameInput().should('be.visible');
    loginPage.elements.passwordInput().should('be.visible');
    loginPage.elements.loginButton().should('be.visible').and('contain', 'Login');
  });

  it('should open the register page', () => {
    loginPage.clickRegisterHereLink();
    cy.url().should('include', '/register');
  });

  it('it should display username required error', () => {
    loginPage.elements.usernameInput().find('mat-error').should('not.exist');
    loginPage.elements.usernameInput().find('input').click().blur();
    loginPage.elements.usernameInput().find('mat-error').should('be.visible').and('contain', 'Username is required');
  });

  it('it should display password required error', () => {
    loginPage.elements.passwordInput().find('mat-error').should('not.exist');
    loginPage.elements.passwordInput().find('input').click().blur();
    loginPage.elements.passwordInput().find('mat-error').should('be.visible').and('contain', 'Password is required');
  });

  it('should enable the login button when the form is valid', () => {
    loginPage.elements.loginButton().should('be.disabled');

    loginPage.fillUsername('username');
    loginPage.fillPassword('password');
    loginPage.elements.loginButton().should('be.enabled');
  });

  it('should show the password when the eye icon is clicked', () => {
    loginPage.elements.passwordInput().find('input').should('have.attr', 'type', 'password');
    loginPage.elements.passwordInput().find('mat-icon').click();
    loginPage.elements.passwordInput().find('input').should('have.attr', 'type', 'text');
  });

  it('should display an error dialog when the login fails', () => {
    loginPage.fillUsername('username');
    loginPage.fillPassword('password');
    loginPage.clickLoginButton();

    loginPage.elements.loginErrorDialog().should('be.visible');
    loginPage.elements.loginErrorDialogError().should('contain', 'Invalid credentials');
  });

  it('should login as default admin', () => {
    loginPage.fillUsername('admin');
    loginPage.fillPassword('coll-ctf-admin');
    loginPage.clickLoginButton();

    cy.url().should('include', '/challenges');
  })
});
