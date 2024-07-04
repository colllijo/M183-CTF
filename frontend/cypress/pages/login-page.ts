class LoginPage {
  public elements = {
    title: () => cy.get('mat-card-title'),
    subtitle: () => cy.get('mat-card-subtitle'),
    usernameInput: () => cy.testId('username-input'),
    passwordInput: () => cy.testId('password-input'),
    loginButton: () => cy.testId('login-button'),
    loginErrorDialog: () => cy.testId('login-error-dialog'),
    loginErrorDialogError: () => cy.testId('login-error-dialog-error'),
    loginErrorDialogCloseButton: () => cy.testId('login-error-dialog-close-button'),
  };

  public clickRegisterHereLink() {
    this.elements.subtitle().find('a').click();
  }

  public fillUsername(username: string) {
    this.elements.usernameInput().find('input').clear().type(username);
  }

  public fillPassword(password: string) {
    this.elements.passwordInput().find('input').clear().type(password);
  }

  public clickLoginButton() {
    this.elements.loginButton().click();
  }
}

const loginPage = new LoginPage();
export default loginPage;
