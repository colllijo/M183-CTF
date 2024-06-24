/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { BaseService } from '../base-service';
import { ApiConfiguration } from '../api-configuration';
import { StrictHttpResponse } from '../strict-http-response';

import { createSolve } from '../fn/solve-controller/create-solve';
import { CreateSolve$Params } from '../fn/solve-controller/create-solve';
import { getAllSolves } from '../fn/solve-controller/get-all-solves';
import { GetAllSolves$Params } from '../fn/solve-controller/get-all-solves';
import { getSolve } from '../fn/solve-controller/get-solve';
import { GetSolve$Params } from '../fn/solve-controller/get-solve';
import { Solve } from '../models/solve';

@Injectable({ providedIn: 'root' })
export class SolveControllerService extends BaseService {
  constructor(config: ApiConfiguration, http: HttpClient) {
    super(config, http);
  }

  /** Path part for operation `getAllSolves()` */
  static readonly GetAllSolvesPath = '/solve';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getAllSolves()` instead.
   *
   * This method doesn't expect any request body.
   */
  getAllSolves$Response(params?: GetAllSolves$Params, context?: HttpContext): Observable<StrictHttpResponse<Array<Solve>>> {
    return getAllSolves(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getAllSolves$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getAllSolves(params?: GetAllSolves$Params, context?: HttpContext): Observable<Array<Solve>> {
    return this.getAllSolves$Response(params, context).pipe(
      map((r: StrictHttpResponse<Array<Solve>>): Array<Solve> => r.body)
    );
  }

  /** Path part for operation `createSolve()` */
  static readonly CreateSolvePath = '/solve';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `createSolve()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  createSolve$Response(params: CreateSolve$Params, context?: HttpContext): Observable<StrictHttpResponse<Solve>> {
    return createSolve(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `createSolve$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  createSolve(params: CreateSolve$Params, context?: HttpContext): Observable<Solve> {
    return this.createSolve$Response(params, context).pipe(
      map((r: StrictHttpResponse<Solve>): Solve => r.body)
    );
  }

  /** Path part for operation `getSolve()` */
  static readonly GetSolvePath = '/solve/{username}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getSolve()` instead.
   *
   * This method doesn't expect any request body.
   */
  getSolve$Response(params?: GetSolve$Params, context?: HttpContext): Observable<StrictHttpResponse<Array<Solve>>> {
    return getSolve(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getSolve$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getSolve(params?: GetSolve$Params, context?: HttpContext): Observable<Array<Solve>> {
    return this.getSolve$Response(params, context).pipe(
      map((r: StrictHttpResponse<Array<Solve>>): Array<Solve> => r.body)
    );
  }

}
