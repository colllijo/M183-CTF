/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { CtfSolveForm } from '../../models/ctf-solve-form';
import { Solve } from '../../models/solve';

export interface SolveCtf$Params {
  name: string;
      body: CtfSolveForm
}

export function solveCtf(http: HttpClient, rootUrl: string, params: SolveCtf$Params, context?: HttpContext): Observable<StrictHttpResponse<Solve>> {
  const rb = new RequestBuilder(rootUrl, solveCtf.PATH, 'post');
  if (params) {
    rb.path('name', params.name, {});
    rb.body(params.body, 'application/json');
  }

  return http.request(
    rb.build({ responseType: 'json', accept: 'application/json', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<Solve>;
    })
  );
}

solveCtf.PATH = '/ctf/{name}';
