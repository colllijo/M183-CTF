/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { RoleRequest } from '../../models/role-request';
import { UserInfo } from '../../models/user-info';

export interface AddRoleToUser$Params {
  username: string;
      body: RoleRequest
}

export function addRoleToUser(http: HttpClient, rootUrl: string, params: AddRoleToUser$Params, context?: HttpContext): Observable<StrictHttpResponse<UserInfo>> {
  const rb = new RequestBuilder(rootUrl, addRoleToUser.PATH, 'put');
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

addRoleToUser.PATH = '/authorisation/roles/{username}';
