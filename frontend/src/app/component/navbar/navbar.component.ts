import { KeyValuePipe } from '@angular/common';
import { Component } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu';
import { MatToolbarModule } from '@angular/material/toolbar';
import { RouterLink } from '@angular/router';
import { TranslateModule, TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'ctf-navbar',
  standalone: true,
  imports: [
    KeyValuePipe,
    MatButtonModule,
    MatIconModule,
    MatMenuModule,
    MatToolbarModule,
    RouterLink,
    TranslateModule,
  ],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.scss',
})
export class NavbarComponent {
  public languages: { [key: string]: string };
  public currentLanguage: string;

  constructor(private translateService: TranslateService) {
    this.languages = {
      de: 'Deutsch',
      en: 'English',
    };
    this.currentLanguage = this.translateService.defaultLang;
  }

  public switchLanguage(lang: string): void {
    this.currentLanguage = lang;
    this.translateService.use(lang);
  }
}
