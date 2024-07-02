import { AsyncPipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { RouterLink } from '@angular/router';
import { Store } from '@ngrx/store';
import { TranslateModule } from '@ngx-translate/core';
import { Observable } from 'rxjs';

import { challengeFeature } from '@app/+store/challenge/challenge.reducers';
import { Ctf } from '@app/core/api/models';
import { ChallengeActions } from '@app/+store/challenge/challenge.actions';

@Component({
  selector: 'ctf-challenges',
  standalone: true,
  imports: [AsyncPipe, MatButtonModule, MatCardModule, RouterLink, TranslateModule],
  templateUrl: './challenges.component.html',
  styleUrl: './challenges.component.scss'
})
export class ChallengesComponent implements OnInit {
  public challenges$: Observable<Ctf[]>;

  constructor(private store: Store) {
    this.challenges$ = this.store.select(challengeFeature.selectChallenges);
  }

  public ngOnInit(): void {
    this.store.dispatch(ChallengeActions.getAllChallenges());
  }
}
