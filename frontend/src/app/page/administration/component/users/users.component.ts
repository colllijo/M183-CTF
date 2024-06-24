import { AsyncPipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import {
  animate,
  state,
  style,
  transition,
  trigger
} from '@angular/animations';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatTableModule } from '@angular/material/table';
import { AdministrationActions } from '@app/+store/administration/administration.actions';
import { administrationFeature } from '@app/+store/administration/administration.reducers';
import { Role, UserInfo } from '@app/core/api/models';
import { Store } from '@ngrx/store';
import { TranslateModule } from '@ngx-translate/core';
import { Observable } from 'rxjs';

@Component({
  selector: 'ctf-users',
  standalone: true,
  imports: [
    AsyncPipe,
    MatButtonModule,
    MatCardModule,
    MatIconModule,
    MatListModule,
    MatTableModule,
    TranslateModule
  ],
  animations: [
    trigger('detailExpand', [
      state('collapsed,void', style({ height: '0px', minHeight: '0' })),
      state('expanded', style({ height: '*' })),
      transition(
        'expanded <=> collapsed',
        animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')
      )
    ])
  ],
  templateUrl: './users.component.html',
  styleUrl: './users.component.scss'
})
export class UsersComponent implements OnInit {
  public displayedColumns: string[] = ['username', 'email', 'expanded'];
  public expandedElement: UserInfo | null = null;

  public userInfos$: Observable<UserInfo[]>;

  constructor(private store: Store) {
    this.userInfos$ = this.store.select(administrationFeature.selectUsers);
  }

  public ngOnInit(): void {
    this.store.dispatch(AdministrationActions.getUserInfos());
  }

  public removeRole(user: UserInfo, role: Role): void {}
}
