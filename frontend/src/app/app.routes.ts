import { Routes } from '@angular/router';

import { featureGuard } from '@core/guard/feature.guard';

import { AdministrationPageComponent } from '@app/page/administration/administration-page.component';
import { RolesComponent } from '@page/administration/component/roles/roles.component';
import { UsersComponent } from '@page/administration/component/users/users.component';

import { ChallengesPageComponent } from '@app/page/challenges/challenges-page.component';
import { ChallengeCreateComponent } from '@page/challenges/component/create/create.component';
import { ChallengeComponent } from '@page/challenges/component/challenge/challenge.component';
import { ChallengesComponent } from '@page/challenges/component/challenges/challenges.component';

import { LeaderboardComponent } from '@page/leaderboard/leaderboard.component';

import { LoginPageComponent } from '@app/page/login/login-page.component';
import { RegistrationComponent } from '@page/registration/registration.component';

export const routes: Routes = [
  { path: '', redirectTo: '/challenges', pathMatch: 'full' },
  {
    path: 'challenges',
    component: ChallengesPageComponent,
    children: [
      { path: '', component: ChallengesComponent, pathMatch: 'full' },
      { path: ':name', component: ChallengeComponent },
      {
        path: 'create',
        component: ChallengeCreateComponent,
        canActivate: [featureGuard],
        data: { feature: 'challenge-creation' }
      }
    ]
  },
  { path: 'leaderboard', component: LeaderboardComponent },
  { path: 'login', component: LoginPageComponent },
  { path: 'register', component: RegistrationComponent },
  {
    path: 'admin',
    component: AdministrationPageComponent,
    canActivate: [featureGuard],
    data: { feature: 'administration' },
    children: [
      { path: '', redirectTo: 'users', pathMatch: 'full' },
      { path: 'users', component: UsersComponent },
      { path: 'roles', component: RolesComponent }
    ]
  }
];
