/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { BaseService } from '../base-service';
import { ApiConfiguration } from '../api-configuration';
import { StrictHttpResponse } from '../strict-http-response';

import { Authentication } from '../models/authentication';
import { checkFeatureAccess } from '../fn/authentication-controller/check-feature-access';
import { CheckFeatureAccess$Params } from '../fn/authentication-controller/check-feature-access';
import { isAuthenticated } from '../fn/authentication-controller/is-authenticated';
import { IsAuthenticated$Params } from '../fn/authentication-controller/is-authenticated';
import { login } from '../fn/authentication-controller/login';
import { Login$Params } from '../fn/authentication-controller/login';
import { logout } from '../fn/authentication-controller/logout';
import { Logout$Params } from '../fn/authentication-controller/logout';
import { refresh } from '../fn/authentication-controller/refresh';
import { Refresh$Params } from '../fn/authentication-controller/refresh';
import { register } from '../fn/authentication-controller/register';
import { Register$Params } from '../fn/authentication-controller/register';

@Injectable({ providedIn: 'root' })
export class AuthenticationControllerService extends BaseService {
  constructor(config: ApiConfiguration, http: HttpClient) {
    super(config, http);
  }

  /** Path part for operation `isAuthenticated()` */
  static readonly IsAuthenticatedPath = '/auth/';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `isAuthenticated()` instead.
   *
   * This method doesn't expect any request body.
   */
  isAuthenticated$Response(params?: IsAuthenticated$Params, context?: HttpContext): Observable<StrictHttpResponse<Authentication>> {
    return isAuthenticated(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `isAuthenticated$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  isAuthenticated(params?: IsAuthenticated$Params, context?: HttpContext): Observable<Authentication> {
    return this.isAuthenticated$Response(params, context).pipe(
      map((r: StrictHttpResponse<Authentication>): Authentication => r.body)
    );
  }

  /** Path part for operation `checkFeatureAccess()` */
  static readonly CheckFeatureAccessPath = '/auth/check/{feature}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `checkFeatureAccess()` instead.
   *
   * This method doesn't expect any request body.
   */
  checkFeatureAccess$Response(params: CheckFeatureAccess$Params, context?: HttpContext): Observable<StrictHttpResponse<void>> {
    return checkFeatureAccess(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `checkFeatureAccess$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  checkFeatureAccess(params: CheckFeatureAccess$Params, context?: HttpContext): Observable<void> {
    return this.checkFeatureAccess$Response(params, context).pipe(
      map((r: StrictHttpResponse<void>): void => r.body)
    );
  }

  /** Path part for operation `login()` */
  static readonly LoginPath = '/auth/login';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `login()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  login$Response(params: Login$Params, context?: HttpContext): Observable<StrictHttpResponse<Authentication>> {
    return login(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `login$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  login(params: Login$Params, context?: HttpContext): Observable<Authentication> {
    return this.login$Response(params, context).pipe(
      map((r: StrictHttpResponse<Authentication>): Authentication => r.body)
    );
  }

  /** Path part for operation `logout()` */
  static readonly LogoutPath = '/auth/logout';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `logout()` instead.
   *
   * This method doesn't expect any request body.
   */
  logout$Response(params?: Logout$Params, context?: HttpContext): Observable<StrictHttpResponse<void>> {
    return logout(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `logout$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  logout(params?: Logout$Params, context?: HttpContext): Observable<void> {
    return this.logout$Response(params, context).pipe(
      map((r: StrictHttpResponse<void>): void => r.body)
    );
  }

  /** Path part for operation `refresh()` */
  static readonly RefreshPath = '/auth/refresh';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `refresh()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  refresh$Response(params: Refresh$Params, context?: HttpContext): Observable<StrictHttpResponse<Authentication>> {
    return refresh(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `refresh$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  refresh(params: Refresh$Params, context?: HttpContext): Observable<Authentication> {
    return this.refresh$Response(params, context).pipe(
      map((r: StrictHttpResponse<Authentication>): Authentication => r.body)
    );
  }

  /** Path part for operation `register()` */
  static readonly RegisterPath = '/auth/register';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `register()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  register$Response(params: Register$Params, context?: HttpContext): Observable<StrictHttpResponse<Authentication>> {
    return register(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `register$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  register(params: Register$Params, context?: HttpContext): Observable<Authentication> {
    return this.register$Response(params, context).pipe(
      map((r: StrictHttpResponse<Authentication>): Authentication => r.body)
    );
  }

}
