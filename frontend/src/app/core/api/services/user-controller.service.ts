/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { BaseService } from '../base-service';
import { ApiConfiguration } from '../api-configuration';
import { StrictHttpResponse } from '../strict-http-response';

import { CollectionModelUserInfoResponse } from '../models/collection-model-user-info-response';
import { CollectionModelUserResponse } from '../models/collection-model-user-response';
import { getUsers } from '../fn/user-controller/get-users';
import { GetUsers$Params } from '../fn/user-controller/get-users';
import { getUsersInfo } from '../fn/user-controller/get-users-info';
import { GetUsersInfo$Params } from '../fn/user-controller/get-users-info';

@Injectable({ providedIn: 'root' })
export class UserControllerService extends BaseService {
  constructor(config: ApiConfiguration, http: HttpClient) {
    super(config, http);
  }

  /** Path part for operation `getUsers()` */
  static readonly GetUsersPath = '/users/';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getUsers()` instead.
   *
   * This method doesn't expect any request body.
   */
  getUsers$Response(params?: GetUsers$Params, context?: HttpContext): Observable<StrictHttpResponse<CollectionModelUserResponse>> {
    return getUsers(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getUsers$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getUsers(params?: GetUsers$Params, context?: HttpContext): Observable<CollectionModelUserResponse> {
    return this.getUsers$Response(params, context).pipe(
      map((r: StrictHttpResponse<CollectionModelUserResponse>): CollectionModelUserResponse => r.body)
    );
  }

  /** Path part for operation `getUsersInfo()` */
  static readonly GetUsersInfoPath = '/users/info';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getUsersInfo()` instead.
   *
   * This method doesn't expect any request body.
   */
  getUsersInfo$Response(params?: GetUsersInfo$Params, context?: HttpContext): Observable<StrictHttpResponse<CollectionModelUserInfoResponse>> {
    return getUsersInfo(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getUsersInfo$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getUsersInfo(params?: GetUsersInfo$Params, context?: HttpContext): Observable<CollectionModelUserInfoResponse> {
    return this.getUsersInfo$Response(params, context).pipe(
      map((r: StrictHttpResponse<CollectionModelUserInfoResponse>): CollectionModelUserInfoResponse => r.body)
    );
  }

}
