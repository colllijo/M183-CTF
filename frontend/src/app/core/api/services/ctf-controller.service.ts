/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { BaseService } from '../base-service';
import { ApiConfiguration } from '../api-configuration';
import { StrictHttpResponse } from '../strict-http-response';

import { CollectionModelCtfResponse } from '../models/collection-model-ctf-response';
import { createCtf } from '../fn/ctf-controller/create-ctf';
import { CreateCtf$Params } from '../fn/ctf-controller/create-ctf';
import { CtfResponse } from '../models/ctf-response';
import { getAllCtfs } from '../fn/ctf-controller/get-all-ctfs';
import { GetAllCtfs$Params } from '../fn/ctf-controller/get-all-ctfs';
import { getCtf } from '../fn/ctf-controller/get-ctf';
import { GetCtf$Params } from '../fn/ctf-controller/get-ctf';

@Injectable({ providedIn: 'root' })
export class CtfControllerService extends BaseService {
  constructor(config: ApiConfiguration, http: HttpClient) {
    super(config, http);
  }

  /** Path part for operation `getAllCtfs()` */
  static readonly GetAllCtfsPath = '/ctf/';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getAllCtfs()` instead.
   *
   * This method doesn't expect any request body.
   */
  getAllCtfs$Response(params?: GetAllCtfs$Params, context?: HttpContext): Observable<StrictHttpResponse<CollectionModelCtfResponse>> {
    return getAllCtfs(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getAllCtfs$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getAllCtfs(params?: GetAllCtfs$Params, context?: HttpContext): Observable<CollectionModelCtfResponse> {
    return this.getAllCtfs$Response(params, context).pipe(
      map((r: StrictHttpResponse<CollectionModelCtfResponse>): CollectionModelCtfResponse => r.body)
    );
  }

  /** Path part for operation `createCtf()` */
  static readonly CreateCtfPath = '/ctf/';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `createCtf()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  createCtf$Response(params: CreateCtf$Params, context?: HttpContext): Observable<StrictHttpResponse<CtfResponse>> {
    return createCtf(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `createCtf$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  createCtf(params: CreateCtf$Params, context?: HttpContext): Observable<CtfResponse> {
    return this.createCtf$Response(params, context).pipe(
      map((r: StrictHttpResponse<CtfResponse>): CtfResponse => r.body)
    );
  }

  /** Path part for operation `getCtf()` */
  static readonly GetCtfPath = '/ctf/{name}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getCtf()` instead.
   *
   * This method doesn't expect any request body.
   */
  getCtf$Response(params: GetCtf$Params, context?: HttpContext): Observable<StrictHttpResponse<CtfResponse>> {
    return getCtf(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getCtf$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getCtf(params: GetCtf$Params, context?: HttpContext): Observable<CtfResponse> {
    return this.getCtf$Response(params, context).pipe(
      map((r: StrictHttpResponse<CtfResponse>): CtfResponse => r.body)
    );
  }

}