import { AsyncPipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatInputModule } from '@angular/material/input';
import { MatTooltipModule } from '@angular/material/tooltip';
import { ActivatedRoute } from '@angular/router';
import { ChallengeActions } from '@app/+store/challenge/challenge.actions';
import { challengeFeature } from '@app/+store/challenge/challenge.reducers';
import { CtfResponse } from '@app/core/api/models';
import { AuthenticationService } from '@app/core/service/authentication.service';
import { Store } from "@ngrx/store";
import { Observable } from 'rxjs';

@Component({
  selector: 'ctf-challenge',
  standalone: true,
  imports: [
    AsyncPipe,
    MatButtonModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatTooltipModule
  ],
  templateUrl: './challenge.component.html',
  styleUrl: './challenge.component.scss'
})
export class ChallengeComponent implements OnInit {
  public challenge$: Observable<CtfResponse | null>;
  public authenticated$: Observable<boolean>;

  private challengeName: string;

  constructor(
    private authenticationService: AuthenticationService,
    private store: Store,
    private route: ActivatedRoute
  ) {
    this.challenge$ = this.store.select(challengeFeature.selectChallenge);
    this.authenticated$ = this.authenticationService.isAuthenticated();

    this.challengeName = this.route.snapshot.params['name'];
  }

  public ngOnInit(): void {
    this.store.dispatch(ChallengeActions.getChallenge({ name: this.challengeName }));
  }
}