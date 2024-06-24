/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { RoleRequest } from '../../models/role-request';
import { UserInfo } from '../../models/user-info';

export interface RemoveRoleFromUser$Params {
  username: string;
      body: RoleRequest
}

export function removeRoleFromUser(http: HttpClient, rootUrl: string, params: RemoveRoleFromUser$Params, context?: HttpContext): Observable<StrictHttpResponse<UserInfo>> {
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
      return r as StrictHttpResponse<UserInfo>;
    })
  );
}

removeRoleFromUser.PATH = '/authorisation/roles/{username}';
