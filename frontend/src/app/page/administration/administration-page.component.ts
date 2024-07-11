import { Component } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatSidenavModule } from '@angular/material/sidenav';
import { RouterModule } from '@angular/router';
import { TranslateModule } from '@ngx-translate/core';

@Component({
  selector: 'ctf-administration',
  standalone: true,
  imports: [MatButtonModule, MatIconModule, MatListModule, MatSidenavModule, RouterModule, TranslateModule],
  templateUrl: './administration-page.component.html',
  styleUrl: './administration-page.component.scss'
})
export class AdministrationPageComponent {}
