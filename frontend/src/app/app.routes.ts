import { Routes } from '@angular/router';

import { featureGuard } from '@core/guard/feature.guard';
import { AdministrationComponent } from '@page/administration/administration.component';
import { RolesComponent } from '@page/administration/component/roles/roles.component';
import { UsersComponent } from '@page/administration/component/users/users.component';
import { ChallengesComponent } from '@page/challenges/challenges.component';
import { LeaderboardComponent } from '@page/leaderboard/leaderboard.component';
import { LoginComponent } from '@page/login/login.component';
import { RegistrationComponent } from '@page/registration/registration.component';
import { ChallengeCreationComponent } from "@page/challenge-creation/challenge-creation.component";
import { ChallengeComponent } from './page/challenge/challenge.component';

export const routes: Routes = [
  { path: '', redirectTo: '/challenges', pathMatch: 'full' },
  { path: 'challenges', component: ChallengesComponent },
  { path: 'challenge/:name', component: ChallengeComponent },
  {
    path: 'create-challenge',
    component: ChallengeCreationComponent,
    canActivate: [featureGuard],
    data: { feature: 'challenge-creation' }
  },
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
      { path: 'roles', component: RolesComponent }
    ]
  }
];
