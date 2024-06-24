import { Component, OnInit } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { AsyncPipe } from '@angular/common';
import { RouterLink } from '@angular/router';
import { MatButton } from '@angular/material/button';
import { Observable } from 'rxjs';
import { Store } from '@ngrx/store';
import { CtfResponse } from '@app/core/api/models';
import { challengeFeature } from '@app/+store/challenge/challenge.reducers';
import { ChallengeActions } from '@app/+store/challenge/challenge.actions';

@Component({
  selector: 'ctf-challenges',
  standalone: true,
  imports: [AsyncPipe, MatCardModule, RouterLink, MatButton],
  templateUrl: './challenges.component.html',
  styleUrl: './challenges.component.scss'
})
export class ChallengesComponent implements OnInit {
  public challenges$: Observable<CtfResponse[]>;

  constructor(private store: Store) {
    this.challenges$ = this.store.select(challengeFeature.selectChallenges);
  }

  public ngOnInit(): void {
    this.store.dispatch(ChallengeActions.getAllChallenges());
  }
}
