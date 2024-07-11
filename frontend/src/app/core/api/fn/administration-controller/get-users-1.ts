/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { CollectionModelUserDetailsResponse } from '../../models/collection-model-user-details-response';

export interface GetUsers1$Params {
}

export function getUsers1(http: HttpClient, rootUrl: string, params?: GetUsers1$Params, context?: HttpContext): Observable<StrictHttpResponse<CollectionModelUserDetailsResponse>> {
  const rb = new RequestBuilder(rootUrl, getUsers1.PATH, 'get');
  if (params) {
  }

  return http.request(
    rb.build({ responseType: 'json', accept: 'application/json', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<CollectionModelUserDetailsResponse>;
    })
  );
}

getUsers1.PATH = '/administration/users';
