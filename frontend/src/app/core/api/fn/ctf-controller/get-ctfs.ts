/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { CollectionModelCtfResponse } from '../../models/collection-model-ctf-response';

export interface GetCtfs$Params {
}

export function getCtfs(http: HttpClient, rootUrl: string, params?: GetCtfs$Params, context?: HttpContext): Observable<StrictHttpResponse<CollectionModelCtfResponse>> {
  const rb = new RequestBuilder(rootUrl, getCtfs.PATH, 'get');
  if (params) {
  }

  return http.request(
    rb.build({ responseType: 'json', accept: 'application/json', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<CollectionModelCtfResponse>;
    })
  );
}

getCtfs.PATH = '/ctf/';
