import { Injectable } from '@angular/core';
import { Observable, catchError, map, of } from 'rxjs';
import { AuthenticationControllerService } from '../api/services';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  constructor(private authenticationControllerService: AuthenticationControllerService) {}

  public isAuthenticated(): Observable<boolean> {
    return this.authenticationControllerService.isAuthenticated().pipe(
      map(() => true),
      catchError(() => of(false))
    );
  }

  public getClaim(claim: string): unknown {
    const token = sessionStorage.getItem('accessToken');
    if (!token) return null;

    const decodedToken = this.decodeToken(token);
    if (!decodedToken) return null;

    return decodedToken[claim];
  }

  public getUsername(): string {
    return this.getClaim('sub') as string;
  }

  public getRoles(): string[] {
    return this.getClaim('roles') as string[];
  }

  private decodeToken(token: string): { [key: string]: unknown; } | null {
    if (!/^[^.]+\.[^.]+\.[^.]+$/.test(token)) return null;

    return JSON.parse(atob(token.split('.')[1]));
  }
}
