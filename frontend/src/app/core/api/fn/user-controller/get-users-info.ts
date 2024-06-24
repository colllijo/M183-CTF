/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { CollectionModelUserInfoResponse } from '../../models/collection-model-user-info-response';

export interface GetUsersInfo$Params {
}

export function getUsersInfo(http: HttpClient, rootUrl: string, params?: GetUsersInfo$Params, context?: HttpContext): Observable<StrictHttpResponse<CollectionModelUserInfoResponse>> {
  const rb = new RequestBuilder(rootUrl, getUsersInfo.PATH, 'get');
  if (params) {
  }

  return http.request(
    rb.build({ responseType: 'json', accept: 'application/json', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<CollectionModelUserInfoResponse>;
    })
  );
}

getUsersInfo.PATH = '/users/info';
