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
import { MatTableModule } from '@angular/material/table';
import { AdministrationActions } from '@app/+store/administration/administration.actions';
import { administrationFeature } from '@app/+store/administration/administration.reducers';
import { RoleResponse } from '@app/core/api/models';
import { Store } from '@ngrx/store';
import { TranslateModule } from '@ngx-translate/core';
import { Observable } from 'rxjs';

@Component({
  selector: 'ctf-roles',
  standalone: true,
  imports: [
    AsyncPipe,
    MatButtonModule,
    MatCardModule,
    MatIconModule,
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
  templateUrl: './roles.component.html',
  styleUrl: './roles.component.scss'
})
export class RolesComponent implements OnInit {
  public displayedColumns: string[] = ['name', 'description', 'expanded'];
  public expandedElement: RoleResponse | null = null;

  public roles$: Observable<RoleResponse[]>;

  constructor(private store: Store) {
    this.roles$ = this.store.select(administrationFeature.selectRoles);
  }

  public ngOnInit() {
    this.store.dispatch(AdministrationActions.getRoles());
  }

}
