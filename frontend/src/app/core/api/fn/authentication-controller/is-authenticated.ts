/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { Authentication } from '../../models/authentication';

export interface IsAuthenticated$Params {
}

export function isAuthenticated(http: HttpClient, rootUrl: string, params?: IsAuthenticated$Params, context?: HttpContext): Observable<StrictHttpResponse<Authentication>> {
  const rb = new RequestBuilder(rootUrl, isAuthenticated.PATH, 'get');
  if (params) {
  }

  return http.request(
    rb.build({ responseType: 'json', accept: 'application/json', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<Authentication>;
    })
  );
}

isAuthenticated.PATH = '/auth/';
