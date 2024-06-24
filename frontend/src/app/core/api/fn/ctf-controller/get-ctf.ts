/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { CtfResponse } from '../../models/ctf-response';

export interface GetCtf$Params {
  name: string;
}

export function getCtf(http: HttpClient, rootUrl: string, params: GetCtf$Params, context?: HttpContext): Observable<StrictHttpResponse<CtfResponse>> {
  const rb = new RequestBuilder(rootUrl, getCtf.PATH, 'get');
  if (params) {
    rb.path('name', params.name, {});
  }

  return http.request(
    rb.build({ responseType: 'json', accept: 'application/json', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<CtfResponse>;
    })
  );
}

getCtf.PATH = '/ctf/{name}';
