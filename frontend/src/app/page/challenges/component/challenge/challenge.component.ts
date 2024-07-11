import { AsyncPipe } from '@angular/common';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatInputModule } from '@angular/material/input';
import { MatTooltipModule } from '@angular/material/tooltip';
import { ActivatedRoute } from '@angular/router';
import { Store } from "@ngrx/store";
import { Observable, Subscription } from 'rxjs';

import { ChallengeActions } from '@app/+store/challenge/challenge.actions';
import { challengeFeature } from '@app/+store/challenge/challenge.reducers';
import { Ctf } from '@app/core/api/models';
import { AuthenticationService } from '@app/core/service/authentication.service';

@Component({
  selector: 'ctf-challenge',
  standalone: true,
  imports: [
    AsyncPipe,
    FormsModule,
    MatButtonModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatTooltipModule,
    ReactiveFormsModule
  ],
  templateUrl: './challenge.component.html',
  styleUrl: './challenge.component.scss'
})
export class ChallengeComponent implements OnInit, OnDestroy {
  public challenge$: Observable<Ctf | null>;
  public authenticated$: Observable<boolean>;

  public subscription: Subscription;

  private challengeName: string;
  public solveForm: FormGroup;

  constructor(
    private authenticationService: AuthenticationService,
    private store: Store,
    private route: ActivatedRoute
  ) {
    this.challenge$ = this.store.select(challengeFeature.selectChallenge);
    this.authenticated$ = this.authenticationService.isAuthenticated();
    this.subscription = new Subscription();

    this.challengeName = this.route.snapshot.params['name'];
    this.solveForm = new FormGroup({
      flag: new FormControl('')
    });
  }

  public ngOnInit(): void {
    this.store.dispatch(ChallengeActions.getChallenge({ name: this.challengeName }));

    this.subscription.add(
      this.authenticationService.isAuthenticated().subscribe((authenticated) => {
        if (!authenticated) this.solveForm.get('flag')?.disable();
        else this.solveForm.get('flag')?.enable();
      })
    )
  }

  public ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  public toFileName(filePath: string): string {
    return filePath.substring(filePath.indexOf('/') + 1);
  }

  public downloadFile(filePath: string): void {
    this.store.dispatch(ChallengeActions.downloadFile({ name: filePath }));
  }

  public submitFlag(): void {
    this.store.dispatch(ChallengeActions.submitFlag({ name: this.challengeName, flag: this.solveForm.value['flag'] }));
  }
}
