import { AsyncPipe, KeyValuePipe } from '@angular/common';
import { Component } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatTooltipModule } from '@angular/material/tooltip';
import { RouterLink } from '@angular/router';
import { AuthenticationActions } from '@app/+store/authentication/authentication.actions';
import { authenticationFeature } from '@app/+store/authentication/authentication.reducers';
import { AuthenticationService } from '@app/core/service/authentication.service';
import { Store } from '@ngrx/store';
import { TranslateModule, TranslateService } from '@ngx-translate/core';
import { Observable } from 'rxjs';

@Component({
  selector: 'ctf-navbar',
  standalone: true,
  imports: [
    AsyncPipe,
    KeyValuePipe,
    MatButtonModule,
    MatIconModule,
    MatMenuModule,
    MatToolbarModule,
    MatTooltipModule,
    RouterLink,
    TranslateModule
  ],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.scss'
})
export class NavbarComponent {
  public languages: { [key: string]: string };
  public currentLanguage: string;

  public authenticated$: Observable<boolean>;
  public username$: Observable<string | null>;

  constructor(
    private authenticationService: AuthenticationService,
    private store: Store,
    private translateService: TranslateService
  ) {
    this.languages = {
      de: 'Deutsch',
      en: 'English'
    };
    this.currentLanguage = this.translateService.defaultLang;

    this.authenticated$ = this.store.select(
      authenticationFeature.selectAuthenticated
    );
    this.username$ = this.store.select(authenticationFeature.selectUsername);

    this.authenticationService.getRoles();
  }

  public switchLanguage(lang: string): void {
    this.currentLanguage = lang;
    this.translateService.use(lang);
  }

  public canAuthorChallenge(): boolean {
    return this.authenticationService.getRoles().includes('AUTHOR') || this.isAdministrator();
  }

  public isAdministrator(): boolean {
    return this.authenticationService.getRoles().includes('ADMIN');
  }

  public logout(): void {
    this.store.dispatch(AuthenticationActions.logout());
  }
}
