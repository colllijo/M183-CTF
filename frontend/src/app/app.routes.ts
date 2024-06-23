import { Routes } from '@angular/router';

import { AdministrationComponent } from '@page/administration/administration.component';
import { ChallengesComponent } from '@page/challenges/challenges.component';
import { LeaderboardComponent } from '@page/leaderboard/leaderboard.component';
import { LoginComponent } from '@page/login/login.component';
import { RegistrationComponent } from '@page/registration/registration.component';
import { featureGuard } from '@core/guard/feature.guard';
import { UsersComponent } from './page/administration/component/users/users.component';
import { RolesComponent } from './page/administration/component/roles/roles.component';
import { PermissionsComponent } from './page/administration/component/permissions/permissions.component';

export const routes: Routes = [
  { path: '', redirectTo: '/challenges', pathMatch: 'full' },
  { path: 'challenges', component: ChallengesComponent },
  { path: 'leaderboard', component: LeaderboardComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegistrationComponent },
  {
    path: 'admin',
    component: AdministrationComponent,
    canActivate: [featureGuard],
    data: { feature: 'administration' },
    children: [
      { path: '', redirectTo: 'users', pathMatch: 'full' },
      { path: 'users', component: UsersComponent },
      { path: 'roles', component: RolesComponent },
      { path: 'permissions', component: PermissionsComponent },
    ]
  }
];
