import {Component, OnInit} from '@angular/core';
import {Observable} from "rxjs";
import {User} from "@core/api/models/user";
import {Store} from "@ngrx/store";
import {userFeature} from "@+store/user/user.reducers";
import {AsyncPipe} from "@angular/common";
import {UserActions} from "@+store/user/user.actions";
import {TranslateModule} from "@ngx-translate/core";
import {MatTableModule} from "@angular/material/table";

@Component({
  selector: 'ctf-leaderboard',
  standalone: true,
  imports: [
    AsyncPipe,
    TranslateModule,
    MatTableModule
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
