/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { Authentication } from '../../models/authentication';
import { RefreshForm } from '../../models/refresh-form';

export interface Refresh$Params {
      body: RefreshForm
}

export function refresh(http: HttpClient, rootUrl: string, params: Refresh$Params, context?: HttpContext): Observable<StrictHttpResponse<Authentication>> {
  const rb = new RequestBuilder(rootUrl, refresh.PATH, 'post');
  if (params) {
    rb.body(params.body, 'application/json');
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

refresh.PATH = '/auth/refresh';
