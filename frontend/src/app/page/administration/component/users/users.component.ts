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
import { MatDialog } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatTableModule } from '@angular/material/table';
import { AdministrationActions } from '@app/+store/administration/administration.actions';
import { administrationFeature } from '@app/+store/administration/administration.reducers';
import { RoleResponse, UserInfo } from '@app/core/api/models';
import { Store } from '@ngrx/store';
import { TranslateModule } from '@ngx-translate/core';
import { Observable } from 'rxjs';

import { AddRoleDialogComponent } from '../add-role-dialog/add-role-dialog.component';
import { AuthenticationService } from '@app/core/service/authentication.service';

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
      state(
        'collapsed,void',
        style({ height: '0px', padding: '0', minHeight: '0' })
      ),
      state('expanded', style({ height: '*', padding: '8px' })),
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
  public roles$: Observable<RoleResponse[]>;

  public activeUser: string;

  constructor(
    private authenticationService: AuthenticationService,
    private dialog: MatDialog,
    private store: Store
  ) {
    this.userInfos$ = this.store.select(administrationFeature.selectUsers);
    this.roles$ = this.store.select(administrationFeature.selectRoles);

    this.activeUser = this.authenticationService.getUsername();
  }

  public ngOnInit(): void {
    this.store.dispatch(AdministrationActions.getUserInfos());
    this.store.dispatch(AdministrationActions.getRoles());
  }

  public addRole(user: UserInfo, roles: RoleResponse[]): void {
    const dialogRef = this.dialog.open(AddRoleDialogComponent, {
      data: {
        roles: roles
          .filter((r) => !user.roles || !user.roles.map((ur) => ur.name).includes(r.name))
          .map((r) => r.name),
      }
    });

    dialogRef.afterClosed().subscribe((role: string) => {
      if (role) {
        this.store.dispatch(AdministrationActions.addRole({ user, role }));
      }
    });
  }

  public removeRole(user: UserInfo, role: RoleResponse): void {
    this.store.dispatch(AdministrationActions.removeRole({ user, role }));
  }
}
