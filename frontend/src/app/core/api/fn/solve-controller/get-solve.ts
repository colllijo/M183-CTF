/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { Solve } from '../../models/solve';

export interface GetSolve$Params {
}

export function getSolve(http: HttpClient, rootUrl: string, params?: GetSolve$Params, context?: HttpContext): Observable<StrictHttpResponse<Array<Solve>>> {
  const rb = new RequestBuilder(rootUrl, getSolve.PATH, 'get');
  if (params) {
  }

  return http.request(
    rb.build({ responseType: 'blob', accept: '*/*', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<Array<Solve>>;
    })
  );
}

getSolve.PATH = '/solve/{username}';
