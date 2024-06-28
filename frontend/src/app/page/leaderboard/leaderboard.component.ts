import { AsyncPipe } from "@angular/common";
import { Component, OnInit } from '@angular/core';
import { MatCardModule } from "@angular/material/card";
import { MatTableModule } from "@angular/material/table";
import { Store } from "@ngrx/store";
import { TranslateModule } from "@ngx-translate/core";
import { Observable } from "rxjs";

import { UserActions } from "@+store/user/user.actions";
import { userFeature } from "@+store/user/user.reducers";
import { User } from "@core/api/models/user";

@Component({
  selector: 'ctf-leaderboard',
  standalone: true,
  imports: [
    AsyncPipe,
    MatCardModule,
    MatTableModule,
    TranslateModule
  ],
  templateUrl: './leaderboard.component.html',
  styleUrl: './leaderboard.component.scss'
})
export class LeaderboardComponent implements OnInit {

  public users$: Observable<User[]>;
  public userTableColumns: string[] = ['username', 'points'];

  constructor(private store: Store) {
    this.users$ = this.store.select(userFeature.selectUsers);
  }

  ngOnInit(): void {
    this.store.dispatch(UserActions.getUsers());
  }

}
