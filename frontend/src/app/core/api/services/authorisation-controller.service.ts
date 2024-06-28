/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { BaseService } from '../base-service';
import { ApiConfiguration } from '../api-configuration';
import { StrictHttpResponse } from '../strict-http-response';

import { addRoleToUser } from '../fn/authorisation-controller/add-role-to-user';
import { AddRoleToUser$Params } from '../fn/authorisation-controller/add-role-to-user';
import { CollectionModelPermissionResponse } from '../models/collection-model-permission-response';
import { CollectionModelRoleResponse } from '../models/collection-model-role-response';
import { getPermissions } from '../fn/authorisation-controller/get-permissions';
import { GetPermissions$Params } from '../fn/authorisation-controller/get-permissions';
import { getRoles } from '../fn/authorisation-controller/get-roles';
import { GetRoles$Params } from '../fn/authorisation-controller/get-roles';
import { removeRoleFromUser } from '../fn/authorisation-controller/remove-role-from-user';
import { RemoveRoleFromUser$Params } from '../fn/authorisation-controller/remove-role-from-user';
import { UserDetails } from '../models/user-details';

@Injectable({ providedIn: 'root' })
export class AuthorisationControllerService extends BaseService {
  constructor(config: ApiConfiguration, http: HttpClient) {
    super(config, http);
  }

  /** Path part for operation `getPermissions()` */
  static readonly GetPermissionsPath = '/authorisation/permissions';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getPermissions()` instead.
   *
   * This method doesn't expect any request body.
   */
  getPermissions$Response(params?: GetPermissions$Params, context?: HttpContext): Observable<StrictHttpResponse<CollectionModelPermissionResponse>> {
    return getPermissions(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getPermissions$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getPermissions(params?: GetPermissions$Params, context?: HttpContext): Observable<CollectionModelPermissionResponse> {
    return this.getPermissions$Response(params, context).pipe(
      map((r: StrictHttpResponse<CollectionModelPermissionResponse>): CollectionModelPermissionResponse => r.body)
    );
  }

  /** Path part for operation `getRoles()` */
  static readonly GetRolesPath = '/authorisation/roles';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getRoles()` instead.
   *
   * This method doesn't expect any request body.
   */
  getRoles$Response(params?: GetRoles$Params, context?: HttpContext): Observable<StrictHttpResponse<CollectionModelRoleResponse>> {
    return getRoles(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getRoles$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getRoles(params?: GetRoles$Params, context?: HttpContext): Observable<CollectionModelRoleResponse> {
    return this.getRoles$Response(params, context).pipe(
      map((r: StrictHttpResponse<CollectionModelRoleResponse>): CollectionModelRoleResponse => r.body)
    );
  }

  /** Path part for operation `addRoleToUser()` */
  static readonly AddRoleToUserPath = '/authorisation/roles/{username}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `addRoleToUser()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  addRoleToUser$Response(params: AddRoleToUser$Params, context?: HttpContext): Observable<StrictHttpResponse<UserDetails>> {
    return addRoleToUser(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `addRoleToUser$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  addRoleToUser(params: AddRoleToUser$Params, context?: HttpContext): Observable<UserDetails> {
    return this.addRoleToUser$Response(params, context).pipe(
      map((r: StrictHttpResponse<UserDetails>): UserDetails => r.body)
    );
  }

  /** Path part for operation `removeRoleFromUser()` */
  static readonly RemoveRoleFromUserPath = '/authorisation/roles/{username}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `removeRoleFromUser()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  removeRoleFromUser$Response(params: RemoveRoleFromUser$Params, context?: HttpContext): Observable<StrictHttpResponse<UserDetails>> {
    return removeRoleFromUser(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `removeRoleFromUser$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  removeRoleFromUser(params: RemoveRoleFromUser$Params, context?: HttpContext): Observable<UserDetails> {
    return this.removeRoleFromUser$Response(params, context).pipe(
      map((r: StrictHttpResponse<UserDetails>): UserDetails => r.body)
    );
  }

}
