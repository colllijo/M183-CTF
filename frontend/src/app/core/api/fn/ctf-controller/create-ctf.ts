/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { Ctf } from '../../models/ctf';
import { CtfResponse } from '../../models/ctf-response';

export interface CreateCtf$Params {
      body: Ctf
}

export function createCtf(http: HttpClient, rootUrl: string, params: CreateCtf$Params, context?: HttpContext): Observable<StrictHttpResponse<CtfResponse>> {
  const rb = new RequestBuilder(rootUrl, createCtf.PATH, 'post');
  if (params) {
    rb.body(params.body, 'application/json');
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

createCtf.PATH = '/ctf/';
