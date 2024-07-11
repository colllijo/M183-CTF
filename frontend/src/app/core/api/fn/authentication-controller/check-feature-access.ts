/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';


export interface CheckFeatureAccess$Params {
  feature: 'ADMINISTRATION' | 'CHALLENGE_CREATION';
}

export function checkFeatureAccess(http: HttpClient, rootUrl: string, params: CheckFeatureAccess$Params, context?: HttpContext): Observable<StrictHttpResponse<void>> {
  const rb = new RequestBuilder(rootUrl, checkFeatureAccess.PATH, 'post');
  if (params) {
    rb.path('feature', params.feature, {});
  }

  return http.request(
    rb.build({ responseType: 'text', accept: '*/*', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return (r as HttpResponse<any>).clone({ body: undefined }) as StrictHttpResponse<void>;
    })
  );
}

checkFeatureAccess.PATH = '/auth/check/{feature}';
