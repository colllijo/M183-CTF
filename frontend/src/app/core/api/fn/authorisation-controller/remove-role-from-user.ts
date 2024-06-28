/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { RoleForm } from '../../models/role-form';
import { UserDetails } from '../../models/user-details';

export interface RemoveRoleFromUser$Params {
  username: string;
      body: RoleForm
}

export function removeRoleFromUser(http: HttpClient, rootUrl: string, params: RemoveRoleFromUser$Params, context?: HttpContext): Observable<StrictHttpResponse<UserDetails>> {
  const rb = new RequestBuilder(rootUrl, removeRoleFromUser.PATH, 'delete');
  if (params) {
    rb.path('username', params.username, {});
    rb.body(params.body, 'application/json');
  }

  return http.request(
    rb.build({ responseType: 'json', accept: 'application/json', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<UserDetails>;
    })
  );
}

removeRoleFromUser.PATH = '/authorisation/roles/{username}';
