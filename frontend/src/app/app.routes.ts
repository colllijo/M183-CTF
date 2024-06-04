import { Routes } from '@angular/router';

import { LoginComponent } from '@page/login/login.component';
import { RegistrationComponent } from '@page/registration/registration.component';
import { ChallengesComponent } from './page/challenges/challenges.component';
import { LeaderboardComponent } from './page/leaderboard/leaderboard.component';

export const routes: Routes = [
  { path: '', redirectTo: '/challenges', pathMatch: 'full' },
  { path: 'challenges', component: ChallengesComponent },
  { path: 'leaderboard', component: LeaderboardComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegistrationComponent }
];
