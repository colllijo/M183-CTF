import { Component, OnInit } from '@angular/core';
import { RouterOutlet } from '@angular/router';

import { NavbarComponent } from '@component/navbar/navbar.component';
import { ApiConfiguration } from '@core/api/api-configuration';
import { AuthenticationService } from './core/service/authentication.service';
import { Store } from '@ngrx/store';
import { AuthenticationActions } from './+store/authentication/authentication.actions';
import { take } from 'rxjs';

@Component({
  selector: 'ctf-root',
  standalone: true,
  imports: [NavbarComponent, RouterOutlet],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent implements OnInit {
  constructor(
    private apiConfig: ApiConfiguration,
    private authenticationService: AuthenticationService,
    private store: Store
  ) {
    this.apiConfig.rootUrl = 'http://localhost:8080/api';
  }

  public ngOnInit(): void {
    this.authenticationService.isAuthenticated().pipe(take(1)).subscribe((authenticated: boolean) => {
      if (authenticated) {
        this.store.dispatch(
          AuthenticationActions.setAuthentication({
            username: this.authenticationService.getUsername(),
            roles: this.authenticationService.getRoles()
          })
        );
      }
    });
  }
}
