import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';

import { NavbarComponent } from '@component/navbar/navbar.component';
import { ApiConfiguration } from '@core/api/api-configuration';

@Component({
  selector: 'ctf-root',
  standalone: true,
  imports: [NavbarComponent, RouterOutlet],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss',
})
export class AppComponent {
  constructor(private apiConfig: ApiConfiguration) {
    this.apiConfig.rootUrl = 'http://localhost:8080/api';
  }
}
