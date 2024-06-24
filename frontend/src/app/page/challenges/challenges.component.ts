import { AfterViewInit, Component } from '@angular/core';
import { TestControllerService } from '@app/core/api/services';
import {MatCardModule} from "@angular/material/card";
import {NgForOf} from "@angular/common";
import {RouterLink} from "@angular/router";
import {MatButton} from "@angular/material/button";
import {userFeature} from "@+store/user/user.reducers";
import {Observable} from "rxjs";
import {Store} from "@ngrx/store";

@Component({
  selector: 'ctf-challenges',
  standalone: true,
  imports: [
    MatCardModule,
    NgForOf,
    RouterLink,
    MatButton
  ],
  templateUrl: './challenges.component.html',
  styleUrl: './challenges.component.scss'
})
export class ChallengesComponent {

  public challenges$: Observable<Challenge[]>;
  constructor(private store: Store) {
    this.challenges$ = this.store.select(userFeature.selectUsers);
  }

}
