/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { CollectionModelRoleResponse } from '../../models/collection-model-role-response';

export interface GetRoles$Params {
}

export function getRoles(http: HttpClient, rootUrl: string, params?: GetRoles$Params, context?: HttpContext): Observable<StrictHttpResponse<CollectionModelRoleResponse>> {
  const rb = new RequestBuilder(rootUrl, getRoles.PATH, 'get');
  if (params) {
  }

  return http.request(
    rb.build({ responseType: 'json', accept: 'application/json', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<CollectionModelRoleResponse>;
    })
  );
}

getRoles.PATH = '/administration/roles';
