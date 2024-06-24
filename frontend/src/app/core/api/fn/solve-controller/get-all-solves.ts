/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { Solve } from '../../models/solve';

export interface GetAllSolves$Params {
}

export function getAllSolves(http: HttpClient, rootUrl: string, params?: GetAllSolves$Params, context?: HttpContext): Observable<StrictHttpResponse<Array<Solve>>> {
  const rb = new RequestBuilder(rootUrl, getAllSolves.PATH, 'get');
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

getAllSolves.PATH = '/solve';
