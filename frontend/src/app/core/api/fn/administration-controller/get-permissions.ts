/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { CollectionModelPermissionResponse } from '../../models/collection-model-permission-response';

export interface GetPermissions$Params {
}

export function getPermissions(http: HttpClient, rootUrl: string, params?: GetPermissions$Params, context?: HttpContext): Observable<StrictHttpResponse<CollectionModelPermissionResponse>> {
  const rb = new RequestBuilder(rootUrl, getPermissions.PATH, 'get');
  if (params) {
  }

  return http.request(
    rb.build({ responseType: 'json', accept: 'application/json', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<CollectionModelPermissionResponse>;
    })
  );
}

getPermissions.PATH = '/administration/permissions';
